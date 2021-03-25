<template>
<div >
    <div style="display : flex; margin-top : 10%; min-height : 500px;">
        <div id="category-table" name="category" >
            <table style="width:100%" >
        <thead>
            <tr>
            <th>My Page</th>
            </tr>
        </thead>
        <tbody>
            <tr @click="ChangeMyMakePage()">
            <td>내가 만든 투표</td>
            </tr>
            <tr @click="ChangeParticipatePage()">
            <td>참여중인 투표</td>
            </tr>
            <tr @click="ChangeInformationPage()">
            <td>내 정보 보기</td>
            </tr>
        </tbody>
        
        </table>
        </div>
        <div v-if="check">
        <h1 >내가만든 투표</h1>
        <div><VoteCard /></div>
        </div>
        <div v-if="check1">
        <h1 >진행중인 투표  | 종료된 투표</h1>
        <div style="display : flex; flex-wrap: wrap;">
            <div><VoteCard /></div>
            <div><VoteCard /></div>
            <div><VoteCard /></div>
        </div>
        </div>
        <div v-if="check2">
        <h1 >내정보 보기</h1>
        <div>
            <h1>이메일 : 얄라리얄라리얄라셩</h1>
            <h1>이름 : 이름이름이</h1>
        </div>
        </div>
    </div>
    <div>
        <FootBar  class="footbar" />
    </div>
</div>
</template>

<script>
import VoteCard from "@/components/vote/VoteCard";
import FootBar from "@/components/common/FootBar";

export default {
  components: {
    VoteCard,
    FootBar
  },

  data() {
    return {
    
      check: true,
      check1: false,
      check2: false
    };
  },
  methods: {
      ChangeMyMakePage(){
        this.check = true;
        this.check1 = false;
        this.check2 = false;
      },
      ChangeParticipatePage(){
        this.check = false;
        this.check1 = true;
        this.check2 = false;
      },
      ChangeInformationPage(){
        this.check = false;
        this.check1 = false;
        this.check2 = true;
      }
  },
};
</script>
<style >
    
#category-table{
    width: 30%;
    justify-content: center;
}
</style>