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
package com.projectlaver.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class EffectiveDatedEntity {

	public static final Long START_OF_TIME = 0L;
	public static final Long END_OF_TIME = 79841808000000L;
	
	public static final Date START_OF_TIME_DATE = new Date( START_OF_TIME );
	public static final Date END_OF_TIME_DATE = new Date( END_OF_TIME );
	
	@Version
	@Column(name = "version", nullable = false)
	private int version;

    @Column(name = "created", nullable = false, insertable = false, updatable = false)
    private Timestamp created;

    @Column(name = "updated", nullable = false, insertable = false, updatable = false)
    private Timestamp updated;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start", nullable = false)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end", nullable = false)
    private Date end;
    
    public EffectiveDatedEntity() {}
    
    public EffectiveDatedEntity( Date start, Date end ) {
    	this.start = start;
    	this.end = end;
    }
	
    
    @PrePersist
	protected void onCreate() {
    	if( this.start == null ) {
    		this.start = new Date();
    	}
    	
    	if( this.end == null ) {
    		this.end = new Date( END_OF_TIME );
    	}
	}

	@PreUpdate
	protected abstract void onUpdate();

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public void setVersion( int version ) {
		this.version = version;
	}
	
	public Timestamp getCreated() {
		return this.created;
	}
	
	public Timestamp getUpdated() {
		return this.updated;
	}
	
}
