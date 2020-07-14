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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.spring.TableCreator;
import com.j256.ormlite.table.TableUtils;

@SpringBootTest
class AppgenApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppgenApplicationTests.class);
	@Autowired
	public HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory;
	@Autowired
	private TableCreator tableCreator;
	private ObjectMapper gson = new ObjectMapper();

	@Test
	void contextLoads() throws Exception {

	}

	@SuppressWarnings("unchecked")
	@Test
	public void checkTest() throws JsonProcessingException {
		Account acc = new Account("ruusey@gmail.com", "my_password");

		Dao<Account, Integer> daoAcc = (Dao<Account, Integer>) daoFactory.get(Account.class);
		Dao<Order, Integer> daoOrder = (Dao<Order, Integer>) daoFactory.get(Order.class);
		try {
			tableCreator.setConfiguredDaos(new ArrayList<Dao<?, ?>>(daoFactory.values()));
			tableCreator.initialize();
			daoFactory.values().forEach(dao -> {
				try {
					TableUtils.createTableIfNotExists(dao.getConnectionSource(), dao.getDataClass());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			daoAcc.create(acc);

			Account accountRes = daoAcc.queryForId(1);

			Order order = new Order(accountRes, (int) (System.currentTimeMillis() % 10), 10.0f, 10);

			order.setAccount(accountRes);
			daoOrder.create(order);
			
			daoAcc.update(accountRes);

			LOGGER.info(gson.writeValueAsString(daoAcc.queryForId(1)));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
