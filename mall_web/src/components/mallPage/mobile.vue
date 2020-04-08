<template>
  <div class="box" style="margin-top: 100px">
    <div class="title">
      热门手机
    </div>
    <div>
      <div>
        <el-row style="width: 1220px" gutter="2" >
          <el-col :span="6" v-for="item  in mobiles">
            <div class="mobile">
              <img :src="item.image" :alt="item.title" >
              <h6>{{item.name}}</h6>
              <h3>{{item.introduce}}</h3>
              <div class="goodPrice">
                <div>
                  <a  @click="toDetailed(item.id)" href="javascript:;"><input type="button" value="查看详情" class="btn"/></a>
                  <input type="button"  @click="joinShoppingCart(item.id,1)" class="btn2" style="background-color: #678ee7;border: 1px solid #5c81e3; background-image: linear-gradient(180deg,#678ee7,#5078df); color: white" value="加入购物车"/>
                </div>
                <p class="p">
                  <span style="font-size: 14px"> ￥</span>
                  {{item.price/100}}
                </p>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "product",
    data() {
      return {
        mobiles: [
          {id:'',
            image:'',
            name: '',
            introduce:'',
            price:'',
          },
        ]
      }
    },
    methods:{
      toDetailed(id){
        localStorage.setItem("commodityId",id);
        this.$router.replace({path:'/mallDetailed'})
      },
      joinShoppingCart(id,number){
        var token;
        var userId
        var user = JSON.parse(localStorage.getItem('user'));
        if(user===null){
          userId=0;token=0;
        }else{
          token=user.token;
          userId=user.id;
        }
        this.$axios.post('joinShoppingCart',{
          userId:userId,
          token:token,
          number:number,
          commodityId:id
        }).then(resp=>{
          if(resp.data.code === 200){
            this.$alert('加入购物车成功', '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
              }
            });
          }
        })
      },
    },
    created:function () {
      this.$axios.post('getGoodByType',{
        type:1,
      }).then(resp=>{
        if(resp.data.code === 200){
          this.mobiles = resp.data.data.commodities
          console.log(this.mobiles)
        }
      })
    }
  }

</script>

<style scoped>

  input{
    display: inline-block;
    cursor: pointer;
    text-align: center;
    width: 100px;
    height: 30px;
    line-height: 28px;
    vertical-align: middle;
    border: 1px solid #e1e1e1;
    border-radius: 4px;
    font-size: 12px;
    color: #646464;
    background-color: #f9f9f9;
    background-image: linear-gradient(180deg,#fff,#f9f9f9)
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

  .mobile:hover {
    transform: translateY(-3px);
    box-shadow: 1px 1px 20px #999;
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

  .mobile {
    background: #fff;
    width:100%;
    transition: all .5s;
    height: 430px;
    background: #fff;
    border: 1px solid transparent;
  }

  img {
    margin: 50px auto 10px;
    width: 206px;
    height: 206px;
    display: block;
  }
  h3{
    text-align: center;
    line-height: 1.2;
    font-size: 12px;
    color: #d0d0d0;
    padding: 10px;
  }
  h6{
    line-height: 1.2;
    font-size: 16px;
    color: #424242;
    margin: 0 auto;
    padding: 0 14px;
    text-align: center;
    overflow: hidden;
  }
  .goodPrice{
    margin: 15px 0;
    height: 30px;
    text-align: center;
    line-height: 30px;
    color: #d44d44;
    font-family: Arial;
    font-size: 18px;
    font-weight: 700;
  }
</style>
