<template>
  <label>
    <input type="radio" name="test" value="small" />
    <div id=txt>
      <span style="margin-left:10px;font-size:30px">{{idx + 1}} . {{title}}</span>
      <!-- <button style="margin-left:200px;margin-top:100px">상세 보기</button> -->
      <b-button @click="openDetail" style="margin-left:850px;margin-top:-66px">상세 보기</b-button>
        
      <b-modal ref="detail" title="상세보기" >
        <p class="my-4">투표 항목에 대한 정보 {{idx}}</p>
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
      title:"항목" + (this.idx+1)
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
[type='radio'] + div#txt {
  cursor: pointer;
  width: 950px;
  height: 50px;
  outline: 1px solid rgb(14, 12, 12);

}

/* CHECKED STYLES */
[type='radio']:checked + div#txt {
  outline: 2px solid #f00;
}
</style>
