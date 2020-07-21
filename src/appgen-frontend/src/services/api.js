/**
 * Simple API class service for making Axios calls
 * and returning a promise for each call
 */
import axios from 'axios';

axios.defaults.headers.common['Accept'] = 'application/json';
axios.defaults.headers.common['Content-Type'] ='application/json;charset=utf-8;';

class Api {

    constructor() {
        this.base_url = this.getBaseUrl();
    }

    getBaseUrl() {
        const API_VERSION = 'v1';
        const { 
            VUE_APP_API_BASE_URL
        } = process.env;
        
        let baseUrl = `${VUE_APP_API_BASE_URL}/${API_VERSION}`;

        return baseUrl;
    }
    //
    /**
     * Get
     * @param {string} endpoint
     * @param {*} params
     */
    get(endpoint, params = {}) {
        return new Promise((resolve, reject) => {
            axios({url: `${this.base_url}/${endpoint}`, params, method: 'GET' }).then(res => {
                resolve(res.data);
            }).catch(err => {
                reject(err);
            });
        });
    }

    /**
     * Post
     * @param {string} endpoint
     * @param {*} data
     */
    post(endpoint, data = {}) {
        return new Promise((resolve, reject) => {
            axios({url: `${this.base_url}/${endpoint}`, data: data, method: 'POST' }).then(res => {
                resolve(res.data);
            }).catch(err => {
                reject(err);
            });
        });
    }

    /**
     * PUT
     * @param {string} endpoint
     * @param {*} data
     */
    put(endpoint, data = {}) {
        return new Promise((resolve, reject) => {
            axios({url:`${this.base_url}/${endpoint}`, data: data, method: 'PUT'}).then(res => {
                resolve(res.data);
            }).catch(err => {
                reject(err);
            });
        });
    }

    /**
     * Delete
     * @param {string} endpoint
     * @param {*} params
     */
    delete(endpoint, params = {}) {
        return new Promise((resolve, reject) => {
            axios({url: `${this.base_url}/${endpoint}`, params, method: 'DELETE' }).then(res => {
                resolve(res.data);
            }).catch(err => {
                reject(err);
            });
        });
    }
}

export default new Api;
