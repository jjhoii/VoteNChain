<template>
  <div>
    <h-nav></h-nav>
    <div id="main-overview" class="container">
      <div class="row">
        <div class="col-md-12">
          <h1>VOTE PLATFORM</h1>
          <h4 class="mt-5">
            블록체인 기반의 투표 플랫폼을 이용해보세요!
          </h4>
          <router-link
            :to="{ name: 'login' }"
            class="btn btn-lg btn-primary mt-5"
            v-if="!$store.state.isSigned"
          >
            로그인
          </router-link>
          <router-link
            :to="{ name: 'shop' }"
            class="btn btn-lg btn-primary mt-5"
            v-if="$store.state.isSigned"
          >
            Shop
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";

export default {
  components: {
    HNav,
  }
};
</script>

<style>
</style>
