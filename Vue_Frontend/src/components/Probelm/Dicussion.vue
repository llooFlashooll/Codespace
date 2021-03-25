<template>
<div class="discussion_container">

  <div>讨论</div>
  <br>
  <el-input type="textarea" v-model="content" placeholder="请输入你的讨论" class="input_discussion">
  </el-input>

  <el-button type="primary" v-on:click="comment" class="btn_comment">发表评论</el-button>

  <br>
  <br>
  <br>
  <div>讨论汇总</div>
  <br>
  <el-row>
    <el-timeline>
      <el-timeline-item :timestamp="discussion.discussionTime" placement="top" v-for="discussion in discussionList" :key="discussion.discussionId">
        <el-card>
          <div>{{discussion.discussionUid}}</div>
          <div>{{discussion.content}}</div>
        </el-card>
      </el-timeline-item>
    </el-timeline>
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
  name: "Dicussion",
  data(){
    return{

      problemId: 1000,
      userId: 3,
      discussionList:[],
      total:0,
      pagenum:1,
      pagesize:5,
      content:""
    }
  },

  created() {
    this.problemId = window.location.href.split("?")[1].split("=")[1];
    this.userId = sessionStorage.getItem("userid");
    this.getDiscussionList();
  },
  methods:{
    getDiscussionList:function() {
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'discussion/all',
          { params: { pagenum: this.pagenum, pagesize: this.pagesize, problemId:this.problemId }})
          .then((response) => {
            //console.log(response);
            const data = response.data;
            if(data.code === 200){
              this.discussionList = data.data.discussionList;
              this.total = data.data.total;

              //console.log(this.problemList);
            }
            else{
              console.log(data.msg);
              alert(data.msg);
            }
          })


    },

    handleCurrentChange:function(newnum){
      this.pagenum = newnum;
      console.log(this.pagenum);
      this.getProblemList();
    },

    comment:function(){
    //  const url='http://localhost:8081/';
      const url='http://106.15.234.251:8081/';
      this.$axios.post(url+'discussion/add',
          {discussionUid:this.userId,
            content:this.content,
            problemId:this.problemId,
            })
          .then((response)=>{
            //console.log(response);
            const data=response.data;
            console.log(data.msg);
            alert(data.msg);
            location.reload();
          })
    }

  }
}
</script>

<style scoped>
.discussion_container {
  margin-top: 10px;
  margin-right: 10px;
  margin-left: 10px;
}

.input_discussion {
  height: 100px;
}

.btn_comment {
  margin-left: 93%;
}
</style>