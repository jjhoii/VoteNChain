<template>
  <div>
    <div class="navbar">
      <div class="navbar-icon">
        <img  src="../../../public/images/horizontal_on_white_by_logaster__3_-removebg-preview.png"/>
      </div>
        
      <div class="navbar-title">
        <router-link   to="/">Vote & Chain</router-link>
      </div>

      <div class="navbar-list">
         <span @click="$bvModal.show('bv-modal-example')">Login</span>

        <b-modal id="bv-modal-example" hide-footer>
          <template #modal-title>
            Using <code>$bvModal</code> Methods
          </template>
          <div class="d-block text-center">
            <h3>Hello From This Modal!</h3>
              <kakaoLogin />
              <GoogleLogin  :params="params" :renderParams="renderParams" :onSuccess="onSuccess"></GoogleLogin>
          </div>
          <b-button class="mt-3" block @click="$bvModal.hide('bv-modal-example')">Close Me</b-button>
        </b-modal>
      </div>

       
    </div>
  </div>
</template>

<script>
import kakaoLogin from "@/components/socialLogin/kakao.vue";
import GoogleLogin from 'vue-google-login';
const cid=process.env.VUE_APP_CLIENT_ID;
export default {
components:{
     
      kakaoLogin,
      GoogleLogin
 
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
    }
  },
methods:{
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



</style>
