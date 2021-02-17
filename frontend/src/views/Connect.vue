<template>
  <!-- 소개, 가이드 페이지로 사용 -->
  <!-- 장비 연결, 자세 연습, 실습 페이지로 사용 -->
  <v-container>
    <v-row no-gutters
    class="ml-auto mr-auto mt-10"
    >
      <v-col cols = "5">
        <v-img
        class="ml-auto mr-auto"   
        min-height="400"
        max-height="400"
        :src="image" 
        contain
        >
        </v-img>
      </v-col>

      <v-spacer></v-spacer>

      <v-col>
        <v-btn
        class = "ml-11 white--text"
        :class="stateColor[connectState]"
        @click="IoTConnect"
        >
          {{connectStateMsg[connectState]}}
        </v-btn>
      </v-col>

      <v-spacer></v-spacer>

      <v-col cols = "5">
        <img 
         v-bind:src="'data:image/jpg;charset=utf-8;base64,' + camera"
         min-height="400"
        />
      </v-col>
    </v-row>
    
    <v-alert
      class="mt-10 mr-auto ml-auto"
      border="left"
      colored-border
      color="deep-purple accent-4"
      elevation="2"
      max-width="1000"
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
import ContentService from '../axios/contentService'
import { io } from "socket.io-client"

@Component
export default class Test extends Vue {
  // 페이지 생성 시 DB에서 가져와야되는 것 : 보여주는 이미지
  // 장비 연결의 경우 착용 부위에 대한 설명도 필요
  // 촬영되야 되는 이미지가 필요한 경우 컴포넌트를 하나 추가적으로 생성
  // 자세 비교 시 진행에 맞춰 피드백도 가져와야 됨 -> 미정

  image = "";
  articles: [string, string][] = [];
  feedback: [string, string][] = [];
  connectState = 0;
  stateColor:string [] = ["grey lighten-1", "success", "error", "primary"]
  connectStateMsg:string [] = ["연결 하기", "연결 중...", "연결 실패", "연결 성공"]
  curUrlName = this.$route.name;
  prevUrl = "";
  nextUrl = "";
  camera = "";

  async created(){
    const exerciseId = this.$route.params.exerciseId;
    const contentId = this.$route.params.contentId;
 
    const detail: AxiosResponse<[]> = await ContentService.getDetail(contentId)
    console.log(detail.data)
    
    this.prevUrl = `/guide/${exerciseId}/${contentId}`;
    this.nextUrl = `/practice/${exerciseId}/${contentId}`;
      
    this.articles.push(["장비 착용 방법", detail.data.iotManual]);
    this.image = require(`@/assets/images/connect/${detail.data.detailId}.jpg`)
 
    }

    mounted(){
      //const socket = io('http://52.79.57.59:8083')
      //this.sensorSocket(socket)
      const imageSocket = io('http://52.79.57.59:8083')
      imageSocket.emit("imageFTS", "start" )

      imageSocket.on('imageSTF', (data) =>{
        console.log(data)
        this.camera = new TextDecoder("utf-8").decode(data);
      })
    }

    destroyed(){
      const imageSocket = io('http://52.79.57.59:8083')
      imageSocket.emit("imageFTS", "stop" )
    }

    IoTConnect(){
      this.connectState = 1;

      const sensorSocket = io('http://52.79.57.59:8083')
      sensorSocket.emit("sensorFTS", "connect" )

      sensorSocket.on('sensorSTF', (data) =>{
        console.log(data)

        // if(){
        //   // 연결 성공하면 
        //   this.connectState = 3;
        // } else {
        //   // 연결 성공하면
        //   this.connectState = 2;
        // }
      })
    }

    sensorSocket(socket){
      const ret = null;
  
        // socket.on('serverToFront', (data)=>{
        //   console.log(data)
        //   console.log("actnum : " + data.actnum)
        //   console.log("ispass : " + data.ispass)

        //   const actNum = parseInt(data.actnum, 10) + 1;

        //   let feedMsg = "";

        //   if(data.ispass == "1") {
        //     feedMsg += "성공하셨습니다. 다음 동작을 시작해주세요!"
        //   } else {
        //     feedMsg += `구분 동작 ${actNum}번 실패했습니다. 동작을 다시 해주세요.`
        //   }

        //   this.articles.pop()
        //   this.articles.push(["피드백", feedMsg])
        // })
    }

    
}
</script>
