<template>
   <div style="background-color:#ededed; width: 100%; height:100% ;">
     <Home></Home>
     <router-view/>
     <div style="height: 70px; color: #2c3e50 ; background-color: white;clear: both; margin-top: 100px ;text-align: center">
       <span style="display: block;margin-top: 10px"><a href="https://cn.vuejs.org/" style="text-decoration:none;">VUE.JS</a></span>
       <span style="display: inline-block;margin-top: 10px">Copyright 2019 zalrjcher&copy;163.com.</span>
     </div>
   </div>
</template>

<script>
  import Home from './mallPage/home'
    export default {
        name: "index",
        components:{Home}
    }
</script>

<style scoped>

</style>
