<template>
  <div class="bloglist_container">
    <div>
      <el-button type="primary" @click="newBlog()" style="padding: 7px">新建博客</el-button>
      <!-- <router-link :to="{path: '/blog/edit', query:{blogId:0}}">新建博客</router-link> -->

      <br/>
      <br/>
      <el-timeline>
        <el-timeline-item :timestamp="blog.submitDate" placement="top" v-for="blog in blogList" :key="blog.blogId">
          <el-card>
            <h4><router-link :to="{path: '/blog/blogDetail', query:{blogId:blog.blogId}}">{{blog.blogTitle}}</router-link></h4>
            <p>作者编号:{{blog.uid}}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>

    </div>

    <!--分页-->
    <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :total="total" :page-size="pagesize" :current-page="pagenum">
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "BlogList",
  data(){
    return {
      blogList:[],
      total:0,
      pagenum:1,
      pagesize:3,
    }
  },
  created() {
    this.getBlogList();
  },
  methods:{
    getBlogList:function() {
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'blog/blogList',
          { params: { pagenum:this.pagenum,pagesize:this.pagesize }})
          .then((response) => {
            //console.log(response);
            const data = response.data;
            if(data.code === 200){
              this.blogList = data.data.blogList;
              this.total = data.data.total;

              //console.log(this.problemList);
            }
            else{
              console.log(data.msg);
              alert(data.msg);
            }
          })
    },

    handleCurrentChange:function(newnum) {
      this.pagenum = newnum;
      console.log(this.pagenum);
      this.getBlogList();
    },

    newBlog: function() {
      this.$router.push(
        {
          path: '/blog/edit',
          query: {
            blogId: '0'
          }
        }
      )
    }
  }
}
</script>

<style scoped>
.bloglist_container {
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>