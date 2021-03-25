import { createStore } from 'vuex';
import createPersistedState from "vuex-persistedstate";

import googleModuls from './modules/google.js'


const store = createStore({
    modules: {
        google: googleModuls,
    },
    plugins:[createPersistedState()],


});

export default store;