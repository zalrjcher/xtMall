<template>
  <div>
    <el-dialog
      title="选择购物地址"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-radio-group v-model="radio">
        <div style="border: 1px solid #6a8fe5;
      width: 600px;
      padding-bottom: 10px;
      padding-left: 30px;
       padding-top: 10px" v-for="item in address " :key="item.id">
          <el-radio :label="item.id">
            <span> 收货人姓名：{{item.name}}</span><br>
            <span style="display: inline-block;margin-left:28px">收货人电话：{{item.tel}}</span><br>
            <span style="display: inline-block;margin-left:28px"> 收货地址：{{item.adress}}</span>
          </el-radio>
        </div>
      </el-radio-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        radio: '',
        dialogFormVisible: false,
        address:[{
          name:'',
          id:'',
          adress:'',
          tel:'',
          userId:''
        }],
        formLabelWidth: '120px'
      }
    },
    created:function(){
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
    methods: {
      clear () {
        this.radio=''
      },
      onSubmit () {
        this.$parent.addressId=this.radio
        this.dialogFormVisible=false
        this.$parent.shopByCart()
      }
    }
  }
</script>

<style scoped>
  .el-icon-circle-plus-outline {
    margin: 50px 0 0 20px;
    font-size: 100px;
    float: left;
    cursor: pointer;
  }
  span{
    line-height: 30px;
  }
</style>
