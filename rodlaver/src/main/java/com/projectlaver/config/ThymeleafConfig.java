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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.projectlaver.service.UrlService;

@Configuration 
public class ThymeleafConfig {
	
	@Value("${thymeleaf.cacheTemplates}")
	private Boolean doCacheTymeleafTemplates;

	@Bean 
	public TemplateResolver webTemplateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix( "/WEB-INF/templates/" );
		resolver.setSuffix( ".html" );
		resolver.setTemplateMode( "HTML5" );
		resolver.setOrder( 2 );
		
		// Declare virtual paths
		resolver.addTemplateAlias("connect/twitterConnect","twitter/connect");
		resolver.addTemplateAlias("connect/twitterConnected","twitter/connected");
		
		// Sets whether resolved patterns should cachable (in effect, when false this seems to 
		// allow development changes to the views without restarting Tomcat) 
		resolver.setCacheable( doCacheTymeleafTemplates );
		return resolver;
	}
	
	@Bean
	public TemplateResolver emailTemplateResolver() {
		TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix( "/mail/" );
		templateResolver.setSuffix( ".html" );
		templateResolver.setTemplateMode( "HTML5" );
		templateResolver.setCharacterEncoding( "UTF-8" );
		templateResolver.setOrder( 1 );
		return templateResolver;
	}
	
	@Bean 
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addTemplateResolver( this.webTemplateResolver() );
		engine.addTemplateResolver( this.emailTemplateResolver() );
		
		engine.addDialect( new SpringSecurityDialect() );
		engine.addDialect( new org.thymeleaf.extras.tiles2.dialect.TilesDialect() );
		return engine;
	}
	
	@Bean 
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setViewClass( org.thymeleaf.extras.tiles2.spring4.web.view.ThymeleafTilesView.class );
		resolver.setTemplateEngine(templateEngine());
		return resolver;
	}
	
	@Bean
	public ThymeleafTilesConfigurer tilesConfigurer() {
		ThymeleafTilesConfigurer configurer = new ThymeleafTilesConfigurer();
		configurer.setDefinitions( new String[] { "/WEB-INF/tiles-defs.xml" } );
		return configurer;
	}
	
	// for handling file uploads
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize( 10737418240L ); // 10 GB
		resolver.setResolveLazily( true );
		return resolver;
	}
	
	// simple bean for returning the current server url
	@Bean
	public UrlService urlService() {
		return new UrlService();
	}

	
}
