package com.appgen.rest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appgen.models.DatabaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.j256.ormlite.dao.Dao;
@RestController
public class RestService {
	@Autowired
    private HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory;
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/v1/{service}/{id}")
	public Object getEntity(@PathVariable String service,@PathVariable Integer id) throws SQLException {
		 try {
			 String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
	         Class<?> cls = Class.forName("com.appgen.models."+serviceName);
	         Dao<?,Integer> dao = (Dao<?,Integer>)daoFactory.get(cls);
	         return dao.queryForId(id);
	      } catch(ClassNotFoundException ex) {
	         System.out.println(ex.toString());
	      }
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/v1/{service}")
	public List<?> getAllEntities(@PathVariable String service) throws SQLException {
		 try {
			 String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
	         Class<?> cls = Class.forName("com.appgen.models."+serviceName);
	         Dao<?,Integer> dao = (Dao<?,Integer>)daoFactory.get(cls);
	         return dao.queryForAll();
	      } catch(ClassNotFoundException ex) {
	         System.out.println(ex.toString());
	      }
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@DeleteMapping("/v1/{service}/{id}")
	public int deleteEntity(@PathVariable String service,@PathVariable Integer id) throws SQLException {
		 try {
			 String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
	         Class<?> cls = Class.forName("com.appgen.models."+serviceName);
	         Dao<?,Integer> dao = (Dao<?,Integer>)daoFactory.get(cls);
	         return dao.deleteById(id);
	      } catch(ClassNotFoundException ex) {
	         System.out.println(ex.toString());
	      }
		return -1;
	}
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/v1/{service}", consumes = "application/json", produces = "application/json")
	public int createEntity(@PathVariable String service,@RequestBody String entity) throws SQLException, JsonMappingException, JsonProcessingException {
		 try {
			 String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
			 Class<? extends DatabaseEntity> cls = (Class<? extends DatabaseEntity>) Class.forName("com.appgen.models."+serviceName);
	         ObjectMapper mapper = new ObjectMapper();
	         mapper.registerModule(new GuavaModule());
	         mapper.registerSubtypes(cls);
	         Object o = mapper.readValue(entity, cls);
	         
	         Dao dao = daoFactory.get(o.getClass());
	         return dao.create(o);
	      } catch(ClassNotFoundException ex) {
	         System.out.println(ex.toString());
	      }
		return -1;
	}
	
}
