<template>
  <v-app-bar app color="grey" dark>
    <div class="d-flex align-center mr-5">
      <a href="/">
        <v-img
          alt="Vuetify Logo"
          class="shrink mr-2"
          contain
          src="../assets/main_logo.png"
          transition="scale-transition"
          width="80"
        />
      </a>

    <v-toolbar-title class="font-weight-bold mr-5">{{ title }}</v-toolbar-title>
    </div>

    <v-row v-if='this.menuType == 2' class="grey align-content-center">
      <v-flex class="text-center" v-for="(menu, idx) in menus" :key="idx" >
        <div v-if="active == idx" class="black--text font-weight-bold">{{menu}}</div>
        <div v-else class="white--text font-weight-bold">{{menu}}</div>
      </v-flex>
    </v-row>

    <v-spacer></v-spacer>

    <v-btn href="/my" text>
      <v-icon>mdi-account</v-icon>
    </v-btn>

    <v-btn href="/about" text>
      <v-icon>mdi-help</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator'

@Component
export default class Header extends Vue{
    title = "";
    curUrl = this.$route;
    menus: string[] = ["소개", "선택", "가이드", "연결", "연습", "실습", "점수"];
    menuType = 0;
    active = 0;

    created(){
      console.log(this.curUrl.name)

      if(this.curUrl.name == "Home"){
        this.title = "온라인 체육 학습 플랫폼"
      } else if(this.curUrl.name == "About"){
        this.title = "도움말"
      } else if(this.curUrl.name == "User"){
        this.title = "마이 페이지"
        this.menuType = 1;
      } else {
        this.title = "운동" // axios를 사용하여 타이틀을 알아와야 됨
        this.menuType = 2;

        if(this.curUrl.name == "ExerciseMain") this.active = 0;
        else if(this.curUrl.name == "ExerciseSel") this.active = 1;
        else if(this.curUrl.name == "ContentMain") this.active = 2;
        else if(this.curUrl.name == "Connect") this.active = 3;
        else if(this.curUrl.name == "Practice") this.active = 4;
        else if(this.curUrl.name == "Test") this.active = 5;
        else if(this.curUrl.name == "Score") this.active = 6;
      }
    }
    
}
</script>
