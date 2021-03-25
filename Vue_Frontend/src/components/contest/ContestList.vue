<template>
  <div class="contestList_container">
    <el-button type="primary" v-on:click="toAddContest" style="padding: 12px">新建比赛</el-button>
    <br>
    <br>

    <el-row>
      <el-timeline>
        <el-timeline-item :timestamp="item.contestStartTime" placement="top" v-for="(item,index) in contestList" :key="index">
          <el-card>
            <div>比赛名字: {{item.contestName}}</div>
            <div>比赛描述: {{item.contestNotes}}</div>
            <div>开始时间: {{item.contestStartTime}}</div>
            <div>结束时间: {{item.contestEndTime}}</div>
            <div v-if="participateStatusList[index]">已报名参加</div>
            <div v-if="statusList[index]===1">
              比赛已经结束<br/>
              <el-button type="primary" v-on:click="toRank(item.contestId)">查看比赛结果</el-button>
            </div>
            <el-button type="primary" v-on:click="participate(item.contestId)" v-if="!participateStatusList[index] && statusList[index]<1">参加</el-button>
            <el-button type="primary" v-on:click="toparticipate(item.contestId)"
                       v-if="statusList[index]===-1 && participateStatusList[index]">进入比赛</el-button>
          </el-card>
        </el-timeline-item>
      </el-timeline>


      <!--分页-->
      <el-pagination
          background
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total="total"
          :page-size="pagesize"
          :current-page="pagenum">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "ContestList",
  data(){
    return {
      userid:0,
      contestList:[],
      statusList:[],
      participateStatusList:[],
      total:0,
      pagenum:1,
      pagesize:3
    }
  },
  created() {
    this.userid=sessionStorage.getItem("userid");
    this.getContestList();
  },
  methods:{
    getContestList:function() {
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'allContest',
          { params: { pagenum: this.pagenum, pagesize: this.pagesize ,userid:this.userid}})
          .then((response) => {
            console.log(response);
            const data = response.data;
            if(data.code === 200){
              this.contestList=data.data.contestList;
              this.statusList = data.data.statusList;
              this.participateStatusList=data.data.participateStatusList;
              this.total = data.data.total;

              console.log(this.statusList);
            }
            else{
              console.log(data.msg);
              alert(data.msg);
            }
          })

    },
    toAddContest:function(){
      location='/contest/edit'

    },
    handleCurrentChange:function(newnum){
      this.pagenum = newnum;
      console.log(this.pagenum);
      this.getContestList();
    },
    participate:function(contestId){
      console.log(contestId);
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'participate',
          { params: { contestid:contestId ,userid:this.userid}})
          .then((response) => {
            console.log(response);
            const data = response.data;
            if(data.code === 200){

              console.log(data.msg);
              alert(data.msg);
              //console.log(this.problemList);
            }
            else{
              console.log(data.msg);
              alert(data.msg);
            }
          })

      location.reload();
    },
    toparticipate:function(contestId){
      console.log(contestId);
      location="/contest/problem?contestId="+contestId;
    },
    toRank:function (contestId){
      location="/contestEndRank?contestId="+contestId;
    }
  }
}
</script>

<style scoped>
.contestList_container {
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>
