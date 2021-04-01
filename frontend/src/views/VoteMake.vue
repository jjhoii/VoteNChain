<template>
  <div>
    <div class="votemake-container">
      <div class="votemake-content">

        <div class="content-title">
          <input class="content-title-input1" type="text" placeholder="제목" />
          <div  id="image" class="content-title">
            <b-form-file
              v-model="fileId"
              placeholder="첨부파일 없음"
              drop-placeholder="Drop file here..."
              required
              accept=".jpg, .png, .gif"
              @change="previewImage"
              style="width: 70%;"
            ></b-form-file>
            <img :src="previewImageData" />
          </div>
          <input class="content-title-input2" type="text" placeholder="내용" />
        </div>
        <div class="content-title">
          <h4>투표종류</h4>
          <button >단일</button>
          <button >복수</button>
          <button >가중치</button>
        </div>
        <div class="content-title" >
          <button @click="CheckWritten()">글</button>
          <button @click="CheckImage()">이미지</button>
        </div>

        <div v-if="WrittenCheck">
          <div v-for="idx in VoteWrittenCnt" :key="idx" class="content-title">
            <VoteWritten />
          </div>
        </div>

        <div v-if="ImageCheck">
          <div  v-for="idx in VoteImageCnt" :key="idx"  class="content-title">
            <VoteImage />
          </div>
        </div>

        <div class="content-title">
          <span>투표기간</span>
            <div style="display : flex;">
              <b-form-input style="width:30%" type="date"></b-form-input>
              <span>~</span>
              <b-form-input style="width:30%" type="date"></b-form-input>
            </div>
           
        </div>

        <div>
          <button @click="AddQuestion()">질문추가</button>
          <button @click="createVote()">제출</button>
        </div>


      </div>
    </div>
  </div>
</template>

<script>
import VoteWritten from "@/components/votemake/VoteWritten";
import VoteImage from "@/components/votemake/VoteImage";
export default {
  components: {
    VoteWritten,
    VoteImage,
  },
  data() {
    return {
      previewImageData: "",
      ImageCheck: false,
      WrittenCheck: true,
      VoteWrittenCnt : 1,
      VoteImageCnt : 1,
    };
  },
  methods: {
    CheckWritten() {
      this.VoteWrittenCnt = 1;
      this.WrittenCheck = true;
      this.ImageCheck = false;
    },
    CheckImage() {
      this.VoteImageCnt = 1;
      this.WrittenCheck = false;
      this.ImageCheck = true;
    },
    AddQuestion() {
      if(this.WrittenCheck == true){
      
        this.VoteWrittenCnt++;
      }else{
        this.VoteImageCnt++;
      }
    },
    createVote(){
      this.$router.push("VoteGraph");
      // this.form = {
      //   "userIdx" : 1,
      //   "contractAddress" : "tmp_contractAddress",
      //   "title" : this.title,
      //   "category" : this.category,
      //   "isPublic" : this.isPublic
      // }
      // axios
      //   .post(`${SERVER_URL}/vote/create`,this.form,{
      //   headers: {
      //     'Access-Control-Allow-Origin': '*',
      //     'Content-Type': 'application/json; charset = utf-8'
      //   }
      // } )
      //   .then((response) => {
      //     console.log("테스트"+response.title + " " + response.voteIdx);
      //     this.$router.replace('/votelist');
      //   })
      //   .catch(function(error) {
      //     console.log(error);
      //   });
    }
  },
  computed: {
    btnStates() {
      return this.buttons.map((btn) => btn.state);
    },
  },
};
</script>

<style>
.votemake-container {
  height: 100vh;
  width: 100%;
  background: rgb(236, 202, 202);
  padding: 1rem;
}
.votemake-content {
  margin: auto;
  border: 2px dotted red;
  color: red;
  background: white;
  padding: 1rem;
  width: 70%;
}
.content-title {
  margin: auto;
  border: 2px dotted red;
  width: 90%;
  text-align: center;
}
.content-title1 {
  margin: auto;
  border: 2px dotted red;
  width: 90%;
  text-align: center;
}
.content-title-input1 {
  border: 2px dotted red;
  width: 90%;
  font-size: 30px;
}
.content-title-input2 {
  border: 2px dotted red;
  width: 90%;
}
</style>
