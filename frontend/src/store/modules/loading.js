import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

var store = new Vuex.Store({

    state: {
        enabled: false,
        text: "로딩중입니다..."
    },


    mutations: {
        Loading(state) {
            state.enabled = !state.enabled
        }
    },
});

export default store;