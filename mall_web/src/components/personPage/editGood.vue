<template>
  <div>
    <el-dialog
      title="上架商品"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="商品名称" :label-width="formLabelWidth" prop="title">
          <el-input v-model="form.title" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="价格" :label-width="formLabelWidth" prop="author">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="上架数量" :label-width="formLabelWidth" prop="author">
          <el-input v-model="form.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介" :label-width="formLabelWidth" prop="press">
          <el-input v-model="form.introduce" autocomplete="off" placeholder="用一句简短的话描述"></el-input>
        </el-form-item>
        <el-form-item label="图片" :label-width="formLabelWidth" prop="cover">
          <el-input v-model="form.image" autocomplete="off" placeholder="图片 URL"></el-input>
          <img-upload @onUpload="uploadImg" ref="imgUpload"></img-upload>
        </el-form-item>
        <el-form-item label="详细" :label-width="formLabelWidth" prop="cover">
          <el-input v-model="form.detailed" autocomplete="off" placeholder="图片 URL"></el-input>
          <img-upload @onUpload="uploadImg2" ref="imgUpload2"></img-upload>
        </el-form-item>
        <el-form-item label="商品类型" :label-width="formLabelWidth" prop="cid">
          <el-select v-model="form.type" placeholder="请选择分类" >
            <el-option v-for="item  in types" :label="item.type" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="id" style="height: 0">
          <el-input type="hidden" v-model="form.id" autocomplete="off"></el-input>
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
  import ImgUpload from"../util/ImgUpload"
  export default {
    name: 'EditGood',
    components:{ImgUpload},
    data () {
      return {
        dialogFormVisible: false,
        form: {
          title: '',
          price: '',
          introduce: '',
          image: '',
          detailed: '',
          number:'',
          type:''
        },
        isUp:false,
        types:[
          {id:'',type:''}
        ],
        formLabelWidth: '120px'
      }
    },
    created:function(){
        this.$axios.post('/getGoodType',{}).then(resp=>{
          if(resp.data.code === 200){
            this.types = resp.data.data.type;
          }
        })
    },
    methods: {
      clear () {
        this.form = {
          title: '',
          price: '',
          introduce: '',
          image: '',
          detailed: '',
          number:'',
          type:''
        }
      },
      uploadImg () {
        this.form.image = this.$refs.imgUpload.url
      },
      uploadImg2 () {
        this.form.detailed = this.$refs.imgUpload2.url
      },
      onSubmit () {
        var token;
        var userId
        var user = JSON.parse(localStorage.getItem('user'));
        if(user===null){
          userId=0;token=0;
        }else{
          token=user.token;
          userId=user.id;
        }
        this.$axios
          .post('/addGood', {
            userId:userId,
            token:token,
            title: this.form.title,
            price: this.form.price,
            introduce:this.form.introduce,
            image: this.form.image,
            detailed: this.form.detailed,
            number: this.form.number,
            type: this.form.type,
          }).then(resp => {
          if (resp && resp.status === 200) {
            this.dialogFormVisible = false
            this.$emit('upGood')
            this.$alert('上架成功', '消息提示', {
              confirmButtonText: '确定',
              callback: action => {
              }
            });
          }
        })
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
