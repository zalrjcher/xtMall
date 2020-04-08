<template>
  <div>
    <el-dialog
      title="投诉"
      :visible.sync="dialogFormVisible"
     >
      <el-select v-model="value" placeholder="请选择" style="margin-left: 50px">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
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
        options: [{
          value: '商品与实物不符',
          label: '商品与实物不符'
        }, {
          value: '发货慢',
          label: '发货慢'
        }, {
          value: '商家价格过高',
          label: '商家价格过高'
        }, {
          value: '商品损坏',
          label: '商品损坏'
        }, {
          value: '对商品不满意',
          label: '对商品不满意'
        }],
        value: '',
        id:'',
        state:'',
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
        this.$axios.post('complaint',{
          value:this.value,
          userId: userId,
          token: token,
          orderId:this.id,
        }).then(resp=>{
          if(resp.data.code===200){
            console.log("GPFIUDGSFPIUDSGFUIDGS")
            const h = this.$createElement;
            this.$notify({
              title: '消息提示',
              message: h('i', { style: 'color: teal'},'投诉成功')
            });
          }
        })
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
