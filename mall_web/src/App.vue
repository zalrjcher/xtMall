<template>
  <div id="app">
    <router-view v-if="isRouterAlive"/>
  </div>
</template>

<script>
export default {
  name: 'App',
  provide(){
    return{
      reload:this.reload
    }
  },
  data(){
    return{
      isRouterAlive:true
    }
  },
  methods:{
    reload(){
      this.isRouterAlive =false
      this.$nextTick(function () {
        this.isRouterAlive=true;
      })
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: #d4d4d4;
  font-family: PingFang SC,Helvetica Neue,Helvetica,Arial,Hiragino Sans GB,Microsoft Yahei,\5FAE软雅黑,STHeiti,\534E文细黑,sans-serif;
}
</style>
