<template>
<body id="poster">
<el-form class="login-container" label-position="left"
         label-width="0px">
  <h3 class="login_title">商城注册</h3>
  <el-form-item>
    <el-input type="text" v-model="registerForm.tel"
              auto-complete="off" placeholder="请输入手机号码"></el-input>
  </el-form-item>
  <el-form-item>
    <el-input type="text" v-model="registerForm.nick"
              auto-complete="off" placeholder="请输入昵称"></el-input>
  </el-form-item>
  <el-form-item>
    <el-input type="password" v-model="registerForm.password"
              auto-complete="off" placeholder="请输入密码"></el-input>
  </el-form-item>
  <el-form-item>
    <el-input type="password" v-model="registerForm.password2"
              auto-complete="off" placeholder="请确认密码"></el-input>
  </el-form-item>
  <el-form-item style="width: 100%;margin-top: 50px">
    <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="register">注册</el-button>
  </el-form-item>
</el-form>
</body>
</template>
<script>
    export default {
        name: "Register",
        data(){
          return{
            registerForm:{
              tel: '',
              password: '',
              password2:'',
              nick:''
            }
          }
        },
      methods:{
        register(){
          var password = this.registerForm.password.replace(/(^\s*)|(\s*$)/g, '');
          var password2 = this.registerForm.password2.replace(/(^\s*)|(\s*$)/g, '');
          var tel = this.registerForm.tel.replace(/(^\s*)|(\s*$)/g, '');
          var nick = this.registerForm.nick.replace(/(^\s*)|(\s*$)/g, '');
          if (!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(tel))) {
            alert(  "手机号码格式不正确");
            return;
          }
          if(nick===''|| nick === undefined || nick === null){
            alert("昵称不能为空");
            return ;
          }
          if(password===''|| password === undefined || password === null){
            alert("密码不能为空");
            return;
          }
          if(password === password2){
            this.$axios.post('/register',{
              tel: this.registerForm.tel,
              password: this.registerForm.password,
              nick: this.registerForm.nick
            }).then(successResponse=>{
              if(successResponse.data.code===200){
                localStorage.setItem('user',JSON.stringify(successResponse.data.data.user));
                this.$router.replace({path: '/mallIndex'})
              }else{
                this.$alert(successResponse.data.message, '消息提示', {
                  confirmButtonText: '确定',
                  callback: action => {
                  }
                });
              }
            }).catch(failResponse =>{

            })
          }else{
            alert("前后两次密码不一样");
          }
        }
      }
    }

</script>

<style scoped>
  #poster {
    background-image:url("../assets/bg.png");
    height: 100%;
    width: 100%;
    position: fixed;
  }
  body{
    margin: 0px;
  }
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
