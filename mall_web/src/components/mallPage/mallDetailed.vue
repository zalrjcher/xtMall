<template>
  <div style="border:  1px transparent solid;margin-top: 200px;clear: both">
    <div class="box" style="border: 1px transparent solid;margin-top: 30px">
      <img :src="commodity.image" style="width: 440px ; height:  440px ;margin-left:50px">
      <div style="
      width: 450px;
      margin-left: 10px;
      float: right">
        <div style="
        overflow: hidden;
        padding: 8px 8px 10px 10px;
        position: relative;">
          <h4>{{commodity.name}}</h4>
          <el-divider></el-divider>
          <h6><span class="good">{{commodity.introduce}}</span>
            <span class="goodPrice">￥{{commodity.price/100}}</span></h6>
          <el-divider></el-divider>
        </div>
        <div style="overflow: hidden;
        padding-left: 10px;padding-right:8px;
        position: relative;">
        <span style="
          padding-right: 20px;
          font-size: 14px;
          color: #8d8d8d;
          line-height: 36px;">购买数量</span>
          <el-input-number v-model="num" :min="1" :max="10" label="描述文字"
                           style=" margin-left:10px;padding: 0px; margin-bottom: 10px"></el-input-number>
          <h6><span class="good">用户评分：{{score}}</span></h6>
          <el-divider></el-divider>
        </div>

        <div style="  padding-left: 10px;margin-top: 10px">
          <el-button @click="joinShoppingCart(commodity.id,num)" type="primary" style="width: 145px;">加入购物车</el-button>
          <el-button @click="buyCommodity(commodity.id)" style="width: 145px;margin-left: 10%">立即购买</el-button>
        </div>
      </div>
    </div>
    <div style="
            overflow: hidden;
            clear: both;
            width: 1220px;
            border-radius: 8px;
            border: 1px solid #dcdcdc;
            box-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
            margin:30px auto 0px auto">
      <img :src="commodity.detailed" style="width: 100% ;height:auto">
    </div>
  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      return {
        score: '',
        num: 1,
        commodity: {
          id: '',
          name: '',
          price: '',
          introduce: '',
          image: '',
          detailed: '',
          number: '',
          type: ''
        }
      }
    },
    methods: {
      buyCommodity(id) {
        localStorage.setItem("buyCommodityId", id);
        localStorage.setItem("buyNum", this.num);
        this.$router.replace('/buyPage')
      },
      joinShoppingCart(id, number) {
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
        this.$axios.post('joinShoppingCart', {
          userId: userId,
          token: token,
          number: number,
          commodityId: id
        }).then(resp => {
          if (resp.data.code === 200) {
            this.$alert('加入购物车成功', '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
              }
            });
          }
          if (resp.data.code === -700) {
            this.$alert('登录失效,或者未登录,请重新登录', '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.push({path: "login"})
              }
            });
          }
        })
      }
    },
    created: function () {
      var commodityId = localStorage.getItem('commodityId');

      this.$axios.post('/getGoodByTyId', {

        commodityId: commodityId
      }).then(resp => {
        if (resp.data.code === 200) {
          this.commodity = resp.data.data.commodity
          this.score = resp.data.data.score
        }
      })

    }
  }
</script>

<style scoped>
  .box {
    clear: both;
    width: 1220px;
    overflow: hidden;
    background: #fff;
    border-radius: 8px;
    border: 1px solid #dcdcdc;
    box-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    margin: 20px auto 0px auto;
  }

  h4 {
    font-size: 24px;
    line-height: 1.25;
    color: #000;
    margin-bottom: 13px;
    font-weight: 400;
  }

  .good {
    font-size: 14px;
    line-height: 20px;
    color: #bdbdbd;
  }

  .goodPrice {
    color: #d44d44;
    font-weight: 700;
    font-size: 20px;
    line-height: 20px;
    text-align: right;
    margin-left: 20%;
  }
</style>
