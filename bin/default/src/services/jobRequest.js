import api from './api.js';

/**
 * Service Provider service wrapper
 */
class JobRequest {

    /**
     * Constructor
     */
    constructor() {
        // Set URLs
        this.url_job_request = 'jobRequest';
    }

    /**
     * Get ServiceProvider
     * @param {*} params
     * @return {Promise}
     */
    all(params = {}) {
        return api.get(this.url_job_request, params);
    }

    /**
     * Get ServiceProvider by Id
     * @param String id
     * @return {Promise}
     */
    get(id){
        return api.get(`${this.url_job_request}/${id}`);
    }

    /**
     * Update ServiceProvider
     * @param {*} data
     * @return {Promise}
     */
    update(data) {
        return api.put(`${this.url_job_request}/${data.id}`, data);
    }

    /**
     * Create ServiceProvider
     * @param {*} data
     * @return {Promise}
     */
    create(data) {
        return api.post(this.url_job_request, data);
    }


    /**
     * Destroy ServiceProvider
     * @param {*} data
     * @return {Promise}
     */
    destroy(data) {
        return api.delete(`${this.url_job_request}/${data.id}`);
    }

}

export default new JobRequest;
