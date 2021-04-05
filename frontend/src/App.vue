<template>
  <div id="app">
    <Spinner v-if="$store.state.loading.enabled"></Spinner>
    <div>
      <router-view />
    </div>
  </div>
</template>
<script>
import HNav from '@/components/common/HNav';
import Spinner from '@/components/common/Spinner.vue';
export default {
  components: {
    HNav,
    Spinner,
  },
};
</script>
