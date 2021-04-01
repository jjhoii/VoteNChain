<template>
  <label>
    <input type="radio" name="test" value="small" />
    <div id="img" style="margin-bottom:20px">
      <h2 style="margin-left:30px;">{{ idx + 1 }} . {{ title }}</h2>
      <img
        :src="imagePath"
        style="margin-left:50px;margin-top:10px; width:200px;height:200px;border-radius:10px"
      />
      <!-- <button style="margin-left:200px;margin-top:100px">상세 보기</button> -->
      <b-button
        pill
        variant="outline-secondary"
        @click="openDetail"
        style="margin-left:180px;margin-top:30px"
        >상세 보기</b-button
      >

      <b-modal ref="detail" title="상세보기">
        <p class="my-4">description: {{ description }}</p>
      </b-modal>
    </div>
  </label>
</template>

<script>
export default {
  props: ['idx', 'title', 'imagePath', 'description'],
  data() {
    return {
      confirm: -1,
    };
  },
  methods: {
    openDetail() {
      //this.confirm = this.idx;
      this.$refs['detail'].show();
    },
    closeDetail() {
      this.confirm = -1;
      //   $bvModal.hide('idx');
    },
  },
};
</script>

<style>
[type='radio'] {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}

/* IMAGE STYLES */
[type='radio'] + div#img {
  cursor: pointer;
  width: 300px;
  height: 350px;
  /* outline: 1px solid rgb(14, 12, 12); */
  margin-left: 10px;
  margin-right: 10px;
  background-color: white;
  border-radius: 50px;
}

/* CHECKED STYLES */
[type='radio']:checked + div#img {
  /* border: 2px solid #f00; */
  border: 2px solid #6c757d;
  background-color: #f8f9fa;
}
#option {
  background-color: white;
}
</style>
