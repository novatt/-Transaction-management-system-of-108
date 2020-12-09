<template>
  <li @click="nodeClick" :style="{width:width}" :class="selected?'node select':'node'">
    <span :class="getArrowClass()"></span>
    <img v-if="headUrl" :src="headUrl" style="width:20px;height:20px;" />
    <span class="folder-box" v-else>
      <i v-if="icon" :class="icon"></i>
      <i v-else-if="hasChild" class="folder"></i>
    </span>
    <span v-if="!isShowCreated" :title="createName" class="text">{{createName}}</span>
    <input
      v-else
      type="text"
      ref="remakeInput"
      v-model="createName"
      @focus="getFocus"
      @blur="changeFileName"
      @keyup.enter="$event.target.blur"
    />
  </li>
</template>
<script>
export default {
  name: "tree-node",
  props: {
    name: {
      type: [String],
    },
    label: {
      type: [String],
    },
    icon: {
      type: [String],
      default: "folder",
    },
    open: {
      type: [Boolean],
      default: true,
    },
    selected: {
      type: [Boolean],
      default: false,
    },
    hasChild: {
      type: [Boolean],
    },
    isRoot: {
      type: [Boolean],
    },
    width: {
      type: String,
      default: "213px",
    },
    isCreated: {
      type: [Boolean],
    },
    headUrl: {
       type: String 
    },
  },
  data() {
    return {
      isOpen: true,
      nodeItem: {},
      //创建文件夹名称
      createName: "",
      //是否显示创建文件夹输入框
      isShowCreated: false,
    };
  },
  computed: {
    nodeClass() {
      if (this.selected) {
        return "node select";
      } else {
        return "node";
      }
    },
  },
  mounted() {
    this.isOpen = this.open;
    this.isShowCreated = this.isCreated;
    this.createName = this.label;
    if (this.isShowCreated) {
      setTimeout(() => {
        this.$refs.remakeInput.focus();
      }, 100);
    }
  },
  watch: {
    isOpen() {
      this.openCloseNode();
    },
    isCreated: {
      handler(val) {
        this.isShowCreated = false;
      },
    },
    createName() {
      //监控重命名的变化
      var t = /[*|:"<>?？\\\/&]/;
      //将createName转成字符串 不然下面报错
      this.createName = this.createName + "";
      //去除所有空格
      this.createName = this.createName.replace(/\s/g, "");
      this.createName = this.createName.replace(t, "");
    },
  },
  methods: {
    nodeClick(e) {
      if (this.isCreated) return;
      if (this.hasChild) {
        this.isOpen = !this.isOpen;
      }

      var item = this.$parent.onNodeClick(this.name, this.label);

      this.$parent.$emit("select", item);

      this.$nextTick(() => {
        this.selected = true;
      });
    },
    getArrowClass() {
      if (this.hasChild) {
        return this.isOpen ? "arrow" : "arrow right";
      } else {
        return "leaf";
      }
    },
    openCloseNode() {
      var n = this.$el.nextSibling;
      if (n && n.nodeName == "UL") {
        if (n.style.display == "") {
          n.style.display = "none";
        } else {
          n.style.display = "";
        }
      }
    },
    getFocus(e) {
      e.target.select();
    },
    changeFileName(e) {
      //去除所有空格
      this.createName = this.createName.replace(/\s/g, "");
      //最后一个字符不能是. eg：.sada(成功)   sada.(报错)
      if (this.createName[this.createName.length - 1] === ".") {
        this.createName = this.createName.substring(
          0,
          this.createName.length - 1
        );
      }
      if (!this.createName.trim()) {
        showToast("输入内容为空，请重新输入")
        this.createName = "新建文件夹";
            setTimeout(() => {
              this.$refs.remakeInput.focus();
              e.target.select();
            }, 100);
        return;
      }
      var item = this.$parent.getNodeById(this.name);
      item.label = this.createName
      this.$parent.$emit("createFileOK", item, this, e);
    },
  },
};
</script>
 
 
