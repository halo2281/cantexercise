<template>
  <v-container
   class="mx-auto"
   max-width="1000"
  > 
   <div class="mt-10">
    <v-row>
        <h1 class="ml-10">우리학교 맞춤 과정</h1>
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
        <div>
          <v-card 
            :color="active ? undefined : 'grey'"
            class="ml-5 mr-5 mt-5"
            height="250"
            width="300"
            :href = nextUrl+article[2]
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
        </div>

      </v-slide-item>  
    </v-slide-group>
  </div>

  <div class="mt-10">
    <v-row>
        <h1 class="ml-10">나의 관심사</h1>
    </v-row>
    <v-slide-group
      class="pa-4"
      active-class="success"
      show-arrows
    >
      <v-slide-item
        v-for="(hobby, idx) in hobbys"
        :key="idx"
        v-slot="{ active }"
      >
        <div>
          <v-card 
            :color="active ? undefined : 'grey'"
            class="ml-5 mr-5 mt-5"
            height="250"
            width="300"
            :href = nextUrl+hobby[2]
          >
  
            <v-img
             height="270"
             :src='hobby[0]'
             alt = "../assets/images/mainUnit/3.PNG"
             class="balck--text align-end"
            >
              <v-card-title
              >{{hobby[1]}}</v-card-title>
            </v-img>
          </v-card>
        </div>

      </v-slide-item>  
    </v-slide-group>
  </div>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import ContentService from '../axios/contentService'
import { AxiosResponse } from 'axios'

@Component
export default class Home extends Vue {

  articles:[string, string, number][] = [];
  hobbys:[string, string, number][] = [];
  nextUrl = "/main/"

  async mounted(){
    //location.reload(true);
    const user = JSON.parse(localStorage.getItem('user'));

    const book: AxiosResponse<[]> = await ContentService.getBook(user)
    //console.log(book);

    const mainUnit: AxiosResponse<[]> = await ContentService.getMainUnitList(book.data.bookId);
    //console.log(mainUnit);

    for(const i in mainUnit.data){
      this.articles.push([ require(`@/assets/images/mainUnit/${mainUnit.data[i].mainUnitId}.jpg`) , 
                            mainUnit.data[i].title, 
                            mainUnit.data[i].mainUnitId]) 
    }

    //console.log(this.articles);

  }
}
</script>
