package com.appgen.util;

import com.appgen.models.DatabaseEntity;

public class ReflectionUtils {
	@SuppressWarnings("unchecked")
	private static Class<? extends DatabaseEntity> getEntityClass(String className) throws ClassNotFoundException{
		return (Class<? extends DatabaseEntity>) Class.forName("com.appgen.models." + className);
	}
	private static String extractClassName(String className) {
		return className.substring(0, 1).toUpperCase() + className.substring(1);
	}
	public static Class<? extends DatabaseEntity> getEntityClassWithService(String className) throws ClassNotFoundException{
		return getEntityClass(extractClassName(className));
	}
}
