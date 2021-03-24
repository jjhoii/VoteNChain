import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import HNav from "./components/common/HNav.vue"
import HBreadcumb from "./components/common/HBreadcrumb.vue"
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Import Bootstrap an BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)
Vue.config.productionTip = false
Vue.filter("truncate", function(text, length, clamp) {
  if (text) {
    clamp = clamp || "...";
    return text.length > length ? text.slice(0, length) + clamp : text;
  }
});
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

Vue.component("HNav", HNav);
Vue.component("HBreadcrumb", HBreadcumb);
