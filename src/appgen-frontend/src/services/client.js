import api from './api.js';

/**
 * Company service wrapper (both Lessees and ServiceProviders are companies)
 */
class Client {
    /**
     * Constructor
     */
    constructor() {

        // Set URLs
        this.url_client = 'client';
    }

    /**
     * Get Lessees, params can include pagination and offset
     * @param {*} params
     * @return {Promise}
     */
    all(params = {}) {
        return api.get(this.url_client, params);
    }

    /**
     * Get LesseeEdit by id
     * @param id
     * @return {Promise}
     */
    get(id){
        return api.get(`${this.url_client}/${id}`);
    }

    /**
     * Update Lessees
     * @param {*} data
     * @return {Promise}
     */
    update(data) {
        return api.put(`${this.url_client}/${data.id}`, data);
    }

    /**
     * Create LesseeEdit
     * @param {*} data
     * @return {Promise}
     */
    create(data){
        return api.post(this.url_client, data);
    }

    /**
     * Destroy LesseeEdit
     * @param String id
     * @return {Promise}
     */
    destroy(data){
        return api.delete(`${this.url_client}/${data.id}`);
    } 
}
export default new Client;