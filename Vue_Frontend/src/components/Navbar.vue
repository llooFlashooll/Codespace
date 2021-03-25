<template>

    <div class="navbar">

      <i class="el-icon-s-operation" id="switch" @click="setCollapse"></i>
      
      <p>{{routeName}}</p>

      <div class="navbar-item-blank-center"></div>

      <el-dropdown>
        <span class="dropdown-link">
          <img src="../assets/avater.png" class="avatar"/>
          <i class="el-icon-caret-bottom"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="logout"><a >登出</a></el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

</template>

<script>
export default {
  name: 'Navbar',
  computed:{
    routeName(){
        const route = this.$route
        const { meta, path } = route
        return meta.name
    }
  },
  data () {
    return {

    }
  },
  methods:{
    setCollapse(){
      this.$store.state.sideBarCollapse = !this.$store.state.sideBarCollapse;
    },
    // 退出登录逻辑
    logout:function(){
      sessionStorage.setItem("isLogin", false);
      sessionStorage.removeItem("userid" );
      location="/login"

    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../assets/css/navbar.css"></style>