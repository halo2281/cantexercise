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

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/about",
    name: "About",
    component: About
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/main/:exerciseId",
    name: "ExerciseMain",
    component: Description
  },
  {
    path: "/sel/:exerciseId",
    name: "ExerciseSel",
    component: Selection
  },
  {
    path: "/guide/:exerciseId/:contentId",
    name: "ContentMain",
    component: Description
  },
  {
    path: "/connect/:exerciseId/:contentId",
    name: "Connect",
    component: Test
  },
  {
    path: "/practice/:exerciseId/:contentId",
    name: "Practice",
    component: Test
  },
  {
    path: "/test/:exerciseId/:contentId",
    name: "Test",
    component: Test
  },
  {
    path: "/score/:exerciseId/:contentId",
    name: "Score",
    component: Score
  },
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
