<template>
  <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-light">
    <div class="container">
      <img id="nav-icon" src="../../../public/images/horizontal_on_white_by_logaster__3_-removebg-preview.png">
      <router-link  style="color : black" class="navbar-brand" to="/">Vote & Chain</router-link>
      <div
        class="navbar-collapse offcanvas-collapse"
        id="navbarsExampleDefault"
      >
        <ul class="navbar-nav ml-auto">
         
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link
              class="nav-link"
              to="/mypage/wallet_create"
              v-if="!$store.state.user.walletAddress"
              >MyPage</router-link
            >
            <router-link
              class="nav-link"
              to="/mypage/wallet_info"
              v-if="$store.state.user.walletAddress"
              >MyPage</router-link
            >
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link style="color : black" class="nav-link" to="/login">Sign In</router-link>
          </li>
          <!-- <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/register">Sign Up</router-link>
          </li> -->
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link style="color : black" class="nav-link" to="/logout">Sign out</router-link>
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
#nav-icon {
  height: 60px;
  padding-right: 0.5rem;
}

</style>
