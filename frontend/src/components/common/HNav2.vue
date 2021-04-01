<template>
  <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
    <div class="container">
      <img
        id="nav-icon"
        src="../../../public/images/horizontal_on_white_by_logaster__3_-removebg-preview.png"
      />
      <router-link style="color: light" class="navbar-brand" to="/"
        >Vote & Chain</router-link
      >
      <div
        class="navbar-collapse offcanvas-collapse"
        id="navbarsExampleDefault"
      >
        <ul class="navbar-nav ml-auto">
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link style="color-font : light;" class="nav-link" to="/votemake"
              >투표만들기</router-link
            >
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link style="color: light" class="nav-link" to="/votelist"
              >투표리스트</router-link
            >
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link style="color: light" class="nav-link" to="/votepage"
              >투표페이지</router-link
            >
          </li>

          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link style="color: light" class="nav-link" to="/mypage"
              >마이페이지</router-link
            >
          </li>

          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link style="color: light" class="nav-link" to="/votegraph"
              >통계페이지</router-link
            >
          </li>

          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link style="color: light" class="nav-link" to="/login"
              >Sign In</router-link
            >
          </li>
          <!-- <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/register">Sign Up</router-link>
          </li> -->
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link style="color: light" class="nav-link" to="/logout"
              >Sign out</router-link
            >
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {};
</script>

<style>
.navbar {
  height: 10vh;
}

#nav-icon {
  height: 60px;
  padding-right: 0.5rem;
}
</style>
