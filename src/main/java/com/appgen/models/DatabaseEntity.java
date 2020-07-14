package com.appgen.models;

import com.j256.ormlite.field.DatabaseField;

public class DatabaseEntity {

	@DatabaseField(generatedId = true)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
