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

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.ConnectionCustomizer;
import com.projectlaver.util.MySQLMaxValueInnoDbIncrementer;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.projectlaver.repository")
public class DataConfig extends WebMvcConfigurerAdapter {


	/**
	 * INSTANCE VARIABLES
	 */
	
	@Inject
    private Environment environment;
	
	@Value("${app.jdbc.driverClassName}")
	private String jdbcDriverClassName;
	
	@Bean
	public DataSource dataSource() throws Exception {
			
		// to make AWS happy
		Class.forName("com.mysql.jdbc.Driver");
		
		ComboPooledDataSource ds = new ComboPooledDataSource();
		
		ds.setDriverClass( this.jdbcDriverClassName );
		
		String appJdbcUrl = environment.getProperty( "app.jdbc.url" );
		
		ds.setJdbcUrl( appJdbcUrl );
		ds.setAcquireIncrement(5);
		ds.setIdleConnectionTestPeriod(60);
		ds.setMaxPoolSize(100);
		ds.setMaxStatements(50);
		ds.setMinPoolSize(10);
		return ds;
	}
	
	@Bean 
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
		vendor.setShowSql(false);
		
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceXmlLocation("classpath*:META-INF/persistence.xml");
		em.setPersistenceUnitName("hibernatePersistenceUnit");
		em.setDataSource(dataSource());
		em.setJpaVendorAdapter(vendor);
		return em;
	}
	
	@Bean 
	public JpaTransactionManager transactionManager() throws Exception {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
	@Bean
	public AbstractDataFieldMaxValueIncrementer voucherMaxValueIncrementer( final DataSource dataSource ) {
		MySQLMaxValueInnoDbIncrementer incrementer = new MySQLMaxValueInnoDbIncrementer();
		incrementer.setColumnName( "ID" );
		incrementer.setIncrementerName( "VOUCHER_SEQ" );
		incrementer.setDataSource( dataSource );
		
		return incrementer;
	}
}
