<template>
  <b-button
    icon
    elevation="3"
    outlined
    style="background-color: transparent; border: 0; outline: 0"
  >
    <img src="@/assets/kakao_login.png" @click="Kakaologin" alt="kakao" />
  </b-button>
</template>

<script>
export default {
  name: "kakaoLogin",
  methods: {
    Kakaologin() {
      this.$store.state.loading.text = "로그인 중입니다...";
      this.$store.state.loading.enabled = true;
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
          await this.$store.dispatch("userStore/getSocialUserinfo", user);

          this.$bvModal.hide("bv-modal-example");
          this.$bvModal.hide("bv-modal-example1");
          this.$store.state.loading.enabled = false;
          window.location.reload();
        },
        fail: function (error) {
          console.log(error.message);
        },
      });
    },
  },
};
</script>
<style scoped></style>
