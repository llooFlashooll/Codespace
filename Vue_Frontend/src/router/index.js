import Vue from 'vue'
import Router from 'vue-router'
import Register from '@/components/Register'
import Login from "@/components/Login";
import Home from "@/components/Home"
import ProblemList from "@/components/Probelm/ProblemList";
import Status from "@/components/Probelm/Status";
import Problem from "@/components/Probelm/Problem";
import Submit from "@/components/Probelm/Submit";
import User from "@/components/user/User";
import BlogEdit from "@/components/blog/BlogEdit";
import BlogList from "@/components/blog/BlogList";
import BlogDetail from "@/components/blog/BlogDetail";
import UserBlogList from "@/components/user/UserBlogList";
import UserStatus from "@/components/user/UserStatus";
import Code from "@/components/Code";
import News from "@/components/News";
import Rank from "@/components/Rank";
import ProblemEdit from "@/components/Probelm/ProblemEdit";
import Dicussion from "@/components/Probelm/Dicussion";
import ContestList from "@/components/contest/ContestList";
import ContestEdit from "@/components/contest/ContestEdit";
import ContestDetail from "@/components/contest/ContestDetail";
import ContestProblemList from "@/components/contest/ContestProblemList";
import ContestRank from "@/components/contest/ContestRank";
import ContestStatus from "@/components/contest/ContestStatus";
import ContestSubmit from "@/components/contest/ContestSubmit";
import ContestProblemDetail from "@/components/contest/ContestProblemDetail";
import Game from "@/components/Game";
import ContestEndRank from "@/components/contest/ContestEndRank";


Vue.use(Router)


const routes = [{
        path: '/register',
        name: 'register',
        component: Register,
        meta: {
            title: '注册'
        }
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
        meta: {
            title: '登录'
        }
    },
    {
        path: '/',
        name: 'home',
        component: Home,
        meta: {
            title: '主页',
            name: '首页'
        },

        children: [{
                path: '/problemList',
                component: ProblemList,
                meta: {
                    name: '题目'
                }
            }, {
                path: '/problemEdit',
                component: ProblemEdit,
                meta: {
                    name: '编辑题目'
                }
            }, {
                path: '/status',
                component: Status,
                meta: {
                    name: '提交状态'
                }
            }, {
                path: '/problem',
                component: Problem,
                meta: {
                    name: '题目描述'
                }
            }, {
                path: '/submit',
                component: Submit,
                meta: {
                    name: '题目提交'
                }
            }, {
                path: '/user',
                component: User,
                meta: {
                    name: '用户信息'
                }
            }, {
                path: '/user/blogList',
                component: UserBlogList,
                meta: {
                    name: '个人博客'
                }
            }, {
                path: '/user/statusList',
                component: UserStatus,
                meta: {
                    name: '提交记录'
                }
            }, {
                path: '/code',
                component: Code,
                meta: {
                    name: '完整代码'
                }
            }, {
                path: '/blog/edit',
                component: BlogEdit,
                meta: {
                    name: '博客编辑'
                }
            }, {
                path: '/blog/list',
                component: BlogList,
                meta: {
                    name: '博客'
                }
            }, {
                path: '/blog/blogDetail',
                component: BlogDetail,
                meta: {
                    name: '博客详情'
                }
            }, {
                path: '/news',
                component: News,
                meta: {
                    name: '资讯'
                }
            }, {
                path: '/rank',
                component: Rank,
                meta: {
                    name: '排名'
                }
            }, {
                path: '/discussion',
                component: Dicussion,
                meta: {
                    name: '讨论'
                }
            }, {
                path: 'game',
                component: Game,
                meta: {
                    name: '游戏'
                }
            },


            {
                path: '/contestList',
                component: ContestList,
                meta: {
                    name: '比赛'
                }
            }, {
                path: '/contest/edit',
                component: ContestEdit,
                meta: {
                    name: '新建比赛'
                }
            },  {
                path: '/contestEndRank',
                component: ContestEndRank,
                meta: {
                    name: '比赛结果'
                }
            }, {
                path: '/contest',
                component: ContestDetail,
                meta: {
                    name: '比赛'
                },
                children: [{
                        path: '/contest/problem',
                        component: ContestProblemList,
                        meta: {
                            name: '比赛题目'
                        }
                    },
                    {
                        path: '/contest/rank',
                        component: ContestRank,
                        meta: {
                            name: '比赛排名'
                        }
                    },
                    {
                        path: '/contest/status',
                        component: ContestStatus,
                        meta: {
                            name: '比赛过题状态'
                        }
                    },
                    {
                        path: '/contest/submit',
                        component: ContestSubmit,
                        meta: {
                            name: '比赛提交'
                        }
                    },
                    {
                        path: '/contest/problemDetail',
                        component: ContestProblemDetail,
                        meta: {
                            name: '题目'
                        }
                    }

                ]
            }







        ]

    }

]

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
