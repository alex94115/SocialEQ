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

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;

public class TrivialJobParametersIncrementer implements JobParametersIncrementer {

	@Override
	public JobParameters getNext(JobParameters parameters) {
		Map<String, JobParameter> map = new HashMap<String, JobParameter>( parameters.getParameters() );

		// defaults the run count to 0 if parameters.getLong( "run.count" ) doesn't exist.
		// otherwise adds one to the current run count
		
		String currentRunCountStr = parameters.getString( "run.count" );
		Long currentRunCount = null;
		if( currentRunCountStr != null ) {
			currentRunCount = Long.valueOf( currentRunCountStr );
		}
		
		Long nextRunCount = null;
		if( currentRunCount != null ) {
			nextRunCount = currentRunCount + 1;
		} else {
			nextRunCount = 0L;
		}
		
		map.put( "run.count", new JobParameter( nextRunCount ) );
		return new JobParameters( map );
	}

}
