<template>
  <div class="container">
   <div class="body1">
      <header class="vid-header container">
        <div class="fullscreen-vid-wrap">
          <video
            src="video/VoteVideo.mp4"
            muted="muted"
            autoplay="true"
            loop="true"
          ></video>
        </div>
        <div class="header-overlay"></div>
        <div class="header-content">
          
          <h1>hello ! Vote And Chain</h1>
          <p>asdasdasdasdasdadasdasdadasda</p>
          <div style=" display:flex; justify-content: center;">
            <table>
              <div>
                <Button :word="word1"/>
              </div>
              <div>
                <!-- <GoogleLogin :params="params" :onSuccess="onSuccess" :onFailure="onFailure">Login</GoogleLogin> -->
                 <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccess"></GoogleLogin>
                 <!-- <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccess" :onFailure="onFailure"></GoogleLogin> -->
                 <GoogleLogin :params="params" :logoutButton=true @click="logout">Logout</GoogleLogin>
              </div>
              <button @click="test()">테스트</button>
            </table>
          </div>
        </div>
      </header>
    </div>
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const cid=process.env.VUE_APP_CLIENT_ID;

import Button from "@/components/home/Button";
import GoogleLogin from 'vue-google-login';
import axios from 'axios';
export default {
  components:{
      Button,
      GoogleLogin,
      axios,
  },
  
  data() {
    return {
      user: {
        email: "",
        password: ""
      },
      params: {
        client_id:cid,
      },
      renderParams: {
        width: 250,
        height: 50,
        longtitle: true
      },
      word1: '카카오로그인',
      // word2: '구글 로그인'
    };
  },
  mounted(){
    this.test()
    
  },
  methods: {
    kakaoLogin() {
      const scope = this;

      login(
        this.user.email,
        this.user.password,
        function(response) {
          scope.$store.commit("setIsSigned", true);
          scope.$store.commit("setUserId", response.data.id);
          scope.$router.push("/");
        },

      );
    },
    onSuccess(googleUser) {
      // console.log(googleUser);      
      // console.log(googleUser.getBasicProfile());
        var profile = googleUser.getBasicProfile();
        console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
        // console.log('access_token' + googleUser.wc.access_token);

        let params={
          userEmail: profile.getEmail(),
          userName: profile.getName(),
          loginType: "google",
          privateKey:"123",
        }
        // console.log(params);
        // console.log("무야호")
        const headers = {
          'Content-type': 'application/json; charset=UTF-8',
          'Accept': '*/*',
          'Access-Control-Allow-Origin': '*',
          "Access-Control-Allow-Headers": "*",
        }
        // console.log("무야호");
        axios.post(`${SERVER_URL}/user/login`,
        JSON.stringify(params),
        { headers, }
        
      )
      .then((result)=>{
          
        console.log(result)
        //
        localStorage.setItem('access_token', googleUser.getAuthResponse().id_token);
        this.$router.push({
        path:'/'
        });
      })
      .catch(e=>{
          console.log('error:',e)
      })
      
    },
    // onFailure(){
    //     console.log("실패");
    // },
    logout(){
      localStorage.removeItem('access_token');
    }
    ,
    test(){

      axios.get("http://localhost:8090/user/getMultiParameters")
      .then((result)=>{
        console.log("hi");
        console.log(result)
      })
      .catch(e=>{
        console.log(e);
      })
    }
  },
  
};
</script>

<style>
.content{
  height: 830px;
}
</style>
