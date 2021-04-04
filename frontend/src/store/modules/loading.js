import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

var store = new Vuex.Store({

    state: {
        enabled: false,

    },


    mutations: {
        Loading(state) {
            state.enabled = !state.enabled
        }
    },
});

export default store;