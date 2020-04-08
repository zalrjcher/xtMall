<template>
   <div style="background-color:#ededed; width: 100%; height:100% ;">
     <Home></Home>
     <router-view/>
     <div style="height: 400px; color: #2c3e50 ; background-color: #2c3e50;clear: both; margin-top: 100px ;"></div>
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
