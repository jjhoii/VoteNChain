<template>
  <div class="spinner-modal">
    <div class="spinner-border" style="width: 3rem; height: 3rem" role="status">
      <span class="sr-only">Loading...</span>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<style>
.spinner-modal {
  display: flex;
  position: fixed;
  justify-content: center;
  align-items: center;
  background-color: black;
  opacity: 0.5;
  height: 100vh;
  width: 100vw;
  z-index: 100;
}
</style>