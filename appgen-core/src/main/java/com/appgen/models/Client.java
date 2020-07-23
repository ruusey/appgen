package com.appgen.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "clients")
public class Client extends DatabaseEntity {

	@DatabaseField(columnName = "email", canBeNull = false)
	private String email;
	@DatabaseField(columnName = "user_name", canBeNull = false)
	private String userName;
	@DatabaseField(columnName = "first_name", canBeNull = false)
	private String firstName;
	@DatabaseField(columnName = "last_name", canBeNull = false)
	private String lastName;

	@JsonIgnore
	@JsonManagedReference
	@ForeignCollectionField(eager = true)
	private Collection<JobRequest> jobs;

	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
	private GeoLocation loc;

	@DatabaseField(columnName = "rating")
	private float rating;

	public Client() {
		// super();
	}

	public Client(String email, String userName, String firstName, String lastName,
			List<JobRequest> jobs,
			GeoLocation loc, float rating) {
		super();
		this.email = email;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setJobs(jobs);
		this.loc = loc;
		this.rating = rating;
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


	public Collection<JobRequest> getJobs() {
		return this.jobs;
	}

	public void setJobs(Collection<JobRequest> jobs) {
		if(this.jobs==null) this.jobs=Collections.emptyList();
		this.jobs = jobs;

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

}
