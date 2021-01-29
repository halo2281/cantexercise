<template>
  <!-- 소개, 가이드 페이지로 사용 -->
  <v-container>
    <v-img 
      class="ml-auto mr-auto mt-10"
      height="400"
      width="400"
      :src='image'
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
      <div>{{article[1]}}</div>
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
export default class Description extends Vue {
    // 페이지 생성 시 DB에서 최상위, 최하위에 해당하는 대표 이미지와 설명들을 가져옴
    image = "";
    articles: [string, string][] = [];
    curUrlName = this.$route.name;
    prevUrl = "";
    nextUrl = "";

    async created(){
      //console.log(this.curUrlName);
      if(this.curUrlName == "ExerciseMain") {
        const exerciseId = this.$route.params.exerciseId;
        this.prevUrl = `/`;
        this.nextUrl = `/sel/${exerciseId}`
        const mainUnit: AxiosResponse<[]> = await AxiosService.instance.get('/mainUnit.json');
       
        for(const i in mainUnit.data){
            //console.log(mainUnit.data[i])
            if(mainUnit.data[i].mainUnitId == exerciseId){
                this.articles.push(["소개글", mainUnit.data[i].intro ]);
                this.articles.push(["향상 능력", mainUnit.data[i].improvement ]);
                this.image = require(`@/assets/images/mainUnit/${mainUnit.data[i].mainUnitId}.jpg`)
            }
        }

      } else {
        const exerciseId = this.$route.params.exerciseId;
        const contentId = this.$route.params.contentId;
        
        this.prevUrl = `/sel/${exerciseId}`;
        this.nextUrl = `/connect/${exerciseId}/${contentId}`

        const detail: AxiosResponse<[]> = await AxiosService.instance.get('/detail.json');
       
        for(const i in detail.data){
            // console.log(detail.data[i])
            if(detail.data[i].detailId == contentId){
                this.articles.push(["목표", detail.data[i].objective]);
                this.articles.push(["자세", detail.data[i].posture ]);
                this.articles.push(["Tip", detail.data[i].tip ]);
                this.image = require(`@/assets/images/detail/${detail.data[i].detailId}.jpg`)
            }
        }
      }

      
      // axios에서 대표 이미지와 설명글들 가져오는 부분 구현 필요   
    }
}
</script>
