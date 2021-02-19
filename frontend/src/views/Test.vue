<template>
  <!-- 소개, 가이드 페이지로 사용 -->
  <!-- 장비 연결, 자세 연습, 실습 페이지로 사용 -->
  <v-container>
    <div 
    class="ml-auto mr-auto mt-10 d-flex justify-space-around align-center"
    align="center"
    justify="center"
    >
      <v-col cols = "5">
        <v-carousel
          cycle
          hide-delimiter-background
          hide-delimiters
          interval="2000"
          :show-arrows = "false"
          style="height: 450px;"
        >
          <v-carousel-item
            v-for="(content, i) in contents"
            :key="i"
          >
            <v-img
              class="ml-auto mr-auto"   
              min-height="400"
              max-height="400"
              :src="content.image" 
              contain
            >
            </v-img>
            <div class="text-center mt-5">
              <span>{{i+1}} / {{totActionNum}} </span>
            </div>
          </v-carousel-item>
        </v-carousel>
      </v-col>

      <v-spacer></v-spacer>

      <v-col>
        <v-btn
        class = "white--text"
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
    </div>

    <v-row
    align="center"
    justify="center">
      <v-btn 
      class="white--text"
      :class="stateColor[testState]"
      @click="startTest()"
      >
        {{testBtnMsg}}
      </v-btn>
    </v-row>

    <v-row
    class = "mt-10"
    align="center"
    justify="space-around">
      <v-btn v-for="(content, idx) in contents" :key="idx"
      class="white--text"
      :class="stateColor[content.state]"
      >
        동작 {{idx+1}}번
      </v-btn>
    </v-row>
    
    <v-alert
      class="mt-10 mr-auto ml-auto"
      border="left"
      colored-border
      color="deep-purple accent-4"
      elevation="2"
      max-width="1000"
    >
      <div class="title">
        {{help.title}}
      </div>
      <v-divider></v-divider>
      <div class="mt-5">{{help.content}}</div>
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

  contents = [];
  help = {
    "title" : "",
    "content" : ""
  };
  connectState = 0;
  testState = 0;
  testBtnMsg = ""
  totActionNum = 0;
  curActionNum = 0;
  iotNum = 0;
  practiceStates = [];
  stateColor:string [] = ["grey lighten-1", "success", "error", "primary"]
  connectStateMsg:string [] = ["연결 하기", "연결 중...", "연결 실패", "연결 성공"]
  curUrlName = this.$route.name;
  prevUrl = "";
  nextUrl = "";
  camera = "";
  socket = io('http://52.79.57.59:8083')

  async created(){
    const exerciseId = this.$route.params.exerciseId;
    const contentId = this.$route.params.contentId;
 
    const detail: AxiosResponse<[]> = await ContentService.getDetail(contentId)
    //console.log(detail.data)

    this.curActionNum = 1;
    this.totActionNum = detail.data.actionNum;
    this.iotNum = detail.data.iotNum;

    this.prevUrl = `/practice/${exerciseId}/${contentId}`;
    this.nextUrl = `/score/${exerciseId}/${contentId}`

    for(let i = 0; i < this.totActionNum ; i++) {
      const tmp = {
          "image" : require(`@/assets/images/practice/${detail.data.detailId}_${i+1}.jpg`),
          "state" : 0,
      }
      this.contents.push(tmp);
    } 

    //console.log(this.contents)
      
    //this.images.push(require(`@/assets/images/detail/${detail.data.detailId}.jpg`))
    //this.articles.push(["피드백","센서와 이미지를 통한 자세 비교 시 적절한 피드백 메세지 제공 예정"])
    this.help.title = "도움말"
    this.help.content = "위의 실습 시작 버튼을 클릭하면 실습이 시작됩니다."

    this.testBtnMsg = "실습 시작" 
    }

    mounted(){

      this.socket.emit("imageFTS", "start" )

      this.socket.on('imageSTF', (data) =>{
        //console.log(data)
        this.camera = new TextDecoder("utf-8").decode(data);
      })

      this.socket.on('sensorSTF', (data) =>{
        const ret = JSON.parse(data);
        console.log(ret);

        if(ret.success){
          if(ret.success == 1) this.connectState = 3;
          else if(ret.success == 0) this.connectState = 2;
        }

        if(ret.actnum){
          if(ret.ispass == 0){
            this.contents[ret.actnum].state = 2;
            console.log("실패")
          } else if(ret.ispass == 1){
            this.contents[ret.actnum].state = 3;
            console.log("성공")
          }

          if(ret.actnum == this.totActionNum-1 && ret.ispass == 1) {
            this.testState = 3;
            this.testBtnMsg = "실습 완료"

            const exerciseId = this.$route.params.exerciseId;
            const contentId = this.$route.params.contentId;
            location.href=`/score/${exerciseId}/${contentId}?score=90`
          }
        }
        
      })
    }

    IoTConnect(){
      this.connectState = 1;
      const contentId = this.$route.params.contentId;

      for(let i = 0; i < this.iotNum ; i++) {
        this.socket.emit("sensorFTS", `${contentId}_0${i+1}` )
      }
    }

    startTest(){
      const contentId = this.$route.params.contentId;
      console.log("실습 시작");
      this.testState = 1;
      this.testBtnMsg = "실습 중..."
      //console.log(this.testState)

      this.socket.emit("sensorFTS", `${contentId}_20` )
    }

}

</script>
