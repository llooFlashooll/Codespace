<template>
  <div>

    <el-table :data="problemList" >
      <el-table-column label="题目序号" prop="problemId">
      </el-table-column>

      <el-table-column label="题目名称" prop="problemName" >
      </el-table-column>

      <el-table-column label="题目链接">
        <template slot-scope="scope">
          <el-button type="primary" v-on:click="toProblem(scope.row)">查看题目</el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>

<script>
export default {
  name: "ContestProblemList",
  data(){
    return {
      activeName:'problem',
      contestId:0,
      problemList:[]
    }
  },
  created() {
    if(window.location.href.indexOf("?")!==-1)
    {
      this.contestId=window.location.href.split("?")[1].split("=")[1];
      sessionStorage.setItem("contestId",this.contestId);
    }
    this.contestId=sessionStorage.getItem("contestId");
    this.getProblemList();
  },
  methods:{
    getProblemList:function() {
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'contest/problem',
          { params: { contestId:this.contestId }})
          .then((response) => {
            //console.log(response);
            const data = response.data;
            if(data.code === 200){
              this.problemList = data.data.problemsList;


              //console.log(this.problemList);
            }
            else{
              console.log(data.msg);
              alert(data.msg);
            }
          })


    },

    toProblem:function(problemInfo){
      console.log(problemInfo.problemId);
      location='/contest/problemDetail?problemId=' + problemInfo.problemId;
    },
  }

}
</script>

<style scoped>

</style>