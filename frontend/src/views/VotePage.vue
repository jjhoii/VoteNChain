<template>
  <div>
    <HNavGray />
    <div class="container" id="doVote">
      <!-- <div style=" margin-top: 200px;">
        <b-button id="show-btn" @click="$bvModal.show('bv-modal-example1')"
          >Open Modal</b-button
        > -->

      <b-modal
        id="bv-modal-example1"
        hide-header-close
        hide-footer
        no-close-on-backdrop
      >
        <template #modal-title> 로그인 </template>
        <div class="d-block text-center justify-center">
          <kakaoLogin />
          <!-- <GoogleLogin  :params="params" :renderParams="renderParams" :onSuccess="onSuccess"></GoogleLogin> -->
        </div>
      </b-modal>
      <!-- </div> -->

      <button class="button_status" style="margin-top: 20px">투표현황</button>
      <div name="title">
        <center>
          <h1>{{ mainTitle }}</h1>
        </center>
      </div>
      <div name="main-image" style="margin-top: 30px">
        <center>
          <img
            :src="mainImagePath"
            style="width: 300px; height: 200px; border-radius: 20px"
            alt=""
          />
        </center>
      </div>
      <div name="content">
        <center>
          <p
            style="
              font-size: 25px;
              margin-top: 50px;
              margin-bottom: 50px;
              margin-left: 50px;
              margin-right: 50px;
            "
          >
            {{ mainDescription }}
          </p>
        </center>
      </div>

      <div
        name="vote-list"
        style="
          width: 100%;
          display: flex;
          flex-wrap: wrap;
          justify-content: center;
        "
      >
        <!-- 이미지 있는 투표 항목-->
        <!-- <div v-if="imageExist"> -->
        <!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
        <div
          v-for="(item, idx) in items"
          v-bind:item="item"
          v-bind:key="idx"
          v-if="imageExist"
        >
          <ImageRadio
            :idx="idx"
            :title="item.title"
            :imagePath="item.imagePath"
            :description="item.description"
            v-on:selectItem="selectItem"
          />
        </div>

        <!-- 이미지 없는 투표 항목-->
        <!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
        <div
          v-for="(item, idx) in items"
          v-bind:item="item"
          v-bind:key="idx"
          v-if="!imageExist"
        >
          <TextRadio
            :idx="idx"
            :title="item.title"
            :description="item.description"
          />
        </div>
      </div>
      <div
        name="vote-end-button"
        style="margin-top: -10px; margin-bottom: 20px"
      >
        <center style="margin-bottom: 50px">
          {{ picked }}
          <a class="button_do" @click="doVote">투표 하기!</a>
        </center>
        <div class="modal" tabindex="-1" style="margin-top: 200px">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title">Modal title</h5>
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div class="modal-body">
                <p>Modal body text goes here.</p>
              </div>
              <div class="modal-footer">
                <button
                  type="button"
                  class="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  Close
                </button>
                <button type="button" class="btn btn-primary">
                  Save changes
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HNavGray from "@/components/common/HNavGray";
import VoteCard from "@/components/votepage/VoteCard";
import VoteWritten from "@/components/votepage/VoteWritten";
import ImageRadio from "@/components/votepage/ImageRadio";
import TextRadio from "@/components/votepage/TextRadio";
import axios from "axios";
import { Utils } from "@/utils/index.js";
import kakaoLogin from "@/components/socialLogin/kakao.vue";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    VoteCard,
    VoteWritten,
    ImageRadio,
    TextRadio,
    HNavGray,
    kakaoLogin,
  },
  data: function () {
    return {
      items: [],
      mainTitle: "",
      mainDescription: "",
      mainImagePath: "",
      imageExist: false,
      isLogin: false,
      picked: 10000,
    };
  },
  async created() {
    this.loginCheck();
    //this.$bvModal.show("bv-modal-example");
    if (this.isLogin == true) {
      await this.getContractAddress();

      const isVoteEnd = await this.isVoteEnd(this.n);
      const isVote = await this.isVote(this.n);
      console.log("isVoteEnd: ", isVoteEnd, ", isVote: ", isVote);
      if (isVoteEnd || isVote) {
        // route to voteGraph
        this.$router.replace("/votegraph/" + this.$route.params.hashKey);
        return;
      }

      //await this.doVote(0); // 임시 0번 투표 진행
    }
  },
  mounted() {
    if (this.isLogin == false) {
      this.$bvModal.show("bv-modal-example1");
    }
    console.log("Test");
  },
  methods: {
    loginCheck() {
      console.log(localStorage.getItem("access_token"));
      console.log(localStorage.getItem("myData"));
      if (
        localStorage.getItem("access_token") == undefined ||
        localStorage.getItem("myData") == undefined
      ) {
        console.log("로그인 안됨.");
        //this.$router.push("VoteMake");
      } else {
        console.log("로그인 됨.");
        this.isLogin = true;
        //this.$router.push("VoteMake");
      }
      // console.log(this.$bvModal.show("bv-modal-example1"));
    },
    async isVote(idx) {
      const rs = await Utils.call(Utils.contract.methods.isVote, [idx]);
      return rs;
    },
    async isVoteEnd(idx) {
      const rs = await Utils.call(Utils.contract.methods.isVoteEnd, [idx]);
      return rs;
    },
    async doVote() {
      if (this.picked == 10000) {
        alert("항목을 선택해 주세요!");
      } else {
        console.log(this.picked + "들어옴");
        await this.sendVote(this.picked);
      }
      // 추가 소켓 통신

      // go to graph
      this.$router.replace("/votegraph/" + this.$route.params.hashKey);
    },
    async sendVote(idx) {
      console.log("sending");
      const rs = await Utils.send(Utils.contract.methods.voteTo, [this.n, idx]);
      console.log("result: ", rs);
    },
    async getContractAddress() {
      // console.log("true");
      this.$store.state.loading.enabled = true;
      try {
        const res = await axios.get(`${SERVER_URL}/vote/read`, {
          params: { hashKey: this.$route.params.hashKey },
        });
        const idx = res.data.vote.contractAddress * 1;
        await this.getData(idx);

        this.n = idx;
      } catch (err) {
        console.log(err);
      }
      // console.log("false");
      this.$store.state.loading.enabled = false;
    },
    async getData(idx) {
      // get vote data
      const rs = await Utils.call(Utils.contract.methods.getVote, [idx]);
      console.log(rs);

      // set data
      this.mainTitle = rs.title;
      this.mainDescription = rs.description;
      this.mainImagePath = rs.imagePath;
      this.imageExist = rs.bImageExist;
      // this.imageExist = false;
      this.items = rs.items;
      //   if(this.mainImagePath==""){
      //       haveImage = false;
      //   }
      //console.log("이미지 경로" + this.mainImagePath+"ㅎ");

      // load complete
      this.loaded = true;
    },
    async selectItem(data) {
      this.picked = data;
    },
  },
};
</script>

<style>
.wrap {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -86px;
  margin-left: -89px;
  text-align: center;
}

a {
  -webkit-transition: all 200ms cubic-bezier(0.39, 0.5, 0.15, 1.36);
  -moz-transition: all 200ms cubic-bezier(0.39, 0.5, 0.15, 1.36);
  -ms-transition: all 200ms cubic-bezier(0.39, 0.5, 0.15, 1.36);
  -o-transition: all 200ms cubic-bezier(0.39, 0.5, 0.15, 1.36);
  transition: all 200ms cubic-bezier(0.39, 0.5, 0.15, 1.36);
  display: block;
  margin: 20px auto;
  max-width: 180px;
  text-decoration: none;
  border-radius: 4px;
  padding: 20px 30px;
}

a.button_do {
  color: rgba(30, 22, 54, 0.6);
  box-shadow: rgba(30, 22, 54, 0.4) 0 0px 0px 2px inset;
}

a.button_do:hover {
  color: rgba(255, 255, 255, 0.85);
  box-shadow: rgba(30, 22, 54, 0.7) 0 0px 0px 40px inset;
}

#doVote {
  background-color: #e9ecef;
  border: 1px solid rgb(245, 233, 233);
  border-radius: 20px;
  margin-bottom: 50px;
  /* background-image: url('~@/assets/note4.jpg'); */

  /* opacity: 0.9; */
}
.button_status {
  width: 140px;
  height: 45px;
  font-family: "Roboto", sans-serif;
  font-size: 11px;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  font-weight: 500;
  color: #000;
  background-color: #fff;
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
}

.button_status:hover {
  background-color: #adb5bd;
  box-shadow: 0px 15px 20px #adb5bd;
  color: rgb(10, 10, 10);
  transform: translateY(-7px);
}
</style>
