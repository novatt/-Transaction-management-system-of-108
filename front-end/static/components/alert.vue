<template>
  <div v-cloak>
    <button @click="openConfirm">点击</button>
    <div class="confirmLayercovering" id="confirmLayercovering">
      <div class="confirmLayer" id="confirmLayer">
        <p class="confirmTit" id="confirmTit">确定要跳转吗?>
          <div class="confirmbtns">
            <a class="confirmClose" id="confirmClose">取消</a>
            <a class="confirmSure" id="confirmSure">确定</a>
          </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Confirm',
    data() {
      return {}
    },
    methods: {
      //确认弹窗
      ConfirmLayer: function(txt, callback) {
        var _this = this;
        var confirmLayercovering = document.getElementById('confirmLayercovering'),
          confirmTit = document.getElementById('confirmTit'),
          confirmClose = document.getElementById('confirmClose'),
          confirmSure = document.getElementById('confirmSure'),
          confirmCall = callback,
          tittxt = txt;
        if (!confirmLayercovering) {
          return;
        }
        var init = function() {
            if (txt) {
              confirmTit.innerHTML = txt;
            }
            show();
            confirmClose.addEventListener('click', function() {
              hide();
              confirmCall && confirmCall(false);
            });
            confirmSure.addEventListener('click', function() {
              hide();
              confirmCall && confirmCall(true);
            });
          },
          hide = function() {
            confirmLayercovering.style.display = 'none';
          },
          show = function() {
            confirmLayercovering.style.display = 'block';
          },
          Confirm = function(txt, callback) {
            if (tittxt != txt && txt) {
              confirmTit.innerHTML = txt;
              tittxt = txt;
            }
            confirmCall = callback;
            show();
          };
        init(); //初始化
        _this.ConfirmLayer = Confirm; //下次不用再初始化
      },
      openConfirm() {
        let _this = this;
        _this.ConfirmLayer("确认要跳转吗？", function(flag) {
          if (flag) {
            console.log('确认');
          } else {
            console.log('取消');
          }
        })
      }
    }
  }
</script>

<style scoped>
  button {
    border: none;
    padding: .05rem .1rem;
    border-radius: 5px;
    background: lightcoral;
    color: #ffffff;
  }

  /* 确认弹框 start */
  .confirmLayercovering {
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    z-index: 1005;
    background-color: rgba(0, 0, 0, .5);
    display: none;
    font-size: 0;
  }

  .confirmLayer {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 2.4rem;
    height: 1.35rem;
    background: #fff;
    border-radius: 10px;
    padding: .1rem;
    box-sizing: border-box;
  }

  .confirmTit {
    width: 100%;
    height: 65%;
    line-height: 4;
    color: #333;
    font-size: .16rem;
  }

  .confirmbtns {
    width: 100%;
    height: 35%;
    display: flex;
  }

  .confirmbtns a {
    display: inline-block;
    flex: 1;
    height: 100%;
    line-height: 2.5;
    border-radius: 4px;
    font-size: .16rem;
  }

  .confirmClose {
    background-color: #bbb;
    color: #fff;
    margin-right: .1rem;
  }

  .confirmSure {
    background: #ff7035;
    color: #fff;
  }

  /* 确认弹框 end */
</style>