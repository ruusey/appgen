package com.appgen.models.builders;

import com.appgen.models.Client;
import com.appgen.models.GeoLocation;
import com.appgen.models.JobRequest;

public class JobRequestBuilder {
	private String shortDescription;
	private String longDescription;
	private GeoLocation loc;
	private double pay;
	private boolean complete;
	private String friendlyLocation;
	private Client client;

	public JobRequestBuilder() {

	}

	public JobRequestBuilder withShortDescrtion(String shortDescritpion) {
		this.shortDescription = shortDescritpion;
		return this;
	}

	public JobRequestBuilder withLongDescrtion(String longDescription) {
		this.longDescription = longDescription;
		return this;
	}

	public JobRequestBuilder withLoc(GeoLocation loc) {
		this.loc = loc;
		return this;
	}

	public JobRequestBuilder withPay(double pay) {
		this.pay = pay;
		return this;
	}

	public JobRequestBuilder withComplete(boolean complete) {
		this.complete = complete;
		return this;
	}

	public JobRequestBuilder withFriendlyLocation(String friendlyLocation) {
		this.friendlyLocation = friendlyLocation;
		return this;
	}

	
	public JobRequestBuilder withClient(Client client) {
		this.client = client;
		return this;
	}

	public JobRequest build() {

		JobRequest jr = new JobRequest(this.shortDescription, this.longDescription, this.loc, this.pay, this.complete,
				this.friendlyLocation, this.client);

		return jr;
	}

}
