<template>
  <div>
    <el-dialog
      title="评分"
      :visible.sync="dialogFormVisible"
     >
      <el-rate
        v-model="value"
        show-score
        text-color="#ff9900"
        score-template="{value}">
      </el-rate>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'EditGood',
    data () {
      return {
        id:'',
        state:'',
        value:0,
        dialogFormVisible: false,
        formLabelWidth: '120px'
      }
    },
    created:function(){
    },
    methods: {
      onSubmit(){
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

        this.$axios.post('score',{
          score:this.value,
          userId: userId,
          token: token,
        }),
        this.$axios.post('changeGoodState',{
          userId: userId,
          token: token,
          state:this.state,
          orderId:this.id,
        }).then(resp=>{
            if(resp.data.code ===200){
              this.dialogFormVisible = false,
              this.$parent.orderResults = resp.data.data.orderResults;
            }
          }
        )
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
</style>
