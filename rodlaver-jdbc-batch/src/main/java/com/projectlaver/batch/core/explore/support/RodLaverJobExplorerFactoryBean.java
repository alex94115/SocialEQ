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
package com.projectlaver.batch.core.explore.support;

import javax.sql.DataSource;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.AbstractJobExplorerFactoryBean;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.dao.AbstractJdbcBatchMetadataDao;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.core.repository.dao.JdbcExecutionContextDao;
import org.springframework.batch.core.repository.dao.JdbcJobExecutionDao;
import org.springframework.batch.core.repository.dao.JdbcJobInstanceDao;
import org.springframework.batch.core.repository.dao.JdbcStepExecutionDao;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.batch.core.repository.dao.JobInstanceDao;
import org.springframework.batch.core.repository.dao.StepExecutionDao;
import org.springframework.batch.core.repository.dao.XStreamExecutionContextStringSerializer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.Assert;

import com.projectlaver.batch.core.repository.dao.RodLaverJdbcJobInstanceDao;

public class RodLaverJobExplorerFactoryBean extends AbstractJobExplorerFactoryBean
implements InitializingBean {

	
	private DataSource dataSource;

	private JdbcOperations jdbcOperations;

	private String tablePrefix = AbstractJdbcBatchMetadataDao.DEFAULT_TABLE_PREFIX;

	private DataFieldMaxValueIncrementer incrementer = new AbstractDataFieldMaxValueIncrementer() {
		@Override
		protected long getNextKey() {
			throw new IllegalStateException("JobExplorer is read only.");
		}
	};

	private LobHandler lobHandler;

	private ExecutionContextSerializer serializer;

	/**
	 * A custom implementation of the {@link ExecutionContextSerializer}.
	 * The default, if not injected, is the {@link XStreamExecutionContextStringSerializer}.
	 *
	 * @param serializer used to serialize/deserialize an {@link org.springframework.batch.item.ExecutionContext}
	 * @see ExecutionContextSerializer
	 */
	public void setSerializer(ExecutionContextSerializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * Public setter for the {@link DataSource}.
	 *
	 * @param dataSource
	 *            a {@link DataSource}
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Public setter for the {@link JdbcOperations}. If this property is not set explicitly,
	 * a new {@link JdbcTemplate} will be created for the configured DataSource by default.
	 * @param jdbcOperations a {@link JdbcOperations}
	 */
	public void setJdbcOperations(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}	

	/**
	 * Sets the table prefix for all the batch meta-data tables.
	 *
	 * @param tablePrefix prefix for the batch meta-data tables
	 */
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	/**
	 * The lob handler to use when saving {@link ExecutionContext} instances.
	 * Defaults to null which works for most databases.
	 *
	 * @param lobHandler Large object handler for saving {@link org.springframework.batch.item.ExecutionContext}
	 */
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		Assert.notNull(dataSource, "DataSource must not be null.");

		if (jdbcOperations == null) {
			jdbcOperations = new JdbcTemplate(dataSource);	
		}	

		if(serializer == null) {
			XStreamExecutionContextStringSerializer defaultSerializer = new XStreamExecutionContextStringSerializer();
			defaultSerializer.afterPropertiesSet();

			serializer = defaultSerializer;
		}
	}

	private JobExplorer getTarget() throws Exception {
		return new SimpleJobExplorer(createJobInstanceDao(),
				createJobExecutionDao(), createStepExecutionDao(),
				createExecutionContextDao());
	}

	@Override
	protected ExecutionContextDao createExecutionContextDao() throws Exception {
		JdbcExecutionContextDao dao = new JdbcExecutionContextDao();
		dao.setJdbcTemplate(jdbcOperations);
		dao.setLobHandler(lobHandler);
		dao.setTablePrefix(tablePrefix);
		dao.setSerializer(serializer);
		dao.afterPropertiesSet();
		return dao;
	}

	@Override
	protected JobInstanceDao createJobInstanceDao() throws Exception {
		JdbcJobInstanceDao dao = new RodLaverJdbcJobInstanceDao();
		dao.setJdbcTemplate(jdbcOperations);
		dao.setJobIncrementer(incrementer);
		dao.setTablePrefix(tablePrefix);
		dao.afterPropertiesSet();
		return dao;
	}

	@Override
	protected JobExecutionDao createJobExecutionDao() throws Exception {
		JdbcJobExecutionDao dao = new JdbcJobExecutionDao();
		dao.setJdbcTemplate(jdbcOperations);
		dao.setJobExecutionIncrementer(incrementer);
		dao.setTablePrefix(tablePrefix);
		dao.afterPropertiesSet();
		return dao;
	}

	@Override
	protected StepExecutionDao createStepExecutionDao() throws Exception {
		JdbcStepExecutionDao dao = new JdbcStepExecutionDao();
		dao.setJdbcTemplate(jdbcOperations);
		dao.setStepExecutionIncrementer(incrementer);
		dao.setTablePrefix(tablePrefix);
		dao.afterPropertiesSet();
		return dao;
	}

	@Override
	public JobExplorer getObject() throws Exception {
		return getTarget();
	}
}
