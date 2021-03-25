<template>
  <div>
    <div class="container">
      <div style="text-align:right;">
        <b-button>진행중인 투표</b-button>
      </div>
      <div style="display : flex">
          <div name="category" style="width : 30%">
              카테고리목록들~~~~~~~~
              
          <b-table  hover :items="items"></b-table>
          </div>
          <div name="vote-list-card" style="width:70%; display:flex;flex-wrap: wrap;"> 
            <div  v-for="(vote, idx) in list" :key="idx"><VoteCard :title="vote.title"/></div>

            <!-- <div><VoteCard/></div>
            <div><VoteCard/></div>
            <div><VoteCard/></div>
            <div><VoteCard/></div>
            <div><VoteCard/></div> -->
          </div>
      </div>
    </div>
    <div >
      <FootBar class="footbar" />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

import VoteCard from "@/components/vote/VoteCard";
import FootBar from "../components/common/FootBar";
export default {
  components: {
    VoteCard,FootBar
},
data() {
  return {
    items: [
      { category: '운동' },
      { category: '음악'  },
      { category: '병원'  },
      { category: '선거'  }
    ],
    list: [],
  }
},
created() {
    //페이지 시작하면은 자동 함수 실행
    this.readVotes();
  },
 methods: {
   readVotes(){
      axios
        .get(`${SERVER_URL}/vote/read`)
        .then((res) => {
          this.list = res.data.list;
        });
   }
 }
}
</script>

<style></style>
