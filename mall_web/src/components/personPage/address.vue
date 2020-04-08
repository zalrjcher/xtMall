<template>
  <div style="
     width: 900px;
    margin-left: 50px;
    border: 1px solid #dcdcdc;
    ox-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;">
    <div class="title">
      收货地址<el-button  @click="showEdit" style="background-color:transparent ;border: transparent ; line-height: 60px;
    font-size: 18px;
    "> 添加收货地址</el-button>
    </div>
    <div style="border: 1px transparent solid; background-color: white; ">
    <div style="background-color: white; border-bottom: 2px black solid" v-for="item in address" :key="item.id ">
      <div style="margin-top: 20px;margin-right: 60px ;float: right; ">
        <el-popconfirm
          title="确定要下架该商品嘛？"
          @onConfirm="deleteAddress(item.id)"
        >
          <el-button slot="reference"  type="primary"  ><i class="el-icon-delete" ></i>&nbsp;&nbsp;删除</el-button>
        </el-popconfirm>
      </div>
      <p>收货人名字：{{item.name}}</p>
      <p>收货人手机：{{item.tel}}</p>
      <p>收货地址：{{item.adress}}</p>

    </div>
  </div>
    <EditAddress ref="editAddress"></EditAddress>
  </div>
</template>

<script>
  import EditAddress from '../util/editAddress'
    export default {
    components:{EditAddress},
        name: "",
      data(){
          return {
            address:[
              {
                name:'',
                id:'',
                adress:'',
                tel:'',
                userId:''
              }
            ]
          }
      },
      methods:{
        deleteAddress(id){
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
          this.$axios.post('deleteAddress',{
            addressId:id,
            userId:userId,
            token:token
          }).then(resp=>{
            if(resp.data.code ===200){
              this.address = resp.data.data.addresses;
              this.$alert('地址删除成功', '消息提示', {
                confirmButtonText: '确定',
                callback: action => {
                }
              });

            }
          })
        },
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
      },
      created:function () {
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
        this.$axios.post('getAllAddress',{
          token: token,
          userId: userId
        }).then(resp=>{
          if(resp.data.code === 200 ){
            console.log("address"+resp)
            this.address = resp.data.data.addresses;
          }
        })
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
   p{
    color: #666;
    padding-left: 20px;padding-right: 20px
  }
</style>
