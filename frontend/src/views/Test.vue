<template>
  <!-- 소개, 가이드 페이지로 사용 -->
  <!-- 장비 연결, 자세 연습, 실습 페이지로 사용 -->
  <v-container>
    <v-img 
      class="ml-auto mr-auto mt-10"
      height="400"
      width="400"
      :src="image" 
      contain
    >
    </v-img>

    <v-alert
      class="mt-5 mr-auto ml-auto"
      border="left"
      colored-border
      color="deep-purple accent-4"
      elevation="2"
      max-width="1200"
      v-for="(article, idx) in articles" :key="idx"
    >
      <div class="title">
        {{article[0]}}
      </div>
      <v-divider></v-divider>
      <div class="mt-5">{{article[1]}}</div>
    </v-alert>

    <v-row
      class="mt-5"
      align="center"
      justify="space-between"
    >
      <v-btn
        :href="this.prevUrl"
        text
      >
        <v-icon>mdi-chevron-left</v-icon>
        <span class="mr-2">prev</span>
      </v-btn>

      <v-btn
        :href="this.nextUrl"
        text
      >
        <span class="mr-2">next</span>
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator'
import AxiosService from '../axios/index'
import { AxiosResponse } from 'axios';

@Component
export default class Test extends Vue {
  // 페이지 생성 시 DB에서 가져와야되는 것 : 보여주는 이미지
  // 장비 연결의 경우 착용 부위에 대한 설명도 필요
  // 촬영되야 되는 이미지가 필요한 경우 컴포넌트를 하나 추가적으로 생성
  // 자세 비교 시 진행에 맞춰 피드백도 가져와야 됨 -> 미정

  image = "";
  articles: [string, string][] = [];
  curUrlName = this.$route.name;
  prevUrl = "";
  nextUrl = "";

  async created(){
    // console.log(this.curUrlName);
    const exerciseId = this.$route.params.exerciseId;
    const contentId = this.$route.params.contentId;
    // console.log(exerciseId);
    // console.log(contentId);

    if(this.curUrlName == "Connect") { 
      this.prevUrl = `/guide/${exerciseId}/${contentId}`;
      this.nextUrl = `/practice/${exerciseId}/${contentId}`;
      const detail: AxiosResponse<[]> = await AxiosService.instance.get('/detail.json');
       
      for(const i in detail.data){
          //console.log(mainUnit.data[i])
          if(detail.data[i].detailId == contentId){
              this.articles.push(["장비 착용 가이드", detail.data[i].iotManual]);
              this.image = require(`@/assets/images/connect/${detail.data[i].detailId}.jpg`)
          }
      } 
    } else if(this.curUrlName == "Practice") {
      this.prevUrl = `/connect/${exerciseId}/${contentId}`;
      this.nextUrl = `/test/${exerciseId}/${contentId}`
      const detail: AxiosResponse<[]> = await AxiosService.instance.get('/detail.json');

      for(const i in detail.data){
          //console.log(mainUnit.data[i])
          if(detail.data[i].detailId == contentId){
              this.image = require(`@/assets/images/detail/${detail.data[i].detailId}.jpg`)
          }
      } 

      this.articles.push(["피드백","센서와 이미지를 통한 자세 비교 시 적절한 피드백 메세지 제공 예정"])
    } else if(this.curUrlName == "Test") {
      this.prevUrl = `/practice/${exerciseId}/${contentId}`;
      this.nextUrl = `/score/${exerciseId}/${contentId}`
      const detail: AxiosResponse<[]> = await AxiosService.instance.get('/detail.json');

      for(const i in detail.data){
          //console.log(mainUnit.data[i])
          if(detail.data[i].detailId == contentId){
              this.image = require(`@/assets/images/detail/${detail.data[i].detailId}.jpg`)
          }
      }

      this.articles.push(["피드백","센서와 이미지를 통한 자세 비교 시 적절한 피드백 메세지 제공 예정"])
    }

    // axios에서 대표 이미지와 설명글들 가져오는 부분 구현 필요   
    }
}
</script>
