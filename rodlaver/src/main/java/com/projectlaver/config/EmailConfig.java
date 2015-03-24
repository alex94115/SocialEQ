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

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	@Value("${emailservice.from.address}")
	private String fromAddress;
    
	@Value("${emailservice.host}")
    private String host;
	
	@Value("${emailservice.smtp.username}")
    private String smtpUsername;
	
	@Value("${emailservice.smtp.password}")
    private String smtpPassword;
	
	@Value("${emailservice.mail.transport.protocol}")
	private String mailTransportProtocol;
	
	@Value("${emailservice.mail.smtp.port}")
	private Integer mailSmtpPort;
	
	@Value("${emailservice.mail.smtp.auth}")
	private String mailSmtpAuth;
	
	@Value("${emailservice.mail.smtp.starttls.enable}")
	private String mailSmtpStartTlsEnable;
	
	@Value("${emailservice.mail.smtp.starttls.required}")
	private String mailSmtpStartTlsRequired;
	
	@Bean
	public JavaMailSender configureJavaMailSender() {
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost( this.host );
		sender.setPort( this.mailSmtpPort );
		sender.setProtocol( this.mailTransportProtocol );
		sender.setUsername( this.smtpUsername );
		sender.setPassword( this.smtpPassword );
		sender.setProtocol( "smtps" );
		
		Properties mailProperties = new Properties();
	  	mailProperties.put( "mail.transport.protocol", this.mailTransportProtocol );
	  	mailProperties.put( "mail.smtp.port", this.mailSmtpPort );
	  	mailProperties.put( "mail.smtp.auth", this.mailSmtpAuth );
	  	mailProperties.put( "mail.smtp.ssl.enable", this.mailSmtpStartTlsEnable );
		sender.setJavaMailProperties( mailProperties );
		
		return sender;
	}
}
