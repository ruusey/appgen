package com.appgen.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.appgen.models.DatabaseEntity;
import com.appgen.util.ReflectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;

@CrossOrigin
@RestController
@SuppressWarnings("rawtypes")
public class BaseRestService {
	
	@Autowired
	public HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory;

	@Autowired
	public ObjectMapper serDes;

	@SuppressWarnings("unchecked")
	@GetMapping("/v1/{service}/{id}")
	public ResponseEntity<?> getEntity(@PathVariable String service, @PathVariable Integer id) throws SQLException {
		try {
			Dao dao = daoFactory.get(ReflectionUtils.getEntityClassWithService(service));
			DatabaseEntity entity = (DatabaseEntity) dao.queryForId(id);
			
			return entity == null ? new ResponseEntity<>("Entity not found for given id", HttpStatus.BAD_REQUEST)
					: new ResponseEntity<DatabaseEntity>(entity, HttpStatus.OK);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Entity not found", HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@PutMapping(value = "/v1/{service}/{id}")
	public ResponseEntity<?> updateEntityById(@PathVariable String service, @PathVariable Integer id,
			@RequestBody String entity) throws SQLException {
		try {
			Dao dao = daoFactory.get(ReflectionUtils.getEntityClassWithService(service));
			JsonNode newNode = serDes.readTree(entity);

			DatabaseEntity o = serDes.readValue(newNode.toString(), ReflectionUtils.getEntityClassWithService(service));
			o.setId(id);
			dao.update(o);
			return new ResponseEntity<>("Updated entity", HttpStatus.OK);
		} catch (ClassNotFoundException | JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Entity not found", HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@PutMapping(value = "/v1/{service}")
	public ResponseEntity<?> updateEntity(@PathVariable String service, @RequestBody String entity)
			throws SQLException {
		try {
			Dao dao = daoFactory.get(ReflectionUtils.getEntityClassWithService(service));
			JsonNode newNode = serDes.readTree(entity);
			DatabaseEntity o = serDes.readValue(newNode.toString(), ReflectionUtils.getEntityClassWithService(service));
			dao.update(o);
			return new ResponseEntity<>("Updated entity", HttpStatus.OK);
		} catch (ClassNotFoundException | JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Unable to update entity", HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/v1/{service}")
	public ResponseEntity<?> getAllEntities(@PathVariable String service) throws SQLException {
		try {
			Dao dao = daoFactory.get(ReflectionUtils.getEntityClassWithService(service));
			return new ResponseEntity<List<DatabaseEntity>>(dao.queryForAll(), HttpStatus.OK);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Entity not found", HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@DeleteMapping("/v1/{service}/{id}")
	public ResponseEntity<?> deleteEntity(@PathVariable String service, @PathVariable Integer id) throws SQLException {
		try {
			Dao dao = daoFactory.get(ReflectionUtils.getEntityClassWithService(service));
			dao.deleteById(id);
			return new ResponseEntity<>("Deleted entity", HttpStatus.OK);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Entity not found", HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/v1/{service}/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addSubEntity(@PathVariable String service, @PathVariable Integer id,
			@RequestBody String entity) throws SQLException {
		try {
			Dao dao = daoFactory.get(ReflectionUtils.getEntityClassWithService(service));

			JsonNode newNode = serDes.readTree(entity);
			ArrayList<DatabaseEntity> list = Lists.newArrayList();

			
			if (newNode.isArray()) {
				for (JsonNode node : newNode)
					list.add(serDes.readValue(node.toPrettyString(), ReflectionUtils.getEntityClassWithService(service)));

				dao.create(list);
				list.forEach(item -> {
					try {
						dao.updateBuilder().updateColumnValue("account_id", id).update();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				return new ResponseEntity<>("Created new list of sub entities", HttpStatus.OK);
			} else {
				dao.create(serDes.readValue(newNode.toPrettyString(), ReflectionUtils.getEntityClassWithService(service)));
				dao.updateBuilder().updateColumnValue("account_id", id).update();
				return new ResponseEntity<>("Created new sub entity", HttpStatus.OK);
			}

		} catch (ClassNotFoundException | JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Parent entity not found", HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/v1/{service}/{id}/{model}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createEntity(@PathVariable String service, @PathVariable int id,
			@PathVariable String model, @RequestBody String entity) throws SQLException, IOException {
		try {
			
			String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
			String modelName = model.substring(0, 1).toUpperCase() + model.substring(1);
			Class<? extends DatabaseEntity> serviceClass = (Class<? extends DatabaseEntity>) Class
					.forName("com.appgen.models." + serviceName);
			Class<? extends DatabaseEntity> subServiceClass = (Class<? extends DatabaseEntity>) Class
					.forName("com.appgen.models." + modelName);

			JsonNode newNode = serDes.readTree(entity);
			JsonNode newNode1 = newNode.findValue(model.toLowerCase() + "s");

			ArrayList<DatabaseEntity> list = Lists.newArrayList();
			if (newNode1.isArray()) {
				for (JsonNode node : newNode1) {
					list.add(serDes.readValue(node.toPrettyString(), subServiceClass));
				}
			}
			Dao serviceDao = daoFactory.get(ReflectionUtils.getEntityClassWithService(serviceName));
			Dao subServiceDao = daoFactory.get(ReflectionUtils.getEntityClassWithService(modelName));
			DatabaseEntity serviceObject = serDes.treeToValue(newNode, serviceClass);

			serviceDao.createIfNotExists(serviceObject);

			try {
				subServiceDao.create(list);
				list.forEach(item -> {
					try {
						subServiceDao.updateBuilder().updateColumnValue("account_id", id).update();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return new ResponseEntity<>("Created new entity", HttpStatus.OK);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>("Error creating entity", HttpStatus.BAD_REQUEST);
	}

}
