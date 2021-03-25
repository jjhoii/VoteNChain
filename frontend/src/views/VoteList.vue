<template>
  <div class="container"  style="margin: 10%">


      <div style="text-align:right;">
          <b-button>진행중인 투표</b-button>
      </div>
      <div style="display : flex">
          <div name="category" style="width : 30%">
              카테고리목록들~~~~~~~~
              
          <b-table  hover :items="items"></b-table>
          </div>
          <div name="vote-list-card" style="width:70%; display:flex;flex-wrap: wrap;"> 
            <div><VoteCard/></div>
            <div><VoteCard/></div>
            <div><VoteCard/></div>
            <div><VoteCard/></div>
            <div><VoteCard/></div>
            <div><VoteCard/></div>
          </div>
      </div>
   
      
      
  </div>
</template>

<script>
import VoteCard from '@/components/votelist/VoteCard';

export default {
components :{
    VoteCard,
},
data() {
      return {
        items: [
          { category: '운동' },
          { category: '음악'  },
          { category: '병원'  },
          { category: '선거'  }
        ]
      }
    }
}
</script>

<style>

</style>
