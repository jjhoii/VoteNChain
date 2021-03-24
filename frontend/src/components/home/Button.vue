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
  background: #34b3a0;
  color: #fff;
  font-size: 1.2rem;
  padding: 1rem 2rem;
  text-decoration: none;
}

</style>