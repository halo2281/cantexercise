<template>
  <v-app>
    <!-- <Header></Header> -->
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Header from "@/components/Header.vue";

export default Vue.extend({
  name: "App",

  components: {
    // Header
  },

  data: () => ({})
});
</script>
