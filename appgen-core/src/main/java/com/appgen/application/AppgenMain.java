package com.appgen.application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.appgen.models.DatabaseEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.spring.TableCreator;
import com.j256.ormlite.table.TableUtils;

@Component
public class AppgenMain implements CommandLineRunner{
    private static final Logger LOGGER = LoggerFactory.getLogger(AppgenMain.class);

    @Autowired
    private TableCreator tableCreator;
    
    @Autowired
    private HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Initializing Appgen Service...");
       
        tableCreator.setConfiguredDaos(new ArrayList<Dao<?, ?>>(daoFactory.values()));
        tableCreator.initialize();
        daoFactory.values().forEach(dao->{
        	 try {
				TableUtils.createTableIfNotExists(dao.getConnectionSource(),dao.getDataClass());
			} catch (SQLException e) {
				LOGGER.error("Failed to create model tables", e);
			}
        });
      


    }

}
