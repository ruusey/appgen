package com.appgen.application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.appgen.models.Account;
import com.appgen.models.DatabaseEntity;
import com.appgen.models.Order;
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
    
    @SuppressWarnings("unchecked")
	@Override
    public void run(String... args) throws Exception {
        LOGGER.info("Initializing Appgen Service...");
       
        tableCreator.setConfiguredDaos(new ArrayList<Dao<?, ?>>(daoFactory.values()));
        tableCreator.initialize();
        daoFactory.values().forEach(dao->{
        	 try {
				TableUtils.createTableIfNotExists(dao.getConnectionSource(),dao.getDataClass());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        Account acc = new Account("ru","password");
        
        Dao<Account,Integer> daoAcc = (Dao<Account,Integer>)daoFactory.get(Account.class);
        Dao<Order,Integer> daoOrder = (Dao<Order,Integer>)daoFactory.get(Order.class);
        daoAcc.create(acc);
        daoOrder.create(new Order(acc,1,99.0f,1));
       
      

    }

}
