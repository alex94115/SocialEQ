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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.projectlaver.util.BraintreePaymentProvider;
import com.projectlaver.util.PaymentProvider;

@Configuration
public class BraintreeConfig {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Braintree properties
	 */
	
	@Value("${braintree.environment}")
	private String braintreeEnvironment;
	
	@Value("${braintree.merchantId}")
	private String braintreeMerchantId;

	@Value("${braintree.publicKey}")
	private String braintreePublicKey;
	
	@Value("${braintree.privateKey}")
	private String braintreePrivateKey;

	/**
	 * Stripe properties
	 */
	
	@Bean
	public BraintreeGateway braintreeGateway() {

		Environment environment = null;
		if( "PRODUCTION".equals( this.braintreeEnvironment )) {
			environment = Environment.PRODUCTION;
		} else if( "SANDBOX".equals( this.braintreeEnvironment )) {
			environment = Environment.SANDBOX;
		} else if ( "DEVELOPMENT".equals( this.braintreeEnvironment )) {
			environment = Environment.DEVELOPMENT;
		} else {
			throw new RuntimeException( "Unknown braintree environment type: " + this.braintreeEnvironment );
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( 
					String.format( "Initializing BraintreeGateway with environment: %s, merchantId: %s, publicKey: %s, and privateKey: {snipped}",
							this.braintreeEnvironment,
							this.braintreeMerchantId,
							this.braintreePublicKey ));
		}
		
		return new BraintreeGateway( environment, this.braintreeMerchantId, this.braintreePublicKey, this.braintreePrivateKey );
	}
	
	@Bean
	public PaymentProvider braintreePaymentProvider() {
		return new BraintreePaymentProvider();
	}
	
}
