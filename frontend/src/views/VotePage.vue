<template>
  <div>
    <div
      class="container"
      id="doVote"
    >
    <button class="button_status" style="margin-top:20px">투표현황</button>
      <div name="title">
        <center>
          <h1>투표 제목</h1>
        </center>
      </div>
      <div name="main-image" style="margin-top:30px">
        <center>
          <img
            src="https://source.unsplash.com/random"
            style="width: 300px; height: 200px;border-radius:20px"
          />
        </center>
      </div>
      <div name="content">
        <center>
          <p style="font-size:25px;margin-top:50px;margin-bottom:50px;margin-left:50px;margin-right:50px">
            당신의 눈앞에 선 101명의 소녀! 걸그룹 최종 멤버 선택과 데뷔 싱글의
            프로듀싱은 모두 TV를 보고 있는 당신의 몫! 과연 당신은 어느 소녀의
            재능과 매력에 손을 들어주게 될 것인가?
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
      <!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
         <div v-for="(item, idx) in list" v-bind:item="item" v-bind:key="idx">
          <ImageRadio :idx="idx" />
        </div> 

        <!-- 이미지 없는 투표 항목-->
        <!-- <div                                          
          v-for="(item, idx) in list"
          v-bind:item="item"
          v-bind:key="idx"
          style=""
        >
          <TextRadio :idx="idx" />
        </div> -->

      </div>
      <div name="vote-end-button" style="margin-top:-10px;margin-bottom:20px">
        <center style="margin-bottom:50px">
          <a href="#" class="button_do">투표 하기!</a>
        </center>
      </div>
    </div>
  </div>
</template>

<script>
import VoteCard from '@/components/votepage/VoteCard';
import VoteWritten from '@/components/votepage/VoteWritten';
import ImageRadio from '@/components/votepage/ImageRadio';
import TextRadio from '@/components/votepage/TextRadio';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    VoteCard,
    VoteWritten,
    ImageRadio,
    TextRadio,
  },
  data: function() {
    return {
      list: ['일', '이', '삼'],
    };
  },
  created() {
    this.getContractAddress();
  },
  methods: {
    getContractAddress() {
      axios
        .get(`${SERVER_URL}/vote/read`, {
          params: { hashKey: this.$route.params.hashKey },
        })
        .then((res) => {
        //   alert("컨트랙트 주소 : " + res.data.vote.contractAddress);
        })
        .catch((err) => {
          console.log(err);
        });
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

#doVote{
    background-color: #E9ECEF;
    border: 1px solid rgb(245, 233, 233);
    border-radius: 20px;
    margin-bottom:50px;
    background-image: url('~@/assets/note4.jpg');

    /* opacity: 0.9; */
}
.button_status {
  width: 140px;
  height: 45px;
  font-family: 'Roboto', sans-serif;
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
  background-color: #ADB5BD;
  box-shadow: 0px 15px 20px #ADB5BD;
  color: rgb(10, 10, 10);
  transform: translateY(-7px);
}
</style>
