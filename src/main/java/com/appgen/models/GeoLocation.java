package com.appgen.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "geolocations")
public class GeoLocation extends DatabaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(columnName = "lat", canBeNull = false, defaultValue = "0.0")
	private double lat;
	@DatabaseField(columnName = "lng", canBeNull = false, defaultValue = "0.0")
	private double lng;
	@DatabaseField(columnName = "date_time", canBeNull = true)
	private String dateTime;
	@JsonIgnore
	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh=true)
	private JobRequest jobGeoLoc;
	@JsonIgnore
	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh=true)
	private Client clientGeoLoc;
	@JsonIgnore
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