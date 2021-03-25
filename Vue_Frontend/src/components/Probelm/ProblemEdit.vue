<template>
  <div class="problemEdit_container">
    <div>NEW PROBLEM</div>
    <div>
      <el-form >
        <el-form-item label="标题" >
          <el-input v-model="problem.problemName"></el-input>
        </el-form-item>
        <el-form-item label="题目描述" >
          <el-input type="textarea"
                    autosize
                    v-model="problem.problemDescription"></el-input>
        </el-form-item>
        <el-form-item label="输入格式" >
          <el-input type="textarea"
                    autosize
                    v-model="problem.problemInputFormat"></el-input>
        </el-form-item>
        <el-form-item label="输出格式" >
          <el-input type="textarea"
                    autosize
                    v-model="problem.problemOutputFormat"></el-input>
        </el-form-item>
        <el-form-item label="输入样例" >
          <el-input type="textarea"
                    autosize
                    v-model="problem.problemSampleInput"></el-input>
        </el-form-item>
        <el-form-item label="输出样例" >
          <el-input type="textarea"
                    autosize
                    v-model="problem.problemSampleOutput"></el-input>
        </el-form-item>
        <el-form-item label="提示" >
          <el-input type="textarea"
                    autosize
                    v-model="problem.problemHint"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="submit">立即创建</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProblemEdit",
  data(){
    return {
      problem:{
        problemName:"",
        problemTimeLimit:1000,
        problemMemoryLimit:262144,
        problemDescription:"",
        problemInputFormat:"",
        problemOutputFormat:"",
        problemSampleInput:"",
        problemSampleOutput:"",
        problemHint:""
      }
    }
  },
  created() {

  },
  methods:{
    submit:function (){
      if(this.problem.problemName==='') {
        console.log("内容不能为空");
        alert("内容不能为空");
        return;
      }
      if(this.problem.problemDescription===''){
        console.log("内容不能为空");
        alert("内容不能为空");
        return;
      }
      if(this.problem.problemInputFormat===''){
        console.log("输入描述不能为空");
        alert("输入描述不能为空");
        return;
      }
      if(this.problem.problemOutputFormat===''){
        console.log("输出描述不能为空");
        alert("输出描述不能为空");
        return;
      }
      if(this.problem.problemSampleOutput===''){
        console.log("输出不能为空");
        alert("输出不能为空");
        return;
      }
      if(this.problem.problemSampleInput===''){
        console.log("输入不能为空");
        alert("输入不能为空");
        return;
      }

      //const url='http://localhost:8081/';
      const url='http://106.15.234.251:8081/';
      this.$axios.post(url+'/addProblem',
          {
            problemName:this.problem.problemName,
            problemTimeLimit:this.problem.problemTimeLimit,
            problemMemoryLimit:this.problem.problemMemoryLimit,
            problemDescription:this.problem.problemDescription,
            problemInputFormat:this.problem.problemInputFormat,
            problemOutputFormat:this.problem.problemOutputFormat,
            problemSampleInput:this.problem.problemSampleInput,
            problemSampleOutput:this.problem.problemSampleOutput,
            problemHint:this.problem.problemHint
          })
          .then((response)=>{
            //console.log(response);
            const data=response.data;
            console.log(data.msg);
            alert(data.msg);
            location='/problemList'
          })
    }
  }
}
</script>

<style scoped>
.problemEdit_container {
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>