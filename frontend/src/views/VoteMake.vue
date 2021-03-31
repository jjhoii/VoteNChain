<template>
  <div class="container">
    <div class="body1">
      <header class="vid-header container vote-make-header">
        <div class="fullscreen-vid-wrap">
          <video
            src="video/VoteVideo.mp4"
            muted="muted"
            autoplay="true"
            loop="true"
          ></video>
        </div>
        <div class="header-overlay"></div>
        <div class="header-content">
          <h1 style="text-align: left; margin: 0px 15% 1% 15%">투표만들기</h1>
          <div id="vote-make">
            <span>투표성격</span><br />
            <b-button-group>
              <b-button @click="isPublic = 1">공개</b-button>
              <b-button @click="isPublic = 0">
                비공개</b-button
              > </b-button-group
            ><br />

            <span>투표명</span><br />
            <b-form-input
              v-model="title"
              placeholder="Enter your name"
            ></b-form-input>
            <span>카테고리 선택</span><br />
            <table>
              <th>
                <b-form-input
                  v-model="categoryName"
                  placeholder="Enter your name"
                ></b-form-input>
              </th>
              <th>
                <b-button v-b-modal.category>선택</b-button>
                <b-modal id="category" title="Category">
                  <button
                    v-for="(categoryItem, index) in categoryItems"
                    v-bind:key="index"
                    @click="
                      category = index;
                      categoryName = categoryItem.category;
                    "
                  >
                    {{ categoryItem.category }}
                  </button>
                </b-modal>
              </th>
            </table>

            <span>투표 종류</span><br />
            <b-button-group>
              <b-button>단일</b-button>
              <b-button>복수</b-button>
              <b-button>가중치</b-button> </b-button-group
            ><br />

            <span>대표 이미지</span><br />
            <b-form-file
              v-model="fileId"
              :state="Boolean(file1)"
              placeholder="첨부파일 없음"
              drop-placeholder="Drop file here..."
              required
              accept=".jpg, .png, .gif"
              @change="previewImage"
              style="width: 70%"
            ></b-form-file
            ><br />

            <img class="profile_image" :src="previewImageData" /><br />

            <span>설명</span><br />
            <b-form-textarea
              id="textarea"
              v-model="text"
              placeholder="Enter something..."
              rows="3"
              max-rows="6"
            ></b-form-textarea
            ><br />

            <span>투표 항목</span> <b-button>이미지/글</b-button> <br />
            <b-card
              title=""
              img-src=""
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="mb-2"
            >
              제목<b-form-input
                v-model="text"
                placeholder="Enter your name"
              ></b-form-input>
              <b-card-text>
                <span>내용</span>
                <b-form-textarea
                  id="textarea"
                  v-model="text"
                  placeholder="Enter something..."
                  rows="3"
                  max-rows="6"
                ></b-form-textarea>
              </b-card-text>
            </b-card>

            <span>투표기간</span>
            <div style="display: flex">
              <b-form-input style="width: 30%" type="date"></b-form-input>
              <span>~</span>
              <b-form-input style="width: 30%" type="date"></b-form-input>
            </div>
            <b-button @click="createVote">제출</b-button>
            <!-- 임시 -->
            <div>
              <b-button @click="showBalance">show balance</b-button>
              <b-button @click="getBalance">get balance</b-button>
            </div>
          </div>
        </div>
      </header>
    </div>

    <div>
      <FootBar class="footbar" />
    </div>
  </div>
</template>

<script>
import FootBar from "../components/common/FootBar";
import axios from "axios";
import { Utils } from "@/utils/index.js";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    FootBar,
  },
  data() {
    return {
      previewImageData: "https://source.unsplash.com/random",
      isPublic: 1,
      title: "",
      categoryName: "",
      categoryItems: [
        { category: "운동" },
        { category: "음악" },
        { category: "병원" },
        { category: "선거" },
      ],
    };
  },
  created() {
    this.sendData();
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
      console.log("send complete: ", rs);
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
        title: this.title,
        category: this.category,
        isPublic: this.isPublic,
      };
      axios
        .post(`${SERVER_URL}/vote/create`, this.form, {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json; charset = utf-8",
          },
        })
        .then((response) => {
          console.log("테스트" + response.title + " " + response.voteIdx);
          this.$router.replace("/votelist");
        })
        .catch(function (error) {
          console.log(error);
        });
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
.vote-make-header {
  color: #233;
}
#vote-make {
  margin: 0px 0px 0px 15%;
  width: 70%;
  padding: 3%;
  text-align: left;
  background-color: #f3f4ed;
  border: black;
  border-style: solid;
  border-width: 3px;
  border-radius: 40px;
}
button {
  border-radius: 50%;
}
.profile_image {
  width: 70%;
  height: 100%;
  object-fit: cover;
}
</style>
