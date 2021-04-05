import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import VoteMake from '@/views/VoteMake.vue';
import VotePage from '@/views/VotePage.vue';
import VoteGraph from '@/views/VoteGraph.vue';
import test from '@/views/test.vue';
import ChatTest from '@/views/chatTest/test.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  
   {
    path: '/test',
    name: 'test',
    component: test
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
    name: "votepage",
    path: "/votepage/:hashKey",
    component: VotePage,
  },
 
  {
    name: "votegraph",
    path: "/votegraph/:hashKey",
    component: VoteGraph,
  },
  {
    name: "chattest",
    path: "/chattest",
    component: ChatTest,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
