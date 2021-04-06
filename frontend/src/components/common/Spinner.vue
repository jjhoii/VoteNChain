<template>
  <div class="spinner-modal">
    <div
      class="spinner-border text-danger"
      style="width: 3rem; height: 3rem"
      role="status"
    >
      <span class="sr-only">Loading...</span>
    </div>
    <div class="txt">{{ $store.state.loading.text }}</div>
  </div>
</template>

<script>
export default {
  props: ["inputText"],
  data() {
    return {};
  },
};
</script>

<style>
.spinner-modal {
  display: flex;
  flex-direction: column;
  position: fixed;
  justify-content: center;
  align-items: center;
  background-color: black;
  opacity: 0.5;
  height: 100vh;
  width: 100vw;
  z-index: 9999;
}

.txt {
  margin: 30px;
  color: white;
  font-size: 20px;
}
</style>
