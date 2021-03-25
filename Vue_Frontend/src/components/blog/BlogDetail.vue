<template>
  <div class="m-container">
    <Header></Header>
    <div>
      <div style="text-align:center;">
        <h2 >{{ blogTitle }}</h2>
        <h4>{{submitDate}}</h4>
      </div>
      <el-link icon="el-icon-edit" v-if="ownBlog">
        <router-link :to="{path: '/blog/edit', query:{blogId:blogId}}">编辑</router-link>
      </el-link>
      <el-divider></el-divider>

      <el-card style="height: auto;margin-left: 20%;margin-right: 20%;min-height: 500px;">
        <div ref="article-content" class="article-content markdown-body" v-html="blogContent" />
        <br>
        <br>
        <van-divider>END.</van-divider>
        <!-- <div v-html="blogContent"></div> -->
      </el-card>
    </div>
  </div>


</template>

<script>
import 'github-markdown-css/github-markdown.css'

export default {
  name: "BlogDetail",
  data(){
    return {
      blogId: 1,
      blogTitle:"",
      blogContent:"",
      userId:"0",
      submitDate:"",

      ownBlog: false
    }
  },
  created() {
    this.blogId = window.location.href.split("?")[1].split("=")[1];
    this.getBlog();
  },
  methods:{
    getBlog:function() {
      // const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + '/blog/getByBlogId',
          { params: { blogId:this.blogId }})
          .then((response) => {
            console.log(response);
            console.log("博客获取成功")
            const data = response.data;
            if(data.code === 200) {
              this.blogTitle = data.data.blogTitle;
              this.userId = data.data.uid;
              this.submitDate = data.data.submitDate;

              const MarkdownIt = require('markdown-it');
              const md = new MarkdownIt();
              this.blogContent = md.render(data.data.content);
              this.ownBlog=(sessionStorage.getItem("userid") === this.userId);
              //this.ownBlog=true;
            }
            else{
              console.log(data.msg);
              alert(data.msg);
            }
          })
    }

  }

}
</script>

<style scoped>
.m-container {
  margin-left: 10px;
  margin-right: 10px;
  margin-top: 10px;
}
</style>