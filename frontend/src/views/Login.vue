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
              <th>
                <Button :word="word1"/>
              </th>
              <th>
                <Button :word="word2"/>
              </th>
            </table>
          </div>
        </div>
      </header>
    </div>
  </div>
</template>

<script>
import Button from "@/components/home/Button";
export default {
  components:{
      Button,
  },
  data() {
    return {
      user: {
        email: "",
        password: ""
      },
      word1: '카카오로그인',
      word2: '구글 로그인'
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
    googleLogin(){
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
    }
  }
};
</script>

<style>
.content{
  height: 830px;
}
</style>
