<template>
    <div style="border:  1px solid transparent ; width: 100%;height: 100%;  clear: both;">
    <el-carousel :interval="4000" type="card" height="400px" style="
    border: 1px solid #dcdcdc;
    ox-shadow: 0 3px 8px -6px rgba(0, 0, 0, 0.1);
    border-radius: 8px; margin-top:70px;">
      <el-carousel-item v-for="item in images" :key="item">
       <img :src="item" style="width: 100%;height: 100%">
      </el-carousel-item>
    </el-carousel>
    </div>
</template>
<script>
    export default {
        name: "Advertisement",
      data(){
          return {
            images:["/static/bao.webp","/static/meizhuang.webp","/static/nvxie.webp","/static/nvzhuang.jpg"]
        }
      }
    }
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
