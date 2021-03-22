<template>
  <div class="container">
    <div class="row">
      <div id="login-form" class="col-md-6 mx-auto bg-white">
        <div class="mt-4">
          <div class="form-group">
            <!-- <label for="email">email</label>
            <input
              type="text"
              class="form-control"
              id="email"
              v-model="user.email"
              placeholder="이메일"
            /> -->

            <button type="submit" class="btn btn-primary form-control" v-on:click="kakaoLogin">
              카카오로 로그인
            </button>
          </div>
          <div class="form-group">
            <!-- <label for="password">비밀번호</label>
            <input
              type="password"
              class="form-control"
              id="password"
              v-model="user.password"
              placeholder="비밀번호"
            /> -->
            <button type="submit" class="btn btn-primary form-control" v-on:click="googleLogin">
              구글로 로그인
            </button>
          </div>
          <!-- <button type="submit" class="btn btn-primary" v-on:click="login">
            로그인
          </button> -->

        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      user: {
        email: "",
        password: ""
      }
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

<style></style>
