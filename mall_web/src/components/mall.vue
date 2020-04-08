<template>
  <div>
    <Navigation></Navigation>
    <router-view/>
  </div>
</template>

<script>
  import Navigation from './mallPage/navigation'
    export default {
        name: "Mall",
        components:{Navigation}
    }
</script>

<style scoped>

</style>
