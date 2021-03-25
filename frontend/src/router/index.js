import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from "@/views/Login.vue";
import VoteMake from '@/views/VoteMake.vue';
import VoteList from '@/views/VoteList.vue';
import VotePage from '@/views/VotePage.vue';
import MyPage from '@/views/MyPage.vue';
import VoteGraph from '@/views/VoteGraph.vue';
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },

  {
    name: "login",
    path: "/login",
    component: Login,
  },

  {
    path: "/logout",
    name: "logout",
    beforeEnter(to, from, next) {
      store.commit("logout");
      alert("로그아웃 되었습니다.");
      next("/");
    },
  },

  {
    name: "votemake",
    path: "/votemake",
    component: VoteMake,
  },
  {
    name: "votelist",
    path: "/votelist",
    component: VoteList,
  },
  {
    name: "votepage",
    path: "/votepage",
    component: VotePage,
  },
  {
    name: "mypage",
    path: "/mypage",
    component: MyPage,
  },
  {
    name: "votegraph",
    path: "/votegraph",
    component: VoteGraph,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
