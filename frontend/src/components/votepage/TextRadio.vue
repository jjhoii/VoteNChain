<template>
  <label>
    <input type="radio" name="textRadio" value="idx" @click="clickItem" />
    <div id="txt">
      <span style="margin-left:35px;font-size:30px"
        >{{ title }}</span
      ></div>
      <b-button
        @click="openDetail"
        pill
        variant="outline-secondary"
        style="margin-left:840px;margin-top:-78px"
        id="detail_btn"
        >상세 보기</b-button
      >

      <b-modal ref="detail" title="상세보기" hide-footer>
        <p class="my-4">{{ description }}</p>
      </b-modal>
    
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
      this.$refs['detail'].show();
    },
    closeDetail() {
      this.confirm = -1;
    },
    clickItem() {
      this.$emit('selectItem', this.idx);
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

[type='radio']:checked + div#txt + b-button#detail_btn{
  margin-top:-60px;
  
}
</style>
