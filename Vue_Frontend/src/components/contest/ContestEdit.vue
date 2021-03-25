<template>
  <div class="contestEdit_container">

    <div>
      <el-form >
        <el-form-item label="标题" >
          <el-input v-model="contest.contestName"></el-input>
        </el-form-item>
        <el-form-item label="比赛描述" >
          <el-input v-model="contest.contestNote"></el-input>
        </el-form-item>
        <el-form-item label="比赛时间" >
          <el-date-picker
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="contest.contestTime"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="比赛题目" >
            <el-checkbox-group v-model="problem" multiple placeholder="请选择">

              <el-checkbox
                  v-for="item in problemList"
                  :key="item.problemId"
                  :label="item.problemId"
                  >

              </el-checkbox>

            </el-checkbox-group>
        </el-form-item>

      </el-form>
      <el-button type="primary" v-on:click="submit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "ContestEdit",
  data(){
    return {
      contest:{
        contestName:"",
        contestNote:"",
        contestStartTime:Date,
        contestEndTime:Date,
        contestProblems:"",
        contestTime:[]
      },
      problem:[],
      problemList:[],
      total:0,
      pagenum:1,
      pagesize:10000,
      query:''

    }
  },
  created() {
    this.getProblemList();
  },
  methods:{
    getProblemList:function() {
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'problemList',
          { params: { pagenum: this.pagenum, pagesize: 10000, query: this.query }})
          .then((response) => {
            //console.log(response);
            const data = response.data;
            if(data.code === 200){
              this.problemList = data.data.problemList;
              this.total = data.data.total;

              //console.log(this.problemList);
            }
            else{
              console.log(data.msg);
              alert(data.msg);
            }
          })


    },

    submit:function (){

      if((new Date()).getTime()>(new Date(this.contest.contestTime[0])).getTime()){
        alert("开始时间不能在过去!");
        return;
      }

      if((new Date(this.contest.contestTime[0])).getTime()>(new Date(this.contest.contestTime[1])).getTime()){
        alert("时间不合理!");
        return;
      }
      // this.contest.contestStartTime=this.contest.contestTime[0].toLocaleDateString().replace(/\//g, "-")
      //     + "T" + this.contest.contestTime[0].toTimeString().substr(0, 8);
      //
      // this.contest.contestEndTime=this.contest.contestTime[1].toLocaleDateString().replace(/\//g, "-")
      //     + "T" + this.contest.contestTime[1].toTimeString().substr(0, 8);

      //console.log(this.contest.contestTime[0])
      //console.log(this.problem);
      if(this.problem.length===0){
        alert("请选择比赛的题目!");
        return ;
      }

      this.contest.contestProblems=this.problem.toString();
     // console.log(this.contest.contestProblems);
      if(this.contest.contestTime.length===0)
      {

        alert("请选择开始时间和结束时间");
        return;
      }
      //console.log(this.contest.contestTime[1].getTime());

      if((new Date(this.contest.contestTime[1])).getTime()
          -(new Date(this.contest.contestTime[0])).getTime()<60000*30){
        alert("比赛时间必须大于30分钟");
        return;
      }




      if(this.contest.contestName==='') {
        console.log("内容不能为空");
        alert("内容不能为空");
        return;
      }



      // console.log(this.contest.contestName);
      console.log(this.contest.contestTime[0].split(' ')[0]+'T'+this.contest.contestTime[0].split(' ')[1]);
      // console.log(this.contest.contestNote);
      console.log(this.contest.contestTime[1]);
      // console.log(this.contest.contestProblems);
      //const url='http://localhost:8081/';
      const url='http://106.15.234.251:8081/';
      this.$axios.post(url+'/addContest',
          {
            contestName:this.contest.contestName,
            contestNotes:this.contest.contestNote,
            contestStartTime:this.contest.contestTime[0].split(' ')[0]+'T'+this.contest.contestTime[0].split(' ')[1],
            contestEndTime:this.contest.contestTime[1].split(' ')[0]+'T'+this.contest.contestTime[1].split(' ')[1],
            contestProblems:this.contest.contestProblems
          })
          .then((response)=>{
            //console.log(response);
            const data=response.data;
            console.log(data.msg);
            alert(data.msg);
            location='/contestList'
          })
    }
  }
}
</script>

<style scoped>
.contestEdit_container {
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>
