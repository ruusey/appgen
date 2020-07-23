package com.appgen.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
            .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		// registry.addResourceHandler("/docs").addResourceLocations("classpath:/static/docs/index.html");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/dashboard/**").setViewName("forward:/");

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/v1/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*");
		registry.addMapping("/v1/**/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*");
		registry.addMapping("/v1/**/**/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*");
	}

}