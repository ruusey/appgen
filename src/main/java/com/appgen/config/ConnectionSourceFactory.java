package com.appgen.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.appgen.models.Client;
import com.appgen.models.DatabaseEntity;
import com.appgen.models.GeoLocation;
import com.appgen.models.JobRequest;
import com.appgen.models.ServiceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.spring.TableCreator;

@Configuration
public class ConnectionSourceFactory {
	
	@Value("${appgen.db-url}")
	private String dbUrl;

	@Value("${appgen.db-name}")
	private String dbName;

	@Value("${appgen.db-user}")
	private String dbUser;

	@Value("${appgen.db-pass}")
	private String dbPass;

	@Bean(name = "connectionSource")
	public JdbcConnectionSource connectionSource() throws SQLException {
		JdbcConnectionSource connectionSource = new JdbcConnectionSource(dbUrl + dbName, dbUser, dbPass);
		connectionSource.initialize();
		return connectionSource;
	}
	
	@Bean(name = "transactionManager")
	public TransactionManager transactionManager() throws SQLException {
		return new TransactionManager(connectionSource());
	}

	@Bean(name = "tableCreator")
	public TableCreator tableCreator() throws SQLException {
		TableCreator tableCreator = new TableCreator();
		tableCreator.setConnectionSource(connectionSource());
		// tableCreator.initialize();
		return tableCreator;
	}

	@Bean(name = "daoFactory")
	public HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory() throws SQLException {
		HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> map = new HashMap<>();
		genClasses().forEach(clazz -> {
			try {
				map.put(clazz, DaoManager.createDao(connectionSource(), clazz));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return map;
	}

	@Bean(name = "genClasses")
	public List<Class<? extends DatabaseEntity>> genClasses() {
		ArrayList<Class<? extends DatabaseEntity>> list = new ArrayList<>();
		list.add(Client.class);
		list.add(ServiceProvider.class);
		list.add(JobRequest.class);
		list.add(GeoLocation.class);
		
		return list;
	}
	
	@Bean(name = "serDes")
	public ObjectMapper serDes() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());
		
		return mapper;
	}



}
