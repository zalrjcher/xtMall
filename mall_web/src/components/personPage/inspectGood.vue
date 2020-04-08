<template>
  <div style="
    width: 900px;
    margin-left: 50px;
    border: 1px solid #dcdcdc;
    ox-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;">
    <div class="title">
      带审核商品
    </div>
    <div style="display: flex;
    align-items: center;
    flex: 1;
    justify-content: space-between;
    padding-top: 10px;
    position: relative;
    background-color: white">
      <el-row style="background-color: white">
        <el-col v-for="item in  commodities" :key="item.id" style="width: 100% ;">
          <div style="height: 100px; width: 900px; background-color: white ;border-bottom: 1px solid #d4d4d4; ;padding-bottom: 20px">
            <div style="padding-left: 30px;padding-top: 20px;">
              <div style="border: 1px solid #ebebeb;float: left">
                <img :src="item.image" style="
              display: block;
              width: 80px;
              height: 80px;">
              </div>
              <div style="
                margin-left: 30px;
                width: 200px;
                float: left; margin-top:4.2%">{{item.name}}
              </div>
            </div>
            <div>
              <div style="
          text-align: center;
          width: 112px;float: left;margin-top:4%">￥{{item.price/100}}
              </div>
              <div style="
          text-align: center;
          width: 150px; float: left;margin-top:3.8%;margin-left: 30px">数量 ：{{item.number}}
              </div>
            </div>
            <div style="margin-top: 20px;margin-left: 60px ;float: left; ">
              <el-popconfirm
                title="确定要上架该商品嘛？"
                @onConfirm="inspectCommodity(item.id,1)"
              >
                <el-button slot="reference"    >&nbsp;&nbsp;上架</el-button>
              </el-popconfirm>
              <el-popconfirm
                title="确定要下架该商品嘛？"
                @onConfirm="inspectCommodity(item.id,2)"
              >
                <el-button slot="reference"    >&nbsp;&nbsp;下架</el-button>
              </el-popconfirm>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import EditGood from './editGood'

  export default {
    name: "",
    data() {
      return {
        isUp: false,
        commodities: [{
          id: '',
          name: '',
          price: '',
          introduce: '',
          image: '',
          detailed: '',
          number: '',
          type: ''
        }],

      }
    },
    components: {EditGood},
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
      this.$axios.post('getInspectCommodity', {
        userId: userId,
        token: token,
      }).then(resp => {
        if (resp.data.code === 200) {
          this.commodities = resp.data.data.commodities;
        }
      })

    },
    methods: {
      inspectCommodity(id,state){
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
        var str ;
        if(state ===1){
          str='成功上架商品'
        }
        if(state===2){
          str='成功下架商品'
        }
        this.$axios.post('inspectCommodity',{
          commodityId:id,
          state:state,
          userId:userId,
          token:token
        }).then(resp=>{
          if(resp.data.code ===200){
            this.commodities = resp.data.data.commodities;
            console.log( this.commodities)
            this.$alert(str, '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
              }
            });

          }
        })
      },
      editGood() {
        this.$refs.edit.dialogFormVisible = true;
      },
    }
  }
</script>

<style scoped>
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
    color: rgb(98, 98, 98);
    font-size: 14px;
  }
</style>
