package com.appgen.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "geolocations")
public class GeoLocation extends DatabaseEntity {

	@DatabaseField(columnName = "lat", canBeNull = false, defaultValue = "0.0")
	private double lat;
	@DatabaseField(columnName = "lng", canBeNull = false, defaultValue = "0.0")
	private double lng;
	@DatabaseField(columnName = "date_time", canBeNull = true)
	private String dateTime;
	@JsonBackReference(value = "geoloc_job")
	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh=true)
	private JobRequest jobGeoLoc;
	@JsonBackReference(value = "geoloc_client")
	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh=true)
	private Client clientGeoLoc;
	@JsonBackReference(value = "geoloc_sp")
	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh=true)
	private ServiceProvider serviceProviderGeoLoc;

	public GeoLocation() {
		super();
	}

	public GeoLocation(double lat, double lng, String dateTime) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.dateTime = dateTime;
	}
	
	public JobRequest getJobGeoLoc() {
		return jobGeoLoc;
	}

	public void setJobGeoLoc(JobRequest jobGeoLoc) {
		this.jobGeoLoc = jobGeoLoc;
	}

	public Client getClientGeoLoc() {
		return clientGeoLoc;
	}

	public void setClientGeoLoc(Client clientGeoLoc) {
		this.clientGeoLoc = clientGeoLoc;
	}
	
	public ServiceProvider getServiceProviderGeoLoc() {
		return serviceProviderGeoLoc;
	}

	public void setServiceProviderGeoLoc(ServiceProvider serviceProviderGeoLoc) {
		this.serviceProviderGeoLoc = serviceProviderGeoLoc;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}