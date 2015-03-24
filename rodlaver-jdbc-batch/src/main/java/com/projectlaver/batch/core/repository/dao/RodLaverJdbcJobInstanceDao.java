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
package com.projectlaver.batch.core.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.repository.dao.JdbcJobInstanceDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * Overriding this class to implement a 'LIMIT 1' clause in the SQL query, since that seems to be all we need
 * for the CommandLineJobLauncher (instead of loading all of the results in the query only to just read the first row).
 * 
 * @author alexduff
 *
 */
public class RodLaverJdbcJobInstanceDao extends JdbcJobInstanceDao {
	
	private static final String FIND_LAST_JOBS_BY_NAME = "SELECT JOB_INSTANCE_ID, JOB_NAME from %PREFIX%JOB_INSTANCE where JOB_NAME = ? order by JOB_INSTANCE_ID desc";
	private static final String FIND_LAST_JOBS_BY_NAME_LIMIT_1 = "SELECT JOB_INSTANCE_ID, JOB_NAME from %PREFIX%JOB_INSTANCE where JOB_NAME = ? order by JOB_INSTANCE_ID desc LIMIT 1";
	
	@Override
	public List<JobInstance> getJobInstances(String jobName, final int start,
			final int count) {

		ResultSetExtractor<List<JobInstance>> extractor = new ResultSetExtractor<List<JobInstance>>() {

			private List<JobInstance> list = new ArrayList<JobInstance>();

			@Override
			public List<JobInstance> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int rowNum = 0;
				while (rowNum < start && rs.next()) {
					rowNum++;
				}
				while (rowNum < start + count && rs.next()) {
					ParameterizedRowMapper<JobInstance> rowMapper = new JobInstanceRowMapper();
					list.add(rowMapper.mapRow(rs, rowNum));
					rowNum++;
				}
				return list;
			}

		};

		String sql = null;
		if( start == 0 && count == 1 ) {
			sql = FIND_LAST_JOBS_BY_NAME_LIMIT_1;
		} else {
			sql = FIND_LAST_JOBS_BY_NAME;
		}
			
		
		List<JobInstance> result = getJdbcTemplate().query(getQuery(sql),
				new Object[] { jobName }, extractor);

		return result;
	}
	
	/**
	 * @author Dave Syer
	 *
	 */
	private final class JobInstanceRowMapper implements
	ParameterizedRowMapper<JobInstance> {

		public JobInstanceRowMapper() {
		}

		@Override
		public JobInstance mapRow(ResultSet rs, int rowNum) throws SQLException {
			JobInstance jobInstance = new JobInstance(rs.getLong(1), rs.getString(2));
			// should always be at version=0 because they never get updated
			jobInstance.incrementVersion();
			return jobInstance;
		}
	}
	
}
