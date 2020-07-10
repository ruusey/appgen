package com.appgen.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts")
public class Account extends DatabaseEntity{

	@DatabaseField(columnName = "name", canBeNull = false)
	private String name;

	@DatabaseField(columnName = "password")
	private String password;

	@ForeignCollectionField
	private ForeignCollection<Order> orders=null;

	Account() {
		// all persisted classes must define a no-arg constructor with at least package visibility
	}

	public Account(String name) {
		this.name = name;
	}

	public Account(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ForeignCollection<Order> getOrders() {
		return orders;
	}


	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		return name.equals(((Account) other).name);
	}

	@Override
	public String toString() {
		return "Account [name=" + name + ", password=" + password + ", orders=" + orders + "]";
	}
	
}