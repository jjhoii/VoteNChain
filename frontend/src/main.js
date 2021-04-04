import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/store.js'
import HNav from "./components/common/HNav.vue"
import HBreadcumb from "./components/common/HBreadcrumb.vue"
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import VueFullPage from 'vue-fullpage.js'
import VueGoogleCharts from 'vue-google-charts'
// Import Bootstrap an BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'


Vue.use(VueGoogleCharts)
// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(VueFullPage);
Vue.use(IconsPlugin)
Vue.config.productionTip = false
Vue.filter("truncate", function (text, length, clamp) {
  if (text) {
    clamp = clamp || "...";
    return text.length > length ? text.slice(0, length) + clamp : text;
  }
});

window.Kakao.init('8f8fba39abde0989337e328c19c136b3');

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

Vue.component("HNav", HNav);
Vue.component("HBreadcrumb", HBreadcumb);
