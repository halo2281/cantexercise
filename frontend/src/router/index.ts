import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import { component } from "vue/types/umd";
import Home from "../views/Home.vue";
import About from "../views/About.vue";
import Selection from "../views/Selection.vue";
import Description from "../views/Description.vue";
import Test from "../views/Test.vue";
import Score from "../views/Score.vue";
import NotFound from "../views/NotFound.vue";
import Login from "../views/Login.vue"
// import Image from "../views/image.vue"
import authToken from "../axios/auth"

Vue.use(VueRouter);

const requireAuth = () => (from, to , next) => {
  if(authToken()) return next();
  else {
    alert("로그인이 필요한 서비스입니다. 로그인을 먼저 진행해주세요")
    next('/');
  }
}

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Login",
    component: Login
  },
  {
    path: "/home",
    name: "Home",
    component: Home,
    beforeEnter: requireAuth()
  },
  {
    path: "/about",
    name: "About",
    component: About,
    beforeEnter: requireAuth()
  },
  {
    path: "/main/:exerciseId",
    name: "ExerciseMain",
    component: Description,
    beforeEnter: requireAuth()
  },
  {
    path: "/sel/:exerciseId",
    name: "ExerciseSel",
    component: Selection,
    beforeEnter: requireAuth()
  },
  {
    path: "/guide/:exerciseId/:contentId",
    name: "ContentMain",
    component: Description,
    beforeEnter: requireAuth()
  },
  {
    path: "/connect/:exerciseId/:contentId",
    name: "Connect",
    component: Test,
    beforeEnter: requireAuth()
  },
  {
    path: "/practice/:exerciseId/:contentId",
    name: "Practice",
    component: Test,
    beforeEnter: requireAuth()
  },
  {
    path: "/test/:exerciseId/:contentId",
    name: "Test",
    component: Test,
    beforeEnter: requireAuth()
  },
  {
    path: "/score/:exerciseId/:contentId",
    name: "Score",
    component: Score,
    beforeEnter: requireAuth()
  },
  // {
  //   path: "/image",
  //   name: "Image",
  //   component: Image
  // },
  {
    path: "/404",
    name: "NotFound",
    component: NotFound
  },
  {
    path: "*",
    redirect: "/404"
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
