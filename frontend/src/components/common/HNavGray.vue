<template>
  <div>
    <div class="navbargray">
      <div class="navbargray-icon">
        <img src="../../../public/images/vnc_logo.png" @click="gohome"  style="cursor: pointer;"/>
        <span @click="gohome" style="cursor: pointer;">Vote & Chain</span>
      </div>

      <div class="navbargray-list">
        <span v-if="!login" @click="$bvModal.show('bv-modal-example')" style="cursor: pointer;"
          >Login</span
        >
        <span v-if="login" @click="clearToken" style="cursor: pointer;">Logout</span>
        <b-modal
          id="bv-modal-example"
          hide-header-close
          hide-footer
          no-close-on-backdrop
          @mousedown.stop
          no-close-on-esc
        >
          <template #modal-title> 로그인 </template>
          <div class="d-block text-center justify-center">
            <kakaoLogin />
          </div>
          <b-button
            class="mt-3"
            block
            @click="
              $bvModal.hide('bv-modal-example'),
                ($store.state.loading.enabled = false)
            "
            >Close Me</b-button
          >
        </b-modal>
      </div>
    </div>
  </div>
</template>

<script>
import kakaoLogin from '@/components/socialLogin/kakao.vue';
import Spinner from '@/components/common/Spinner.vue';

const cid = process.env.VUE_APP_CLIENT_ID;
export default {
  components: {
    kakaoLogin,
    Spinner,
  },
  data() {
    return {
      user: {
        email: '',
        password: '',
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
      this.$router.push('/');
    },
    kakaoLogin() {
      const scope = this;

      login(this.user.email, this.user.password, function(response) {
        scope.$store.commit('setIsSigned', true);
        scope.$store.commit('setUserId', response.data.id);
        scope.$router.push('/');
      });
    },
    clearToken() {
      localStorage.clear();
      window.location.href = '/';
    },
  },
  created() {
    if (localStorage.getItem('access_token') != undefined) {
      this.login = true;
    }
  },
};
</script>

<style>
.navbargray {
  background: #f9f9f9;
  z-index: 99;
  display: flex;
  height: 100px;
  left: 0;
  right: 0;
  top: 0;
  position: fixed !important;
 
}
.navbargray-icon {
  width: 50%;
  height: 100px;
  display: flex;
  align-items: center;
}
.navbargray-icon img {
  height: 100px;
}
.navbargray-icon span {
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
  color: #000;
  font-size: 35px;
}
.navbargray-list {
  width: 50%;
  height: 100%;

  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.navbargray-list span {
  color: #000;
  font-size: 25px;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande',
    'Lucida Sans', Arial, sans-serif;
}
</style>
