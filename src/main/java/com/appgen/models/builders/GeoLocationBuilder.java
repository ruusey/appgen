package com.appgen.models.builders;

import com.appgen.models.Client;
import com.appgen.models.GeoLocation;
import com.appgen.models.JobRequest;

public class GeoLocationBuilder {
	private double lat;
	private double lng;
	private String dateTime;
	private JobRequest geoLocJob;
	private Client geoLocClient;
	public GeoLocationBuilder() {
		
	}
	public GeoLocationBuilder withLat(double lat) {
		this.lat = lat;
		return this;
	}
	public GeoLocationBuilder withLng(double lng) {
		this.lng = lng;
		return this;
	}
	public GeoLocationBuilder withDateTime(String dateTime) {
		this.dateTime = dateTime;
		return this;
	}
	public GeoLocationBuilder withJobGeoLoc(JobRequest geoLocJob) {
		this.geoLocJob = geoLocJob;
		return this;
	}
	public GeoLocationBuilder withClientGeoLoc(Client geoLocClient) {
		this.geoLocClient = geoLocClient;
		return this;
	}
	
	public GeoLocation build() {

		GeoLocation geoLoc = new GeoLocation();
		geoLoc.setLat(this.lat);
		geoLoc.setLng(this.lng);
		geoLoc.setDateTime(this.dateTime);
		geoLoc.setJobGeoLoc(this.geoLocJob);
		geoLoc.setClientGeoLoc(this.geoLocClient);
		geoLoc.setDateTime(this.dateTime);
		return geoLoc;
	}
}
