<template>
    <el-container class="home-container">

        <!-- <el-header>
          <div style="display: flex;align-items: center;">
            <img src="../assets/avater.png" alt="logo" style="width: 5%;height: 5%;">
            <span style="font-size:large;">codespace</span>
          </div>
          <el-button type="primary" v-on:click="logout">退出</el-button>
        </el-header> -->

        <!-- 侧边栏 -->
        <Sidebar/>

        <el-main>
          <Navbar/>

          <div v-show="routeName == '首页'" class="home_container">
            <div class="title">
              <h2>CODESPACE</h2>
            </div>

          <div class="match">重要比赛</div>
          <br>
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in activities"
              :key="index"
              :icon="activity.icon"
              :type="activity.type"
              :color="activity.color"
              :size="activity.size"
              :timestamp="activity.timestamp">
              {{activity.content}}
            </el-timeline-item>
          </el-timeline>

          <el-card class="recommend">
            <div slot="header" class="clearfix">
              <span>每日推荐</span>
            </div>
            <div class="text item">
              精彩好题： 1002.谁拿了最多的奖学金
              <el-button style="float: right; padding: 3px 0" type="text" v-on:click="toProblem(1001)">跳转</el-button>
            </div>
            <div class="text item">
              精彩好题： 1003.「CEOI2017」Sure Bet
              <el-button style="float: right; padding: 3px 0" type="text" v-on:click="toProblem(1002)">跳转</el-button>
            </div>
            <div class="text item">
              优秀博客： 判断树同构
              <el-button style="float: right; padding: 3px 0" type="text" v-on:click="toBlog(8)">跳转</el-button>
            </div>
          </el-card>

          </div>
          

          <!-- 路由占位符-->
          <router-view></router-view>

        </el-main>

    </el-container>
</template>

<script>

import Sidebar from "@/components/Sidebar";
import Navbar from "@/components/Navbar";

export default {
  name: "Home",
  computed:{
    routeName(){
        const route = this.$route
        const { meta, path } = route
        return meta.name
    }
  },
  components:{
    Sidebar,
    Navbar
  },
  data(){
    return{
      // 左侧菜单
      menuList:[],
      
      activities: [{
          content: 'NERC Challenge 2020: Marathon —— Codeforces',
          timestamp: '2020-12-12 17:00  to  2020-12-20 17:00',
          size: 'large',
          type: 'primary'
        }, {
          content: 'Hitachi Hokudai Lab. & Hokkaido University Contest 2020 —— AtCoder',
          timestamp: '2020-12-16 23:00  to  2021-01-16 22:59',
          size: 'large',
          type: 'primary'
        }, {
          content: '第 45 届国际大学生程序设计竞赛（ICPC）亚洲区域赛（南京）',
          timestamp: '2020-12-20 03:00  to  2020-12-20 08:00',
          size: 'large',
          type: 'primary'
        }, {
          content: '牛客2020跨年场',
          timestamp: '2020-12-31 13:00  to  2020-12-31 16:30',
          size: 'large'
        }, {
          content: '「GLR Round 2」洛水之灵 Round 2 —— 洛谷',
          timestamp: '2021-01-02 04:30  to  2021-01-02 10:30',
          size: 'large'
        }, {
          content: 'Technocup 2021 - Elimination Round 3 —— Codeforces',
          timestamp: '2020-12-20 23:05  to  2020-12-21 01:05',
          size: 'large'
        }]
    }
  },

  methods:{
    logout:function(){
      sessionStorage.setItem("isLogin", false);
      sessionStorage.removeItem("userid" );
      //
      location="/login"

    },

    toProblem:function(problemId){
      location='/problem?problemId=' + problemId;
    },

    toBlog:function(blogId) {
      location='/blog/blogDetail?blogId=' + blogId;

    }
  }

}
</script>

<style scoped>

.home-container{
  height: 100%;
}

.el-main {
  padding: 0;
}

.el-header{
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
}

.el-aside{

}

.el-main{
  /* background-color: azure; */
}


.title {
  font-weight: bold;
  text-align: center;
}

.row {
  margin-top: 40px;
}

.recommend {
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
}

.match {
  margin-left: 20px
}

</style>
