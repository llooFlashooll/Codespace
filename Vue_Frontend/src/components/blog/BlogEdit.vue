<template>

  <div id="main" class="blogedit_container">
    <Header></Header>
    <div class="m-content">
      <el-form label-width="80px">
        <el-form-item label="标题" >
          <el-input v-model="blogTitle"></el-input>
        </el-form-item>
        <el-form-item label="内容" >
          <mavon-editor v-model="blogContent"/>
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
  name: "Blog",
  data(){
    return{
      blogId:0,
      blogContent:'',
      blogTitle:'',
      userId:'3'
    }
  },
  created() {
    this.userId=sessionStorage.getItem("userid");
    this.blogId=window.location.href.split("?")[1].split("=")[1];
    this.getBlog();
  },
  methods:{
    getBlog:function (){
      //const url='http://localhost:8081/';
      const url='http://106.15.234.251:8081/';
      this.$axios.get(url+'blog/getByBlogId',
          {params: {blogId:this.blogId}})
          .then((response)=>{
            //console.log(response);
            const data=response.data;
            if(data.code===200){
              this.blogTitle=data.data.blogTitle;
              this.blogContent=data.data.content;

            }
            else{
              console.log(data.msg);
              alert(data.msg);
            }
          })
    },
    submit:function (){
      if(this.blogContent==='') {
        console.log("内容不能为空");
        alert("内容不能为空");
        return;
      }
      if(this.blogTitle===''){
        console.log("标题不能为空");
        alert("标题不能为空");
        return;
      }
      //const url='http://localhost:8081/';
      const url='http://106.15.234.251:8081/';
      this.$axios.post(url+'blog/save',
          {blogId:this.blogId,
                content:this.blogContent,
                blogTitle:this.blogTitle,
                uid:this.userId})
          .then((response)=>{
            //console.log(response);
            const data=response.data;
            console.log(data.msg);
            alert(data.msg);
            location='/blog/list'
          })
    }
  }
}
</script>

<style scoped>
.blogedit_container {
  margin-left: 10px;
  margin-right: 10px;
  margin-top: 10px;
}
</style>
