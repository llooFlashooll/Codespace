<template>
  <div>
    <el-row>排名</el-row>

    <el-table
        ref="singleTable"
        :data="statusList"
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
          property="acceptList"
          label="过题编号">
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
  name: "ContestEndRank",
  data(){
    return {
      statusList:[],
      total:0,
      pagenum:1,
      pagesize:20,
      contestId: 0
    }
  },
  created() {
    this.contestId=window.location.href.split("?")[1].split("=")[1];
    this.getRankList();
  },
  methods:{
    getRankList:function() {
      //const url='http://localhost:8081/';
      const url = 'http://106.15.234.251:8081/';
      this.$axios.get(url + 'contest/rank',
          { params: { pagenum: this.pagenum, pagesize: this.pagesize,contestId:this.contestId}})
          .then((response) => {
            //console.log(response);
            const data = response.data;
            if(data.code === 200){
              this.statusList = data.data.statusList;
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

</style>
