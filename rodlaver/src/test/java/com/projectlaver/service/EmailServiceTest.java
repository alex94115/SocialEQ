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
package com.projectlaver.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.test.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class EmailServiceTest {
	
	@Autowired
	SocialTestConfig socialTestConfig;

	@Test
	public void test() {
		EmailService service = new EmailService();

		ReflectionTestUtils.setField( service, "fromAddress", this.socialTestConfig.getEmailFromAddress() );
		ReflectionTestUtils.setField( service, "host", this.socialTestConfig.getEmailHost() );
		ReflectionTestUtils.setField( service, "smtpUsername", this.socialTestConfig.getEmailSmtpUsername() );
		ReflectionTestUtils.setField( service, "smtpPassword", this.socialTestConfig.getEmailSmtpPassword() );
		ReflectionTestUtils.setField( service, "mailTransportProtocol", this.socialTestConfig.getEmailProtocol() );
		ReflectionTestUtils.setField( service, "mailSmtpPort", this.socialTestConfig.getEmailPort() );
		ReflectionTestUtils.setField( service, "mailSmtpAuth", this.socialTestConfig.getEmailDoAuth() );
		ReflectionTestUtils.setField( service, "mailSmtpStartTlsEnable", this.socialTestConfig.getEmailDoEnableTls() );
		ReflectionTestUtils.setField( service, "mailSmtpStartTlsRequired", this.socialTestConfig.getEmailDoRequireTls() );
		
		service.sendEmail( new String[] { "alex@socialeq.co" }, "Test subject", "Test body" );
		
	}

}
