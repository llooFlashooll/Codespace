<template>
  <div>
    <el-card class="problemsubmit_container">
      <div slot="header">
        <span>提交</span>
      </div>

      <el-select v-model="language" placeholder="请选择语言" v-on:change="selectLanguage">
        <el-option
            v-for="item in languages"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="problemId" placeholder="请选择题目" v-on:change="selectProblem">
        <el-option
            v-for="item in problemList"
            :key="item.problemId"
            :label="item.problemId"
            :value="item.problemId">
        </el-option>
      </el-select>
      <br>
      <br>
      <el-input
          type="textarea"
          :rows="20"
          placeholder="请输入代码"
          v-model="code">
      </el-input>
      <br>
      <br>
      <el-button type="primary" v-on:click="submit">提交</el-button>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "ContestSubmit",
  data(){
    return{
      contestId: 0,
      code:'',
      languages:[
        {
          value: '0',
          label: 'c++'
        }

      ],
      language: 'c++',
      languageId: 0,
      problemList:[],
      problemId: 0,
      userId: 3
    }
  },

  created() {
    this.contestId=sessionStorage.getItem("contestId");
    this.getProblemList();
    this.userId = sessionStorage.getItem("userid");
  },
  methods:{
    submit:function (){
      //console.log(this.code);

      if(this.problemId==0) {
        alert("请选择要提交的题目");
        return ;
      }
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.post(url + 'contest/judge',
          { language: this.language,
            languageId: this.languageId,
            problem_id: this.problemId,
            uid: this.userId,
            submissionCode: this.code,
            contestId:this.contestId
          })
          .then((response) => {
            console.log(response);
            alert(response.data.data.submissionJudgeResult
                + "   "
                +response.data.data.error);
            location='/contest/status';
          })
    },

    selectLanguage:function(id) {
      console.log(this.languages[id]);
      this.language = this.languages[id].label;
      this.languageId = id;
    },
    selectProblem:function(id) {

      this.problemId = this.problemList[id].problemId;

    },
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
  }
}
</script>

<style scoped>

</style>
