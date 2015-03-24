/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 PURPLE & GOLD, INC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.projectlaver.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.projectlaver.controller.CommonViewAttributeSettingHandler;
import com.projectlaver.util.MaintenanceModeHandlerInterceptor;


@Configuration 
@ComponentScan(basePackages = {"com.projectlaver"})
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ImportResource({"classpath:trace-context.xml","classpath:static-typelists.xml"})
@EnableCaching
@EnableScheduling
public class ApplicationContext extends WebMvcConfigurerAdapter {
	
	
	private static final Log LOGGER = LogFactory.getLog(ApplicationContext.class);
	
	// Maps resources path to webapp/resources
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// add a cache period of one week to static resources returned by Tomcat
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/")
				.setCachePeriod( 604800 ); // one week in seconds
	}
	
	// Provides internationalization of messages
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		// use this setting to apply consistent single-quote escaping to all messages 
		messageSource.setAlwaysUseMessageFormat( true );
		
		return messageSource;
	}
	
	// Only needed if we are using @Value and ${...} when referencing properties
	// Otherwise @PropertySource is enough by itself
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		
		String environment = (String) System.getProperties().get( "ENVIRONMENT" );
		
		// AWS was returning the property as ["test"] rather than just [test]
		environment = environment.replaceAll("^\"|\"$", "");

		if( ApplicationContext.LOGGER.isDebugEnabled() ) {
			ApplicationContext.LOGGER.debug( "Environment variable: " + environment );
			ApplicationContext.LOGGER.debug( "Revised environment variable: " + environment );
		}
		
		PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { 
				new ClassPathResource( environment + "-spring.properties") };
		propertySources.setLocations(resources);
		propertySources.setIgnoreUnresolvablePlaceholders(true);
		return propertySources;
	}
	
	
	// Enable pagination objects to be passed into the controller
	@Override
    public void addArgumentResolvers( List<HandlerMethodArgumentResolver> argumentResolvers) {

		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setFallbackPageable( (Pageable) new PageRequest(1, 10 ));
		argumentResolvers.add( resolver );
    }
	
	/**
	 * Add an Interceptor that runs after Controller methods and sets common view variables
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// Add a maintenance mode interceptor that redirects request to a maintenance mode page
		registry.addInterceptor( this.maintenanceModeHandlerInterceptor() ); 
				
		// Add an interceptor to set common model properties
		registry.addInterceptor( this.commonViewAttributeSettingHandler() );
	}
	
	@Bean
	public HandlerInterceptor commonViewAttributeSettingHandler() {
		return new CommonViewAttributeSettingHandler();
	}
	
	@Bean
	public HandlerInterceptor maintenanceModeHandlerInterceptor() {
		return new MaintenanceModeHandlerInterceptor();
	}
	
	/**
	 * Implement caching with EhCache
	 * 
	 * @return
	 */
	@Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager cacheManager(EhCacheManagerFactoryBean ehcache) {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(ehcache.getObject());
        return ehCacheCacheManager;
    }
}
