<template>
  <label>
    <input type="radio" name="test" value="small" />
    <div id="txt">
      <span style="margin-left:10px;font-size:30px"
        >{{ idx + 1 }} . {{ title }}</span
      >
      <!-- <button style="margin-left:200px;margin-top:100px">상세 보기</button> -->
      <b-button
        @click="openDetail"
        pill
        variant="outline-secondary"
        style="margin-left:840px;margin-top:-72px"
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
  props: ['idx', 'title', 'description'],
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
[type='radio'] + div#txt {
  cursor: pointer;
  width: 950px;
  height: 50px;
  background-color: white;
  border-radius: 50px;
}

/* CHECKED STYLES */
[type='radio']:checked + div#txt {
  border: 2px solid #6c757d;
  background-color: #f8f9fa;
}
</style>
