<template>
  <div>
    <div class="navbar">
      <div class="navbar-icon">
        <img  src="../../../public/images/votelogo2.png" @click="gohome"/>
      </div>

      <div class="navbar-title">
        
        <span>Vote & Chain</span>
      </div>

      <div class="navbar-list">
        <span @ ="$bvModal.show('bv-modal-example')">Login</span>

        <b-modal id="bv-modal-example" hide-footer>
          <template #modal-title>
            로그인
          </template>
          <div class="d-block text-center justify-center">
            <kakaoLogin />
            <!-- <GoogleLogin  :params="params" :renderParams="renderParams" :onSuccess="onSuccess"></GoogleLogin> -->
          </div>
          <b-button
            class="mt-3"
            block
            @click="$bvModal.hide('bv-modal-example')"
            >Close Me</b-button
          >
        </b-modal>
      </div>
    </div>
  </div>
</template>

<script>
import kakaoLogin from "@/components/socialLogin/kakao.vue";
import GoogleLogin from "vue-google-login";
const cid = process.env.VUE_APP_CLIENT_ID;

export default {
  components: {
    kakaoLogin,
    GoogleLogin,
  },
  data() {
    return {
      user: {
        email: "",
        password: "",
      },
      params: {
        client_id: cid,
      },
      renderParams: {
        width: 250,
        height: 50,
        longtitle: true,
      },
    };
  },
  methods: {
    gohome() {
      this.$router.push("/");
    },
    kakaoLogin() {
      const scope = this;

      login(this.user.email, this.user.password, function(response) {
        scope.$store.commit("setIsSigned", true);
        scope.$store.commit("setUserId", response.data.id);
        scope.$router.push("/");
      });
    },
    onSuccess(googleUser) {
      var profile = googleUser.getBasicProfile();
      console.log("ID: " + profile.getId()); // Do not send to your backend! Use an ID token instead.
      console.log("Name: " + profile.getName());
      console.log("Image URL: " + profile.getImageUrl());
      console.log("Email: " + profile.getEmail()); // This is null if the 'email' scope is not present.
      // console.log('access_token' + googleUser.wc.access_token);

      const user = {
        userEmail: profile.getEmail(),
        userName: profile.getName(),
        logintype: "google",
        privateKey: "123123",
      };
      console.log("시작");
      console.log(user.userEmail);
      console.log(user.userName);
      console.log(user.logintype);
      this.$store.dispatch("userStore/getSocialUserinfo", user);
    },
  },
};
</script>

<style>
.navbar {
 
  z-index: 99;
  display: flex;
  height: 100px;
  left: 0;
  right: 0;
  top: 0;
  position: fixed !important;
}
.navbar-icon {
  width: 8%;
  height: 100px;
}
.navbar-icon img {
  height: 100px;
}
.navbar-title{
  width: 40%;
  height: 100px;
  display: flex;
  align-items: center;
  
}
.navbar-title span{
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  color: #fff;
  font-size : 35px;
}
.navbar-list{
  width: 50%;
  height: 100%;
  
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.navbar-list span{
  color:#fff;
  font-size : 25px;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
}
</style>
