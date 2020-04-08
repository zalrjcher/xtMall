<template>
  <!-- 已经评价订单-->
  <div style="
    width: 900px;
    margin-left: 50px;
    border: 1px solid #dcdcdc;
    ox-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;">
    <div class="box" style="">
      <div class="title">
        已完成订单
      </div>
      <div v-for="item in orderResults" :key="item.id"
           style="height: 300px; width: 900px; background-color: white ; border-bottom: black solid 2px  ">
        <div style="padding-left:10px;padding-top: 20px; padding-right: 10px">
          <div style=" height: 20px ; background-color: white">
            <span>订单编号：{{item.id}}</span>
            <span style="float: right;margin-right: 10px">下单时间：{{item.createTime}}</span>
          </div>
          <el-divider></el-divider>
          <div style="border: 1px solid #ebebeb;float: left">
            <img :src="item.image" style="
              display: block;
              width: 100px;
              height: 100px;">
          </div>
          <div style="
                margin-left: 10%;
                width:150px;
                float: left; margin-top:3.5%">{{item.name}}
          </div>
        </div>
        <div>

          <div style="
          text-align: center;
          width: 112px;float: left;margin-top:3.5%">单价：<span style="color: red">￥{{item.price/100}}</span>
          </div>
          <div style="
          text-align: center;
          width: 150px; float: left;margin-top:3.5%;margin-left: 50px">购买数量 ：{{item.number}}
          </div>
          <div style=";margin-top:3.5%; width: 200px; text-align: center; float: left;margin-left: 20px ">
            <span class="h4">总金额：</span>
            <span class="money" style="color: red ; margin-right: 30px">￥{{item.allPrice/100}}</span>
          </div>
        </div>
        <div v-if="item.address" style="width:880px; border: 1px transparent solid;; float:left;  line-height: 30px;padding-left: 10px ;">
          <el-divider></el-divider>
          <span style="display: inline-block"> 收货人姓名：{{item.address.name}}</span>
          <span style="display: inline-block;margin-left: 100px">收货人手机：{{item.address.tel}}</span>
          <span style="display: inline-block;margin-left: 100px">收货地址：{{item.address.adress}}</span>
          <span style="display: inline-block;float: right">
         </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "",

    data() {
      return {
        state:3,
        orderResults: [
          {
            image: '',
            name: '',
            price: '',
            allPrice: '',
            number: '',
            createTime: '',
            address: {
              name:'',
              id:'',
              adress:'',
              tel:'',
              userId:''
            }
          }
        ]
      }
    },
    methods:{

    },
    created: function () {
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
      this.$axios.post('getOrderByUserId', {
        userId: userId,
        token: token,
        state:this.state,
      }).then(resp => {
        if (resp.data.code === 200) {
          this.orderResults = resp.data.data.orderResults;
        }
      })
    }
  }
</script>

<style scoped>
  .box{
    border: 1px solid #dcdcdc;
    ox-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;
    overflow: hidden;
  }
  .title {

    padding-left: 30px;
    position: relative;
    z-index: 10;
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
    -ms-flex-pack: justify;
    justify-content: space-between;
    -ms-flex-align: center;
    align-items: center;

  }
  div{
    font-size: 14px;
    color:
      rgb(141, 141, 141);
    line-height: 30px;
  }
</style>
