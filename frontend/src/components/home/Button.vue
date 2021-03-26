<template>
    <div>
         <button href="#" class="content-btn">{{word}}</button>
    </div>
</template>

<script>
export default {
    props: {
    word : Object,

    }
}
</script>

<style scoped>
.content-btn {
  background: #f8f1f1;
  color: black;
  font-size: 1.2rem;
  padding: 1rem 2rem;
  text-decoration: none;
  width: 180px;
  border-radius: 10px;
}

</style>