package com.appgen.models.builders;

import com.appgen.models.ServiceProvider;
import com.appgen.models.GeoLocation;

public class ServiceProviderBuilder {

	private String email = null;
	private String userName = null;
	private String firstName = null;
	private String lastName = null;
	private GeoLocation loc = null;
	private float rating = 0.0f;
	private String friendlyLocation = null;

	public ServiceProviderBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public ServiceProviderBuilder withUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public ServiceProviderBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public ServiceProviderBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public ServiceProviderBuilder withLoc(GeoLocation loc) {
		this.loc = loc;
		return this;
	}

	public ServiceProviderBuilder withRating(float rating) {
		this.rating = rating;
		return this;
	}
	public ServiceProviderBuilder withFriendlyLocation(String friendlyLocation) {
		this.friendlyLocation = friendlyLocation;
		return this;
	}

	public ServiceProvider build() {

		ServiceProvider sp = new ServiceProvider();
		sp.setEmail(this.email);
		sp.setUserName(this.userName);
		sp.setFirstName(this.firstName);
		sp.setLastName(this.lastName);
		sp.setLoc(this.loc);
		sp.setRating(this.rating);
		sp.setFriendlyLocation(this.friendlyLocation);
		return sp;
	}
}
