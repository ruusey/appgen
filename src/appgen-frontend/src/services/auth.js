import api from './api.js';

/**
 * Auth service for registering, logging in and out, and getting
 * new access token with refresh request
 */
class Auth {

    /**
     * Constructor
     */
    constructor() {
        // Set up URLs
        // this.url_register = '/api/auth/register';
        this.url_login = 'users/login';
        this.url_logout = 'users/logout';
        this.url_user_service = 'users/current';
    }

    /**
     * Register a new user
     * @param {} data
     * @return {Promise}
     */
    // register(data) {
    //     return api.post(this.url_register, data);
    // }

    /**
     * Login
     * @param {*} creds
     * @return {Promise}
     */
    login(creds) {
        return api.post(this.url_login, creds);
    }

    /**
     * Log out
     * @return {Promise}
     */
    logout() {
        return api.delete(this.url_logout);
    }

    /**
     * Sends an empty POST request to refresh access token.  API uses
     * an HTTP-only cookie that is automatically sent with request
     * to check auth state
     * @return {Promise}
     */
    // refresh() {
    //     return api.post(this.url_refresh);
    // }

    /**
     * Sends a get request to return current user data via 'user-token' in header
     * @return { Promise}
     */
     getCurrentUser(){
        return api.get(this.url_user_service,{});
     }

}

export default new Auth;
