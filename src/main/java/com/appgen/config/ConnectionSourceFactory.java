package com.appgen.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.appgen.models.Account;
import com.appgen.models.DatabaseEntity;
import com.appgen.models.Order;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.spring.TableCreator;

@Configuration
public class ConnectionSourceFactory {
	@Autowired
	private ApplicationContext appContext;
	@Value("${appgen.db-url}")
	private String dbUrl;

	@Value("${appgen.db-name}")
	private String dbName;

	@Value("${appgen.db-user}")
	private String dbUser;

	@Value("${appgen.db-pass}")
	private String dbPass;

	@Bean(name = "connectionSource")
	public JdbcConnectionSource connectionSource() throws SQLException {
		JdbcConnectionSource connectionSource = new JdbcConnectionSource(dbUrl + dbName, dbUser, dbPass);
		connectionSource.initialize();
		return connectionSource;
	}

	@Bean(name = "transactionManager")
	public TransactionManager transactionManager() throws SQLException {
		return new TransactionManager(connectionSource());
	}

	@Bean(name = "tableCreator")
	public TableCreator tableCreator() throws SQLException {
		TableCreator tableCreator = new TableCreator();
		tableCreator.setConnectionSource(connectionSource());
		// tableCreator.initialize();
		return tableCreator;
	}

	@Bean(name = "daoFactory")
	public HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> daoFactory() throws SQLException {
		HashMap<Class<?>, Dao<? extends DatabaseEntity, ?>> map = new HashMap<>();
		genClasses().forEach(clazz -> {
			try {
				map.put(clazz, DaoManager.createDao(connectionSource(), clazz));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return map;
	}

	@Bean(name = "genClasses")
	public List<Class<? extends DatabaseEntity>> genClasses() {
		ArrayList<Class<? extends DatabaseEntity>> list = new ArrayList<>();
		list.add(Account.class);
		list.add(Order.class);
		return list;
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		// SpringResourceTemplateResolver automatically integrates with Spring's own
		// resource resolution infrastructure, which is highly recommended.
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.appContext);
		templateResolver.setPrefix("/src/main/resources/templates/");
		templateResolver.setSuffix(".html");
		// HTML is the default value, added here for the sake of clarity.
		templateResolver.setTemplateMode(TemplateMode.HTML);
		// Template cache is true by default. Set to false if you want
		// templates to be automatically updated when modified.
		templateResolver.setCacheable(true);
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		// SpringTemplateEngine automatically applies SpringStandardDialect and
		// enables Spring's own MessageSource message resolution mechanisms.
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		// Enabling the SpringEL compiler with Spring 4.2.4 or newer can
		// speed up execution in most scenarios, but might be incompatible
		// with specific cases when expressions in one template are reused
		// across different data types, so this flag is "false" by default
		// for safer backwards compatibility.
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		// NOTE 'order' and 'viewNames' are optional
		viewResolver.setOrder(1);
		viewResolver.setViewNames(new String[] { ".html", ".xhtml" });
		return viewResolver;
	}

}
