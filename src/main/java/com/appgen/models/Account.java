package com.appgen.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
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
	@JsonIgnore
	@ForeignCollectionField(eager = false)
	@JsonManagedReference
	private ForeignCollection<Order> orders;

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
	@JsonProperty("orders")
	public ArrayList<Order> getOrders(){
		if(this.orders==null) return new ArrayList<Order>();
		return new ArrayList<Order>(this.orders);
	}

	@JsonIgnore
	public ForeignCollection<Order> getOrders1(){
		return this.orders;
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

	
	
}