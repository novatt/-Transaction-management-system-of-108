<template>
  <div class="cloud_main_r">
    <div class="main_r_tool flex_items_center flex_between fileHandleTools">
      <div class="clearfix main_tool">
        <span title="上传" class="main_tool_left" @click="uploadFamilyFile">
          <i class="icons i_upload"></i>上传
        </span>
        <a href="javascript:;" title="下载" class="main_tool_a">
          <span v-show="fileClickList.length > 0" class="main_tool_left" @click="download">
            <i class="icons i_download"></i>下载
          </span>
        </a>
        <a href="javascript:;" title="下载" class="main_tool_a">
          <span v-show="fileClickList.length <= 0" class="main_tool_gray">
            <i class="icons i_download"></i>下载
          </span>
        </a>
        <a href="javascript:;" title="新建文件夹" class="main_tool_a">
          <span v-show="!isAllFile && !remakeNameID" class="main_tool_left" @click="createFile()">
            <i class="icons i_folder"></i>新建文件夹
          </span>
        </a>
        <a href="javascript:;" title="新建文件夹" class="main_tool_a">
          <span v-show="isAllFile || remakeNameID" class="main_tool_left main_tool_gray">
            <i class="icons i_folder"></i>新建文件夹
          </span>
        </a>
        <span title="更多" class="main_tool_gray dropmenu" v-if="!fileClickList.length">
          <div class="default">
            <i class="icons i_point1"></i>
            <span style="margin-left:-4px">更多</span>
            <i class="icons i_down1" style="margin-right: 8px;"></i>
          </div>
        </span>
        <a
          href="javascript:;"
          style="background: #d2d2d2;"
          @click="moreClick"
          title="更多"
          class="main_tool_a"
          v-else
        >
          <span class="main_tool_left">
            <drop-menu
              :items="menuMore"
              text="更多"
              icon="icons i_point"
              type="menu"
              @change="menuMoreChange"
              :clickdroplist="clickDropList"
            ></drop-menu>
          </span>
        </a>
        <span title="锁定保险箱" class="main_tool_left c-pointer" @click="lockSafeBox">
          <i class="icons i-lock-safe-box"></i>锁定保险箱
        </span>
      </div>
    </div>
    <div class="main_all_file">
      <div class="main_all clearfix main_all_list" ref="main_all_steps">
        <i
          @click="goBack()"
          title="后退"
          ref="back"
          class="icons i_left"
          v-if="PathStack.length != 1"
        ></i>
        <i @click="goBack()" title="后退" ref="back" class="icons i_righted i_righted_to_lft" v-else></i>

        <i
          @click="goAhead()"
          title="前进"
          ref="back"
          class="icons i_left i_left_to_right gray c-default"
          v-if="ParentStack.length < ParentStack_copy.length"
        ></i>
        <i @click="goAhead()" title="前进" ref="ahead" class="icons i_righted gray c-default" v-else></i>

        <i class="icons i_line"></i>

        <span
          v-for="(item,idx) in PathStack"
          :key="idx"
          @click="nav(item,idx)"
          ref="step"
          class="steps"
        >
          <p class="steps_name">{{item.name}}</p>
          <p class="steps_arrow">></p>
        </span>
        <span class="i_sort_box">
          <a href="javascript:;" title="排序" class="main_tool_a main_tool_a_list">
            <drop-menu
              :items="menuSort"
              text="排序"
              icon="icons i_sort i_sortNew"
              type="menu"
              dropTop="37px"
              :show_i_tran="false"
              :clickdroplist="clickDropList"
              @change="menuSortChange"
            ></drop-menu>
          </a>
        </span>

        <span class="i_preview_box" v-if="isList" @click="listStyleHandle()">
          <a href="javascript:;" title="图标" class="main_tool_a main_tool_a_list">
            <i class="icons i_preview_to_list"></i>
            <span class="i_preview_word">图标</span>
          </a>
        </span>

        <span class="i_preview_box" v-if="!isList" @click="listStyleHandle()">
          <a href="javascript:;" title="列表" class="main_tool_a main_tool_a_list">
            <i class="icons i_preview"></i>
            <span class="i_preview_word">列表</span>
          </a>
        </span>
      </div>
    </div>
    <!--列表模式-->
    <div v-if="isList" class="p_relative over-f" ref="tableList">
      <!--没有文件-->
      <div class="no_file_tip" v-if="noFile">
        <img :src="require('../../../images/noFileImg/safeBox_no.png')" class="no_file_tip_img" />
        <p class="cloud_net_file no_file_tip_word">没有文件哦</p>
        <button class="uploadBtn" @click="uploadFamilyFile()" style="margin-left: -120px">上传文件</button>
      </div>
      <!--有文件显示-->
      <div
        v-else
        class="main_table over-x p_relative"
        @scroll="tableScroll"
        :style="{height:'calc(100vh - 95px)',paddingTop:'30px',paddingBottom:'0px',boxSizing: 'border-box'}"
        v-scroll-load
      >
        <!--这是表头（名字、修改时间、大小）-->
        <div
          class="main_table_head clearfix main_table_head_position"
          v-if="fileList.length"
          :style="tableTop"
        >
          <span @click="allClick" class="main_table_choose_all">
            <i
              class="icons i_selected_all i_select"
              v-bind:class="{i_select:fileClickList.length <= 0,i_selected:fileClickList.length>0 && fileClickList.length ==fileList.length}"
            ></i>
          </span>
          <div
            class="main_table_name bgc-light-white bgc-choose-gray"
            @click="changeSort(1)"
            style="padding-left:5px"
            :style="sortData.name"
          >
            <span>名称</span>
            <i v-if="orderType===1 && orderDesc===1" class="table_icons iconarrowDown"></i>
            <i v-if="orderType===1 && orderDesc===0" class="table_icons iconarrowUp"></i>
            <span v-if="fileClickList.length > 0">
              &nbsp; | &nbsp; 选中
              <label style="color: #00C0FF;">{{fileClickList.length}}</label>个项目
            </span>
            <i class="table_icons i_table-line" v-scroll-div></i>
          </div>
          <div
            class="main_table_time bgc-light-white bgc-choose-gray"
            @click.stop="changeSort(0)"
            :style="sortData.time"
          >
            <i class="icons i_table-line" v-scroll-div></i>
            修改时间
            <i v-if="orderType === 0 && orderDesc===0" class="icons iconarrowUp"></i>
            <i v-if="orderType === 0 && orderDesc===1" class="icons iconarrowDown"></i>
          </div>
          <div
            class="main_table_size bgc-light-white bgc-choose-gray"
            @click.stop="changeSort(3)"
            :style="sortData.size"
          >
            <i class="icons i_table-line" v-scroll-div></i>
            大小
            <i v-if="orderType ===3 && orderDesc===0" class="icons iconarrowUp"></i>
            <i v-if="orderType ===3 && orderDesc===1" class="icons iconarrowDown"></i>
          </div>
        </div>
        <!-- <albumContents></albumContents> -->

        <ul class="main_table_head_ul" :class="fileColorType|changeColor">
          <li
            @contextmenu="rightClick($event,item)"
            ref="fileClick_table"
            class="clearfix"
            :class="fileClickList.indexOf(item) != -1 ? fileColorType + 'Color': ''"
            v-for="(item,index) in fileList"
            :key="index"
            @click="fileClick(item,index,$event)"
          >
            <span @click="iconsClick($event,item,index)" class="main_table_choose">
              <i
                ref="fileClick_select"
                :class="{'icons':true, 'i-select':fileClickList.indexOf(item) == -1,'i-selected':fileClickList.indexOf(item) != -1}"
              ></i>
            </span>
            <div class="main_table_name" :style="sortData.name">
              <img style="cursor:default;" class="main_disk_icon" v-bind:src="item|icon(!isList)" />
              <p class="every_file_name">
                <input
                  class="remakeInput"
                  type="text"
                  maxlength="30"
                  ref="remakeInput"
                  v-model="renameOrCreateName"
                  v-if="remakeNameID === item.id"
                  @focus="getFocus"
                  @blur="changeFileName"
                  @keyup.enter="$event.target.blur"
                />
                <a
                  href="javascript:;"
                  v-else
                  :title="item.name"
                  class="item_name_a"
                  v-html="item.name"
                ></a>
              </p>
            </div>
            <div class="main_table_time" :style="sortData.time">{{item.modifyTime}}</div>
            <div
              :style="sortData.size"
              class="main_table_size"
              v-if="item.type === 'file'"
            >{{item.file.fileSize | size}}</div>
          </li>
        </ul>
      </div>
    </div>

    <!--缩略图模式-->
    <div
      class="main_photo"
      ref="tableList"
      v-if="!isList"
      v-scroll-load
      style="height: calc(100vh - 80px)"
    >
      <p
        class="photo_selected_tip"
        :style="isList?'':'top:45px'"
        :class="{photo_selected_byTip:fileClickList.length > 0}"
      >
        <span v-if="fileClickList.length > 0">
          选中
          <label style="color: #00C0FF;">{{fileClickList.length}}个项目</label>
        </span>
      </p>
      <!--没有文件-->
      <div class="no_file_tip" v-if="noFile">
        <img :src="require('../../../images/noFileImg/safeBox_no.png')" v-if="noFile" class="no_file_tip_img" />
        <p class="cloud_net_file no_file_tip_word" v-if="noFile">没有文件哦</p>
        <button class="uploadBtn" @click="uploadFamilyFile()" style="margin-left: -120px">上传文件</button>
      </div>
      <ul
        class="clearfix main_photo_ul cleanCheck p_relative"
        v-selection-box
        @click="cleanCheck"
        v-else
        :class="fileColorType|changeColor"
      >
        <li
          @contextmenu="rightClick($event,item)"
          v-for="(item,index) in fileList"
          :key="index"
          ref="fileClick_table"
          class="main_photo_li"
          v-bind:class="fileClickList.indexOf(item) != -1 ? fileColorType + 'Color': ''"
          @click="fileClick(item,index,$event)"
        >
          <div class="li_photo_info_box">
            <img v-bind:src="item.file.thumbnailURL" v-if="isShowImgRovideo(item)"  class="main_phooto_img" @error="e => e.target.src = require('../../../images/novideo.png')"/>
            <img v-bind:src="item|icon(!isList)" v-else class="main_phooto_num" />
            <i
              class="i_start_video"
              v-if="showVideoIcon && 'mp4,avi,mpg,flv,wmv,mov,mkv,rmvb'.indexOf(item.file.ext) >= 0 && item.filethumbnailURL"
            ></i>
          </div>
          <p class="main_photo_p txtEllipsis_tow">
            <input
              class="remakeInput"
              type="text"
              maxlength="30"
              ref="remakeInput"
              v-if="remakeNameID === item.id"
              v-model="renameOrCreateName"
              @focus="getFocus"
              @blur="changeFileName"
              @keyup.enter="$event.target.blur"
            />
            <a href="javascript:;" v-else :title="item.name" class="item_name_a" v-html="item.name"></a>
          </p>
        </li>
        <div class="main_photo_li main_photo_li_upload" @click="uploadFamilyFile" v-if="fileList.length">
          <div class="main_photo_info c-pointer">
            <div class="li_photo_info_box">
               <img :src="require('../../../images/svg/upload.svg')" class="main_phooto_num  c-pointer"/>
            </div>
            <p class="main_photo_p">
              <a href="javascript:;" title="上传文件" class="item_name_a">上传文件</a>
            </p>
          </div>
        </div>
      </ul>
    </div>

    <!--对话框-->
    <div id="movedialog" v-if="dialog=='move'">
      <moveModule :safeRootId="safeRootId" :type="moveType" :rootid="rootId"></moveModule>
    </div>

    <!-- 详细信息 -->
    <div
      v-if="detailModal"
      class="list_box fileinfodetail"
      style="top: 200px; left: 270px; z-index: 9001; position: absolute; display: block;width: 400px;"
    >
      <div class="menu_head" style="border-bottom: solid 1px #d2d2d2;">
        <h4 class="menu_head_h">详细信息</h4>
        <i @click="detailModal = false" class="icons i_icon_close"></i>
      </div>
      <div v-if="fileInfo.type === 'file'" class="menu_sure" style="height: 100%">
        <p class="menu_sure_p">
          <span class="fileDetailInfoName">文件:</span>
          <span>{{fileInfo.name}}</span>
        </p>
        <p class="menu_sure_p">
          <span class="fileDetailInfoName">类型:</span>
          <span>{{fileInfo.file.ext}}</span>
        </p>
        <p class="menu_sure_p">
          <span class="fileDetailInfoName">上传时间:</span>
          <span>{{fileInfo.createTime}}</span>
        </p>
        <p class="menu_sure_p" style="padding-bottom: 10px">
          <span class="fileDetailInfoName">大小:</span>
          <span>{{fileInfo.file.rawSize | size}}</span>
        </p>
        <span
          v-for="(item,index) in ParentStack"
          :key="index"
          style="display: inline-block;overflow: hidden"
        >
          <p v-if="index === 0" class="steps_name fileDetailInfoName">位置:</p>
          <p class="steps_name">{{item.name}}</p>
          <p class="steps_arrow">></p>
        </span>
      </div>
      <div v-if="fileInfo.type === 'directory'" class="menu_sure" style="height: 100%">
        <p class="menu_sure_p">
          <span class="fileDetailInfoName">文件:</span>
          <span>{{fileInfo.name}}</span>
        </p>
        <p class="menu_sure_p">
          <span class="fileDetailInfoName">类型:</span>
          <span>文件夹</span>
        </p>
        <p class="menu_sure_p">
          <span class="fileDetailInfoName">创建时间:</span>
          <span>{{fileInfo.createTime}}</span>
        </p>
      </div>
    </div>

    <!--文件分享-->
    <!-- <dialogbox
      title="分享链接"
      v-model="shareFileVisible"
      showfoot="1"
      width="450"
      height="320"  
      ok_text="关闭"
      canceltext
    >
      <div style="overflow: hidden">
        <div v-for="(item,index) of shareFileInfoArr.slice(0,1)" :key="index" style="float: left;margin: 0 5px">
          <img
            class="menu_main_img"
            v-bind:src="item|icon(!isList)"
            style="margin:2.5px 2px 2.5px 10px"
          />
          <p
            style="max-width: 270px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap"
            v-if="shareFileInfoArr.length>=2"
          >{{item.name}}</p>
          <p
            style="max-width: 350px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap"
            v-else
          >{{item.name}}</p>
        </div>
        <p
          v-if="shareFileInfoArr.length>=2"
          style="width: 80px;float: left;"
        >等{{shareFileInfoArr.length}}个文件</p>
    </div>-->

    <!--	<img class="menu_main_img" src="shareFileInfoArr[0]|icon(!isList)" >
                  <p class="menu_main_v">{{shareFileInfoArr[0].name}}</p>
    <p v-if="shareFileInfoArr.length>=2"> 等{{shareFileInfoArr.length}}个文件</p>-->

    <!-- <p class="menu_main_safe">加密分享，只有分享好友才能看见，更加隐私安全！</p>
      <div class="menu_main_code clearfix" style="margin: 0 15px">
        <span class="menu_code_l">链接地址</span>
        <input class="menu_code_r" disabled :value="shareFileInfo.linkurl" />
      </div>
      <div class="menu_main_code clearfix" style="margin: 0 15px">
        <span class="menu_code_l">提取码</span>
        <input class="menu_code_input" disabled :value="shareFileInfo.passwd" />
      </div>

      <div class="menu_main_footer clearfix">
        <a
          href="javascript:"
          @click="copyLink()"
          class="menu_main_get"
          style="margin: 0 15px"
        >点击复制链接和提取码</a>
        <a
          href="javascript:"
          @click="sendShareLink()"
          class="menu_main_share"
          style="margin: 0 19.5px"
        >发送邮件分享</a>
      </div>
      <div v-show="isShareCopy" style="color:red;margin-left: 15px">复制成功</div>
      <p class="menu_main_banner" style="margin-left: 15px">禁止分享反动、色情低俗。侵权内容，如发现则封号处理</p>
    </dialogbox>-->
    <!-- 图片在线预览 -->
    <viewerBox ref="viewer"></viewerBox>
  </div>
</template>

<script>
import moveModule from "./component/moveModule.vue";
import { safeBox } from "../../api/safeBox.js"

export default {
  name: "safeBox",
  props: {
    colorType: {
      default: "Default",
    },
  },
  data() {
    return {
      //保险箱根目录id
      safeRootId: "00019700101000000040",
      //刷新session定时器
      sessionDateId: null,
      //重命名显示id
      remakeNameID: "",
      fileColorType: "",
      //排序固定位置top值
      tableTop: { top: 0 },
      //标记是否有返回的文件
      noFile: false,
      clickDropList: false,
      //是否显示视频缩略图的播放icon
      showVideoIcon: true,
      //记录从根路径进来的文件夹
      chooseFileFromAll: [],
      //是否含有特殊字符的标志提示语
      isFalseNameTip: "",
      //是否含有特殊字符的标志
      isFalseName: false,
      lastClick: {},
      //当前页名称，用于单页导航
      view: "list",
      //当前显示的对话框
      dialog: null,
      //数据总条数
      totalSize: 0,
      //当前文件夹
      currentId: null,
      fileList: [],
      pageIndex: 1,
      //排序字段 0:按上传时间 1:按名称  3:按大小
      orderType: window["orderType"] || 0,
      // 0 正排序 1 倒排序
      orderDesc: window["orderDesc"] || 1,
      //标记切换缩略图模式和普通模式的值
      isList: true,
      fileInfo: {
        directory: {
          dirFlag: 0,
        },
        type: "",
      }, //文件夹信息
      createDirectoryParms: {
        //新建文件夹参数
        name: "新建文件夹",
        dirType: "1",
      },
      removeDirectoryParms: {
        //删除文件夹参数
        directoryIds: "",
        fileIds: "",
        dirType: "1",
        opr: "2",
      },
      menuMore: [
        { text: "移动到", value: "move", arrow: true },
        { text: "删除", value: "del" },
        { text: "重命名", value: "rename" },
        { text: "移出保险箱", value: "moveOut" },
      ],
      menuSort: [
        { text: "文件名", key: "byName", type: 1 },
        { text: "大小", key: "bySize", type: 3 },
        { text: "修改时间", key: "byTime", type: 0 },
        { line: true },
        { text: "升序", key: "asc", desc: 0 },
        { text: "降序", key: "desc", desc: 1 },
      ],
      renameParms: {
        //重命名参数
        name: "",
        directoryId: "",
        dirType: "1",
      },
      //统计父级文件夹
      ParentStack: [{
        name: "保险箱",
        type: "safeBox",
        id: "00019700101000000040",
      }],
      //统计副本
      ParentStack_copy: [],
      //多选选中数据
      fileClickList: [],
      //显示新建文件夹按钮
      isAllFile: false,
      //重命名或者新建文件夹
      renameOrCreateShow: "重命名",
      //重命名或者新建文件夹输入框名称展示
      renameOrCreateName: "新建文件夹",
      //确认删除弹框
      isRemoveModal: false,
      //详细信息弹框
      detailModal: false,
      //传输列表类型
      transferType: "",
      shareFileVisible: false,
      isShareCopy: false,
      shareFileInfo: {},
      //存放选中信息
      shareFileInfoArr: [],
      syncDialogVisible: false,
      //是否点击的更多
      ismenuMore: 0,
      shiftBetweenLi: [],
      sortData: {
        name: "",
        time: "",
        size: "",
      },
      //判断移动类型 moveOut：移出保险箱  moveTo：移动到
      moveType: "moveTo",
      //判断是否点击锁定保险箱 false: 没有 true：有
      isClickLockBtn: false,
      //判断是重命名还是新建文件夹
      inputType: "",
      //用于判断滚动请求是否结束 结束了才可再请求
      isRequestFinish: true
    };
  },
  // 当组件去传输列表时将当前所在路径传过去
  beforeRouteLeave(to, from, next) {
    if (to.name === "transferList") {
      to.query["pathData"] = this.ParentStack;
      to.query["preCatalog"] = "safeBox";
    }
    next();
  },
  mounted: async function () {
    this.$parent.leaveSafeBoxTime = new Date().getTime();
    if (
      window.localStorage.getItem("orderDesc") != null &&
      window.localStorage.getItem("orderType") != null
    ) {
      this.orderType = Number(window.localStorage.getItem("orderType"));
      this.orderDesc = Number(window.localStorage.getItem("orderDesc"));
    }
    this.currentId = this.safeRootId;
    this.$on("refresh", () => {
      this.remakeNameID = "";
      this.getList(this.safeRootId);
    });
    this.getPreCatalogId();
    this.refreshSesssionDate();
    this.updateSortIcon();
    this.getList(this.currentId);
    //监听上传文件完成刷新列表  判断返回ID是否与当前文件ID相同  相同刷新
    this.$parent.$on("UpdataList", (e) => {
      if (this.$route.name === "safeBox") {
        if (Array.isArray(e) && e.length && this.currentId) {
          let arr = e.filter((item) => item === this.currentId);
          arr.length ? this.getList(this.currentId) : null;
        }
      }
    });
  },
  beforeDestroy() {
    //切换理由时 关闭$on 以防重复进入创造多个重复$on事件
    this.$parent.$off('UpdataList')
    clearInterval(this.sessionDateId);
    if (!this.isClickLockBtn) {
      //记录离开保险箱时间
      this.$parent.leaveSafeBoxTime = new Date().getTime();
    }
  },
  computed: {
    rootId() {
      return this.$parent.rootId;
    },
    PathStack: function () {
      if (this.ParentStack.length <= PATH_DISPLAY_COUNT) {
        return this.ParentStack;
      } else {
        var arr = this.ParentStack;
        return arr.slice(arr.length - PATH_DISPLAY_COUNT);
      }
    },
    isShowImgRovideo() {
      return function(item) {
        //判断缩略图是否是视频或图片并且有图片路径
        let isVideo = 'mp4,avi,mpg,flv,wmv,mov,mkv,rmvb'.indexOf(item.file.ext) >= 0
        let isImg = 'png,jpg,gif,jpeg,heic'.indexOf(item.file.ext) >= 0
        let url = item.file.thumbnailURL
        if((isVideo || isImg) && url) {
          return true
        }
        return false
      }
    }
  },
  watch: {
    currentId: function (v) {
      this.$parent.currentId = v;
    },
    renameOrCreateName() {
      //监控重命名的变化
      var t = /[*|:"<>?？\\\/&]/;
      //将renameOrCreateName转成字符串 不然下面报错
      this.renameOrCreateName = this.renameOrCreateName + "";
      //去除所有空格
      this.renameOrCreateName = this.renameOrCreateName.replace(/\s/g, "");
      this.renameOrCreateName = this.renameOrCreateName.replace(t, "");
      if (this.renameOrCreateName.match(t)) {
        //含有特殊字符
        this.isFalseName = true; //显示提示框
        this.isFalseNameTip = '文件夹名不能包含* | : " < > ? \\ / &等特殊字符';
      } else if (!this.renameOrCreateName.trim()) {
        this.isFalseName = true; //显示提示框
        this.isFalseNameTip = "输入内容为空，请重新输入";
      } else {
        this.isFalseName = false;
        this.isFalseNameTip = "";
      }
    },
    colorType: {
      handler(val) {
        this.fileColorType = val;
      },
      deep: true,
      immediate: true,
    },
    "$parent.keyUpCode": {
      handler(e) {
        if (e.target === document.getElementsByTagName("body")[0]) {
          switch (e.keyCode) {
            case 13: //回车
              if (
                this.fileClickList.length === 1 &&
                !this.remakeNameID &&
                this.fileClickList[0].type === "directory" &&
                this.fileInfo.name
              ) {
                this.goIntoFile(this.fileClickList[0]);
              }
              break;
            case 8:
              if (this.ParentStack.length > 1) {
                this.goBack();
              }
              break;
          }
        }
      },
    },
  },
  methods: {
    //标题栏排序
    changeSort(type) {
      if (window.stopPro == undefined || window.stopPro) {
        this.orderType = type;
        this.orderDesc = this.orderDesc === 0 ? 1 : 0;
        window.localStorage.setItem("orderDesc", this.orderDesc);
        window.localStorage.setItem("orderType", this.orderType);
        this.updateSortIcon();
        this.getList(this.currentId);
      } else {
        window.stopPro = true;
      }
    },
    //上传个人云文件
    uploadFamilyFile: debounce(
      function () {
        this.$emit("uploadFile", "uploadSafeListFile", this.currentId, 1);
      },
      1000,
      true
    ),
    download: function (item, source) {
      //source 图片预览的组件调这个方法
      BH(110523);
      var self = this;
      var list = this.fileClickList;
      this.transferType = "download";
      if (source && source === "chilrenVue") {
        list = [item];
      }
      var ids = [];
      list.forEach((item) => {
        if (item.type == "file") {
          var obj = {
            id: item.id,
            type: 0,
            name: item.name + "",
            size: item.file.rawSize,
          };
          ids.push(obj);
        } else if (item.type == "directory") {
          obj = {
            id: item.id,
            type: 1,
            name: item.name + "",
            size: 0,
          };
          ids.push(obj);
        }
      });

      if (ids.length > 0) {
        var socket = new LocalSocket();
        socket.send(
          "download",
          { userId: window.userId, type: 4, ids: ids },
          function (result) {
            if (result.code == "S_OK") {
              if (result["var"].fileCount == 1) {
                showToast("已添加至下载列表", { type: "success" });
              }
            } else {
              showToast("下载失败，请检查网络连接")
            }
          }
        );
      }
    },
    downloadAndOpen: function (item, typeClick) {
      // BH(110523);
      var self = this;
      var list = this.fileClickList;

      this.transferType = "download";

      var ids = [];
      if (list.length <= 0) {
        if (this.fileInfo.type == "file") {
          var obj = {
            id: this.fileInfo.id,
            type: 0,
            name: this.fileInfo.name,
            size: this.fileInfo.file.rawSize,
          };
          ids.push(obj);
        } else if (this.fileInfo.type == "directory") {
          obj = {
            id: this.fileInfo.id,
            type: 1,
            name: this.fileInfo.name,
            size: 0,
          };
          ids.push(obj);
        }
      } else {
        list.forEach((item) => {
          if (item.type == "file") {
            var obj = {
              id: item.id,
              type: 0,
              name: item.name,
              size: item.file.rawSize,
            };
            ids.push(obj);
          } else if (item.type == "directory") {
            obj = {
              id: item.id,
              type: 1,
              name: item.name,
              size: 0,
            };
            ids.push(obj);
          }
        });
      }

      if (ids.length > 0) {
        self.openFile(item, typeClick);
      }
    },
    getList: function (directoryId, append, callback) {
      //清除重命名id
      this.remakeNameID = "";
      //清除重命名
      this.renameOrCreateName = "";
      if (!append) {
        //解决切换文件夹时复选框没有清空的问题，以及进入文件夹时的闪烁问题
        this.$nextTick(() => {
          this.restFilseList();
        });
      }
      var self = this;
      self.isAllFile = false;
      if (directoryId === this.safeRootId) {
        this.ParentStack = [];
        this.ParentStack.push({ name: "保险箱", id: this.safeRootId });
      }
      if (!directoryId) {
        directoryId = this.currentId;
        this.pageIndex = 1;
        this.ParentStack = [];
        this.ParentStack.push({ name: "保险箱", id: this.safeRootId });
      }
      if (!append) {
        this.pageIndex = 1; //重置页码为1
        setCurrentDirId(directoryId, 0);
      }
      this.currentId = directoryId;
      let { heightNum, widthNum } = getMaxFilesLength();
      var data = {
        directoryId: directoryId,
        dirType: 0,
        filterType: 0,
        toPage: this.pageIndex,
        pageSize: heightNum * widthNum + 1,
        catalogSortType: this.orderType,
        contentSortType: this.orderType,
        sortDirection: this.orderDesc,
        thumbnailSize: "65*65",
      };
      //视图渲染了再打开loading
      this.$nextTick(() => {
        this.$myLoading.open({ el: this.$refs.tableList });
      });
      //透传
      safeBox.safeBoxGetDisk(data, (json) => {
        if (json) {
          this.$myLoading.hide();
          this.isRequestFinish = false
          if (append) {
            //当前处于全选状态 添加的数据也要添加到选择数组中 保持全选状态
            if(this.fileClickList.length > 0 &&  this.fileClickList.length === this.fileList.length) {
              this.$set(this,"fileClickList",this.fileClickList.concat(json["files"]));
            }
            this.$set(this, "fileList", this.fileList.concat(json["files"]));
          } else {
            this.totalSize = json["totalSize"];
            this.$set(this, "fileList", json["files"]);
          }
          //判断显示没有文件图标
          this.noFile = this.fileList.length ? false : true;
          //同步列表跳转文件id
          if (this.syncId) {
            if (this.fileList.find((item) => item.id === this.syncId))
              this.fileClickList.push(
                this.fileList.find((item) => item.id === this.syncId)
              );
            this.syncId = "";
          }
          callback && callback(json);
        }
      });
    },
    fileClick(item, index, e) {
      //防止文件再重命名时 点击另外一个文件 将id 改变 造成修改文件错误
      if (this.remakeNameID) return;
      //防止冒泡和右键菜单关闭不了
      if (
        e.target === this.$refs.fileClick_select[index] ||
        e.target.className.indexOf("main_table_choose") >= 0
      )
        return;
      if (e.shiftKey || e.ctrlKey) {
        if (e.shiftKey) {
          if (this.shiftBetweenLi.length === 0) {
            this.fileClickList.push(item);
            this.shiftBetweenLi.push(index);
          } else {
            this.shiftBetweenLi.push(index);
            this.shiftBetweenLi = Array.from(new Set(this.shiftBetweenLi));
            let minIndex = Math.min(...this.shiftBetweenLi);
            let maxIndex = Math.max(...this.shiftBetweenLi);
            this.fileClickList = [];
            for (let i = minIndex; i <= maxIndex; i++) {
              this.fileClickList.push(this.fileList[i]);
            }
          }
        } else if (e.ctrlKey) {
          if (this.fileClickList.indexOf(item) === -1) {
            this.fileClickList.push(item);
            this.shiftBetweenLi.push(index);
          } else {
            this.shiftBetweenLi = Array.from(new Set(this.shiftBetweenLi));
            this.fileClickList.splice(this.fileClickList.indexOf(item), 1);
            this.shiftBetweenLi.splice(this.shiftBetweenLi.indexOf(index), 1);
          }
        }
      } else {
        //没有使用ctrl和shift，直接点击的
        if (item.type == "directory") {
          //文件夹, 回收站里的文件夹需要排除掉
          if (item.directory.directoryLevel === 1) {
            this.chooseFileFromAll.push(item.id);
          }
          //单击文件名或双击空白时，才打开文件夹
          if (e.target.className == "item_name_a" || this.isDblClick(item)) {
            this.goIntoFile(item);
          }
        } else {
          if (this.isDblClick(item)) {
            if (item.type != "directory") {
              if (
                "png,jpg,gif,jpeg,heic".indexOf(item.file.ext) >= 0 &&
                item.file.thumbnailURL
              ) {
                //全部列表和同步盘列表 单独将图片分出来
                let arr = this.fileList.filter((item) => {
                  return (
                    "png,jpg,gif,jpeg,heic".indexOf(item.file.ext) >= 0 &&
                    item.file.thumbnailURL
                  );
                });
                let imgIndex = arr.findIndex((e) => e.id === item.id);
                this.$refs.viewer.show(arr, imgIndex);
              } else if (
                "mp4,avi,mpg,flv,wmv,mov,MOV,MP4,AVI,MPG,MWV,FLV,mkv,MKV,rmvb,RMVB".indexOf(
                  item.file.ext
                ) >= 0 &&
                item.file.thumbnailURL
              ) {
                this.openVideo(item);
              } else {
                this.downloadAndOpen(item);
              }
            }
          }
        }
        this.fileClickList = [];
        this.fileClickList.push(item);
        this.shiftBetweenLi = [];
        this.shiftBetweenLi.push(index);
        this.fileInfo = item
      }
      this.lastClick = { time: new Date(), id: item.id };
    },
    //多选框
    iconsClick(e, item, index) {
      if (e.shiftKey || e.ctrlKey) {
        if (e.shiftKey) {
          if (this.shiftBetweenLi.length === 0) {
            this.fileClickList.push(item);
            this.shiftBetweenLi.push(index);
          } else {
            this.shiftBetweenLi.push(index);
            this.shiftBetweenLi = Array.from(new Set(this.shiftBetweenLi));
            let minIndex = Math.min(...this.shiftBetweenLi);
            let maxIndex = Math.max(...this.shiftBetweenLi);
            this.fileClickList = [];
            for (let i = minIndex; i <= maxIndex; i++) {
              this.fileClickList.push(this.fileList[i]);
            }
          }
        } else if (e.ctrlKey) {
          if (this.fileClickList.indexOf(item) === -1) {
            this.fileClickList.push(item);
            this.shiftBetweenLi.push(index);
          } else {
            this.shiftBetweenLi = Array.from(new Set(this.shiftBetweenLi));
            this.fileClickList.splice(this.fileClickList.indexOf(item), 1);
            this.shiftBetweenLi.splice(this.shiftBetweenLi.indexOf(index), 1);
          }
        }
      } else {
        if (!this.fileClickList.some((e) => e.id === item.id)) {
          this.fileClickList.push(item);
          this.shiftBetweenLi.push(index);
        } else {
          this.shiftBetweenLi = Array.from(new Set(this.shiftBetweenLi));
          this.fileClickList.splice(
            this.fileClickList.findIndex((e) => e.id === item.id),
            1
          );
          this.shiftBetweenLi.splice(this.shiftBetweenLi.indexOf(index), 1);
        }
      }
      //清除数组里的undefined （单击也添加进shift的shiftBetweenLi 索引判断数组）
      this.fileClickList = this.fileClickList.filter(item => item)
    },
    //进入文件夹
    goIntoFile(item) {
      this.currentId = item.id;
      this.fileInfo = {};
      this.fileInfo = item;
      this.fileList = [];
      this.ParentStack.push({ name: item.name, id: item.id });
      this.getList(item.id, false, () => {
        //防双击重复点击
        if (item.id != this.ParentStack[this.ParentStack.length - 1].id) {
          this.ParentStack.push({ name: item.name, id: item.id });

          if (this.ParentStack.length >= this.ParentStack_copy.length) {
            //进行过最长的路径进行存储，便于前进和后退操作
            this.ParentStack_copy = this.ParentStack;
          }
          if (this.chooseFileFromAll[1] !== this.chooseFileFromAll[0]) {
            this.chooseFileFromAll.shift(); //移除上一项
            this.ParentStack_copy = this.ParentStack; //更新副本
          } else {
            this.chooseFileFromAll.shift(); //移除上一项
          }
        }
      });
    },
    isDblClick(item) {
      var obj = this.lastClick;
      if (obj.id == item.id && new Date() - obj.time <= 500) {
        return true;
      }

      return false;
    },
    openFile(file, typeClick) {
      if (file.type === "file") {
        //如果是文件的话，执行打开

        let data = {
          userId: window.userId,
          id: file.id,
          name: file.name,
          size: file.file.fileSize,
          type: 4,
        };
        if (typeClick && typeClick === 1) {
          data["openas"] = 1;
        } else {
          showToast("文件正在下载，完成后自动打开", {
            type: "tips",
            delay: 2000,
          });
        }
        var socket = new LocalSocket();
        socket.send("openFile", data, function (result) {});
      } else {
        //如果是文件夹的话，执行进入到下一个目录
        this.getList(file.id);
        this.ParentStack.push({ name: file.name, id: file.id });
      }
    },
    allClick() {
      if (this.fileClickList.length != this.fileList.length) {
        this.fileClickList = [];
        for (var i = 0; i < this.fileList.length; i++) {
          this.fileClickList.push(this.fileList[i]);
        }
      } else {
        this.fileClickList = [];
      }
    },
    //关于跨级回退标题栏导航显示
    nav(node, idx) {
      var list = this.PathStack;
      for (var i = 0; i < list.length; i++) {
        var item = list[i];
        if (idx === i) {
          var delta = 0;
          //最多显示6级目录，加上偏移量
          if (this.ParentStack.length > PATH_DISPLAY_COUNT) {
            delta = this.ParentStack.length - PATH_DISPLAY_COUNT;
          }

          this.ParentStack = this.ParentStack.slice(0, idx + 1 + delta);
          if (item.id) {
            //普通列表
            this.getList(item.id);
          } else if (item.type) {
            if (item.type == "trash") {
              //回收站
              this.getTrash();
            }
          }
          break;
        }
      }
    },
    //逐级回退
    goBack() {
      if (this.ParentStack.length != 1) {
        this.ParentStack = this.ParentStack.slice(
          0,
          this.ParentStack.length - 1
        );
        let id = this.ParentStack[this.ParentStack.length - 1].id;
        this.getList(id);
        this.fileList = [];
      }
    },
    //逐级前进
    goAhead() {
      if (this.ParentStack[0].id === "1811ZaFQ81Qq00019700101000000001") {
        this.ParentStack = this.ParentStack_copy.slice(
          0,
          this.ParentStack.length + 1
        );
        this.getList(this.ParentStack[this.ParentStack.length - 1].id);
      }
    },
    //右击菜单
    rightClick(e, item) {
      var self = this;
      e.preventDefault();
      if (this.fileClickList.length <= 0 || this.fileClickList.length == 1) {
        this.fileInfo = item
        this.fileClickList = [];
        this.fileClickList.push(item);
      }
      var menuItems = [];
      if (this.fileClickList.length == 1) {
        menuItems = menuItems.concat([
          {
            text: "打开",
            value: "downloadAndOpen",
            icon: "icons i_open detail_icon",
          },
          {
            text: "打开方式",
            value: "openType",
            icon: "icons i_open_type detail_icon op-0",
            arrow: true,
          },
          { line: true },
          {
            text: "下载",
            value: "download",
            icon: "icons i_desktop detail_icon",
          },
          { line: true },
          {
            text: "移动到",
            value: "moveFile",
            icon: "icons i_moveto detail_icon op-0",
          },
          {
            text: "移出保险箱",
            value: "moveSafeBox",
            icon: "icons i_moveto detail_icon op-0",
          },
          { line: true },
          {
            text: "删除",
            value: "deleteFile",
            icon: "icons i_del detail_icon",
          },
          {
            text: "重命名",
            value: "renameFile",
            icon: "icons i_rename detail_icon op-0",
          },
          {
            text: "详细信息",
            value: "opendetail",
            icon: "icons i_info detail_icon op-0",
          },
        ]);
        if (item.type === "directory") {
          menuItems.splice(1, 1);
        } else if (
          "mp4,avi,mpg,flv,wmv,mov,MOV,MP4,AVI,MPG,MWV,FLV,mkv,MKV,rmvb,RMVB".indexOf(
            item.file.ext
          ) >= 0 &&
          item.file.thumbnailURL
        ) {
          menuItems.splice(0, 1);
          menuItems.unshift({
            text: "在线播放",
            value: "openVideo",
            icon: "icons i_open_video detail_icon",
          });
        } else if (
          "png,jpg,gif,jpeg,heic".indexOf(item.file.ext) >= 0 &&
          item.file.thumbnailURL
        ) {
          menuItems.splice(0, 1);
          menuItems.unshift({
            text: "在线预览",
            value: "openPicture",
            icon: "icons i_open_picture detail_icon",
          });
        }
      }
      if (this.fileClickList.length > 1) {
        menuItems = menuItems.concat([
          {
            text: "下载",
            value: "download",
            icon: "icons i_desktop detail_icon",
          },
          { line: true },
          {
            text: "移动到",
            value: "moveFile",
            icon: "icons i_moveto detail_icon op-0",
          },
          {
            text: "移出保险箱",
            value: "moveSafeBox",
            icon: "icons i_moveto detail_icon op-0",
          },
          { line: true },
          {
            text: "删除",
            value: "deleteFile",
            icon: "icons i_del detail_icon",
          },
        ]);
      }
      this.showMenu({
        id: "menu_context",
        position: {
          left: e.clientX,
          top: e.clientY,
        },
        items: menuItems,
        change: function (arg) {
          self[arg.item.value].call(self, item);
        },
      });
    },
    //重置列表
    restFilseList: async function () {
      this.fileClickList = [];
      document.querySelector(".main_table")
        ? (document.querySelector(".main_table").scrollTop = 0)
        : ""; //重置滚动条
    },
    shareToGroup() {
      this.dialog = "shareGroup";
    },
    //删除文件
    deleteFile (item, source) {
      var self = this;
      if (source && source === "chilrenVue") {
        this.fileClickList = [item];
      }
      if (this.fileClickList.length == 0) {
        var msg = "未选择文件";
      } else if (this.fileClickList.length == 1) {
        var msg =
          "<span class='txtEllipsis' title=" +
          this.fileClickList[0].name +
          " style='display:inline-block;width:100%'>确定删除" +
          this.fileClickList[0].name +
          "吗？</span><br/>删除后无法恢复，将永久删除。";
      } else {
        var msg =
          "确定删除这" +
          this.fileClickList.length +
          "个文件/文件夹吗？<br/>删除后无法恢复，将永久删除。";
      }

      this.showDialog({
        title: "提示",
        content: msg,
        canceltext: "取消",
        ok: function () {
          self.deletFileOk();
        },
      });
    },
    //删除文件确认
    deletFileOk: function () {
      var self = this;
      var fileIds = "";
      var directoryIds = "";
      if (self.fileClickList.length > 0) {
        for (var i = 0; i < self.fileClickList.length; i++) {
          if (self.fileClickList[i].type == "file") {
            fileIds += self.fileClickList[i].id + ",";
          } else {
            directoryIds += self.fileClickList[i].id + ",";
          }
        }
        fileIds = fileIds.substring(0, fileIds.lastIndexOf(","));
        directoryIds = directoryIds.substring(0, directoryIds.lastIndexOf(","));
        var data = {
          fileIds: fileIds,
          directoryIds: directoryIds,
          dirType: self.removeDirectoryParms.dirType,
          opr: self.removeDirectoryParms.opr,
        };
      } else {
        return;
      }
      showToast("正在删除", { type: "delete" }); //显示删除提示
      safeBox.deleteItems(data, (json) => {
        //透传
        if (json) {
          this.isRemoveModal = false;
          this.getList(this.currentId);
          hideToast(); //隐藏显示
          showToast("删除成功");
          this.$refs.viewer.deleteImg();
        }
      });
    },
    //新建文件夹
    createFile() {
      if (this.ParentStack.length > 19) {
        this.showDialog({
          title: "提示",
          content: "文件夹目录层级太多，创建失败",
        });
        return;
      }
      this.inputType = "created";
      this.noFile = false;
      this.renameOrCreateName = "新建文件夹";
      //显示输入框
      this.remakeNameID = true;
      this.fileInfo = {
        createTime: getNowFormatDate(),
        directory: {
          dirFlag: 1,
          dirType: 1,
          directoryLevel: 1,
          fileNum: 0,
          parentDirectoryId: this.safeRootId,
        },
        file: {},
        id: this.remakeNameID,
        isShare: 0,
        modifyTime: getNowFormatDate(),
        name: "",
        type: "directory",
      };
      this.fileClickList = [this.fileInfo];
      this.fileList.unshift(this.fileInfo);
      setTimeout(() => {
        this.$refs.remakeInput[0].focus();
      }, 100);
    },
    //新建文件夹确认
    createFileOK() {
      var self = this;
      var data = {
        name: self.renameOrCreateName.trim().replace(/\s/g, ""),
        parentId: self.currentId || self.safeRootId,
        dirType: self.createDirectoryParms.dirType,
      };
      safeBox.createDirectory(data, (json) => {
        if (json) {
          if (json.code === "S_OK") {
            self.getList(this.currentId);
          } else if (json.code === "FS_Unknow") {
            this.$nextTick(() => {
              setTimeout(() => {
                self.$refs.remakeInput[0].focus();
              }, 100);
            });
          }
        }
      });
    },
    //移动文件
    moveFile() {
      let contentInfoList = this.fileClickList.filter(
        (item) => item.type === "file"
      );
      let catalogInfoList = this.fileClickList.filter(
        (item) => item.type === '"directory"'
      );
      if (contentInfoList.length > 50 || catalogInfoList > 50) {
        this.showDialog({
          title: "提示",
          content: "每次移动最多支持50个文件和50个文件夹",
        });
        return;
      }
      this.dialog = "move";
      this.moveType = "moveTo";
    },
    /**
     * moveSafeBox
     * @desc 移除保险箱
     * @date 2020-08-04 18:13:30
     **/
    moveSafeBox() {
      let contentInfoList = this.fileClickList.filter(
        (item) => item.type === "file"
      );
      let catalogInfoList = this.fileClickList.filter(
        (item) => item.type === '"directory"'
      );
      if (contentInfoList.length > 50 || catalogInfoList > 50) {
        this.showDialog({
          title: "提示",
          content: "每次移动最多支持50个文件和50个文件夹",
        });
        return;
      }
      this.moveType = "moveOut";
      this.dialog = "move";
    },
    //重命名
    renameFile() {
      this.inputType = "rename";
      this.remakeNameID = this.fileInfo.id;
      this.renameOrCreateName = this.fileInfo.name;
      setTimeout(() => {
        this.$refs.remakeInput[0].vaule = this.fileInfo.name;
        this.$refs.remakeInput[0].focus();
      }, 100);
    },
    //提交重命名
    renameFileOK() {
      var self = this;
      let type;
      if (self.fileInfo.type === "file") {
        var data = {
          name: self.renameOrCreateName,
          fileId: self.fileInfo.id,
        };
        type = "file";
      } else {
        var data = {
          name: self.renameOrCreateName,
          directoryId: self.fileInfo.id,
          dirType: self.renameParms.dirType,
        };
        type = "directory";
      }

      safeBox.rename(data, type, (json) => {
        if (json && json.code === "S_OK") {
          self.getList(self.currentId);
        } else {
          setTimeout(() => {
            self.$refs.remakeInput[0].focus();
          }, 100);
        }
      });
    },
    //打开文件详情
    opendetail() {
      this.detailModal = true;
    },
    //	切换列表（缩略图模式和普通模式的切换）
    listStyleHandle() {
      //切换列表表头位置重置
      !this.isList ? (this.tableTop = { top: 0 }) : "";
      this.isList = !this.isList;
      //切换列表模式的时候顺便清除之前单选多选的数组
      //this.fileClickList = []
      this.shiftBetweenLi = [];
    },

    shareFile(item, source) {
      BH(110816);
      //图片预览组件调用这个方法才判断
      if (source && source === "chilrenVue") {
        this.fileClickList = [item];
      }
      this.shareFileInfoArr = [];
      this.isShareCopy = false;
      var reg_exe = /(exe|com|apk|AAC|AC3|ASF|AU|AVS|DAT|DIVX|DTS|MKA|MP2|MPA|MPG|Ogg|PCM|RM|TTA|WMA|WV|XVID|aif|aiff|ape|cda|chm|cmf|csv|dif|docm|dot|dotm|dotx|heic|htm|html|ilbm|mht|mhtml|midi|mpc|mv|nAVI|nsv|odf|odp|ods|odt|ogm|pac|pot|potm|potx|ppa|ppam|pps|ppsm|ppsx|pptm|prn|ra|rtf|shtml|slk|swf|sxc|tp|ts|vob|webp|wps|wtf|xhtml|xla|xlam|xlsb|xlsm|xlt|xltm|xml)$/i;
      var isUnsafe = false;
      if (this.fileClickList.length > 0) {
        if (this.fileClickList.length > 50) {
          this.showDialog({
            title: "提示",
            content: "单次分享文件最多为50项，请重新选择",
          });
          return;
        }
        var files = [],
          dirs = [];
        this.fileClickList.forEach((item) => {
          if (item.type == "file") {
            var m = item.name.match(/\.(\w+)$/);
            if (m) {
              var ext = m[1];
              if (ext.match(reg_exe)) {
                isUnsafe = true;
              }
            }
            files.push(item);
            this.shareFileInfoArr.push(item);
          } else if (item.type == "directory") {
            dirs.push(item);
            this.shareFileInfoArr.push(item);
          }
        });

        if (isUnsafe) {
          this.showDialog({
            title: "提示",
            content: `<p id="unsafe1">分享内容存在敏感信息，请重新选择<a href='javascript:' onclick="unsafe2.style.display='';unsafe1.remove();"><i class="icons i_info" style="position:relative;top:0px;left:5px"></i></a></p>
						<p id='unsafe2' style="display:none">以下格式暂不支持分享<br>
						exe、com、apk、AAC、AC3、ASF、AU、AVS、DAT、DIVX、DTS、MKA、MP2、MPA、MPG、Ogg、PCM、RM、TTA、WMA、WV、XVID、aif、aiff、ape、cda、chm、cmf、csv、dif、docm、dot、dotm、dotx、heic、htm、html、ilbm、mht、mhtml、midi、mpc、mv、nAVI、nsv、odf、odp、ods、odt、ogm、pac、pot、potm、potx、ppa、ppam、pps、ppsm、ppsx、pptm、prn、ra、rtf、shtml、slk、swf、sxc、tp、ts、vob、webp、wps、wtf、xhtml、xla、xlam、xlsb、xlsm、xlt、xltm、xml
						</p>`,
          });
          return;
        }

        showToast("正在加载中...");

        OutLink.createItems(files, dirs, (result) => {
          this.shareFileInfo = result;
          if (files.length > 0) {
            this.shareFileInfo.name = files[0].name;
          } else if (dirs.length > 0) {
            this.shareFileInfo.name = dirs[0].name;
          }

          this.shareFileVisible = true;
          hideToast();
          // OutLink.getInfo(result.linkid,(result2)=>{

          // })
        });
      } else {
        //this.showDialog({title:"提示",content:"请选择文件"})
      }
    },
    copyLink() {
      copyText(
        "链接:" +
          this.shareFileInfo.linkurl +
          "\n提取码:" +
          this.shareFileInfo.passwd +
          "\n复制内容打开和彩云手机APP，操作更方便哦"
      );
      this.isShareCopy = true;
    },
    sendShareLink() {
      var html =
        "链接:" +
        this.shareFileInfo.linkurl +
        "\n提取码:" +
        this.shareFileInfo.passwd;

      var socket = new LocalSocket();
      socket.send(
        "openWriteLetterPage",
        { userId: userId, title: "分享文件", content: html, recever: [] },
        function (result) {}
      );
    },
    updateSortIcon() {
      window.menuSort = this.menuSort;
      this.menuSort.forEach((item) => {
        if (item.key) {
          if (item.key == "asc" || item.key == "desc") {
            item.icon = this.orderDesc == item.desc ? "checked" : null;
          }

          if (item.key.indexOf("by") >= 0) {
            item.icon = this.orderType == item.type ? "checked" : null;
          }
        }
      });
    },
    menuSortChange(e) {
      if (e.item.desc != undefined) {
        this.orderDesc = e.item.desc;
      } else {
        this.orderType = e.item.type;
      }
      window.localStorage.setItem("orderDesc", this.orderDesc);
      window.localStorage.setItem("orderType", this.orderType);
      this.updateSortIcon();
      this.getList(this.currentId);
    },
    menuMoreChange(e) {
      if (e.item.value == "rename") {
        //重命名
        if (this.fileClickList.length > 1) {
          showToast("只能选择一个文件/文件夹进行重命名哦~", {
            type: "tips",
            delay: 1500,
          });
          return;
        } else if (this.fileClickList.length === 0) {
          showToast("请选择一个文件/文件夹进行重命名哦~", {
            type: "tips",
            delay: 1500,
          });
          return;
        }

        this.fileInfo = this.fileClickList[0];
        this.renameFile();
      } else if (e.item.value == "move") {
        this.moveFile();
      } else if (e.item.value == "del") {
        if (hasSysFolder(this.fileInfo)) {
          showToast("该文件夹为系统文件夹，不可删除", {
            type: "tips",
            delay: 3000,
          });
          return;
        }
        this.deleteFile();
      } else if (e.item.value == "moveOut") {
        this.moveSafeBox();
      }
    },
    moreClick: function () {
      this.ismenuMore = 1;
    },
    tableScroll(e) {
      this.tableTop = { top: `${e.target.scrollTop}px` };
    },
    getFocus(e) {
      if (this.fileInfo.type === "file") {
        const dotIndex = e.target.value.lastIndexOf(".");
        setTimeout(function () {
          e.target.selectionStart = 0;
          e.target.selectionEnd = dotIndex;
        }, 100);
      } else {
        e.target.select();
      }
    },
    changeFileName(e) {
      //去除所有空格
      this.renameOrCreateName = this.renameOrCreateName.replace(/\s/g, "");
      //最后一个字符不能是. eg：.sada(成功)   sada.(报错)
      if (this.renameOrCreateName[this.renameOrCreateName.length - 1] === ".") {
        this.renameOrCreateName = this.renameOrCreateName.substring(
          0,
          this.renameOrCreateName.length - 1
        );
      }
      if (
        !this.renameOrCreateName.trim() ||
        (this.fileInfo.type === "file" &&
          !this.renameOrCreateName.split(".")[0])
      ) {
        this.remakeNameID = false;
        showToast("输入内容为空，请重新输入")
        this.remakeNameID = this.fileInfo.id;
        this.renameOrCreateName = this.fileInfo.name;
        if (this.fileInfo.type === "file") {
          setTimeout(() => {
            const dotIndex = this.renameOrCreateName.lastIndexOf(".");
            this.$refs.remakeInput[0].focus();
            e.target.selectionStart = 0;
            e.target.selectionEnd = dotIndex;
          }, 100);
        } else {
          setTimeout(() => {
            this.$refs.remakeInput[0].focus();
            e.target.select();
          }, 100);
        }
        return;
      }
      if (this.inputType === "created") {
        this.createFileOK();
      } else if (this.inputType === "rename") {
        //名字相同不修改
        if (
          this.fileInfo.name.toString().replace(/\s/g, "") !==
          this.renameOrCreateName.replace(/\s/g, "")
        ) {
          this.renameFileOK();
        } else {
          this.remakeNameID = false;
        }
      }
    },
    //点击空白清空缩略图选像
    cleanCheck(e) {
      let ul = document.querySelector(".cleanCheck");
      if (e.target === ul) {
        this.fileClickList = [];
      }
      //触发重命名失去焦点事件
      if (this.remakeNameID) {
        this.$refs.remakeInput[0].blur();
      }
    },
    //在线播放
    openVideo(item) {
      self = this;
      // item = item || this.fileClickList[0]
      if (item.type != "directory") {
        if (
          "mp4,avi,mpg,flv,wmv,mov,MOV,MP4,AVI,MPG,MWV,FLV,mkv,MKV,rmvb,RMVB".indexOf(
            item.file.ext
          ) >= 0
        ) {
          localStorage.setItem("fileList", JSON.stringify(this.fileList));
          localStorage.setItem("item", JSON.stringify(item));
          //点击在线播放，弹框
          var socket = new LocalSocket();
          socket.send(
            "newWindow",
            {
              width: 853,
              height: 480,
              // url: "/H5/../../web/src/views/videoplayer.html",
              url: "/H5/src/views/videoplayer.html",
              model: false,
              type: "videowindow",
            },
            function (result) {
              //打开新的弹框，客户端页面监听localstorage
              function storageListener() {
                //点击还原，改变视频窗口大小
                if (localStorage.getItem("show") == "3") {
                  var socket = new LocalSocket();
                  socket.send(
                    "windowOperate",
                    {
                      type: "videowindow",
                      operate: 1,
                    },
                    function (result) {}
                  );
                }
                if (localStorage.getItem("show") == "2") {
                  var socket = new LocalSocket();
                  socket.send(
                    "windowOperate",
                    {
                      type: "videowindow",
                      operate: 3,
                    },
                    function (result) {}
                  );
                }
                //点击最小化
                if (localStorage.getItem("hidden") == "1") {
                  var socket = new LocalSocket();
                  socket.send(
                    "windowOperate",
                    {
                      type: "videowindow",
                      operate: 2,
                    },
                    function (result) {}
                  );
                  localStorage.clear();
                }
                //点击关闭
                if (localStorage.getItem("close") == "4") {
                  var socket = new LocalSocket();
                  socket.send(
                    "windowOperate",
                    {
                      type: "videowindow",
                      operate: 4,
                    },
                    function (result) {}
                  );
                  window.removeEventListener("storage", storageListener);
                  localStorage.clear();
                }
              }
              window.addEventListener("storage", storageListener);
            }
          );
        }
      }
    },
    //在线预览
    openPicture() {
      //全部列表和同步盘列表 单独将图片分出来
      let arr = this.fileList.filter((item) => {
        return (
          "png,jpg,gif,jpeg,heic".indexOf(item.file.ext) >= 0 &&
          item.file.thumbnailURL
        );
      });
      let imgIndex = arr.findIndex((e) => e.id === this.fileClickList[0].id);
      this.$refs.viewer.show(arr, imgIndex);
    },
    //打开方式
    openType() {
      this.downloadAndOpen(...arguments, 1);
    },
    /**
     * @desc 获取从传输列表过来的文件夹id（返回上次文件目录）
     * @date 2020-07-10 11:01:48
     **/
    getPreCatalogId() {
      if ("pathData" in this.$route.query) {
        let { pathData } = this.$route.query;
        this.currentId = pathData[pathData.length - 1].id;
        this.ParentStack = pathData;
      }
    },
    /**
     * @desc 锁定保险箱
     * @date 2020-08-05 13:31:34
     **/
    lockSafeBox() {
      showToast("保险箱已锁定");
      this.isClickLockBtn = true;
      this.$parent.leaveSafeBoxTime = 0;
      this.$router.push({ name: "loginSafebox" });
      safeBox.safeBoxAppLogout((json) => {});
    },
    /**
     * @desc sesssion过期刷新有效期
     * @date 2020-08-05 14:34:45
     * @param {Object} [title] - 参数说明
     * @param {String} [columns] - 参数说明
     **/
    refreshSesssionDate() {
      if ("sessionDate" in this.$route.query) {
        this.sessionDateId = setInterval(() => {
          safeBox.refreshSesssionDate();
        }, 3595000);
      }
    },
  },
  filters: {
    changeColor: (val, type) => val + 'Type'
  },
  directives: {
    "scroll-load": {
      inserted: function (el, binding, vnode, oldVnode) {
        var self = vnode.context;
        let isLoading = false;
        el.addEventListener("scroll", async (e) => {
          //滚动高度
          let scrollTop = el.scrollTop;
          //可视高度
          let clientHeight = el.clientHeight;
          //列表高度
          let offsetHeight = el.lastElementChild.clientHeight;
          if (
            scrollTop + clientHeight >= offsetHeight &&
            self.fileList.length
          ) {
            if (self.fileList.length < self.totalSize) {
              if (!self.isRequestFinish) {
                self.isRequestFinish = true;
                self.pageIndex++;
                self.getList(self.currentId, true, () => self.isRequestFinish = false);
              }
            }
          }
        });
      },
    },
    "scroll-div": {
      inserted: function (el, binding, vnode) {
        let self = vnode.context;
        let tagNameArr = el.parentElement.className.split(" ");
        //获取要拉伸的dom类名
        let className = tagNameArr.find((item) => {
          return item.indexOf("main_table") > -1;
        });
        //dom拉伸最小值
        function getMin(moveLen, last) {
          if (moveLen < last) {
            return last;
          } else {
            return moveLen;
          }
        }
        el.onmousedown = (e) => {
          window.stopPro = true;
          e.stopPropagation();
          var startX = e.clientX;
          el.left = el.offsetLeft;
          document.onmousemove = (e) => {
            var endX = e.clientX;
            var moveLen = el.left + (endX - startX);
            if (className == "main_table_name") {
              moveLen = getMin(moveLen, 365);
              self.sortData.name = `width:${moveLen}px`;
            }
            if (className == "main_table_time") {
              moveLen = getMin(moveLen, 200);
              self.sortData.time = `width:${moveLen}px`;
            }
            if (className == "main_table_size") {
              moveLen = getMin(moveLen, 95);
              self.sortData.size = `width:${moveLen}px`;
            }
          };
          document.onmouseup = function (evt) {
            if (evt.target == el.parentElement) {
              window.stopPro = false;
            } else {
              window.stopPro = true;
            }
            evt.stopPropagation();
            document.onmousemove = null;
            document.onmouseup = null;
            el.releaseCapture && el.releaseCapture();
          };
          el.setCapture && el.setCapture();
          return false;
        };
      },
    },
    //列表框选
    "selection-box": {
      inserted(el, binging, vnode) {
        let self = vnode.context;
        //检测元素是否在框选范围内 el：选择的元素  el2：框选元素
        function isCollision(el, el2) {
          let elRect = el.getBoundingClientRect();
          let elRect2 = el2.getBoundingClientRect();
          if (
            elRect.top > elRect2.bottom ||
            elRect2.top > elRect.bottom ||
            elRect2.left > elRect.right ||
            elRect.left > elRect2.right
          ) {
            return false;
          }
          return true;
        }
        el.onmousedown = function (e) {
          if (e.button !== 0) return; //判断点击的是鼠标左键还是右键
          e.stopPropagation();
          if (e.target === el && e.button === 0) {
            let startClient = {
              x: e.offsetX,
              y: e.offsetY,
            };
            let selection;
            if (document.querySelectorAll(".selection").length) {
              selection = document.querySelectorAll(".selection")[0];
            } else {
              selection = document.createElement("div");
            }
            selection.className = "selection";
            el.appendChild(selection);
            el.onmousemove = function (e) {
              e.stopPropagation();
              let nowClient = {};
              if (e.target === el) {
                nowClient = {
                  x: e.offsetX,
                  y: e.offsetY,
                };
              } else {
                nowClient = {
                  x: e.offsetX + e.target.offsetLeft,
                  y: e.offsetY + e.target.offsetTop,
                };
              }
              selection.style.width =
                Math.abs(startClient.x - nowClient.x) + "px";
              selection.style.height =
                Math.abs(startClient.y - nowClient.y) + "px";
              selection.style.top = Math.min(startClient.y, nowClient.y) + "px";
              selection.style.left =
                Math.min(startClient.x, nowClient.x) + "px";
              let li = self.$refs.fileClick_table;
              //在框内添加，不在删除
              li.forEach((item, i) => {
                if (isCollision(item, selection)) {
                  if (
                    !self.fileClickList.some(
                      (e) => e.id === self.fileList[i].id
                    )
                  ) {
                    self.fileClickList.push(self.fileList[i]);
                  }
                } else {
                  if (
                    self.fileClickList.some((e) => e.id === self.fileList[i].id)
                  ) {
                    self.fileClickList.splice(
                      self.fileClickList.findIndex(
                        (e) => e.id === self.fileList[i].id
                      ),
                      1
                    );
                  }
                }
              });
            };
            el.onmouseup = function (e) {
              if (e.button === 0) {
                el.removeChild(selection);
                el.onmousemove = null;
                el.onmouseup = null;
              }
            };
          }
          return false;
        };
      },
    },
  },
  components: {
    moveModule,
  },
};
</script>
