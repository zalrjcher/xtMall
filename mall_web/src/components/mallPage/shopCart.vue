<template>
  <div class="box" style="margin-top: 100px">
    <div class="title">
      购物车清单
    </div>
    <div style="width: 320px; ;float: right; margin-right: 0px;text-align: center">
      <span style="display: block;font-size: 24px;color: red;font-weight: 700;margin-top: 50px">合计：￥{{allMoney/100}}</span>
      <el-button @click="showAddress"style="width: 160px;height: 50px ;margin-top: 30px" type="primary">立即购买</el-button>
    </div>
    <div v-for="item in  orderResults" :key="item.id"
         style="height: 200px; width: 900px; background-color: white ; border-bottom: black solid 2px ;float: left ">
      <div style="padding-left:10px;padding-top: 20px; padding-right: 10px">
        <div style="border: 1px solid #ebebeb;float: left">
          <img :src="item.image" style="
              display: block;
              width: 100px;
              height: 100px;">
        </div>
        <div style="
                margin-left: 10%;
                width:200px;
                font-size: 20px;
                float: left; margin-top:3.5%">{{item.name}}
        </div>
      </div>
      <div>
        <div style="
          text-align: center;
          width: 200px;float: left;margin-top:3.5%">库存：<span style="">{{item.commodityNum}}</span>
        </div>
        <div style="
          text-align: center;
          width: 200px;float: left;margin-top:3.5%">单价：<span style="color: red">￥{{item.price/100}}</span>
        </div>
      </div>
      <div style="clear: both"></div>
      <div style=" margin-top: 30px">
        <div style="overflow: hidden;
        padding-left: 10px;padding-right:8px;
        position: relative;">
        <span style="
          padding-right: 20px;margin-left: 150px;
          ">购买数量</span>
          <el-input-number v-model="item.number" :min="1" :max="item.commodityNum" label="描述文字"
                           @change="handleChange"      style=" margin-left:10px;padding: 0px; margin-bottom: 10px"></el-input-number>
          <span class="h4" style=" margin-left: 250px">小计：</span>
          <span class="money" style="color: red ; margin-right: 30px">￥{{item.number*item.price/100}}</span>
        </div>
      </div>
    </div>
  <EditShopAddress ref="edit"></EditShopAddress>
  </div>
</template>

<script>
  import EditShopAddress from './editShopAddress'
    export default {
        name: "",
      components:{EditShopAddress},
      data(){
          return{
            allMoney:0,
            addressId:'',
            orderResults: [
              { id:'',
                image: '',
                name: '',
                price: '',
                allPrice: '',
                number: '',
                commodityNum:''
              }
            ],
            ids:[]
          }
      },
      methods:{
        showAddress(){
          this.$refs.edit.dialogFormVisible = true;
        },
        handleChange(){
          var money = 0 ;
          for(var i = 0 ; i < this.orderResults.length ; i++){
            money+=this.orderResults[i].number*this.orderResults[i].price;
          }
          this.allMoney=money;
        },


        shopByCart(){
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
          this.ids=[];
          for(var i = 0 ;i<this.orderResults.length;i++){
            var id = this.orderResults[i].id;
            var number = this.orderResults[i].number;
            this.ids.push({id,number})
          }
          console.log("ids"+ JSON.stringify(this.ids))
          this.$axios.post('shoppingCart',{
            userId:userId,
            token:token,
            ids: this.ids,
            addressId:this.addressId
          }).then(resp=>{
            if(resp.data.code===200){
              this.orderResults=[]
              this.allMoney='',
              this.$alert('够买成功', '消息提示', {
                confirmButtonText: '确定',
                callback: action => {
                  this.$router.replace({path: '/reGoodBill'})
                }
              });
            }
          })
        }
      },
      created:function () {
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
        this.$axios.post('/getShopCart',{
          userId:userId,
          token:token
        }).then(resp=>{
          if(resp.data.code === 200){
            this.orderResults =resp.data.data.orderResults
            for( var i = 0 ; i< this.orderResults.length; i++){
             this.allMoney += this.orderResults[i].allPrice
            }
            console.log( this.orderResults)
            console.log( this.allMoney)
          }
        })
      }
    }
</script>

<style scoped>
  div{
    font-size: 16px;
    color: black;
    line-height: 36px
  }
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
    position: relative;
    z-index: 10;
    height: 60px;
    padding: 0 10px 0 24px;
    border-bottom: 1px solid #d4d4d4;
    border-radius: 8px 8px 0 0;
    box-shadow: 0 1px 7px rgba(0,0,0,.06);
    background: #f3f3f3;
    background: linear-gradient(#fbfbfb,#ececec);
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
</style>
