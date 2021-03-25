<template>
  <div>
    <el-container class="contestDetail_container">
      <el-header>
        <el-menu router :default-active="activeIndex" mode="horizontal">


          <el-menu-item index="/contest/problem">
            <i class="el-icon-s-order"></i>
            <a slot="title">题目</a>
          </el-menu-item>
          <el-menu-item index="/contest/submit">
            <i class="el-icon-s-data"></i>
            <a slot="title">提交</a>
          </el-menu-item>
          <el-menu-item index="/contest/status">
            <i class="el-icon-s-home"></i>
            <a slot="title">个人提交记录</a>
          </el-menu-item>
          <el-menu-item index="/contest/rank">
            <i class="el-icon-s-marketing"></i>
            <a slot="title">排名</a>
          </el-menu-item>


        </el-menu>

      </el-header>

      <el-main>
        <router-view>

        </router-view>

      </el-main>
    </el-container>

  </div>
</template>

<script>
export default {
  name: "ContestDetail",
  data(){
    return {
      activeIndex: "/",
      contestId:"",
      activeName:'problem'
    }
  },
  created() {
    // if(window.location.href.indexOf("?")!==-1)
    // {
    //   this.contestId=window.location.href.split("?")[1].split("=")[1];
    //   sessionStorage.setItem("contestId",this.contestId);
    // }
  },
  methods:{

  }
}
</script>

<style scoped>
.contestDetail_container {
  margin-top: 10px;
}
</style>