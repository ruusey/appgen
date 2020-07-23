package com.appgen.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "job_requests")
public class JobRequest extends DatabaseEntity {

	@DatabaseField(columnName = "short_description", canBeNull = false)
	private String shortDescription;
	@DatabaseField(columnName = "long_description", canBeNull = false)
	private String longDescription;
	@JsonManagedReference(value = "geoloc_job")
	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh=true)
	private GeoLocation loc;
	@DatabaseField(columnName = "pay", canBeNull = false)
	private double pay;
	@DatabaseField(columnName = "complete", canBeNull = false)
	private boolean complete;
	@DatabaseField(columnName = "friendly_location", canBeNull = true)
	private String friendlyLocation;
	@JsonIgnore
	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
	private Client client;

	public JobRequest() {

	}

	public JobRequest(String shortDescription, String longDescription, GeoLocation loc, double pay, boolean complete,
			String friendlyLocation, Client client) {


		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.loc = loc;
		this.pay = pay;
		this.complete = complete;
		this.friendlyLocation = friendlyLocation;

		this.client = client;
	}

	@JsonProperty("clientId")
	public int getClientId() {
		return this.client.getId();
	}
	public String getFriendlyLocation() {
		return this.friendlyLocation;
	}

	public void setFriendlyLocation(String friendlyLocation) {
		this.friendlyLocation = friendlyLocation;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescrption(String longDescription) {
		this.longDescription = longDescription;
	}

	public GeoLocation getLoc() {
		return loc;
	}

	public void setLoc(GeoLocation loc) {
		this.loc = loc;
	}

	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

}