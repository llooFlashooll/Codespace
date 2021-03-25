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
  name: "Submit",
  data(){
    return{
      code:'',
      languages:[
        {
          value: '0',
          label: 'c++'
        }

      ],
      language: 'c++',
      languageId: 0,
      problemId: 1000,
      userId: 3
    }
  },

  created() {
    this.problemId = window.location.href.split("?")[1].split("=")[1];
    this.userId = sessionStorage.getItem("userid");
  },
  methods:{
    submit:function (){
      //console.log(this.code);

      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.post(url + 'problem/judge',
          { language: this.language,
                languageId: this.languageId,
                problem_id: this.problemId,
                uid: this.userId,
                submissionCode: this.code
          })
          .then((response) => {
            console.log(response);
            alert(response.data.data.submissionJudgeResult
                + "   "
                +response.data.data.error);
            location='/status';
          })
    },

    selectLanguage:function(id) {
      console.log(this.languages[id]);
      this.language = this.languages[id].label;
      this.languageId = id;
    }
  }
}
</script>

<style scoped>
.problemsubmit_container {
  /* margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px; */
}
</style>
