<template>
  <button icon elevation="3" outlined>
    <img src="@/assets/kakao_login.png" @click="Kakaologin" alt="kakao" />
  </button>
</template>

<script>
export default {
  name: "kakaoLogin",
  methods: {
    Kakaologin() {
      window.Kakao.Auth.login({
        scope: "profile, account_email",
        success: this.kakaogetUserinfo,
      });
    },

    kakaogetUserinfo() {
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: async (response) => {
          const user = {
            userEmail: response.kakao_account.email,
            userName: response.kakao_account.profile.nickname,
            account: "",
          };
          console.log("시작");
          console.log(user.userEmail);
          console.log(user.userName);
          await this.$store.dispatch("userStore/getSocialUserinfo", user);

          this.$bvModal.hide("bv-modal-example");
          this.$bvModal.hide("bv-modal-example1");
          window.location.reload();
          //새로고침?
        },
        fail: function(error) {
          console.log(error.message);
        },
      });
    },
  },
};
</script>
<style scoped></style>
