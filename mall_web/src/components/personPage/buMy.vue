<template>
  <div style="
    width: 900px;
    margin-left: 50px;
    border: 1px solid #dcdcdc;
    ox-shadow: 0 0px 8px -6px rgba(0, 0, 0, .1);
    border-radius: 8px;">
    <div class="title">
      购买我的商品
    </div>
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          fixed
          prop="nickName"
          label="昵称"
          width="150">
        </el-table-column>
        <el-table-column
          prop="commodityImg"
          label="商品图片"
          width="120">
          <template scope="scope">
            <img :src="scope.row.commodityImg" width="100" height="100"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="commodityName"
          label="商品名称"
          width="120">
        </el-table-column>
        <el-table-column
          prop="byTime"
          :formatter="dateFormat"
          label="购买时间"
          width="120">
        </el-table-column>
        <el-table-column
          prop="byNum"
          label="购买数量"
          width="120">
        </el-table-column>
        <el-table-column
          prop="allMoney"
          :formatter="checkMoney"
          label="总价"
          width="120">
        </el-table-column>
        </el-table-column>
      </el-table>
    </div>
</template>

<script>
  import moment from 'moment'
    export default {
        name: "",
      methods: {

        checkMoney(row,column){
          return row[column.property]/100
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
          this.$axios.post('getByMy',{
            token:token,
            userId:userId,
          }).then(resp=>{
            if(resp.data.code===200){
              this.tableData=resp.data.data.byMyList
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
