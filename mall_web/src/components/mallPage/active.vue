<template>
  <div class="box" style="margin-top: 100px;">
      <div v-for="item  in actives">
        <div class="mobile">
          <img :src="item" style="width: 100%;height: 100%">
          </div>
      </div>
    <div style="clear: both; height: 100px"></div>
  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      return {
        actives: ["/static/active/hd1.webp", "/static/active/hd2.webp",
          "/static/active/hd3.webp", "/static/active/hd4.webp",
          "/static/active/hd5.webp", "/static/active/hd5.webp",
        ]
      }
    }
  }
</script>

<style scoped>
  .box {
    clear: both;
    width: 100%;
    margin: auto;
    border: 1px solid transparent;
    border-radius: 8px;
    background-color: #ededed;
    float: left;
  }
.mobile{
  float: left;
  width: 16.666%;
}



</style>
