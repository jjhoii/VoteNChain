<template>
  <div class="container">
<<<<<<< Updated upstream
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
              <th>
                <Button :word="word1"/>
              </th>
              <th>
                <Button :word="word2"/>
              </th>
            </table>
          </div>
=======
    <div class="row">
      <div id="login-form" class="col-md-6 mx-auto bg-white">
        <div class="mt-4">
          <div class="form-group">
            <!-- <button type="submit" class="btn btn-primary form-control" v-on:click="kakaoLogin">
              카카오로 로그인
            </button> -->
          </div>
          <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccess" :onFailure="onFailure"></GoogleLogin>
           <GoogleLogin :params="params" :logoutButton=true>Logout</GoogleLogin>
          <!-- <input class="big-button" type="button" value="구글 로그아웃" @click="GoogleSignout()" /> -->

          <!-- <div class="g-signin2 " data-width="auto" data-onsuccess="onSignIn" ></div> -->
           <!-- <a href="#" v-on:click="signOut">로그아웃</a> -->
>>>>>>> Stashed changes
        </div>
      </header>
    </div>
  </div>
</template>

<script>
<<<<<<< Updated upstream
import Button from "@/components/home/Button";
export default {
  components:{
      Button,
=======
import GoogleLogin from "vue-google-login";

export default {
  components: {
    GoogleLogin,
>>>>>>> Stashed changes
  },
  data() {
    
    return {
      params:{
        client_id:"728976855685-5opfb7817n7lhqblm7cnadbtdaokt1qq.apps.googleusercontent.com",
      },
      renderParams:{},


      user: {
        email: "",
        password: ""
      },
      word1: '카카오로그인',
      word2: '구글 로그인'
    };
  },
  methods: {
    onSuccess(googleUser) {
     var profile = googleUser.getBasicProfile();
      console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
      console.log('Name: ' + profile.getName());
      console.log('Image URL: ' + profile.getImageUrl());
      console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
      

    },
    onFailure(){
      console.log("실패");
    },
    signOut(){
    var auth2 = gapi.auth2.getAuthInstance();
      // auth2.signOut().then(function () {
      //   console.log('User signed out.');
      // });
      auth2.signOut();
    },


  }
};
</script>

<style>
.content{
  height: 830px;
}
</style>
