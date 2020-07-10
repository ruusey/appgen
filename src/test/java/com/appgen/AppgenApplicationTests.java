package com.appgen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.appgen.models.Account;
import com.appgen.models.DatabaseEntity;
import com.appgen.models.Order;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.spring.TableCreator;
import com.j256.ormlite.table.TableUtils;

@SpringBootTest
//@RunWith(SpringRunner.class)
class AppgenApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppgenApplicationTests.class);
	@Autowired
	private HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory;
	 @Autowired
	    private TableCreator tableCreator;
	private Gson gson = new Gson();
	@Test
	void contextLoads() throws Exception {
		checkTest();

	}
	@Test
	public void checkTest() {
		Account acc = new Account("ru", "password");
		Order order = new Order(acc,17281,10.0f,10);
		Dao<Account, Integer> daoAcc = (Dao<Account, Integer>) daoFactory.get(Account.class);
		Dao<Order, Integer> daoOrder = (Dao<Order, Integer>) daoFactory.get(Order.class);
		try {
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
			daoAcc.create(acc);
			daoOrder.create(order);
			DatabaseEntity accountRes = daoAcc.queryForId(2);
			DatabaseEntity orderRes = daoOrder.queryForId(1);
			LOGGER.info(accountRes.toString());
			LOGGER.info(orderRes.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
