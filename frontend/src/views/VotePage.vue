<template>
    <div >
        <div class="container">
            <div name='title'>
                <h1> 투표 제목</h1>
            </div>
            <div name='main-image' style="width:100% ; height: 100%;">
                <img  src="https://source.unsplash.com/random" style="width:600px; height:400px" />
            </div>
            <div name='content'>
                   <p>asdasdadkfalsdfjsdjklf   </p>   
            </div>
                              
            <div name='vote-list'  style="width:100%; display:flex;flex-wrap: wrap; justify-content:center">
                <div><VoteCard/></div>
                <div><VoteCard/></div>
                <div><VoteCard/></div>
                <div><VoteCard/></div>
                <div><VoteCard/></div>
                <div><VoteCard/></div>
            </div>

            <div name='vote-list'  style="width:100%;">
                <div><VoteWritten/></div>
                <div><VoteWritten/></div>
                <div><VoteWritten/></div>
                <div><VoteWritten/></div>
                <div><VoteWritten/></div>
                <div><VoteWritten/></div>
            </div>
            <div name='vote-end-button'>
                <b-button>투표 확인</b-button> 
                <b-button>투표 종료</b-button>
            </div>
        </div>

         <div>
          <FootBar class="footbar" />
        </div>
    </div>
</template>

<script>
import VoteCard from '@/components/vote/VoteCard';
import VoteWritten from '@/components/vote/VoteWritten'
import FootBar from "../components/common/FootBar";
export default {
    components :{
    VoteCard,
    VoteWritten,
    FootBar
},
}
</script>

<style >
    
</style>