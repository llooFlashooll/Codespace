<template>
  <div>

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <h1>{{ problemId }}.{{ problemName }}</h1>
        </div>

        <el-row>
          <h2>题目描述</h2>
          <el-scrollbar :native="false" noresize="false">
            <pre>{{ problemDescription }}</pre>
          </el-scrollbar>
          <!-- <pre>{{problemDescription}}</pre> -->
        </el-row>
        <el-row>
          <h2>输入格式</h2>
          <el-scrollbar :native="false" noresize="false">
            <pre>{{ problemInputFormat }}</pre>
          </el-scrollbar>
          <!-- <pre>{{problemInputFormat}}</pre> -->
        </el-row>
        <el-row>
          <h2>输出格式</h2>
          <el-scrollbar :native="false" noresize="false">
            <pre>{{ problemOutputFormat }}</pre>
          </el-scrollbar>
          <!-- <pre>{{problemOutputFormat}}</pre> -->
        </el-row>
        <el-row>
          <h2>输入样例</h2>
          <el-scrollbar :native="false" noresize="false">
            <pre>{{ problemSampleInput }}</pre>
          </el-scrollbar>
          <!-- <pre>{{problemSampleInput}}</pre> -->
        </el-row>
        <el-row>
          <h2>输出样例</h2>
          <el-scrollbar :native="false" noresize="false">
            <pre>{{ problemSampleOutput }}</pre>
          </el-scrollbar>
          <!-- <pre>{{problemSampleOutput}}</pre> -->
        </el-row>
        <el-row>
          <h2>时间空内存限制</h2>
          <el-scrollbar :native="false" noresize="false">
            <pre>时间限制:{{ problemTimeLimit }}ms</pre>
            <pre>内存限制:{{ problemMemoryLimit }}KB</pre>
          </el-scrollbar>
          <!-- <div>时间限制:{{problemTimeLimit}}s</div>
          <div>内存限制:{{problemMemoryLimit}}KB</div> -->
        </el-row>
        <el-row>
          <h2>提示</h2>
          <el-scrollbar :native="false" noresize="false">
            <pre class="hint">{{ problemHint }}</pre>
          </el-scrollbar>
          <!-- <pre class="hint">
            {{problemHint}}
          </pre> -->
        </el-row>

      </el-card>

  </div>
</template>

<script>
export default {
  name: "ContestProblemDetail",
  data(){
    return{
      problemId:1000,
      problemDescription: '',
      problemHint:'',
      problemInputFormat: '',
      problemMemoryLimit: 65536,
      problemName: '',
      problemOutputFormat: '',
      problemSampleInput:'',
      problemSampleOutput: '',
      problemTimeLimit:1000,

      // isContest:false
    }
  },
  created() {

    this.problemId=window.location.href.split("?")[1].split("=")[1];
    // if(window.location.href.split("?")[1].split("&")[1].split("=")[1]==='true'){
    //   this.isContest=true;
    // }

    this.getProblem();
  },
  methods: {
    getProblem: function () {
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'problem',
          {params: {problemId: this.problemId}})
          .then((response) => {
            //console.log(response);
            console.log("题目获取成功")
            const data = response.data;
            if (data.code === 200) {
              this.problemDescription = data.data.problemDescription;
              this.problemHint = data.data.problemHint;
              this.problemInputFormat = data.data.problemInputFormat
              this.problemOutputFormat = data.data.problemOutputFormat
              this.problemMemoryLimit = data.data.problemMemoryLimit
              this.problemName = data.data.problemName
              this.problemSampleInput = data.data.problemSampleInput
              this.problemSampleOutput = data.data.problemSampleOutput
              this.problemTimeLimit = data.data.problemTimeLimit
              console.log(data.data);
            } else {
              console.log(data.msg);
              alert(data.msg);
            }
          })
    }
  }
}
</script>

<style scoped>
.el-card {
  width: auto;
}

.hint{
  border-style:solid;
  border-color: aliceblue;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

pre {
  font-weight: bold;
  font-size: large;
}
</style>
