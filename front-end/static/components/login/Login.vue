<template>
  <div >
    <div class="outer_label">
      <img class="inner_label login_logo" src="../../assets/bootstrap-solid.png">
    </div>
    <div class="login_form">
      <input type="text"  class="qxs-ic_user qxs-icon"  placeholder="用户名" v-model="userName">
      <input type="text"  class="qxs-ic_password qxs-icon"  placeholder="密码" v-model="password">
      <!--<button class="login_btn el-button el-button&#45;&#45;primary is-round" type="primary" round>登录</button>-->
      <el-button class="login_btn" @click.native="login" type="primary" round :loading="isBtnLoading">登录</el-button>
      <div style="margin-top: 10px">
        <!--<span style="color: #000099;" @click="login">手机账号登陆</span>--><span style="float: right;color: #A9A9AB">忘记密码？</span>
      </div>
    </div>
  </div>
</template>
 
 
 
<script>
//  import { userLogin } from '../../api/api';
 
  export default {
    data() {
      return {
        userName: '',
        password: '',
        isBtnLoading: false
      }
    },
    created () {
      if(JSON.parse( localStorage.getItem('user')) && JSON.parse( localStorage.getItem('user')).userName){
        this.userName = JSON.parse( localStorage.getItem('user')).userName;
        this.password = JSON.parse( localStorage.getItem('user')).password;
      }
    },
    computed: {
      btnText() {
        if (this.isBtnLoading) return '登录中...';
        return '登录';
      }
    },
    methods: {
      login() {
        if (!this.userName) {
          this.$message.error('请输入用户名');
          return;
        }
        if (!this.password) {
          this.$message.error('请输入密码');
          return;
        }
 
      }
    }
  }
</script>
<style>
  .login_form {
    padding-top: 10%;
    padding-left: 10%;
    padding-right: 10%;
  }
  .qxs-ic_user {
    background: url("../../assets/login/ic_user.png") no-repeat;
    background-size: 13px 15px;
    background-position: 3%;
  }
  .qxs-ic_password {
    background: url("../../assets/login/ic_password.png") no-repeat;
    background-size: 13px 15px;
    background-position: 3%;
    margin-bottom: 20px;
  }
  .login_logo {
    height: 100%;
  }
  .login_btn {
    width: 100%;
    font-size: 16px;
    background: -webkit-linear-gradient(left, #000099, #2154FA); /* Safari 5.1 - 6.0 */
    background: -o-linear-gradient(right, #000099, #2154FA); /* Opera 11.1 - 12.0 */
    background: -moz-linear-gradient(right, #000099, #2154FA); /* Firefox 3.6 - 15 */
    background: linear-gradient(to right, #000099 , #2154FA); /* 标准的语法 */
    filter: brightness(1.4);
  }
</style>