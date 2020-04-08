<template>
  <div style="
    width: 900px;
    margin-left: 50px;
    border: 1px solid #dcdcdc;
    ox-shadow: 0 0px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;">
    <div class="title">
      个人信息
      <el-button @click="loginOut" style="background-color:transparent ;border: transparent ; line-height: 60px;
    font-size: 18px;
    "> 退出登录
      </el-button>
    </div>
    <div style="background-color: white; border: 1px solid transparent;padding-left: 40px;height: 450px">
      <p>昵称：
        <el-input style="width: 200px" type="text" v-model="user.nick"
                  auto-complete="off" placeholder="请输入手机号码"></el-input>
      </p>
      <p>手机号码：{{user.tel}}</p>
      <p>性别：
        <el-radio-group v-model="radio">
          <el-radio :label="3">男</el-radio>
          <el-radio :label="6">女</el-radio>
          <el-radio :label="9">保密</el-radio>
        </el-radio-group>
      </p>
      <p>余额：￥{{user.money/100}} &nbsp; &nbsp;&nbsp;<el-button @click="open" round size="mini">充值</el-button></p>
      <p style="font-size: 12px">兴趣爱好：
        请选择您感兴趣的分类，给您最精准的推荐</p>
      <div style="padding-left: 40px">
        <div>
          <el-checkbox v-model="checked1" label="图书音像/电子书刊" border></el-checkbox>
          <el-checkbox v-model="checked2" label="家用电器" border></el-checkbox>
          <el-checkbox v-model="checked3" label="手机/数码" border size="medium"></el-checkbox>
        </div>
        <div style="margin-top: 20px">
          <el-checkbox v-model="checked3" label="鞋靴/箱包/钟表/奢侈品" border size="medium"></el-checkbox>
          <el-checkbox v-model="checked3" label="食品饮料/保健食品" border size="medium"></el-checkbox>
          <el-checkbox v-model="checked4" label="服饰内衣/珠宝首饰" border size="medium"></el-checkbox>
        </div>
      </div>


      <div style="padding-top: 50px;padding-left: 700px">
        <el-button @click="subPersonMessage" type="primary" round >提交</el-button>
      </div>

    </div>
  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      return {
        user: {
          id: '',
          nick: '',
          password: '',
          tel: '',
          static: '',
          money: ''
        },
        radio: 3
      }
    },
    created: function () {
      var user = JSON.parse(localStorage.getItem('user'));
      this.user = user;
    },
    methods:{
      subPersonMessage(){
        var token;
        var userId;
        var user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
          userId = 0;
          token = 0;
        } else {
          token = user.token;
          userId = user.id;
        }
        this.$axios.post('subPersonMessage',{
          userId:userId,
          token:token,
          nick: this.user.nick,
        }).then(resp=>{
          if(resp.data.code===200){
            this.$alert('信息提交成功', '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
                localStorage.removeItem('user')
                localStorage.setItem('user',JSON.stringify(resp.data.data.user))
                this.$event.$emit("getUserAgain");
              }
            });
          }
        })
      },
      open() {
        this.$prompt('请输入充值金额', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^([1-9][0-9]*)$/,
          inputErrorMessage: '金额格式不正确'
        }).then(({ value }) => {
          var token;
          var userId;
          var user = JSON.parse(localStorage.getItem('user'));
          if (user === null) {
            userId = 0;
            token = 0;
          } else {
            token = user.token;
            userId = user.id;
          }
          this.$axios.post('addMoney',{
            userId:userId,
            token:token,
            money: value,
          }).then(resp=>{
            if(resp.data.code===200){
              this.$message({
                type: 'success',
                message: '成功充值: ' + value+'元'
              });
              var user1 = JSON.parse(localStorage.getItem('user'));
              user1.money =resp.data.data.money
              localStorage.setItem('user',JSON.stringify(user1));
              this.user.money=resp.data.data.money;
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消充值'
          });
        });
      },
      loginOut(){
        var token;
        var userId
        var user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
          userId = 0;
          token = 0;
        } else {
          token = user.token;
          userId = user.id;
        }
        this.$axios.post('loginOut',{
          userId: userId,
          token: token,
        }).then(resp=>{
          if(resp.data.code===200){
            localStorage.removeItem('user');

            this.$alert('成功退出登录', '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.push({path:"mallIndex"})
                this.$event.$emit("loginOut");
              }
            });

          }else{
            this.$alert('退出登录失败', '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
              }
            });
          }
        })
      }
    }

  }
</script>

<style scoped>
  .title {


    position: relative;
    z-index: 10;
    height: 60px;
    padding: 0px 30px 0 24px;
    border-bottom: 1px solid #d4d4d4;
    border-radius: 8px 8px 0 0;
    box-shadow: 0 1px 7px rgba(0, 0, 0, .06);
    background: #f3f3f3;
    background: linear-gradient(#fbfbfb, #ececec);
    line-height: 60px;
    font-size: 18px;
    color: #333;
    display: flex;
    justify-content: space-between;
    align-items: center;

  }

  p {
    color: #666;
  }
</style>
