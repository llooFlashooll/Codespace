<template>
  <div class="news_container">
    <!-- 循环卡片列表，显示资讯 -->
    <div>
      <el-row>
        <el-col v-for="(item) in newsList" :key="item.id" :span="8">
          <div class="card_item">
            <el-card shadow="hover">
              <el-row :gutter="20">
                <el-col :span="6" :xs="24">
                  <el-image
                    style="width: 90px; height: 160px"
                    :src="item.envelopePic"
                    :preview-src-list="item.envelopePic"

                  />
                </el-col>
                <el-col :span="18" :xs="24">
                  <div>
                    <div slot="header" class="clearfix">
                      <span>{{ item.title }}</span>
                      <el-button style="float: right; padding: 8px" type="success" @click="goTo(item.projectLink)">查看</el-button>
                    </div>
                    <el-divider />
                    <div class="text item">
                      {{ item.desc }}
                    </div>
                  </div>
                </el-col>
              </el-row>
            </el-card>
          </div>
        </el-col>
      </el-row>

      <div style="margin-left:35%">
        <!-- 分页 -->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pagesize"
          :current-page="pagenum"
          @current-change="handleCurrentChange"
        />
      </div>

    </div>
  </div>

</template>

<script>

export default {
  name: 'News',
  data() {
    return {
      cid: 294,
      newsList: [],
      pagenum: 1,
      pagesize: 15
    }
  },
  created() {
    this.getNews()
  },
  methods: {
    getNews: function() {
    //   const url = 'https://www.wanandroid.com'
      this.$axios.get('/api/project/list/' + this.pagenum + '/json',
        { params: { cid: this.cid }})
        .then((response) => {
          const newsList = response.data.data.datas
          // console.log(newsList);
          var count = 0
          this.newsList = []
          newsList.forEach(element => {
            var item = {
              id: '',
              chapterName: '',
              title: '',
              desc: '',
              envelopePic: '',
              projectLink: ''
            }
            item.id = count
            item.chapterName = element.chapterName
            item.title = element.title
            item.desc = element.desc
            item.envelopePic = element.envelopePic
            item.projectLink = element.projectLink

            count += 1
            this.newsList.push(item)
            // console.log(this.newsList);
          })
          // console.log(this.newsList)
        })
    },

    handleCurrentChange: function(newnum) {
      this.pagenum = newnum
      this.getNews()
    },

    goTo: function(url) {
      alert('跳转至' + url)
      window.open(url, '_blank')
    }
  }

}
</script>

<style>
  .text {
    font-size: 14px;
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

  .box-card {
    width: 480px;
  }

  .card_item {
      margin-top: 10px;
      margin-left: 10px;
      margin-right: 10px;
      width: 500px;
      height: 300px;
  }
</style>
