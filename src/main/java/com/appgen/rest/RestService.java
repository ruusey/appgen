package com.appgen.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.appgen.models.DatabaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;

@CrossOrigin
@RestController
@SuppressWarnings("rawtypes")
public class RestService {
	@Autowired
	private HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory;

	@Autowired
	private ObjectMapper serDes;

	@SuppressWarnings("unchecked")
	@GetMapping("/v1/{service}/{id}")
	public Object getEntity(@PathVariable String service, @PathVariable Integer id) throws SQLException {
		try {
			String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
			Class<? extends DatabaseEntity> cls = (Class<? extends DatabaseEntity>) Class
					.forName("com.appgen.models." + serviceName);
			Dao dao = daoFactory.get(cls);
			return dao.queryForId(id);
		} catch (ClassNotFoundException ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@PutMapping(value = "/v1/{service}/{id}")
	public Object updateEntity(@PathVariable String service, @PathVariable Integer id, @RequestBody String entity)
			throws SQLException {
		try {
			String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
			Class<? extends DatabaseEntity> cls = (Class<? extends DatabaseEntity>) Class
					.forName("com.appgen.models." + serviceName);
			Dao dao = daoFactory.get(cls);
			JsonNode newNode = serDes.readTree(entity);

			DatabaseEntity o = serDes.readValue(newNode.toString(), cls);
			o.setId(id);

			return dao.update(o);
		} catch (ClassNotFoundException | JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/v1/{service}")
	public List<DatabaseEntity> getAllEntities(@PathVariable String service) throws SQLException {
		try {
			String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
			Class<? extends DatabaseEntity> cls = (Class<? extends DatabaseEntity>) Class
					.forName("com.appgen.models." + serviceName);
			Dao dao = daoFactory.get(cls);
			return dao.queryForAll();
		} catch (ClassNotFoundException ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@DeleteMapping("/v1/{service}/{id}")
	public int deleteEntity(@PathVariable String service, @PathVariable Integer id) throws SQLException {
		try {
			String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
			Class<? extends DatabaseEntity> cls = (Class<? extends DatabaseEntity>) Class
					.forName("com.appgen.models." + serviceName);
			Dao dao = daoFactory.get(cls);
			return dao.deleteById(id);
		} catch (ClassNotFoundException ex) {
			System.out.println(ex.toString());
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/v1/{service}/{id}", consumes = "application/json", produces = "application/json")
	public int addSubEntity(@PathVariable String service, @PathVariable Integer id, @RequestBody String entity)
			throws SQLException {
		try {
			String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
			Class<? extends DatabaseEntity> serviceClass = (Class<? extends DatabaseEntity>) Class
					.forName("com.appgen.models." + serviceName);

			JsonNode newNode = serDes.readTree(entity);
			ArrayList<DatabaseEntity> list = Lists.newArrayList();

			Dao dao = daoFactory.get(serviceClass);
			if (newNode.isArray()) {
				for (JsonNode node : newNode)
					list.add(serDes.readValue(node.toPrettyString(), serviceClass));

				dao.create(list);
				list.forEach(item -> {
					try {
						dao.updateBuilder().updateColumnValue("account_id", id).update();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			} else {
				dao.create(serDes.readValue(newNode.toPrettyString(), serviceClass));
				dao.updateBuilder().updateColumnValue("account_id", id).update();

			}

		} catch (ClassNotFoundException | JsonProcessingException ex) {
			System.out.println(ex.toString());
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/v1/{service}/{id}/{model}", consumes = "application/json", produces = "application/json")
	public int createEntity(@PathVariable String service, @PathVariable int id, @PathVariable String model,
			@RequestBody String entity) throws SQLException, IOException {
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
			Dao serviceDao = daoFactory.get(serviceClass);
			Dao subServiceDao = daoFactory.get(subServiceClass);
			DatabaseEntity serviceObject = serDes.treeToValue(newNode, serviceClass);

			serviceDao.createIfNotExists(serviceObject);

			try {
				subServiceDao.create(list);
				list.forEach(item -> {
					try {
						subServiceDao.updateBuilder().updateColumnValue("account_id", id).update();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return 1;
		} catch (ClassNotFoundException ex) {
			System.out.println(ex.toString());
		}
		return -1;
	}

}
