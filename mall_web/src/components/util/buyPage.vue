<template>
  <div>
    <div style="height: 100px; width: 1220px"></div>
    <div class="box" style="margin-top: 100px" >
      <div class="title">
        收货地址
        <el-button @click="showEdit" style="background-color:transparent ;border: transparent ; line-height: 60px;
    font-size: 18px;
    "> 添加收货地址
        </el-button>
      </div>

      <el-radio-group v-model="radio">
      <div style="border: 1px solid #6a8fe5;
      width: 270px;
      height: 158px;
      padding-left: 30px;
       float: left;
       padding-top: 30px" v-for="item in address " :key="item.id">
        <el-radio :label="item.id">
       <span> 收货人姓名：{{item.name}}</span><br>
          <span style="display: inline-block;margin-left:28px">收货人电话：{{item.tel}}</span><br>
       <span style="display: inline-block;margin-left:28px"> 收货地址：{{item.adress}}</span>
        </el-radio>
      </div>
      </el-radio-group>
      <EditAddress ref="editAddress"></EditAddress>
    </div>
  <div class="box" style="margin-top: 100px">
    <div class="title">
      购买清单
    </div>
    <div style="height: 250px; width: 1220px; background-color: white ; padding-bottom: 20px">
      <div style="padding-left: 100px;padding-top: 20px;">
        <div style="border: 1px solid #ebebeb;float: left">
          <img :src="commodity.image" style="
              display: block;
              width: 100px;
              height: 100px;">
        </div>
        <div style="
                margin-left: 15%;
                width: 300px;
                float: left; margin-top:3%">{{commodity.name}}
        </div>
      </div>
      <div>
        <div style="
          text-align: center;
          width: 112px;float: left;margin-top:2.5%">单价：<span style="color: red">￥{{commodity.price/100}}</span>
        </div>
        <div style="
          text-align: center;
          width: 150px; float: left;margin-top:2.8%;margin-left: 50px">购买数量 ：{{num}}
        </div>

      </div>
      <div style="clear: both;"></div>
      <div style=" margin-top:40px;border-top: 1px solid #dcdcdc ; height: 100px;width: 1220px; text-align: right ; ">
        <span class="h4">应付总金额：</span>
        <span class="money" style="color: red ; margin-right: 30px" >￥{{commodity.price*num/100}}</span>
        <el-button @click="shop"type="primary" style="width: 145px; height: 60px; margin-right: 50px;margin-top: 20px">立即购买</el-button>
      </div>
  </div>
  </div>
  </div>
</template>

<script>
  import EditAddress from './editAddress'
  export default {
    name: "",
    components:{EditAddress},
    data() {
      return {
        num: '',
        radio: '',
        commodity: {
          id: '',
          name: '',
          price: '',
          introduce: '',
          image: '',
          detailed: '',
          number: '',
          type: ''
        }, user: {
          id: '',
          nick: '',
          password: '',
          tel: '',
          static: '',
          money: ''
        }, address:[{
          name:'',
          id:'',
          adress:'',
          tel:'',
          userId:''
        }],
      }
    },
    methods:{
      getAddress(){
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
        this.$axios.post('getAllAddress',{
          token: token,
          userId: userId
        }).then(resp=>{
          if(resp.data.code === 200 ){
            console.log("address"+resp)
            this.address = resp.data.data.addresses
          }
        })
      },
      showEdit(){
        this.$refs.editAddress.dialogFormVisible=true;
      },
      shop(){
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
        console.log(this.radio)
        this.$axios.post('shop',{
          commodityId: this.commodity.id,
          token: token,
          userId: userId,
          addressId:this.radio,
          number:this.num
        }).then(resp=>{
          if(resp.data.code===200){
            this.$alert('够买成功', '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'info',
                  message: `action: ${ action }`
                });
              }
            });
          }
        })
      }
    },
    created: function () {
      var commodityId = localStorage.getItem('buyCommodityId')
      var num = localStorage.getItem('buyNum')
      this.num = num
      console.log(commodityId)
      var token;
      var userId
      var user = JSON.parse(localStorage.getItem('user'));
      if (user === null) {
        userId = 0;
        token = 0;
      } else {
        this.user = user
        token = user.token;
        userId = user.id;
      }
      this.$axios.post('getGoodByTyId', {
        commodityId: commodityId,
        token: token,
        userId: userId
      }).then(resp => {
        if (resp.data.code === 200) {
          this.commodity = resp.data.data.commodity
          console.log(this.commodity)
        }
      })
      this.$axios.post('getAllAddress',{
        token: token,
        userId: userId
      }).then(resp=>{
        if(resp.data.code === 200 ){
          console.log("address"+resp)
          this.address = resp.data.data.addresses
        }
      })
    }
  }
</script>

<style scoped>
  .box {
    clear: both;
    width: 1220px;
    max-width: 1250px;
    margin: auto;
    border: 1px solid #dcdcdc;
    ox-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;
    overflow: hidden;
  }


  .title {
    padding-left: 30px;
    height: 60px;
    padding: 0 10px 0 24px;
    border-bottom: 1px solid #d4d4d4;
    border-radius: 8px 8px 0 0;
    box-shadow: 0 1px 7px rgba(0, 0, 0, .06);
    background: #f3f3f3;
    background: linear-gradient(#fbfbfb, #ececec);
    line-height: 60px;
    font-size: 18px;
    color: #333;
    display: -ms-flexbox;
    display: flex;
    justify-content: space-between;
    align-items: center;

  }
.h4{
  margin-block-start: 1.33em;
  margin-block-end: 1.33em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
}
.money{
  display: inline-block;
  position: relative;
  top: 3px;
  margin-top: -4px;
  font-size: 24px;
  color: #d44d44;
  font-weight: 700;
}
span{
  line-height: 30px;
}
  .el-icon-circle-plus-outline {
    margin: 35px 0 0 20px;
    font-size: 100px;
    float: left;
    cursor: pointer;
  }
</style>
