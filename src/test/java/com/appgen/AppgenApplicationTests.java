package com.appgen;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.appgen.models.Client;
import com.appgen.models.DatabaseEntity;
import com.appgen.models.GeoLocation;
import com.appgen.models.JobRequest;
import com.appgen.models.ServiceProvider;
import com.appgen.models.builders.ClientBuilder;
import com.appgen.models.builders.GeoLocationBuilder;
import com.appgen.models.builders.JobRequestBuilder;
import com.appgen.models.builders.ServiceProviderBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
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
		GeoLocation geoLoc1 = new GeoLocationBuilder().withDateTime(Instant.now().toString()).withLat(10.001).withLng(-14.1).build();
		GeoLocation geoLoc2 = new GeoLocationBuilder().withDateTime(Instant.now().toString()).withLat(-10.001).withLng(14.1).build();
		GeoLocation geoLoc3 = new GeoLocationBuilder().withDateTime(Instant.now().toString()).withLat(41.001).withLng(114.9).build();
		JobRequest job = new JobRequestBuilder().withComplete(false).withLoc(geoLoc3).withShortDescrtion("Wanted software engineer").withLongDescrtion("long-desc").withPay(100000.0).build();
		ServiceProvider sp = new ServiceProviderBuilder().withEmail("ruusey@gmail.com").withFirstName("Robert").withLastName("Usey").withUserName("ruusey").withRating(100f).build();
		Client client = new ClientBuilder().withEmail("ruusey@gmail.com").withFirstName("Robert").withLastName("Usey").withUserName("ruusey").withRating(100f).build();
		
		Dao<Client, Integer> daoCli = (Dao<Client, Integer>) daoFactory.get(Client.class);
		Dao<GeoLocation, Integer> daoGeo = (Dao<GeoLocation, Integer>) daoFactory.get(GeoLocation.class);
		Dao<ServiceProvider, Integer> daoSp = (Dao<ServiceProvider, Integer>) daoFactory.get(ServiceProvider.class);
		Dao<JobRequest, Integer> daoJob = (Dao<JobRequest, Integer>) daoFactory.get(JobRequest.class);
		
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
			daoGeo.create(Lists.newArrayList(geoLoc1,geoLoc2,geoLoc3));
			
			daoCli.create(client);
			daoSp.create(sp);
			daoJob.create(job);
			
			geoLoc1.setClientGeoLoc(client);
			geoLoc2.setServiceProviderGeoLoc(sp);
			geoLoc3.setJobGeoLoc(job);
			
			client.setLoc(geoLoc1);
			sp.setLoc(geoLoc2);
			job.setLoc(geoLoc3);
			job.setClient(client);
			daoCli.update(client);
			daoSp.update(sp);
			daoJob.update(job);
			
			daoGeo.update(geoLoc1);
			daoGeo.update(geoLoc2);
			daoGeo.update(geoLoc3);


			LOGGER.info(gson.writeValueAsString(daoCli.queryForId(1)));
			LOGGER.info(gson.writeValueAsString(daoSp.queryForId(1)));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
