<template>
  <div id="app" >
    <div >
      <HNav />
      <div>
        <router-view />
      </div>
    </div>
  </div>
</template>
<script>
import HNav from "@/components/common/HNav";
export default {
  components: {
    HNav,
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
