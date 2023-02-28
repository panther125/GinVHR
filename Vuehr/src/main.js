import Vue from 'vue'
import App from './App.vue'
import router from './router';
import store from './utils/store'
Vue.config.productionTip = false


import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';

Vue.use(ElementUI);
// 防止dialog导致页面滚动条消失抖动
// ElementUI.Dialog.props.lockScroll.default = false;

import { postRequest } from "./utils/api";
import { postKeyValueRequest } from "./utils/api";
import { putRequest } from "./utils/api";
import { deleteRequest } from "./utils/api";
import { getRequest } from "./utils/api";

Vue.prototype.postRequest = postRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.getRequest = getRequest;

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
