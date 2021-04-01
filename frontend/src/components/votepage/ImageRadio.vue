<template>
  <label>
    <input type="radio" name="test" value="small" />
    <div id="img">
        <h2 style="margin-left:10px">{{idx + 1}} . {{title}}</h2>
      <img
        src="https://source.unsplash.com/random"
        style="margin-left:50px;margin-top:10px; width:200px;height:200px"
      />
      <!-- <button style="margin-left:200px;margin-top:100px">상세 보기</button> -->
      <b-button @click="openDetail" style="margin-left:200px;margin-top:50px">상세 보기</b-button>
        
      <b-modal ref="detail" title="상세보기" >
        <p class="my-4">투표 항목에 대한 정보 {{idx + 1}}</p>
      </b-modal>
    </div>
  </label>
</template>

<script>
export default {
  props: ['idx'],
  data() {
    return {
      confirm: -1,
      title: "항목" + (this.idx+1)
    };
  },
  methods: {
    openDetail() {
      //this.confirm = this.idx;
      this.$refs['detail'].show()
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
  outline: 1px solid rgb(14, 12, 12);
  margin-left: 10px;
  margin-right: 10px;
}

/* CHECKED STYLES */
[type='radio']:checked + div#img {
  outline: 2px solid #f00;
}
</style>
