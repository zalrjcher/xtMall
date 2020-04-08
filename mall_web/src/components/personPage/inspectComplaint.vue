<template>

  <div style="
    width: 900px;
    margin-left: 50px;
    border: 1px solid #dcdcdc;
    ox-shadow: 0 0px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;">
    <div class="title">
      待处理投诉
    </div>
    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        fixed
        prop="userName"
        label="昵称"
        >
      </el-table-column>
      <el-table-column
        prop="commodityName"
        label="手机号码"
       >
      </el-table-column>
      <el-table-column
        prop="text"
        label="投诉原因"
        >
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        >
        <template slot-scope="scope">
          <el-button  type="text" size="small"  @click="DoComplaint(scope.row,2)" >通过</el-button>
          <el-button  type="text" size="small" @click="DoComplaint(scope.row,0)" >拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
    export default {
        name: "",
        data(){
          return{
            tableData:[],
          }
        },
      methods:{
        DoComplaint(row,state){
          var str
          if(state===2){
            str='该投诉已经通过'
          }if(state===0){
            str='该投诉不被允许'
          }
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
          this.$alert('是否同意'+str, '消息提示', {
            confirmButtonText: '确定',
            callback: action => {
              this.$axios.post('doComplaint',{
                token:token,
                userId:userId,
                state:state,
                complaintId:row.id,
              }).then(resp=>{
                if(resp.data.code===200){
                  this.tableData=resp.data.data.complaintList
                  const h = this.$createElement;
                  this.$notify({
                    title: '消息提示',
                    message: h('i', { style: 'color: teal'},str)
                  });
                }
              })
            }
          });

        }
      },
      created:function(){
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

        this.$axios.post('getComplaint',{
          token:token,
          userId:userId,
        }).then(resp=>{
          if(resp.data.code===200){
            this.tableData=resp.data.data.complaintList
          }
        })
      },
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

</style>
