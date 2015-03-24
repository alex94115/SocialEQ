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
package com.projectlaver.integration;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestTemplate;

import com.shippingapis.usps.AddressValidateRequest;
import com.shippingapis.usps.AddressValidateResponse;
import com.shippingapis.usps.ObjectFactory;

public class UspsAddressValidateTest {

	@Test
	public void test() throws Exception {
		
		
        JAXBContext jc = JAXBContext.newInstance( AddressValidateRequest.class, AddressValidateResponse.class );
        
        ObjectFactory objectFactory = new ObjectFactory();
        AddressValidateRequest.Address unvalidatedAddress = objectFactory.createAddressValidateRequestAddress();
        unvalidatedAddress.setAddress1( "" );
        unvalidatedAddress.setAddress2( "232 6th" );
        unvalidatedAddress.setCity( "NYC" );
        unvalidatedAddress.setState( "NY" );
        unvalidatedAddress.setZip5( "" );
        unvalidatedAddress.setZip4( "" );
        
        AddressValidateRequest validateRequest = objectFactory.createAddressValidateRequest();
        validateRequest.setUSERID( "672HOOTI1751" );
        validateRequest.setAddress( unvalidatedAddress );

        Marshaller marshaller = jc.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal( validateRequest, sw );
        System.out.println( sw.toString() );
        
		URIBuilder builder =  URIBuilder.fromUri( "https://secure.shippingapis.com/ShippingAPI.dll" );
		builder.queryParam( "API", "Verify" );
		builder.queryParam( "XML", sw.toString() );
		URI url = builder.build();
		
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet( url );
		HttpResponse response = client.execute( get );
		HttpEntity entity = response.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader( entity.getContent(),"utf-8"), 8 );
		
		//This just writes the response to the console		
//		String line = null;
//		while ((line = reader.readLine()) != null) {
//			System.out.println( line.toString());
//		}

        
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        AddressValidateResponse validateResponse = (AddressValidateResponse) unmarshaller.unmarshal( reader );
        System.out.println( validateResponse );
        
        System.out.println( "Validated address:" );
        System.out.println( "Address1: " + validateResponse.getAddress().getAddress1() );
        System.out.println( "Address2: " + validateResponse.getAddress().getAddress2() );
        System.out.println( "City: " + validateResponse.getAddress().getCity() );
        System.out.println( "State: " + validateResponse.getAddress().getState() );
        System.out.println( "Zip: " + validateResponse.getAddress().getZip5() + "-" + validateResponse.getAddress().getZip4() );
        
        com.shippingapis.usps.AddressValidateResponse.Address.Error error = validateResponse.getAddress().getError();
        if( error != null ) {
        	System.out.println( "Error description: " + error.getDescription() );
        }
        
        entity.getContent().close(); 
 	}

}
