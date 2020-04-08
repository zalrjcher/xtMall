<template>
  <div style=" width: 100%;height: 100px; background-color: white ;text-align:center ; border: 1px solid transparent;float: left ">
    <el-breadcrumb separator-class="el-icon-arrow-right"  style=" margin-left: 10%; font-size: 17px;margin-top:3.3%">
      <el-breadcrumb-item :to="{ path: '/mallIndex' }"><a href="javascript:;">首页</a></el-breadcrumb-item>
      <el-breadcrumb-item v-for="item in types " :key="item.id"><a @click="toAllGood(item.id)" href="javascript:">{{item.type}}</a></el-breadcrumb-item>
      <el-breadcrumb-item ><a href="javascript:"></a></el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
    export default {
        name: "navigation",
      data(){
          return{types:[{
              id:'',
              type:''
            }]}
          },
      methods:{
          toAllGood(id){
            localStorage.setItem("type",id);
            this.$router.replace({path:"allGood"})
            this.$event.$emit("formOthers");
          }
      },
      created:function () {
        this.$axios.post('getGoodType',{})
          .then(resp=>{
            if(resp.data.code === 200){
              this.types = resp.data.data.type;
              console.log( this.types)
            }
          })
      }
    }
</script>

<style scoped>

</style>
