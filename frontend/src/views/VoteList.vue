<template>
  <div>
    <div class="container">
      <div style="text-align:right;">
        <b-button>진행중인 투표</b-button>
      </div>
      <div style="display : flex">
        <div name="category" style="width : 30%">
          카테고리목록들~~~~~~~~

          <b-table hover :items="items"></b-table>
        </div>
        <div
          name="vote-list-card"
          style="width:70%; display:flex;flex-wrap: wrap;"
        >
          <div><VoteCard /></div>
          <div><VoteCard /></div>
          <div><VoteCard /></div>
          <div><VoteCard /></div>
          <div><VoteCard /></div>
          <div><VoteCard /></div>
        </div>
      </div>
    </div>
    <div >
      <FootBar class="footbar" />
    </div>
  </div>
</template>

<script>
import VoteCard from "@/components/vote/VoteCard";
import FootBar from "../components/common/FootBar";
export default {
  components: {
    VoteCard,
    FootBar,
  },
  data() {
    return {
      items: [
        { category: "운동" },
        { category: "음악" },
        { category: "병원" },
        { category: "선거" },
      ],
    };
  },
};
</script>

<style></style>
