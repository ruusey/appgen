package com.appgen.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "service_providers")
public class ServiceProvider extends DatabaseEntity {
	
	@DatabaseField(columnName = "email", canBeNull = false)
	private String email = null;
	@DatabaseField(columnName = "user_name", canBeNull = false)
	private String userName = null;
	@DatabaseField(columnName = "first_name", canBeNull = false)
	private String firstName = null;
	@DatabaseField(columnName = "last_name", canBeNull = false)
	private String lastName = null;
	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh=true)
	private GeoLocation loc = null;
	@DatabaseField(columnName = "name", canBeNull = false, defaultValue = "0.0")
	private float rating = 0.0f;
	@DatabaseField(columnName = "friendly_location", canBeNull = true)
	private String friendlyLocation = null;

	public ServiceProvider() {
		super();
	}

	public ServiceProvider(int id, String email, String userName, String firstName, String lastName, GeoLocation loc, int rating, String friendlyLocation) {
		super();
		this.email = email;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.loc = loc;
		this.rating = rating;
		this.friendlyLocation = friendlyLocation;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public GeoLocation getLoc() {
		return loc;
	}

	public void setLoc(GeoLocation loc) {
		this.loc = loc;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getFriendlyLocation() {
		return this.friendlyLocation;
	}

	public void setFriendlyLocation(String friendlyLocation) {
		this.friendlyLocation = friendlyLocation;
	}
}