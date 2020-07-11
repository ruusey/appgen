package com.appgen.models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.j256.ormlite.field.DatabaseField;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BackRef{
	JsonBackReference reference();
	DatabaseField field();
}
