<template>
    <div class="container"  style="margin: 10%">
        <div>
            <div name='title'>
                <h1> 투표 제목</h1>
            </div>
            <div name='main-image' style="width:100% ; height: 100%;">
                <img  src="https://source.unsplash.com/random" style="width:600px; height:400px" />
            </div>
            <div name='content'>
                   <p>asdasdadkfalsdfjsdjklf   </p>   
            </div>
                              
            <div name='vote-list'  style="width:100%; display:flex;flex-wrap: wrap;">
                <div><VoteCard/></div>
                <div><VoteCard/></div>
                <div><VoteCard/></div>
                <div><VoteCard/></div>
                <div><VoteCard/></div>
                <div><VoteCard/></div>

            </div>
            <div name='vote-end-button'>
                <b-button>투표 확인</b-button> 
                <b-button>투표 종료</b-button>
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
}
</script>

<style >
    
</style>