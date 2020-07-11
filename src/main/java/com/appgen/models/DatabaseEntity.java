package com.appgen.models;

import java.util.List;
import java.util.stream.Collectors;

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
	public static <T> List<T> filter(Class<T> clazz, List<?> items) {
	    return items.stream()
	        .filter(clazz::isInstance)
	        .map(clazz::cast)
	        .collect(Collectors.toList());
	}
	
}

