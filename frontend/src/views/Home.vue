<template>
  <div class="home">
    <!-- <HelloWorld msg="Welcome to Your Vue.js + TypeScript App" /> -->
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import HelloWorld from "@/components/HelloWorld.vue"; // @ is an alias to /src


@Component({
  components: {
  }
})
export default class Home extends Vue {

  created(){
    //console.log(this.$store.state.auth.user)
  }
}
</script>
