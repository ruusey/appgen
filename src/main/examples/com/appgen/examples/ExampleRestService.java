package com.appgen.examples;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.appgen.models.DatabaseEntity;
import com.appgen.util.ReflectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;

@CrossOrigin
@RestController
@SuppressWarnings("rawtypes")
public class ExampleRestService {

	@Autowired
	public HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory;

	@Autowired
	public ObjectMapper serDes;

	@SuppressWarnings("unchecked")
	@GetMapping("/v2/{service}")
	public ResponseEntity<?> testGetJobRequests(@PathVariable String service) {
		try {
			Dao dao = daoFactory.get(ReflectionUtils.getEntityClassWithService(service));
			List<DatabaseEntity> entity = dao.queryForAll();

			return entity == null ? new ResponseEntity<>("Entity not found for given id", HttpStatus.BAD_REQUEST)
					: new ResponseEntity<List<DatabaseEntity>>(entity, HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Entity not found", HttpStatus.BAD_REQUEST);
	}
	@SuppressWarnings("unchecked")
	@GetMapping("/v2/geoLocation/1")
	public ResponseEntity<?> testGetJobRequestById(@PathVariable String service) {
		try {
			Dao dao = daoFactory.get(ReflectionUtils.getEntityClassWithService(service));
			List<DatabaseEntity> entity = dao.queryForAll();

			return entity == null ? new ResponseEntity<>("Entity not found for given id", HttpStatus.BAD_REQUEST)
					: new ResponseEntity<List<DatabaseEntity>>(entity, HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Entity not found", HttpStatus.BAD_REQUEST);
	}
	
}
