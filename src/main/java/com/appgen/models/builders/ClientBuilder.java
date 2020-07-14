package com.appgen.models.builders;

import com.appgen.models.Client;
import com.appgen.models.GeoLocation;
import com.appgen.models.JobRequest;
import com.j256.ormlite.dao.ForeignCollection;

public class ClientBuilder {

	private String email;
	private String userName;
	private String firstName;
	private String lastName;
	private ForeignCollection<JobRequest> jobs;
	private GeoLocation loc;
	private float rating;

	public ClientBuilder() {
	}

	public ClientBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public ClientBuilder withUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public ClientBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public ClientBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public ClientBuilder withJobs(ForeignCollection<JobRequest> jobs) {
		this.jobs = jobs;
		return this;
	}

	public ClientBuilder withLoc(GeoLocation loc) {
		this.loc = loc;
		return this;
	}

	public ClientBuilder withRating(float rating) {
		this.rating = rating;
		return this;
	}

	public Client build() {

		Client client = new Client();
		client.setEmail(this.email);
		client.setUserName(this.userName);
		client.setFirstName(this.firstName);
		client.setLastName(this.lastName);
		client.setJobs(this.jobs);
		client.setLoc(this.loc);
		client.setRating(this.rating);
		return client;
	}

}
