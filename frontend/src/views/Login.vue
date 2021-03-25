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
                <kakaoLogin/>
              </div>
              <br>
              <div>
                <!-- <GoogleLogin :params="params" :onSuccess="onSuccess" :onFailure="onFailure">Login</GoogleLogin> -->
                 <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccess"></GoogleLogin>
                 <!-- <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccess" :onFailure="onFailure"></GoogleLogin> -->
                 <!--<GoogleLogin :params="params" :logoutButton=true @click="logout">Logout</GoogleLogin> -->
              </div>
            </table>
          </div>
        </div>
      </header>
    </div>
  </div>
</template>

<script>
const cid=process.env.VUE_APP_CLIENT_ID;

import Button from "@/components/home/Button";
import kakaoLogin from "@/components/socialLogin/kakao.vue";
import GoogleLogin from 'vue-google-login';

export default {
  components:{
      Button,
      kakaoLogin,
      GoogleLogin,
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
    };
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
      var profile = googleUser.getBasicProfile();
        console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
        // console.log('access_token' + googleUser.wc.access_token);

      const user={
        userEmail:profile.getEmail(),
        userName:profile.getName(),
        logintype:"google",
        privateKey:"123123"
      }
      console.log("시작");
      console.log(user.userEmail);
      console.log(user.userName);
      console.log(user.logintype);
      this.$store.dispatch("userStore/getSocialUserinfo",user);  
    }
  }
};
</script>

<style>
.content{
  height: 830px;
}
</style>
