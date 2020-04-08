<template>
  <el-container>
    <el-aside width="400px" style=" margin-top: 20px" >
    <LeftGuide @indexSelect="personPage" ref="personPage"></LeftGuide>
    </el-aside>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
  import LeftGuide from  './leftGuide'
    export default {
        name: "",
      data(){
          return{
            permissions:{
              id:'',
              name:'',
              url:'',
              level:''
            },
          }
      },
      components:{LeftGuide},
      created:function(){
        var token;
        var userId;
        var user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
          userId = 0;
          token = 0;
        } else {
          token = user.token;
          userId = user.id;
        }
        this.$axios.post('getAllPermission',{
          token:token,
          userId:userId,
        }).then(resp=>{
          if(resp.data.code === 200){
            this.permissions = resp.data.data.permissions
            console.log( this.permissions)
          }
        })
      },
      methods:{
        personPage(){
          let _this = this;
          var cid = _this.$refs.personPage.cid;
          console.log(cid)
          for(var i = 0 ; i < _this.permissions.length+1 ; i++){
            if(_this.permissions[i].id===cid){
              this.$router.replace({path:_this.permissions[i].url})
            }
          }
        }
      }
    }

</script>

<style scoped>

</style>
