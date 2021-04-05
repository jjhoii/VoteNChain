<template>
  <div class="votemake-container">
    <HNavGray />
    <div class="votemake-content1">
      <!-- <img  src="../../public/images/votelogo3.jpg" /> -->
    </div>
    <form class="votemake-content2">
      <!-- <div class="votemake-container"> -->
      <div class="container d-flex p-2 bd-highlight" style="margin-top: 100px">
        <!-- <div class="votemake-content"> -->
        <div class="container-sm">
          <!-- <div class="mb-3 container-sm"> -->
          <div v-if="contentData1">
            <div class="mb-3">
              <div class="">
                <h3>투표 만들기</h3>
                <div class="input-group input-group-lg">
                  <span class="input-group-text" id="inputGroup-sizing-lg"
                    >투표 제목</span
                  >
                  <input
                    type="text"
                    class="form-control"
                    aria-label="Sizing example input"
                    v-model="title"
                    aria-describedby="inputGroup-sizing-lg"
                  />
                </div>
                <br />

                메인이미지
                <br />
                <input
                  id="upload-image"
                  ref="file"
                  type="file"
                  accept=".jpg, .png, .gif"
                  @change="previewImage"
                /><br />
                <img id="previewimage" :src="previewImageData" />

                <div class="input-group input-group-lg">
                  <span class="input-group-text" id="inputGroup-sizing-lg"
                    >투표 내용</span
                  >
                  <textarea
                    class="form-control"
                    aria-label="Sizing example input"
                    aria-describedby="inputGroup-sizing-lg"
                    v-model="description"
                  ></textarea>
                </div>
              </div>
            </div>
            <div style="margin-bottom: 15px">
              <button
                type="button"
                class="btn btn-secondary"
                @click="chageContent()"
              >
                Next
              </button>
            </div>
          </div>

          <div v-if="contentData2">
            <div style="margin-bottom: 15px">
              <button
                type="button"
                class="btn btn-secondary"
                :imageFlag="this.imageFlag"
                @click="changeFlag()"
              >
                항목 / 이미지 투표 전환
              </button>

              이미지 투표
            </div>
            <!-- 계속 추가되는 라인 -->
            <div>
              <div v-if="WrittenCheck" class="border-top border-bottom">
                <VoteWritten
                  :imageFlag="imageFlag"
                  v-for="(list, index) in voteList"
                  :key="list.idx"
                  :index="index"
                  @changed="changed"
                  @deleteIndex="deleteIndex"
                  :list="list"
                >
                </VoteWritten>
              </div>
            </div>
            <div>
              <button
                type="button"
                class="btn btn-secondary"
                @click="AddSubject()"
              >
                항목 추가
              </button>
            </div>
            <div class="continer" style="margin-top: 15px">
              <span>투표 마감일</span>
              <div style="display: flex">
                <b-form-input type="date" v-model="endDate"></b-form-input>
              </div>
            </div>
            <div style="margin-top: 15px">
              <button
                type="button"
                class="btn btn-secondary"
                @click="chageContent()"
              >
                Pre
              </button>
              <button
                @click="createVote()"
                type="button"
                class="btn btn-secondary"
              >
                제출
              </button>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import HNavGray from '@/components/common/HNavGray';
import axios from 'axios';
import { Utils } from '@/utils/index.js';
import VoteWritten from '@/components/votemake/VoteWritten';
import VoteImage from '@/components/votemake/VoteImage';
import AWS from 'aws-sdk';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    VoteWritten,
    VoteImage,
    HNavGray,
  },
  data() {
    return {
      endDate: '',
      loading: true,
      imageFlag: false,
      isPublic: 1,
      title: '',
      description: '',
      previewImageData: '',
      ImageCheck: false,
      WrittenCheck: true,
      VoteWrittenCnt: 1,
      VoteImageCnt: 1,
      fileId: null,
      voteList: [
        {
          idx: '',
          title: '',
          description: '',
          imagePath: '',
          count: 0,
        },
      ],
      idxCount: 0,

      voteTitle: '',
      mainImage: null,
      mainImagePath: '',
      mainDescription: '',

      bucketName: 'vncbucket',
      bucketRegion: 'ap-northeast-2',
      IdentityPoolId: 'ap-northeast-2:de2bc69f-a616-4734-a2c5-1d7bc1b95350',

      contentData1: true,
      contentData2: false,
    };
  },
  created() {
    // Utils.createAccount().then((rs) => {
    //   this.sendData();
    // });
    this.endDate = this.setDate();
  },
  methods: {
    setDate() {
      var date = new Date();
      var y = date.getUTCFullYear();
      var m = date.getUTCMonth() + 1;
      var d = date.getUTCDate();
      if (m < 10) {
        m = '0' + m;
      }
      if (d < 10) {
        d = '0' + d;
      }
      return y + '-' + m + '-' + d;
    },
    changeFlag() {
      this.imageFlag = !this.imageFlag;
    },
    async showBalance() {
      const rs = await Utils.getBalance();
      console.log(rs);
    },
    async getBalance() {
      const rs = await Utils.receiveBalance();
      console.log(rs);
    },
    sendCallback(data) {
      console.log('result!!: ', data);
    },
    async sendData() {
      // send test data to contract
      // data: { title:"test", description:"test", voteType:0, imagePath:"path", bImageExist:true, bShowDetail:true, createdAt:Date.now(), endedAt:Date.now() + 600 * 1000, items:[{ title:"test1", description:"test1", imagePath:"testPath", count:0 },{ title:"test2" description:"test2", imagePath:"testPath2", count:0 }] }

      this.loading = false;
      const dat = {
        title: this.title,
        description: this.description,
        voteType: 0,
        imagePath: this.mainImagePath,
        bImageExist: this.imageFlag,
        bShowDetail: true,
        createdAt: Date.now(), // dummy data. contract gets current time from block.
        // endedAt: Date.now() + 600 * 1000, // 5분 뒤
        endedAt: Date.parse(new Date(this.endDate + 'T24:00:00')), // 24시간 뒤
        // items: [
        //   {
        //     title: "Title1",
        //     description: "Desc1",
        //     imagePath:
        //       "https://cdn.pixabay.com/photo/2021/03/26/19/05/flamingo-6126763_960_720.jpg",
        //     count: 0, // dummy data.
        //   },
        //   {
        //     title: "Title2",
        //     description: "Desc2",
        //     imagePath:
        //       "https://cdn.pixabay.com/photo/2021/03/26/19/05/flamingo-6126763_960_720.jpg",
        //     count: 0,
        //   },
        // ],

        items: this.voteList,
      };

      // send data
      // const rs = await Utils.signAndSend(Utils.contract.methods.setVote, [dat]);
      console.log(dat);
      const rs = await Utils.send(Utils.contract.methods.setVote, [dat]);

      // send complete
      console.log(
        'send complete: ',
        rs,
        parseInt(rs.events.VoteCreated.raw.data)
      );
      this.loading = true;
      return parseInt(rs.events.VoteCreated.raw.data);
    },
    uploadImage() {
      this.mainImage = this.$refs.file.files[0];
      console.log(this.mainImage, '파일 업로드');

      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId,
        }),
      });

      var s3 = new AWS.S3({
        apiVersion: '2006-03-01',
        params: {
          Bucket: this.bucketName,
        },
      });

      let imageName = this.mainImage.name;
      let imageKey = 'images/' + Date.now().toString() + '_' + imageName;

      console.log(imageKey);

      s3.upload(
        {
          Key: imageKey,
          Body: this.mainImage,
          ACL: 'public-read',
        },
        (err, data) => {
          if (err) {
            console.log(err);
          } else {
            this.mainImagePath = data.Location;
            console.log('mainImagePath : ' + this.mainImagePath);
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

        console.log('uploadImage start');
        this.uploadImage();
        console.log('uploadImage end');
      } else {
        this.previewImageData = null;
      }

      document.getElementById('previewimage').style =
        'width:200px; height:200px';
    },
    createVote() {
      if (!this.validationCheck()) {
        // alert('입력을 확인해주세요.');
        return;
      }

      this.validationCheck();

      this.$store.state.loading.enabled = true;

      this.sendData().then((rs) => {
        (this.form = {
          userIdx: 1,
          contractAddress: rs,
        }),
          axios
            .post(`${SERVER_URL}/vote/create`, this.form, {
              headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json; charset = utf-8',
              },
            })
            .then((response) => {
              alert('투표 URL : ' + '/votepage/' + response.data.hashKey);
              // this.$router.replace('/votepage/' + response.data.hashKey);
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
      // this.voteList.val.push(this.vote);
      this.voteList.push({
        title: '',
        description: '',
        imagePath: '',
        count: 0,
        idx: this.idxCount++,
      });
      console.log(this.voteList);
    },

    changed() {
      // console.log("인덱스",)
      // console.log(this.voteList);
    },
    deleteIndex(index) {
      console.log('인덱스', index);
      this.voteList.splice(index, 1);
    },
    validationCheck() {
      if (this.title == '') {
        alert('투표 제목을 입력해주세요.');
        return false;
      }

      if (this.description == '') {
        alert('투표 내용을 입력해주세요.');

        return false;
      }

      for (var i = 0; i < this.voteList.length; i++) {
        if (this.voteList[i].title == '') {
          alert(i + 1 + '번째 투표 항목명을 입력해주세요.');
          return false;
        }

        if (this.voteList[i].description == '') {
          alert(i + 1 + '번쨰 투표 설명을 입력해주세요.');
          return false;
        }
      }

      return true;
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
  background: #f9f9f9;
  display: flex;
}
.votemake-content1 {
  padding: 100px 0px 0px 0px;
  margin: 0px 0px 70px 70px;
  width: 40%;
  background: white;
}
.votemake-content1 img {
  width: 100%;
  height: 100%;
}
.votemake-content2 {
  background: #f7f6e7;
  margin: 0px 70px 70px 0px;
  padding: 150px 100px 50px 100px;
  width: 60%;
}
</style>
