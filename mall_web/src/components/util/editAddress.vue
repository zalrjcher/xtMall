<template>
  <div>
    <el-dialog
      title="上架商品"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form style="text-align: left" ref="dataForm">
        <el-form-item label="收货人姓名" :label-width="formLabelWidth" prop="title">
          <el-input  v-model="address.name" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="收货人电话" :label-width="formLabelWidth" prop="author">
          <el-input  v-model="address.tel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="详细地址" :label-width="formLabelWidth" prop="author">
          <el-input   v-model="address.adress" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "",
      data(){
          return {
            address:{
              name:'',
              id:'',
              adress:'',
              tel:'',
              userId:''
            },
            dialogFormVisible: false,
            formLabelWidth: '100px'
          }
      },
      methods:{
          clear(){
            this.address={
              name:'',
              id:'',
              specificAddress:'',
              tel:'',
              userId:''
            }
          },
        onSubmit(){
          var token;
          var userId
          var user = JSON.parse(localStorage.getItem('user'));
          if(user===null){
            userId=0;token=0;
          }else{
            token=user.token;
            userId=user.id;

          }
          this.$axios.post('addAddress',{
            token:token,
            userId:userId,
            adress:this.address.adress,
            name:this.address.name,
            tel:this.address.tel
          }).then(resp=>{
            if(resp.data.code===200){
              this.dialogFormVisible=false;
              this.$parent.getAddress();
            }
          })
        }

      }
    }
</script>

<style scoped>

</style>
