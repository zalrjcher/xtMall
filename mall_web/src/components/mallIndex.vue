<template>
  <div>
  <Advertisement></Advertisement>
  <Mobile></Mobile>
  <Book></Book>
  <PC></PC>
  <Active></Active>
  </div>
</template>

<script>
  import  Mobile from './mallPage/mobile'
  import  Advertisement from './mallPage/advertisement'
  import  Book from './mallPage/book'
  import  PC from './mallPage/pc'
  import  Active from './mallPage/active'
    export default {
      name: "",
      components:{Mobile,Advertisement,Book,PC,Active},
      methods:{

      }
    }
</script>

<style scoped>

</style>
