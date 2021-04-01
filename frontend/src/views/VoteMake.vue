<template>
  <div>
    <!-- <div class="votemake-container"> -->
    <div class="container d-flex p-2 bd-highlight">
      <!-- <div class="votemake-content"> -->
      <div class="container-sm">
        <!-- <div class="mb-3 container-sm"> -->
        <div class="mb-3">
          <div class="">
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
            <input
              id="upload-image"
              ref="file"
              type="file"
              accept=".jpg, .png, .gif"
              @change="previewImage"
            />
            <img :src="previewImageData" />
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
        <fieldset class="row mb-3 alien-center">
          <legend class="col-form-label col-sm-2 pt-0">투표 종류</legend>
          <div class="col-sm-10">
            <div class="btn-group" role="group" aria-label="Basic example">
              <button type="button" class="btn btn-secondary">단일</button>
              <button type="button" class="btn btn-secondary">중복</button>
              <button type="button" class="btn btn-secondary">가중치</button>
            </div>
          </div>
        </fieldset>

        <!-- <div class="content-title">
          <button @click="CheckWritten()">글</button>
          <button @click="CheckImage()">이미지</button>
        </div> -->

        <!-- 계속 추가되는 라인 -->
        <div class="container-sm">
          <div v-if="WrittenCheck" class="border-top border-bottom">
            <!-- <div v-for="idx in voteList" :key="idx" class="border-top border-bottom "> -->
            <VoteWritten
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

        <!-- <div v-if="ImageCheck">
            <div  v-for="idx in VoteImageCnt" :key="idx" class="content-title">
              <VoteImage />
            </div>
          </div> -->
        <div>
          <button @click="AddSubject()">항목 추가</button>
        </div>
        <div class="continer" style="margin-top:15px">
          <span>투표기간</span>
          <div style="display: flex">
            <b-form-input type="date"></b-form-input>
            <span>~</span>
            <b-form-input type="date"></b-form-input>
          </div>
        </div>
        <div style="margin-top:15px">
          <button @click="createVote()">제출</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
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
  },
  data() {
    return {
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
    };
  },
  created() {
    // Utils.createAccount().then((rs) => {
    //   this.sendData();
    // });
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
      console.log('result!!: ', data);
    },
    async sendData() {
      // send test data to contract
      // data: { title:"test", description:"test", voteType:0, imagePath:"path", bImageExist:true, bShowDetail:true, createdAt:Date.now(), endedAt:Date.now() + 600 * 1000, items:[{ title:"test1", description:"test1", imagePath:"testPath", count:0 },{ title:"test2" description:"test2", imagePath:"testPath2", count:0 }] }

      const dat = {
        title: this.title,
        description: this.description,
        voteType: 0,
        imagePath: this.mainImagePath,
        bImageExist: true,
        bShowDetail: true,
        createdAt: Date.now(), // dummy data. contract gets current time from block.
        endedAt: Date.now() + 600 * 1000, // 5분 뒤
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
    },
    createVote() {
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
              //this.$router.replace("/votelist");
            })
            .catch(function(error) {
              console.log(error);
            });
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
  },
  computed: {
    btnStates() {
      return this.buttons.map((btn) => btn.state);
    },
  },
};
</script>
