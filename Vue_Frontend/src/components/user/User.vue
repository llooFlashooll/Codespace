<template>

  <div class="user_container">
    <!-- <el-card>
      <el-row>
        用户编号:{{userId}}
      </el-row>
      <el-row>
        用户名:{{username}}
      </el-row>
      <el-row>
        邮箱:{{email}}
      </el-row>
    </el-card> -->

    <el-row :gutter="20">

      <el-col :span="6" :xs="24">
        <el-card class="user_card">
          <div>
            <el-avatar src="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80" />
          </div>
          <br>
          <el-row>
            用户编号:{{ userId }}
          </el-row>
          <el-row>
            用户名:{{ username }}
          </el-row>
          <el-row>
            邮箱:{{ email }}
          </el-row>


          <!-- 这里修改了                   -->
          <el-row>通过的题目：</el-row>
          <el-row v-for = "id in acceptProblem" :key="id">
              <span>{{id}} </span>
          </el-row>

        </el-card>



      </el-col>

      <el-col :span="18" :xs="24">
        <el-card class="profile_card">
          <el-tabs v-model="activeTab">
            <el-tab-pane v-if="true" label="基本信息" name="first">
              <el-form :label-position="labelPosition" label-width="80px">
                <el-form-item label="姓名">
                  <el-input v-model="user.name" />
                </el-form-item>
                <el-form-item label="英文名">
                  <el-input v-model="user.englishName" />
                </el-form-item>
                <el-form-item label="性别">
                  <el-select v-model="user.gender" placeholder="请选择性别">
                    <el-option label="男" value="male" />
                    <el-option label="女" value="female" />
                  </el-select>
                </el-form-item>
                <el-form-item label="班级">
                  <el-cascader
                    :options="classroomOptions"
                    clearable
                    style="width: 100%"
                  />
                </el-form-item>
                <el-form-item label="github">
                  <el-input v-model="user.github">
                    <template slot="prepend">https://github.com/</template>
                  </el-input>
                </el-form-item>
                <el-form-item label="个人网站">
                  <el-input v-model="user.website" placeholder="应以 http:// 或者 https:// 开头" />
                </el-form-item>
                <el-form-item label="个人简介">
                  <mavon-editor v-model="blogContent" />
                </el-form-item>
              </el-form>
            </el-tab-pane>

          </el-tabs>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      userId:1,
      username:'',
      email:'',
      acceptProblem:[],

      labelPosition: '',
      user: {
        name: '',
        englishName: '',
        gender: '',
        github: '',
        website: '',
        about: '',
        blogContent: ''
      },
      classroomOptions: [{ 'label': '计算机学院（软件学院）', 'value': 101, 'children': [{ 'label': '计算机科学与技术', 'value': 10101, 'children': null }, { 'label': '网络工程', 'value': 10102, 'children': null }, { 'label': '信息管理与信息系统', 'value': 10103, 'children': null }, { 'label': '电子商务', 'value': 10104, 'children': null }, { 'label': '数据科学与大数据技术', 'value': 10105, 'children': null }, { 'label': '软件工程一班', 'value': 10106, 'children': null }, { 'label': '软件工程二班', 'value': 10107, 'children': null }] }, { 'label': '电子信息工程学院', 'value': 102, 'children': [{ 'label': '电子信息科学与技术', 'value': 10201, 'children': null }, { 'label': '通信工程', 'value': 10202, 'children': null }, { 'label': '自动化', 'value': 10203, 'children': null }, { 'label': '其他', 'value': 10204, 'children': null }] }, { 'label': '数学科学学院', 'value': 103, 'children': [{ 'label': '数学与应用数学', 'value': 10301, 'children': null }, { 'label': '信息与计算科学', 'value': 10302, 'children': null }, { 'label': '统计学', 'value': 10303, 'children': null }, { 'label': '其他', 'value': 10304, 'children': null }] }, { 'label': '经济管理学院', 'value': 104, 'children': [{ 'label': '经济学', 'value': 10401, 'children': null }, { 'label': '金融学', 'value': 10402, 'children': null }, { 'label': '金融工程', 'value': 10403, 'children': null }, { 'label': '管理科学', 'value': 10404, 'children': null }, { 'label': '人力资源管理', 'value': 10405, 'children': null }, { 'label': '市场营销', 'value': 10406, 'children': null }, { 'label': '工商管理', 'value': 10407, 'children': null }, { 'label': '会计学', 'value': 10408, 'children': null }, { 'label': '财务管理', 'value': 10409, 'children': null }, { 'label': '其他', 'value': 10410, 'children': null }] }, { 'label': '文学与新闻传播学院', 'value': 105, 'children': [{ 'label': '新闻学', 'value': 10501, 'children': null }, { 'label': '编辑出版学', 'value': 10503, 'children': null }, { 'label': '播音与主持艺术', 'value': 10503, 'children': null }, { 'label': '其他', 'value': 10504, 'children': null }] }, { 'label': '外国语学院', 'value': 106, 'children': null }, { 'label': '物理学科与技术学院', 'value': 107, 'children': null }, { 'label': '化学化工学院', 'value': 108, 'children': null }, { 'label': '生命科学学院', 'value': 109, 'children': null }, { 'label': '生态与环境学院', 'value': 110, 'children': null }, { 'label': '其他学院', 'value': 111, 'children': null }],
      activeTab: 'first'

    }
  },
  created() {
    this.userId=sessionStorage.getItem("userid");
    this.getUserInformation();
    this.getAcceptProblem();
  },
  methods:{
    getUserInformation:function (){
      //const url='http://localhost:8081/';
      const url='http://106.15.234.251:8081/';
      this.$axios.get(url + 'user/index',
          {params: {userId:this.userId}})
          .then((response) => {
            console.log(response);
            const data = response.data;
            if (data.code === 200) {
              this.email = data.data.email;
              this.username = data.data.username;

              //console.log(this.problemList);
            } else {
              console.log(data.msg);
              alert(data.msg);
            }
          })
    },

    getAcceptProblem:function (){
      //const url='http://localhost:8081/';
      const url='http://106.15.234.251:8081/';
      this.$axios.get(url + 'user/accept',
          {params: {userId:this.userId}})
          .then((response) => {
            console.log(response);
            const data = response.data;
            if (data.code === 200) {
              this.acceptProblem = data.data.acceptList;


              //console.log(this.problemList);
            } else {
              console.log(data.msg);
              alert(data.msg);
            }
          })
    },
  }
}
</script>

<style scoped>

.user_card {
  margin-top: 10px;
  margin-left: 10px;
}

.profile_card {
  margin-top: 10px;
  margin-right: 10px;
}
</style>