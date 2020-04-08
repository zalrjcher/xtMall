<template>
  <div style="width: 100%;height: 100%; ">
    <div style="border:  1px solid transparent ; width: 100%;height: 100px; background-color: #2c3e50; ">
      <div style=" width: 1320px ; margin: 30px auto; ">
        <div style="width: 300px ; margin-left: 35%; float: left;">
          <el-input placeholder="输入搜索商品" style="width: 300px; height: 10px"></el-input>
        </div>
        <table style="float: right;margin-right:20px; margin-top: 5px;">
          <tr>
            <td style="padding-right:50px"><a  @click="toAllGood"href="javascript:;">全部商品</a></td>
            <td style="padding-left:100px"><a @click="toPersonCenter" class="el-icon-user-solid" href="javascript:;">&nbsp;&nbsp;{{user.nick}}</a>
            </td>
            <td style="padding-left:50px">
              <a  @click="toShopCart" class="el-icon-shopping-cart-2" href="javascript:;"></a>

            </td>
          </tr>
        </table>
        <div style="width: 100%;height:78px; "></div>
      </div>
    </div>
  </div>
</template>


<script>
  export default {
    inject:['reload'],
    name: "home",
    data() {
      return {
        user:{
          nick: '你好，请登录'
        }

      }
    },
    mounted(){
      this.$event.$on('loginOut',function () {
        this.$forceUpdate();
        this.nick='请登录';
        location.reload();
        console.log(this.nick)
      });
        this.$event.$on('getUserAgain',function () {
          location.reload();
          console.log(this.nick)
        })
    },
    created: function () {
      console.log(this.nick)
      var user = JSON.parse(localStorage.getItem('user'));
      if(user===null){
        return;
      }
      this.$axios.post('/checkToken', {
        token: user.token,
        userId: user.id
      }).then(successResponse => {
        if (successResponse.data.code === -700) {
          user.token=0;
          localStorage.removeItem('user');
          this.$router.replace({path: '/mallIndex'})
        }
        if (successResponse.data.code === 200) {
          this.user.nick = user.nick;
        }
      })
    },
    methods: {
      toAllGood(){
        localStorage.setItem("type",'0');
        this.$router.replace({path:"allGood"})
        this.$event.$emit("formOthers");
      },
      toShopCart(){
        this.$router.replace({path:"shopCart"})
      },
      toPersonCenter() {
        var user = JSON.parse(localStorage.getItem('user'));
        if(user===null){
          this.$router.replace({path: '/login'})
          return;
        }
        this.$axios.post('/checkToken', {
          token: user.token,
          userId: user.id
        }).then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$router.replace({path: '/personMessage'})
          }
          if (successResponse.data.code === -700) {
            this.$router.replace({path: '/login'})
          }
        })
      }
    }
  }
</script>

<style scoped>


  {
    box-shadow: inset 0 0 38px rgba(0, 0, 0, .08)
  ;
    transition: all .15s ease
  ;
  }
  a {
    text-decoration: none;
    color: #eaeaea;
  }

  li {
    display: inline-block;
  }
</style>
