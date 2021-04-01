<template>
  <div>
    <div class="votemake-container">
      <div class="votemake-content">
        <div class="content-title">
          <input class="content-title-input1" type="text" placeholder="제목" />
          <div id="image" class="content-title">
            <b-form-file
              v-model="fileId"
              placeholder="첨부파일 없음"
              drop-placeholder="Drop file here..."
              required
              accept=".jpg, .png, .gif"
              @change="previewImage"
              style="width: 70%"
            ></b-form-file>
            <img :src="previewImageData" />
          </div>
          <input class="content-title-input2" type="text" placeholder="내용" />
        </div>
        <div class="content-title">
          <h4>투표종류</h4>
          <button>단일</button>
          <button>복수</button>
          <button>가중치</button>
        </div>
        <div class="content-title">
          <button @click="CheckWritten()">글</button>
          <button @click="CheckImage()">이미지</button>
        </div>

        <div v-if="WrittenCheck">
          <div v-for="idx in VoteWrittenCnt" :key="idx" class="content-title">
            <VoteWritten />
          </div>
        </div>

        <div v-if="ImageCheck">
          <div v-for="idx in VoteImageCnt" :key="idx" class="content-title">
            <VoteImage />
          </div>
        </div>

        <div class="content-title">
          <span>투표기간</span>
          <div style="display: flex">
            <b-form-input style="width: 30%" type="date"></b-form-input>
            <span>~</span>
            <b-form-input style="width: 30%" type="date"></b-form-input>
          </div>
        </div>

        <div>
          <button @click="AddQuestion()">질문추가</button>
          <button @click="createVote()">제출</button>
        </div>
        <!-- 임시 -->
        <div>
          <b-button @click="showBalance">show balance</b-button>
          <b-button @click="getBalance">get balance</b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { Utils } from "@/utils/index.js";
import VoteWritten from "@/components/votemake/VoteWritten";
import VoteImage from "@/components/votemake/VoteImage";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    VoteWritten,
    VoteImage,
  },
  data() {
    return {
      isPublic: 1,
      title: "",
      previewImageData: "",
      ImageCheck: false,
      WrittenCheck: true,
      VoteWrittenCnt: 1,
      VoteImageCnt: 1,
    };
  },
  created() {
    Utils.createAccount().then((rs) => {
      this.sendData();
    });
  },
  methods: {
    async showBalance() {
      const rs = await Utils.getBalance();
      console.log(rs);
    },
    async getBalance() {
      const rs = await Utils.receiveBalance();
      console.log(rs);
    },
    sendCallback(data) {
      console.log("result!!: ", data);
    },
    async sendData() {
      // send test data to contract
      // data: { title:"test", description:"test", voteType:0, imagePath:"path", bImageExist:true, bShowDetail:true, createdAt:Date.now(), endedAt:Date.now() + 600 * 1000, items:[{ title:"test1", description:"test1", imagePath:"testPath", count:0 },{ title:"test2" description:"test2", imagePath:"testPath2", count:0 }] }
      const dat = {
        title: "Test Title",
        description: "Test Description",
        voteType: 0,
        imagePath:
          "https://cdn.pixabay.com/photo/2021/03/26/19/05/flamingo-6126763_960_720.jpg",
        bImageExist: true,
        bShowDetail: true,
        createdAt: Date.now(), // dummy data. contract gets current time from block.
        endedAt: Date.now() + 600 * 1000, // 5분 뒤
        items: [
          {
            title: "Title1",
            description: "Desc1",
            imagePath:
              "https://cdn.pixabay.com/photo/2021/03/26/19/05/flamingo-6126763_960_720.jpg",
            count: 0, // dummy data.
          },
          {
            title: "Title2",
            description: "Desc2",
            imagePath:
              "https://cdn.pixabay.com/photo/2021/03/26/19/05/flamingo-6126763_960_720.jpg",
            count: 0,
          },
        ],
      };
      // send data
      // const rs = await Utils.signAndSend(Utils.contract.methods.setVote, [dat]);
      const rs = await Utils.send(Utils.contract.methods.setVote, [dat]);

      // send complete
      console.log(
        "send complete: ",
        rs,
        parseInt(rs.events.VoteCreated.raw.data)
      );
      return parseInt(rs.events.VoteCreated.raw.data);
    },
    previewImage(event) {
      var input = event.target;
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          this.previewImageData = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
      } else {
        this.previewImageData = null;
      }
    },
    createVote() {
      this.form = {
        userIdx: 1,
        contractAddress: "tmp_contractAddress",
      };
      console.log("hi");
      axios
        .post(`${SERVER_URL}/vote/create`, this.form, {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json; charset = utf-8",
          },
        })
        .then((response) => {
          alert("투표 URL : " + "/votepage/" + response.data.hashKey);
          //this.$router.replace("/votelist");
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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
      if (this.WrittenCheck == true) {
        this.VoteWrittenCnt++;
      } else {
        this.VoteImageCnt++;
      }
    },
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
