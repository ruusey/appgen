import api from './api.js';

/**
 * Lessees service wrapper
 */
class GeoLocation {

    /**
     * Constructor
     */
    constructor() {

        // Set URLs
        this.url_geoloc = 'geoLocation';
    }

    /**
     * Get Lessees, params can include pagination and offset
     * @param {*} params
     * @return {Promise}
     */
    all(params = {}) {
        return api.get(this.url_geoloc, params);
    }

    /**
     * Get LesseeEdit by id
     * @param id
     * @return {Promise}
     */
    get(id){
        return api.get(`${this.url_geoloc}/${id}`);
    }

    /**
     * Update Lessees
     * @param {*} data
     * @return {Promise}
     */
    update(data) {
        return api.put(`${this.url_geoloc}/${data.id}`, data);
    }

    /**
     * Create LesseeEdit
     * @param {*} data
     * @return {Promise}
     */
    create(data){
        return api.post(this.url_geoloc, data);
    }

    /**
     * Destroy LesseeEdit
     * @param String id
     * @return {Promise}
     */
    destroy(data){
        return api.delete(`${this.url_geoloc}/${data.id}`);
    }
}
export default new GeoLocation;
