<template>
  <div style="display: flex">
    <HNavGray />
    <div class="container" id="doVote" style="margin-top: 150px">
      <b-modal
        id="bv-modal-example1"
        hide-header-close
        hide-footer
        no-close-on-backdrop
      >
        <template #modal-title>LOGIN</template>
        <div style="text-align: center; font-family:NIXGONM-Vb;">
          로그인 후 투표를 진행할 수 있습니다.
          <img src="@/assets/vnc_logo.png" style="margin-top:-40px" />
        </div>
        <div
          class="d-block text-center justify-center"
          style="margin-top:-60px"
        >
          <kakaoLogin />
        </div>
      </b-modal>

      <button
        @click="openStatus()"
        class="button_status"
        style="margin-top: 20px"
      >
        투표현황
      </button>
      <b-modal
        id="vote_status"
        ref="status"
        size="xl"
        title="투표 현황"
        hide-footer
        hide-header
      >
        <VoteStatus style="" />
      </b-modal>
      <div name="title">
        <div style="text-align: center; word-break: break-all">
          <h1 id="votepage_title">{{ mainTitle }}</h1>
          <br />
          <div>{{ createDate }} ~ {{ endDate }}</div>

          <div>
            <b-badge variant="success" v-if="this.endDayCheck()"
              >진행중</b-badge
            >
            <b-badge variant="secondary" v-else>마감</b-badge>
          </div>
        </div>
      </div>
      <div name="main-image" style="margin-top: 30px">
        <div style="text-align: center">
          <img
            v-if="mainImagePath != ''"
            :src="mainImagePath"
            style="width: 500px; height: 400px; border-radius: 20px"
            alt=""
          />
        </div>
      </div>
      <div name="content">
        <div style="text-align: center; word-break: break-all">
          <p id="votepage_desc">
            {{ mainDescription }}
          </p>
        </div>
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
            v-on:selectItem="selectItem"
          />
        </div>
      </div>
      <p
        style="padding: 0; margin: 0"
        v-for="(obj, index) in receivedMessages"
        :key="index"
      >
        {{ obj.sender }} : {{ obj.content }}
      </p>
      <div
        name="vote-end-button"
        style="margin-top: -10px; margin-bottom: 20px"
      >
        <div style="margin-bottom: 50px; text-align: center">
          <a class="button_do" @click="doVote">투표 하기!</a>
        </div>
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
import ImageRadio from "@/components/votepage/ImageRadio";
import TextRadio from "@/components/votepage/TextRadio";
import axios from "axios";
import { Utils } from "@/utils/index.js";
import kakaoLogin from "@/components/socialLogin/kakao.vue";
import VoteStatus from "@/components/votepage/VoteStatus";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    VoteCard,
    ImageRadio,
    TextRadio,
    HNavGray,
    kakaoLogin,
    VoteStatus,
  },
  data: function() {
    return {
      createdAt: "",
      endedAt: "",
      userName: "",
      message: "",
      receivedMessages: [],
      items: [],
      mainTitle: "",
      mainDescription: "",
      mainImagePath: "",
      imageExist: false,
      isLogin: false,
      picked: 10000,
      hashKey: "",
      createDate: "",
      endDate: "",
    };
  },
  async created() {
    this.loginCheck();
    if (this.isLogin == true) {
      await this.getContractAddress();

      const isVoteEnd = await this.isVoteEnd(this.n);
      const isVote = await this.isVote(this.n);
      if (isVoteEnd || isVote) {
        // route to voteGraph
        this.$router.replace("/votegraph/" + this.$route.params.hashKey);
        return;
      }
    }
    this.dayCheck();
    this.endDayCheck();
  },
  mounted() {
    if (this.isLogin == false) {
      this.$bvModal.show("bv-modal-example1");
    }
  },
  methods: {
    dayCheck() {
      var createDated = new Date(this.createdAt * 1000);
      var endDated = new Date(this.endedAt * 1000);

      var m = createDated.getMonth() + 1;
      var d = createDated.getDate();
      if (m < 10) {
        m = "0" + m;
      }
      if (d < 10) {
        d = "0" + d;
      }

      this.createDate = createDated.getFullYear() + "." + m + "." + d;

      m = endDated.getMonth() + 1;
      d = endDated.getDate();
      if (m < 10) {
        m = "0" + m;
      }
      if (d < 10) {
        d = "0" + d;
      }
      this.endDate = endDated.getFullYear() + "." + m + "." + d;
    },

    endDayCheck() {
      //지났음
      if (this.endedAt * 1000 < Date.now() * 1) {
        return false;
      }
      //안지났음
      return true;
    },
    loginCheck() {
      if (
        localStorage.getItem("access_token") == undefined ||
        localStorage.getItem("myData") == undefined
      ) {
      } else {
        this.isLogin = true;
      }
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
        return;
      } else {
        await this.sendVote(this.picked);
      }
      // 추가 소켓 통신
      this.syncSocket();
    },
    async sendVote(idx) {
      this.$store.state.loading.text = "투표가 진행중입니다...";
      this.$store.state.loading.enabled = true;
      const rs = await Utils.send(Utils.contract.methods.voteTo, [this.n, idx]);
      this.$store.state.loading.enabled = false;
      alert("투표가 완료 되었습니다.");
    },
    async getContractAddress() {
      try {
        const res = await axios.get(`${SERVER_URL}/vote/read`, {
          params: { hashKey: this.$route.params.hashKey },
        });

        this.hashKey = res.data.vote.hashKey;
        const idx = res.data.vote.contractAddress * 1;
        await this.getData(idx);

        this.n = idx;
      } catch (err) {
        console.log(err);
      }
      // console.log("false");
    },
    async getData(idx) {
      // get vote data
      const rs = await Utils.call(Utils.contract.methods.getVote, [idx]);

      // set data
      this.mainTitle = rs.title;
      this.mainDescription = rs.description;
      this.mainImagePath = rs.imagePath;
      this.imageExist = rs.bImageExist;
      this.endedAt = rs.endedAt;
      this.createdAt = rs.createdAt;
      this.items = rs.items;

      // load complete
      this.loaded = true;
    },
    async selectItem(data) {
      this.picked = data;
    },
    openStatus() {
      this.$refs["status"].show();
    },

    syncSocket() {
      // const serverURL = 'http://localhost:8080/ws';
      const serverURL = `${SERVER_URL}/ws`;
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect("", this.onConnected, this.onError);
    },

    onConnected() {
      //sendData
      var hashcode = this.$route.params.hashKey;
      this.stompClient.send(
        "/socket/chart/" + hashcode + "/receive",
        {},
        JSON.stringify({
          content: "",
          sender: this.picked,
          type: "JOIN",
        })
      );

      // go to graph
      this.$router.replace("/votegraph/" + this.hashKey);
    },
    onError(error) {
      console.log(error);
    },
    onDisconnected() {
      this.stompClient = null;
      this.receivedMessages = [];
    },
  },
};
</script>

<style>
@font-face {
  font-family: "BMJUA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@import url(//fonts.googleapis.com/earlyaccess/hanna.css);

.hanna * {
  font-family: "Hanna", fantasy;
}

@font-face {
  font-family: "NIXGONM-Vb";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/NIXGONM-Vb.woff")
    format("woff");
  font-family: "Hanna", fantasy;
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "TmoneyRoundWindExtraBold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/TmoneyRoundWindExtraBold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

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
  cursor: pointer;
}

a.button_do:hover {
  color: rgba(255, 255, 255, 0.85);
  box-shadow: rgba(30, 22, 54, 0.7) 0 0px 0px 40px inset;
}

#doVote {
  background-color: #ebeff3;
  /* border: 1px solid rgb(245, 233, 233); */
  border-radius: 20px;
  margin-bottom: 50px;
}
.button_status {
  width: 140px;
  height: 45px;
  font-family: "Hanna", fantasy;
  font-size: 18px;
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
#votepage_title {
  font-family: "TmoneyRoundWindExtraBold";
  font-size: 80px;
}
#votepage_desc {
  font-family: "NIXGONM-Vb";

  font-size: 25px;
  margin-top: 50px;
  margin-bottom: 50px;
  margin-left: 170px;
  margin-right: 170px;
  line-height: 50px;
}
</style>
