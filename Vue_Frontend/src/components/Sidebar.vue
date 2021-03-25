<template>

  <el-aside class="sidebar">

    <el-menu router :default-active="activeIndex" :collapse="this.$store.state.sideBarCollapse">
      <img src="../assets/tongji_logo.png" class="avatar"/>

        <!-- <el-image
            style="width: 50px; height: 50px"
            src="../assets/tongji_logo.png">
        </el-image> -->
      <el-menu-item index="/">
        <i class="el-icon-s-home"></i>
        <a slot="title">首页</a>
      </el-menu-item>

      <el-submenu index="/" class="submenu">
        <template slot="title">
            <i class="el-icon-s-custom"></i>
            <!-- 用户 -->
        </template>
        <el-menu-item index="/user">
            <i class="el-icon-user-solid"></i>
            <a slot="title">用户信息</a>
        </el-menu-item>
        <el-menu-item index="/user/statusList">
            <i class="el-icon-info"></i>
            <a slot="title">提交记录</a>
        </el-menu-item>
        <el-menu-item index="/user/blogList">
            <i class="el-icon-s-management"></i>
            <a slot="title">个人博客</a>
        </el-menu-item>
      </el-submenu>

      <el-menu-item index="/problemList">
        <i class="el-icon-s-order"></i>
        <a slot="title">题目</a>
      </el-menu-item>
      <el-menu-item index="/status">
        <i class="el-icon-s-data"></i>
        <a slot="title">提交状态</a>
      </el-menu-item>
      <el-menu-item index="/blog/list">
        <i class="el-icon-document"></i>
        <a slot="title">博客</a>
      </el-menu-item>

      <el-menu-item index="/rank">
        <i class="el-icon-s-marketing"></i>
        <a slot="title">排名</a>
      </el-menu-item>

      <el-menu-item index="/contestList">
        <i class="el-icon-s-flag"></i>
        <a slot="title">比赛</a>
      </el-menu-item>
<!--
      <el-menu-item index="/news">
        <i class="el-icon-s-management"></i>
        <a slot="title">资讯</a>
      </el-menu-item>

      <el-menu-item index="/game">
        <i class="el-icon-s-shop"></i>
        <a slot="title">游戏</a>
      </el-menu-item>
      -->
    </el-menu>
  </el-aside>

</template>

<script>
export default {
  name: 'Sidebar',
  computed:{
    // 计算路由事件并高亮
    // activeIndex(){
    //     const route = this.$route
    //     const { meta, path } = route
    //     return meta.activeIndex
    // }
  },
  data () {
    return {
      // 初始化选中tab格
      activeIndex: "/"
    }
  },
  methods:{
  }
}
</script>

<!-- 导入sidebar css -->
<style scoped src="../assets/css/sidebar.css"></style>

<style>
    .el-submenu__title {
        color: rgb(191, 203, 217);
    }
</style>
