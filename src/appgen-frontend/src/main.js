import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import './registerServiceWorker'
import vuetify from './plugins/vuetify';
import { abilitiesPlugin } from '@casl/vue';
import '@babel/polyfill';
import moment from 'moment';
import x5GMaps from 'x5-gmaps';

Vue.config.productionTip = false;

Vue.filter('datetimeFormat', function (value) {
  if (!value) return '';
  return moment(value).format('MM/DD/YYYY, h:mm:ss A');
});

const UNAUTHENTICATED = 401;
const UNAUTHORIZED = 403;


Vue.use(x5GMaps, { key: 'AIzaSyAPPbs2Yz_oie_ldvKjEyG86ZUmh_PAdiY', libraries: ['places'] })

new Vue({
  el: '#app',
  router,
  store,
  vuetify,
  x5GMaps,
  render: h => h(App),
  created: () => {
    console.log('vue instance created');
  }
})
Vue.use(x5GMaps, { key: 'AIzaSyAPPbs2Yz_oie_ldvKjEyG86ZUmh_PAdiY', libraries: ['places'] })
Vue.use(abilitiesPlugin);
