<template>
  <el-menu
    style="text-align: center;margin-left: 200px "
    default-active="1"
    @select="handleSelect"

  >
    <div v-for="item in permissionsResult ">
      <el-submenu v-if="item.permissions.length>0" :index="item.id">
        <template slot="title">
          {{item.name}}
        </template>
        <el-menu-item v-for="item1 in item.permissions " :index=" item1.id" :key="item1.id">{{item1.name}}
        </el-menu-item>
      </el-submenu>
      <el-menu-item v-if="item.permissions.length==0" :index=" item.id" :key="item.id">{{item.name}}</el-menu-item>
    </div>
  </el-menu>


</template>

<script>
  export default {
    name: "",
    data() {
      return {
        permissionsResult: [],
        permissions: {
          id: '',
          name: '',
          url: '',
          level: ''
        },
        cid: '',
      }
    },
    created: function () {
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
      this.$axios.post('getMyPermission', {
        token: token,
        userId: userId,
      }).then(resp => {
        if (resp.data.code === 200) {
          this.permissionsResult = resp.data.data.permissionsResultList
        }
      })
    },
    methods: {
      handleSelect(key, keypath) {
        this.cid = key;
        this.$emit('indexSelect')
      }
    }
  }
</script>

<style scoped>

</style>
