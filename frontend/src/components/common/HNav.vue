<template>
  <div>
    <div class="navbar">
      <div class="navbar-icon">
        <img
          src="../../../public/images/vnc_logo2.png"
          @click="gohome"
          style="cursor: pointer;"
        />

        <span style="cursor: pointer;">Vote & Chain</span>
      </div>

      <div class="navbar-list">
        <span
          v-if="!login"
          @click="$bvModal.show('bv-modal-example')"
          style="cursor: pointer;"
          >Login</span
        >
        <span v-if="login" @click="clearToken" style="cursor: pointer;"
          >Logout</span
        >

        <b-modal
          id="bv-modal-example"
          hide-header-close
          hide-footer
          @mousedown.stop
          no-close-on-esc
        >
          <template #modal-title>LOGIN</template>
          <div style="text-align:center; font-family:NIXGONM-Vb;">
            Kakao 계정으로 VNC의 서비스를 이용할 수 있습니다.
            <img
              src="../../../public/images/vnc_logo.png"
              style="margin-top:-40px"
            />
          </div>
          <br />
          <div
            class="d-block text-center justify-center"
            style="margin-top:-60px"
          >
            <kakaoLogin />
          </div>
          <br />
          <div style="text-align:center;">
            <b-button
              variant="info"
              class="mt-3"
              style="width: 65%;height:40px;margin-top:30px;background-color:#A68C7C;border-color: white;"
              @click="$bvModal.hide('bv-modal-example')"
              >닫기</b-button
            >
          </div>
        </b-modal>
      </div>
    </div>
  </div>
</template>

<script>
import Spinner from "@/components/common/Spinner.vue";
import kakaoLogin from "@/components/socialLogin/kakao.vue";

const cid = process.env.VUE_APP_CLIENT_ID;

export default {
  components: {
    kakaoLogin,
    Spinner,
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
      login: false,
    };
  },

  methods: {
    gohome() {
      this.$router.push("/");
    },
    clearToken() {
      localStorage.clear();
      window.location.href = "/";
    },
  },
  created() {
    if (localStorage.getItem("access_token") != undefined) {
      this.login = true;
    }
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
  width: 50%;
  height: 100px;
  display: flex;
  align-items: center;
}
.navbar-icon img {
  height: 100px;
}
.navbar-icon span {
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
  color: #fff;
  font-size: 35px;
}
.navbar-list {
  width: 50%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.navbar-list span {
  color: #fff;
  font-size: 25px;
  font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
    "Lucida Sans", Arial, sans-serif;
}
</style>
