<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <body id="poster">
  <el-form class="login-container" label-position="left"
           label-width="0px">
    <h3 class="login_title">登录商城</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.tel"
                auto-complete="off" placeholder="请输入手机号码"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
      <el-checkbox size="20px" style="float: left"> 记住密码 </el-checkbox>
      <a href="javascript:;" style="float: right;" @click="toRegister" >注册</a>

    <el-form-item style="width: 100%;margin-top: 50px">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>

  export default {
    name: 'Login',
    data () {
      return {
        loginForm: {
          tel: '',
          password: ''
        },
        responseResult: []
      }
    },
    methods: {
      login () {
        this.$axios
          .post('/login', {
            tel: this.loginForm.tel,
            password: this.loginForm.password
          })
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              this.$router.replace({path: '/mallIndex'})
              console.log(successResponse.data.data.user);
              localStorage.setItem('user',JSON.stringify(successResponse.data.data.user));
            }else{
              this.$alert(successResponse.data.message, '消息提示', {
                confirmButtonText: '确定',
                callback: action => {
                }
              });
            }
          })
          .catch(failResponse => {
          })
      },
      toRegister () {
        this.$router.replace({
          path: '/register'
        })
      },
    }
  }
</script>
<style>
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
  a{
    text-decoration: none;
    color: #5079d9;
  }
  a:hover {
    color:
      #6b95ea;
  }
</style>
