<template>
  <label>
    <input
      type="radio"
      name="imageRadio"
      value="idx"
      v-model="picked"
      @click="clickItem"
    />
    <div id="img" style="margin-bottom:20px; word-break:break-all;">
      <h2 id="option_title">{{ title }}</h2>
      <img
        :src="imagePath"
        style="margin-left:50px;margin-top:10px; width:200px;height:200px;border-radius:10px"
      />
      <b-button
        pill
        variant="outline-secondary"
        @click="openDetail"
        style="margin-left:180px;margin-top:20px"
        >상세 보기</b-button
      >

      <b-modal ref="detail" title="상세보기" hide-footer>
        <p class="my-4" style="word-break:break-all;">{{ description }}</p>
      </b-modal>
    </div>
  </label>
</template>

<script>
export default {
  props: ["idx", "title", "imagePath", "description"],
  data() {
    return {
      confirm: -1,
      picked: "",
    };
  },

  methods: {
    openDetail() {
      this.$refs["detail"].show();
    },
    closeDetail() {
      this.confirm = -1;
    },
    clickItem() {
      this.$emit("selectItem", this.idx);
    },
  },
};
</script>

<style>
[type="radio"] {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}

/* IMAGE STYLES */
[type="radio"] + div#img {
  cursor: pointer;
  width: 300px;
  height: 350px;
  margin-left: 10px;
  margin-right: 10px;
  background-color: white;
  border-radius: 50px;
}

/* CHECKED STYLES */
[type="radio"]:checked + div#img {
  border: 2px solid #6c757d;
  background-color: #f8f9fa;
}
#option {
  background-color: white;
}
#option_title {
  font-family: "NIXGONM-Vb";
  margin-left: 0px;
  margin-top: 0px;
  padding-top: 15px;
  text-align: center;
}
</style>
