<template>
  <div class="rank_container">
    <el-row>排名</el-row>

    <el-table
        ref="singleTable"
        :data="rankList"
        highlight-current-row
        style="width: 100%">
      <el-table-column
          type="index"
          width="50">
      </el-table-column>
      <el-table-column
          property="userid"
          label="用户编号"
          width="120">
      </el-table-column>
      <el-table-column
          property="username"
          label="用户名"
          width="120">
      </el-table-column>
      <el-table-column
          property="acceptCnt"
          label="过题数">
      </el-table-column>
    </el-table>


    <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :total="total"
        :page-size="pagesize"
        :current-page="pagenum">
    </el-pagination>

  </div>

</template>

<script>
export default {
  name: "Rank",
  data(){
    return {
      rankList:[],
      total:0,
      pagenum:1,
      pagesize:20,
    }
  },
  created() {

    this.getRankList();
  },
  methods:{
    getRankList:function() {
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'allRank',
          { params: { pagenum: this.pagenum, pagesize: this.pagesize}})
          .then((response) => {
            //console.log(response);
            const data = response.data;
            if(data.code === 200){
              this.rankList = data.data.rankList;
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
      this.getRankList();
    }
  }
}
</script>

<style scoped>
.rank_container {
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>