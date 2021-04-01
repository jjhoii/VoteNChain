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
                   <p>투표 내용   </p>   
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
                <button>투표 확인</button> 
                <button>투표 종료</button>
            </div>
        </div>

    </div>
</template>

<script>
import VoteCard from '@/components/votepage/VoteCard';
import VoteWritten from '@/components/votepage/VoteWritten'

export default {
    components :{
    VoteCard,
    VoteWritten,
    
},
}
</script>

<style >
    
</style>