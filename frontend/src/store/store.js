import Vue from 'vue'
import Vuex from 'vuex'
import userStore from '@/store/modules/userStore.js'
import loading from '@/store/modules/loading.js'


Vue.use(Vuex)

const store = new Vuex.Store({

  modules: {
    userStore: userStore,
    loading: loading,
  }
})

export default store

// 컴포넌트에서 스토어에 접근할 경우
/*  원래 컴포넌트에서는 스토어의 state와 mutation에 직접적으로 접근하지 않는것이 원칙 (getters와 actions로직 내부에서 해당 동작 명령)
1. state : this.$store.state.모듈명.state명    ex) this.$store.state.userStore.targetName
2. getters : this.$store.getters["모듈명/함수명"]    ex) this.$store.getters["userStore/getUserId"]
3. actions : this.$store.dispatch('모듈명/함수명')   ex) this.$store.dispatch('userStore/login')
4. mutations : this.$store.commit('모듈명/함수명')
*/