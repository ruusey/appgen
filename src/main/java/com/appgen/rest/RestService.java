package com.appgen.rest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appgen.models.Account;
import com.appgen.models.DatabaseEntity;
import com.appgen.models.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
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
	@PostMapping(value = "/v1/{service}/{model}", consumes = "application/json", produces = "application/json")
	public int createEntity(@PathVariable String service,@PathVariable String model,@RequestBody String entity) throws SQLException, JsonMappingException, JsonProcessingException {
		 try {
			 String serviceName = service.substring(0, 1).toUpperCase() + service.substring(1);
			 String modelName = model.substring(0, 1).toUpperCase() + model.substring(1);
			 Class<? extends DatabaseEntity> cls = (Class<? extends DatabaseEntity>) Class.forName("com.appgen.models."+serviceName);
			 Class<? extends DatabaseEntity> cls1 = (Class<? extends DatabaseEntity>) Class.forName("com.appgen.models."+modelName);
	         ObjectMapper mapper = new ObjectMapper();
	         mapper.registerModule(new GuavaModule());
	         mapper.registerModule(new Jdk8Module());
	         mapper.registerSubtypes(cls,Order[].class);
	         JsonNode newNode = mapper.readTree(entity);
	         JsonNode newNode1 = newNode.findValue(model.toLowerCase()+"s");
	         
	         //mapper.readValue(entity, cls);
	         DatabaseEntity towns = mapper.treeToValue(newNode, cls);
	         Order[] towns1 = mapper.treeToValue(newNode1, Order[].class);
	        
	         Dao dao1 = daoFactory.get(cls);
	         dao1.createIfNotExists(towns);
	         Dao dao = daoFactory.get(cls1);
	         //dao.create(towns);
	         for(Order o : towns1) {
	        	 o.setAccount((Account) cls.cast(towns));
	        	 dao.create(o);
	         }
	        
//	         .forEach(town->{
//	        	 try {
//	        		 DatabaseEntity ent = cls.cast(town);
//	        		 
//	        	     dao1.getEmptyForeignCollection("orders").add(town);
//	        	     dao1.update(town);
//					
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	         });
	         
	         return 1;
	      } catch(ClassNotFoundException ex) {
	         System.out.println(ex.toString());
	      }
		return -1;
	}
	
}
