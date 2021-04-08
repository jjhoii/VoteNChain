<template>
  <div class="votemake-container">
    <HNavGray />
    <form class="votemake-content">
      <div class="container d-flex p-1 bd-highlight" style="margin-top: 50px">
        <div class="container-sm">
          <div class="mb-3">
            <div class="">
              <h2
                class="votemake-content-title votemake_font"
                style="font-size:60px;margin-bottom:40px;text-align:center"
              >
                투표 만들기
              </h2>
              <div class="votemake_font" style="margin-bottom:10px">
                투표 제목
              </div>
              <div class="input-group input-group-lg">
                <input
                  type="text"
                  class="form-control"
                  aria-label="Sizing example input"
                  v-model="title"
                  aria-describedby="inputGroup-sizing-lg"
                  placeholder="투표 제목을 입력해주세요."
                />
              </div>
              <br />
              <p class="votemake_font">메인 이미지</p>
              <div class="filebox">
                <label for="ex_file">업로드</label>
                <input
                  type="file"
                  ref="file"
                  id="ex_file"
                  accept=".jpg, .png, .gif"
                  @change="previewImage"
                />
              </div>
              <img
                class="preimg"
                :src="previewImageData"
                v-if="previewImageData"
              />
              <br />
              <div class="input-group input-group-lg">
                <textarea
                  class="form-control"
                  aria-label="Sizing example input"
                  aria-describedby="inputGroup-sizing-lg"
                  v-model="description"
                  placeholder="투표에 대한 설명을 입력해주세요."
                ></textarea>
              </div>
            </div>
          </div>

          <div style="margin-bottom: 15px; display:flex;">
            <b-form-radio-group
              v-on:change="changeFlag()"
              v-model="selected"
              :options="options"
              class="mb-3"
              value-field="item"
              text-field="name"
            ></b-form-radio-group>

            <b-button id="tooltip-target-1">
              Tip!
            </b-button>
            <b-tooltip
              placement="topright"
              variant="danger"
              target="tooltip-target-1"
              triggers="hover"
            >
              글: 이미지가없는 투표 항목 <br />
              이미지: 이미지가있는 투표 항목
            </b-tooltip>
          </div>
          <!-- 계속 추가되는 라인 -->
          <div>
            <div v-if="WrittenCheck" class="border-top border-bottom">
              <VoteWritten
                :imageFlag="imageFlag"
                v-for="(list, index) in voteList"
                :key="list.idx"
                :index="index"
                @deleteIndex="deleteIndex"
                :list="list"
              >
              </VoteWritten>
            </div>
          </div>

          <div style="text-align:right;">
            <button
              type="button"
              class="button"
              id="plus_button"
              style="margin-top: 15px;background-color:#D9B79A;height:37px;width:100px"
              @click="AddSubject()"
            >
              항목 추가
            </button>
          </div>
          <div class="continer" style="margin-top: 15px">
            <span class="votemake_font" style="line-height:70px"
              >투표마감일</span
            >
            <div style="display: flex">
              <b-form-input type="date" v-model="endDate"></b-form-input>
            </div>
          </div>
          <b-modal
            ref="url"
            title="투표 링크"
            id="modal-1"
            no-close-on-backdrop
            hide-footer
            hide-header
            size="lg"
          >
            <h3 style="margin-left:0px;text-align:center">투표 링크</h3>
            <hr />
            <div>
              <a
                @click="moveToVotePage"
                href=""
                style="
                font-size: 25px;
                margin-left: 30px;
                margin-top: 0px;
                margin-bottom: 0px;
              "
                >{{ voteUrl }}</a
              >

              <input
                type="hidden"
                id="myInput"
                v-model="voteUrl"
                readonly
                style="width: 300px; margin-left: 0px;height:39px"
              />

              <b-button
                variant="outline-secondary"
                id="copy_button"
                @click="copyToClipboard('myInput')"
                style="margin-top:-97px;margin-left:570px"
                >링크 복사</b-button
              >
            </div>
            <div style="text-align:center">
              <b-button
                pill
                variant="outline-secondary"
                @click="moveToPage()"
                style="margin-left: 0px; margin-top: 0px;"
                >닫기</b-button
              >
            </div>
          </b-modal>
          <div style="margin-top: 50px;text-align:center">
            <b-button
              @click="createVote()"
              class="createbutton"
              type="button"
              variant="info"
              style="width: 60%;height:50px;margin-top:30px;background-color:#A68C7C;border-color: white; color:white;"
            >
              제출하기!
            </b-button>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import HNavGray from "@/components/common/HNavGray";
import axios from "axios";
import { Utils } from "@/utils/index.js";
import VoteWritten from "@/components/votemake/VoteWritten";
import VoteImage from "@/components/votemake/VoteImage";
import AWS from "aws-sdk";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    VoteWritten,
    VoteImage,
    HNavGray,
  },
  data() {
    return {
      endDate: "",
      loading: true,
      imageFlag: false,
      isPublic: 1,
      title: "",
      description: "",
      previewImageData: "",
      ImageCheck: false,
      WrittenCheck: true,
      VoteWrittenCnt: 1,
      VoteImageCnt: 1,
      fileId: null,
      voteList: [
        {
          idx: "",
          title: "",
          description: "",
          imagePath: "",
          count: 0,
        },
      ],
      idxCount: 0,

      voteTitle: "",
      mainImage: null,
      mainImagePath: "",
      mainDescription: "",

      bucketName: "vncbucket",
      bucketRegion: "ap-northeast-2",
      IdentityPoolId: "ap-northeast-2:de2bc69f-a616-4734-a2c5-1d7bc1b95350",

      contentData1: true,
      contentData2: false,

      voteUrl: "https://votenchain.tk/votepage/",
      hashKey: "",
      myInput: "",
      selected: "글",
      options: [
        { item: "글", name: "글" },
        { item: "이미지", name: "이미지" },
      ],
    };
  },
  created() {
    this.endDate = this.setDate();
  },
  methods: {
    setDate() {
      var date = new Date();
      var y = date.getFullYear();
      var m = date.getMonth() + 1;
      var d = date.getDate();
      if (m < 10) {
        m = "0" + m;
      }
      if (d < 10) {
        d = "0" + d;
      }

      return y + "-" + m + "-" + d;
    },
    changeFlag() {
      if (this.selected == "글") {
        this.imageFlag = false;
      }

      if (this.selected == "이미지") {
        this.imageFlag = true;
      }
    },
    async showBalance() {
      const rs = await Utils.getBalance();
    },
    async getBalance() {
      const rs = await Utils.receiveBalance();
    },
    sendCallback(data) {
      console.log("result!!: ", data);
    },
    async sendData() {
      // send test data to contract

      this.loading = false;
      const dat = {
        title: this.title,
        description: this.description,
        voteType: 0,
        imagePath: this.mainImagePath,
        bImageExist: this.imageFlag,
        bShowDetail: true,
        createdAt: Date.now(), // dummy data. contract gets current time from block.
        endedAt: Date.parse(new Date(this.endDate + "T24:00:00")), // 24시간 뒤

        items: this.voteList,
      };

      // send data
      const rs = await Utils.send(Utils.contract.methods.setVote, [dat]);
      // send complete
      // console.log(
      //   "send complete: ",
      //   rs,
      //   parseInt(rs.events.VoteCreated.raw.data)
      // );
      this.loading = true;
      return parseInt(rs.events.VoteCreated.raw.data);
    },
    uploadImage() {
      this.mainImage = this.$refs.file.files[0];

      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId,
        }),
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: {
          Bucket: this.bucketName,
        },
      });

      let imageName = this.mainImage.name;
      let imageKey = "images/" + Date.now().toString() + "_" + imageName;


      s3.upload(
        {
          Key: imageKey,
          Body: this.mainImage,
          ACL: "public-read",
        },
        (err, data) => {
          if (err) {
            console.log(err);
          } else {
            this.mainImagePath = data.Location;
          }
        }
      );
    },
    previewImage(event) {
      var input = event.target;
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          this.previewImageData = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);

        this.uploadImage();
      } else {
        this.previewImageData = null;
      }
    },
    createVote() {
      if (!this.validationCheck()) {
        return;
      }

      this.validationCheck();

      this.$store.state.loading.text = "투표를 생성하는 중입니다...";
      this.$store.state.loading.enabled = true;
      this.sendData().then((rs) => {
        this.form = {
          userIdx: 1,
          contractAddress: rs,
        };
        axios
          .post(`${SERVER_URL}/vote/create`, this.form, {
            headers: {
              "Access-Control-Allow-Origin": "*",
              "Content-Type": "application/json; charset = utf-8",
            },
          })
          .then((response) => {
            this.voteUrl =
              "https://votenchain.tk/votepage/" + response.data.hashKey;
            this.hashKey = response.data.hashKey;
            this.$refs["url"].show();
          })
          .catch(function(error) {
            console.log(error);
          });
        this.$store.state.loading.enabled = false;
      });
    },
    chageContent() {
      this.contentData1 = !this.contentData1;
      this.contentData2 = !this.contentData2;
    },

    AddSubject() {
      this.voteList.push({
        title: "",
        description: "",
        imagePath: "",
        count: 0,
        idx: this.idxCount++,
      });
    },

    deleteIndex(index) {
      this.voteList.splice(index, 1);
    },
    validationCheck() {
      if (this.title == "") {
        alert("투표 제목을 입력해주세요.");
        return false;
      }

      if (this.description == "") {
        alert("투표 내용을 입력해주세요.");

        return false;
      }

      for (var i = 0; i < this.voteList.length; i++) {
        if (this.voteList[i].title == "") {
          alert(i + 1 + "번째 투표 항목명을 입력해주세요.");
          return false;
        }

        if (this.voteList[i].description == "") {
          alert(i + 1 + "번쨰 투표 설명을 입력해주세요.");
          return false;
        }

        if (this.imageFlag && this.voteList[i].imagePath == "") {
          alert(i + 1 + "번쨰 투표 이미지를 입력해주세요.");
          return false;
        }
      }

      return true;
    },
    moveToVotePage() {
      window.open("/votepage/" + this.hashKey, "_blank");
      this.$router.replace("/");
    },
    copy() {
      var tempElem = document.createElement("textarea");
      tempElem.value = "I am copied text!";
      document.body.appendChild(tempElem);

      tempElem.select();
      document.execCommand("copy");
      document.body.removeChild(tempElem);
      alert("복사");
    },
    doCopy() {
      this.$copyText(this.myInput);
      alert(this.myInput + "을 복사했습니다.");
    },
    copyToClipboard(val) {
      const copyText = document.getElementById("myInput");
      copyText.select();
      document.execCommand("copy");
      alert(copyText.value + "을 복사했습니다.");
    },
    moveToPage() {
      this.$router.replace("/votepage/" + this.hashKey);
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
@font-face {
  font-family: "MaruBuri-Regular";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.0/MaruBuri-Regular.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

.votemake-container {
  background: #f9f9f9;
  display: flex;
}

.votemake-content {
  background: white;
  margin: 140px 270px 70px 270px;
  padding: 0px 100px 50px 100px;
  width: 100%;
  border-radius: 10px;
  font-family: "Playfair Display", serif;
  line-height: 1.7;

  font-weight: 100;
  font-size: 1rem;
  font-family: "MaruBuri-Regular";
}

.button {
  border-radius: 10px;
  color: #fff;
}
.button:hover {
  border-color: #212529;
  color: #212529;
}

#copy_button {
  height: 42px;
  width: 100px;
  margin-bottom: 3px;
  margin-left: 10px;
}
#tooltip-target-1 {
  font-size: 13px;
  background: white;
  color: rosybrown;
  border-color: rosybrown;
  height: 30px;
}
#plus_button {
  border: 0px;
}
.votemake_font {
  font-family: MaruBuri-Regular;
  font-size: 28px;
}

/*  파일 버튼선택  css 5개 */
.filebox label {
  display: inline-block;
  padding: 0.5em 0.75em;
  color: #fff;
  font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  background-color: #d9b79a;
  cursor: pointer;
  border-radius: 10px;
  -webkit-transition: background-color 0.2s;
  transition: background-color 0.2s;
}
.filebox label:hover {
  color: #212529;
}
.filebox label:active {
  background-color: #367c36;
}
.filebox input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}
</style>
