import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import AuthService from './services/auth.js';
import router from './router.js';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: defaultUser(),
    token: null,
    notificationItems: [],
  },
  getters: {
    isAuthenticated: state => !!state.token,
    user: state => state.user,
  },
  mutations: {
    AUTH_SUCCESS(state, data) {
      state.token = data['user-token'];
    },
    AUTH_LOGOUT(state) {
      state.token = null;
      state.user = defaultUser();
    },
    AUTH_ERROR(state, error) {
      state.token = null;
      state.user = defaultUser();
    },
    AUTH_SETUSER(state, user) {
        state.user = user;
    },
    LOAD_TOKEN(state, token){
        state.token = token;
    },
    SET_NOTIFICATION_ITEM(state, message){
        state.notificationItems.push(message);
    },
    DELETE_NOTIFICATION_ITEM(state, notificationId){
        state.notificationItems.splice(
            state.notificationItems.findIndex(item => item.id === notificationId)
            , 1);
    }
  },
  actions: {
    AUTH_LOGIN({ commit, dispatch }, creds) {
      return new Promise((resolve, reject) => {
        AuthService.login(creds).then(res => {
          let token = res.userToken;
          axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          localStorage.setItem('user-token', token);
          commit('AUTH_SUCCESS', token);
          dispatch('REFRESH_USER');
          dispatch('ADD_NOTIFICATION', {
            text: 'Successfully Logged In' ,
            color: 'success'
          });
          resolve();
        }).catch(error => {
          delete axios.defaults.headers.common['Authorization'];
          localStorage.removeItem('user-token');
          commit('AUTH_ERROR', error);
          reject(error);
        });
      });
    },
    AUTH_LOGOUT({ commit }) {
      return new Promise((resolve, reject) => {
        AuthService.logout().then(() => {
          commit('AUTH_LOGOUT');
          router.push('/login');
          delete axios.defaults.headers.common['Authorization'];
          localStorage.removeItem('user-token');
          resolve();
        }).catch(() => {
          delete axios.defaults.headers.common['Authorization'];
          localStorage.removeItem('user-token');
          router.push('/login');
          commit('AUTH_ERROR');
          reject();
        });
      });
    },
    REFRESH_USER({ commit }){
      console.log('refreshing user...');
      return new Promise((resolve, reject) => {
        AuthService.getCurrentUser().then(res => {
          console.log(res);
          commit('AUTH_SUCCESS', res);
          commit('AUTH_SETUSER', res);
          resolve();
        }).catch((err) => {
          console.log(err);
          commit('AUTH_ERROR');
          router.push('/login');
          reject();
        });
      });
    },
    GET_USER_TOKEN({ commit }){
      return new Promise((resolve,reject) => {
        let token = localStorage.getItem('user-token');
        console.log('trying to get token', token);
         if (token){
           commit('LOAD_TOKEN',token);
           axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
           resolve();
         } else {
           reject();
         }
      })
    },
    ADD_NOTIFICATION({ commit }, messageInfo){
      let timeout = messageInfo.timeout || 6000;
      let id = Math.random().toString(36).substring(2) + Date.now().toString(36);
      let message = { ...messageInfo, timeout, id };
      commit('SET_NOTIFICATION_ITEM', message);
      setTimeout(() => {
        commit('DELETE_NOTIFICATION_ITEM', message.id)
      }, message.timeout);
    },
    REMOVE_NOTIFICATION({ commit}, notificationId){
      commit('DELETE_NOTIFICATION_ITEM', notificationId);
    }
  }
})

function defaultUser() {
  return {
    ownerId: null,
    firstName: null,
    lastName: null,
    email: null,
    notifySms: null,
    notifyEmail: null,
  }
};
