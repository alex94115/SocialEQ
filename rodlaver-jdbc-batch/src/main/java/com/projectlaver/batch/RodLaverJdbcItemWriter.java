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
package com.projectlaver.batch;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;


public abstract class RodLaverJdbcItemWriter<T> implements ItemWriter<T>, InitializingBean {

	/**
	 * Instance variables
	 */

	private boolean assertUpdates;
	private JdbcTemplate template;
	private ItemSqlParameterSourceProvider<T> itemSqlParameterSourceProvider;

	/**
	 * Static variables
	 */

	private static final Long START_OF_TIME = 0L;
	private static final Long END_OF_TIME = 79841808000000L;

	public static final Date START_OF_TIME_DATE = new Date(START_OF_TIME);
	public static final Date END_OF_TIME_DATE = new Date(END_OF_TIME);

	/**
	 * Public methods
	 */

	public void setAssertUpdates(boolean assertUpdates) {
		this.assertUpdates = assertUpdates;
	}
	
	public boolean getAssertUpdates() {
		return this.assertUpdates;
	}

	public void setDataSource(DataSource dataSource) {
		if (this.template == null) {
			this.template = new JdbcTemplate(dataSource);
		}
	}

	/**
	 * Public setter for the {@link NamedParameterJdbcOperations}.
	 * 
	 * @param namedParameterJdbcTemplate
	 *            the {@link NamedParameterJdbcOperations} to set
	 */
	public void setJdbcTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return this.template;
	}

	public void setItemSqlParameterSourceProvider( ItemSqlParameterSourceProvider<T> itemSqlParameterSourceProvider ) {
		this.itemSqlParameterSourceProvider = itemSqlParameterSourceProvider;
	}

	public abstract void afterPropertiesSet();
	
	@Override
	public abstract void write(final List<? extends T> items) throws Exception;
}
