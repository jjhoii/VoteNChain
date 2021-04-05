<template>
  <div class="votemake-container">
    <HNavGray />
    <!-- <div class="votemake-content1">
      <h2>Page1</h2>
      <div><strong> 투표 제목</strong><img v-if="title" class="votemake-content1-img" src="../../public/images/check.jpg" /></div>
      <div><strong> 투표 이미지</strong><img v-if="previewImageData" class="votemake-content1-img" src="../../public/images/check.jpg" /></div>
      <div><strong> 투표 내용</strong><img v-if="description" class="votemake-content1-img" src="../../public/images/check.jpg" /></div>
      <div><strong> 투표 투표종류</strong><img v-if="false" class="votemake-content1-img" src="../../public/images/check.jpg" /></div>
      <h2>Page2</h2>
      <div><strong> 항목명 </strong><img v-if="title" class="votemake-content1-img" src="../../public/images/check.jpg" /></div>
      <div v-show="false"><strong> 항목 이미지</strong><img v-if="previewImageData" class="votemake-content1-img" src="../../public/images/check.jpg" /></div>
      <div ><strong> 투표 내용</strong><img v-if="description" class="votemake-content1-img" src="../../public/images/check.jpg" /></div>
      <div><strong> 투표 기간</strong><img v-if="false" class="votemake-content1-img" src="../../public/images/check.jpg" /></div>  
    </div> -->
    <form class="votemake-content2">
      <!-- <div class="votemake-container"> -->
      <div class="container d-flex p-2 bd-highlight" style="margin-top: 100px">
        <!-- <div class="votemake-content"> -->
        <div class="container-sm">
          <!-- <div class="mb-3 container-sm"> -->
          <div class="mb-3">
            <div class="">
              <h2 class="votemake-content2-title">Vote Make</h2>
              <div class="input-group input-group-lg">
                <!-- <span class="input-group-text" id="inputGroup-sizing-lg"
                  >투표 제목</span>         -->
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
              <!-- <b-form-file
                  v-model="fileId"
                  ref="file"
                  type="file"
                  placeholder="첨부파일 없음"
                  drop-placeholder="Drop file here..."
                  required
                  accept=".jpg, .png, .gif"
                  @change="previewImage"
                  style="width: 70%"
            ></b-form-file> -->
              Main Image
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
                <!-- <span class="input-group-text" id="inputGroup-sizing-lg"
                  >투표 내용</span
                > -->
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
          <!-- <fieldset class="row mb-3 alien-center">
            <legend class="col-form-label col-sm-2 pt-0">투표 종류</legend>
            <div class="col-sm-10">
              <div class="btn-group" role="group" aria-label="Basic example">
                <button type="button" class="button" style="width: 80px;">단일</button>
                <button type="button" class="btn btn-secondary">중복</button>
                <button type="button" class="btn btn-secondary">
                  가중치
                </button> 
              </div>
            </div>
          </fieldset> -->

          <div style="margin-bottom: 15px">
            <button
              type="button"
              class="button"
              :imageFlag="this.imageFlag"
              @click="changeFlag()"
            >
              항목 / 이미지 투표 전환
            </button>
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
            <button type="button" class="button" @click="AddSubject()">
              항목 추가
            </button>
          </div>
          <div class="continer" style="margin-top: 15px">
            <span>투표마감일</span>
            <div style="display: flex">
              <b-form-input type="date" v-model="endDate"></b-form-input>
            </div>
          </div>
          <!-- <b-button v-b-modal.modal-1>Launch demo modal</b-button> -->
          <b-modal ref="url" title="투표 URL" id="modal-1">
            <a
              @click="moveToVotePage"
              href=""
              style="font-size: 20px; margin-left: 10px"
              >https://votenchain.tk/votepage/{{ hashKey }}</a
            >
            <!-- <b-button @click="copy()"  v-clipboard="value">URL 복사</b-button> -->
          </b-modal>
          <div style="margin-top: 15px">
            <button
              @click="createVote()"
              type="button"
              class="button"
              style="width:80px"
            >
              제출
            </button>
            <!-- <button class="btn btn-secondary" type="button" disabled v-else>
              <span
                class="spinner-border spinner-border-sm"
                role="status"
                aria-hidden="true"
              ></span>
              <span class="sr-only">Loading...</span>
            </button> -->
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

      hashKey: '',
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
      var y = date.getFullYear();
      var m = date.getMonth() + 1;
      var d = date.getDate();
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
      // console.log("ㅋㄴㅋ");
      const rs = await Utils.send(Utils.contract.methods.setVote, [dat]);
      // console.log("ㅋㅁㅋ");
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
      console.log('11');
      this.sendData().then((rs) => {
        console.log('22');
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
              //alert("투표 URL : " + "/votepage/" + response.data.hashKey);
              console.log('gdg');
              this.hashKey = response.data.hashKey;
              this.$refs['url'].show();
              //this.$router.replace("/votepage/" + response.data.hashKey);
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

        if (this.imageFlag && this.voteList[i].imagePath == '') {
          alert(i + 1 + '번쨰 투표 이미지를 입력해주세요.');
          return false;
        }
      }

      return true;
    },
    test() {},
    moveToVotePage() {
      window.open('/votepage/' + this.hashKey, '_blank');
      this.$router.replace('/');
      //this.$router.replace("/votepage/" + this.hashKey);
    },
    copy() {
      // var url = "https://votenchain.tk/votepage/" +this.hashKey
      // window.ClipboardData.setData("Text",url);
      // alert("복사되었습니다.");

      // var obShareUrl = document.getElementById("ShareUrl");
      // obShareUrl.value = "https://votenchain.tk/votepage/" +this.hashKey  // 현재 URL 을 세팅해 줍니다.
      // obShareUrl.select();  // 해당 값이 선택되도록 select() 합니다
      // document.execCommand("copy"); // 클립보드에 복사합니다.
      // obShareUrl.blur(); // 선택된 것을 다시 선택안된것으로 바꿈니다.
      // alert("URL이 클립보드에 복사되었습니다");

      //  var copyText = "https://votenchain.tk/votepage/";
      // copyText.select();
      // document.execCommand("copy");
      // alert("복사된 문자열: " + copyText.value);

      var tempElem = document.createElement('textarea');
      tempElem.value = 'I am copied text!';
      document.body.appendChild(tempElem);

      tempElem.select();
      document.execCommand('copy');
      document.body.removeChild(tempElem);
      alert('복사');
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
  padding: 180px 0px 0px 0px;
  margin: 0px 0px 70px 150px;
  width: 30%;
  background: #f9f9f9;
  display: flex;
  flex-direction: column;
}
.votemake-content1 div {
  height: 60px;
  width: 100%;
  margin-left: 10px;
}
.votemake-content1 div strong {
  font-size: 20px;
}
.votemake-content1-img {
  width: 50px;
  height: 50px;
}
.votemake-content2 {
  background: white;
  margin: 140px 270px 70px 270px;
  padding: 0px 100px 50px 100px;
  width: 100%;
  border-radius: 10px;
  font-family: 'Playfair Display', serif;
  line-height: 1.7;

  font-weight: 100;
  font-size: 1rem;
}
.votemake-content2-title {
  font-family: 'Playfair Display', serif;
}

.button {
  background-color: #343a40;
  border: 2px solid #333;
  border-radius: 10px;
  color: #fff;
  line-height: 50px;
}
.button:hover {
  background-color: #fff;
  border-color: #212529;
  color: #212529;
}
</style>
