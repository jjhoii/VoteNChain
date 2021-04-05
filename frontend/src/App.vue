<template>
  <div id="app">
    <div>
      <Spinner v-if="$store.state.loading.enabled"></Spinner>
      <router-view />
    </div>
  </div>
</template>
<script>
import HNav from "@/components/common/HNav";
import Spinner from "@/components/common/Spinner.vue";
export default {
  components: {
    HNav,
    Spinner,
  },
};
</script>
<style>
.footbar {
  left: 0;
  bottom: 0;
  width: 100%;
  padding: 15px 0;
  text-align: center;
  background: #333;
}
</style>
