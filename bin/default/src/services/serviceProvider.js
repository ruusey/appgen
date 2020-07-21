import api from './api.js';

/**
 * Lessees service wrapper
 */
class ServiceProvider {

    /**
     * Constructor
     */
    constructor() {

        // Set URLs
        this.url_service_provider = 'serviceProvider';
    }

    /**
     * Get Lessees, params can include pagination and offset
     * @param {*} params
     * @return {Promise}
     */
    all(params = {}) {
        return api.get(this.url_service_provider, params);
    }

    /**
     * Get LesseeEdit by id
     * @param id
     * @return {Promise}
     */
    get(id){
        return api.get(`${this.url_service_provider}/${id}`);
    }

    /**
     * Update Lessees
     * @param {*} data
     * @return {Promise}
     */
    update(data) {
        return api.put(`${this.url_service_provider}/${data.id}`, data);
    }

    /**
     * Create LesseeEdit
     * @param {*} data
     * @return {Promise}
     */
    create(data){
        return api.post(this.url_service_provider, data);
    }

    /**
     * Destroy LesseeEdit
     * @param String id
     * @return {Promise}
     */
    destroy(data){
        return api.delete(`${this.url_service_provider}/${data.id}`);
    }

}

export default new ServiceProvider;
