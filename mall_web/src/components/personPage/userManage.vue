<template>
  <div style="
    width: 900px;
    margin-left: 50px;
    border: 1px solid #dcdcdc;
    ox-shadow: 0 0px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;">
    <div class="title">
      用户管理
    </div>
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          fixed
          prop="nick"
          label="昵称"
          width="150">
        </el-table-column>
        <el-table-column
          prop="tel"
          label="手机号码"
          width="120">
        </el-table-column>
        <el-table-column
          prop="status"
          :formatter="checkState"
          label="状态"
          width="120">
        </el-table-column>
        <el-table-column
          prop="lastTime"
          :formatter="dateFormat"
          label="最后登陆时间"
          width="120">
        </el-table-column>
        <el-table-column
          prop="createTime"
          :formatter="dateFormat"
          label="注册时间"
          width="120">
        </el-table-column>
        <el-table-column
          prop="money"
          :formatter="checkMoney"
          label="余额"
          width="120">
        </el-table-column>
        <el-table-column
          prop="score"
          label="评分"
          width="120">
        </el-table-column>
        <el-table-column
          prop="comCount"
          label="投诉次数"
          width="120">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="changeUserState(scope.row,0)" type="text" size="small" v-if="checkStates(scope.row,scope.column)" >禁用</el-button>
            <el-button @click="changeUserState(scope.row,1)" type="text" size="small" v-if="checkStatesUse(scope.row,scope.column)">启用</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
</template>

<script>
  import moment from 'moment'
    export default {
        name: "",
      methods: {
        changeUserState(row,state) {
          var str
          var str1
          if(state===0){
            str = '是否确定禁用该账户，禁用后无法上架商品'
            str1='禁用'
          }
          if(state===1){
            str = '是否确定启用用该账户，启用后可以上架商品'
            str1='启用'
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
          this.$alert(str, '消息提示', {
            confirmButtonText: '确定',
            callback: action => {
              this.$axios.post('changeUserState',{
                token:token,
                userId:userId,
                otherId:row.id,
                state:state
              }).then(resp=>{
                if(resp.data.code===200){
                  this.tableData=resp.data.data.userList
                  const h = this.$createElement;
                  this.$notify({
                    title: '消息提示',
                    message: h('i', { style: 'color: teal'},'该账户'+str1+'成功')
                  });
                }
              })
            }
          });

        },
        checkStates(row,column){
          return row.status === 1 ;
        },
        checkStatesUse(row,column){
          return row.status !== 1
        },
        checkMoney(row,column){
          return row[column.property]/100
        },
        checkState(row,column){
          let state = row[column.property];
          if(state===1){
            return'正常'
          }
          if(state===0){
            return'禁用'
          }
        },
        dateFormat:function(row,column){
            const date = new Date(row[column.property]);
            return moment(date).format("YYYY-MM-DD ")
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
          this.$axios.post('getAllUser',{
            token:token,
            userId:userId,
          }).then(resp=>{
            if(resp.data.code===200){
              this.tableData=resp.data.data.userList
            }
          })
      },
      data() {
        return {
          tableData: []
        }
      }
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
