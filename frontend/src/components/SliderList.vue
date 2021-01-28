<template>
  <div class="mt-10">
    <v-row>
        <h1 class="ml-10">{{title}}</h1>
    </v-row>
    <v-slide-group
      class="pa-4"
      active-class="success"
      show-arrows
    >
      <v-slide-item
        v-for="(article, idx) in articles"
        :key="idx"
        v-slot="{ active }"
      >
        <v-card 
          :color="active ? undefined : 'grey'"
          class="ml-5 mr-5 mt-5"
          height="250"
          width="300"
          @click="sendSig(article[2])"
        >

          <v-img
           height="270"
           :src='article[0]'
           alt = "../assets/images/mainUnit/3.PNG"
           class="balck--text align-end"
          >
            <v-card-title
            >{{article[1]}}</v-card-title>
          </v-img>
        </v-card>
      </v-slide-item>  
    </v-slide-group>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Prop, Emit, Watch} from 'vue-property-decorator'
import AxiosService from '../axios/index'
import { AxiosResponse } from 'axios';

@Component
export default class SliderList extends Vue {
  @Prop() id: number; 
  @Prop() depth: number;

  title = "";
  articles:[string, string, number][] = [];

  async mounted(){
    //   props에서 받아온 아이디를 통해
    //   console.log("id : "+this.id);
    //   console.log("depth : "+this.depth);
    //   console.log(this.isChange);
    //   console.log(this.$route.params)
      
      //this.articles.push(["https://cdn.vuetifyjs.com/images/cards/cooking.png","제목"+this.index++])
  
     this.getData();
  }

  @Watch('id')
  update(){
      this.getData();
  }

  async getData(){
      if(this.depth == 1){
          const primaryUnit: AxiosResponse<[]> = await AxiosService.instance.get('/primaryUnit.json');
          const mainUnit: AxiosResponse<[]> = await AxiosService.instance.get('/mainUnit.json')
          for(const i in mainUnit.data){
              //console.log(mainUnit.data[i])
              if(mainUnit.data[i].mainUnitId == this.id){
                  this.title = mainUnit.data[i].title;
              }
          }
          
          for(const i in primaryUnit.data){
              if(primaryUnit.data[i].main == this.id){
                this.articles.push([ require(`@/assets/images/primaryUnit/${primaryUnit.data[i].priUnitId}.jpg`) , 
                                  primaryUnit.data[i].title, 
                                  primaryUnit.data[i].priUnitId])
              }  
          }

          console.log(this.articles);
          
          // mainUnit(대단원) id를 사용하여 primaryUnit(중단원) 테이블의 아이디, 이미지와 제목을 가져옴
          // mainUnit(대단원) id를 사용하여 mainUnit(대단원) 제목을 가져옴
      } else if(this.depth == 2){
          const primaryUnit: AxiosResponse<[]> = await AxiosService.instance.get('/primaryUnit.json');
          const subUnit: AxiosResponse<[]> = await AxiosService.instance.get('/subUnit.json')
          for(const i in primaryUnit.data){
              //console.log(mainUnit.data[i])
              if(primaryUnit.data[i].priUnitId == this.id){
                  this.title = primaryUnit.data[i].title;
              }
          }
          
          this.articles.length = 0;
          for(const i in subUnit.data){
              if(subUnit.data[i].primarys == this.id){
              this.articles.push([require(`@/assets/images/subUnit/${subUnit.data[i].subUnitId}.jpg`), 
                                  subUnit.data[i].title, 
                                  subUnit.data[i].subUnitId])
              }
          }
          // primaryUnit(중단원) id를 사용하여 subUnit(소단원) 테이블의 아이디, 이미지와 제목을 가져옴
          // primaryUnit(중단원) id를 사용하여 primaryUnit(중단원) 제목을 가져옴
      } else if(this.depth == 3){
          const detail: AxiosResponse<[]> = await AxiosService.instance.get('/detail.json');
          const subUnit: AxiosResponse<[]> = await AxiosService.instance.get('/subUnit.json')
          for(const i in subUnit.data){
              //console.log(mainUnit.data[i])
              if(subUnit.data[i].subUnitId == this.id){
                  this.title = subUnit.data[i].title;
              }
          }
          
          this.articles.length = 0;
          for(const i in detail.data){
              if(detail.data[i].unit == this.id){
              this.articles.push([ require(`@/assets/images/detail/${detail.data[i].detailId}.jpg`), 
                                  detail.data[i].title, 
                                  detail.data[i].detailId])
              }
          }
          // subUnit(소단원) id를 사용하여 detail(세부사항) 테이블의 아이디, 이미지와 제목을 가져옴
          // subUnit(소단원) id를 사용하여 subUnit(소단원) 제목을 가져옴
      }
  }

  async sendSig(id){
    //   console.log(this.depth);
    //   console.log(event.path[1].innerText)
      
      if(this.depth == 3){
          // router를 이용하여 /guide/exerciseId/contetnId로 이동 
          const exerciseId = this.$route.params.exerciseId;
          const contentId = id;
          this.$router.push(`/guide/${exerciseId}/${contentId}`)
      } else {
          // emit을 이용하여 SliderList를 추가해야 한다는 신호를 보냄
          // 현재 depth와 id를 보냄
          //console.log(id);
          this.$emit("curDepth", this.depth, id )
      }
    
  }
}
</script>