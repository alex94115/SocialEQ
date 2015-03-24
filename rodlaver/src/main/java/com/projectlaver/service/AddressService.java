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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.Locale;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.projectlaver.domain.Address;
import com.projectlaver.domain.User;
import com.projectlaver.repository.AddressRepository;
import com.shippingapis.usps.AddressNotFoundException;
import com.shippingapis.usps.AddressValidateRequest;
import com.shippingapis.usps.AddressValidateResponse;
import com.shippingapis.usps.ObjectFactory;

@Service
@Transactional(readOnly = false)
public class AddressService {

	@Autowired
	private AddressRepository repository;

	@Autowired
	private UserService userService;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Transactional(readOnly = true)
	public Address findById( Long id ) {
		return this.repository.findOne( id );
	}
	
	@Transactional(readOnly = false)
	public Boolean create( Address address ) {
		boolean created = false;
		
		Address existingAddress = this.repository.findOne( address.getId() );
		if (existingAddress == null) { 
			
			Address saved = this.repository.save( existingAddress );
			if (saved != null) { 
				created = true;
			}
		}
		
		return created;
	}
	
	@Transactional(readOnly = false)
	public Boolean setPrimaryAddress( Long newPrimaryAddressId ) {
		
		User user = this.userService.getCurrentUser();
		Set<Address> addresses = user.getAddresses();
		
		Boolean result = false;
		for( Address address : addresses ) {
			
			// Find the new primary address and update it
			if( address.getId().equals( newPrimaryAddressId )) {
				address.setIsPrimary( true );
				this.update( address );
				result = true;
			
			// Re-set any other previous primary address 
			} else if( address.getIsPrimary() ) {
				address.setIsPrimary( false );
				this.update( address );
			}
		}
		
		return result;
		
		
	}
	
	@Transactional(readOnly = false)
	public Boolean update( Address inputAddress ) {
		boolean updated = false;

		Address existingAddress = this.repository.findOne( inputAddress.getId() );
		
		if( existingAddress != null ) {
			existingAddress.setFirstLine( inputAddress.getFirstLine() );
			existingAddress.setSecondLine( inputAddress.getSecondLine() );
			existingAddress.setThirdLine( inputAddress.getThirdLine() );
			existingAddress.setCity( inputAddress.getCity() );
			existingAddress.setState( inputAddress.getState() );
			existingAddress.setZip( inputAddress.getZip() );
			existingAddress.setIsPrimary( inputAddress.getIsPrimary() ) ;
			existingAddress.setIsVerified( inputAddress.getIsVerified() );
			
			Address saved = this.repository.save( existingAddress );
			if( saved != null ) {
				updated = true;
			}
		}
		
		return updated;
	}
	
	/**
	 * Validates US addrresses with the USPS
	 * 
	 * @param inputAddress
	 * @return
	 * @throws AddressNotFoundException
	 */
	@Transactional(readOnly = true)
	public Address clean( Address inputAddress, Locale locale ) throws AddressNotFoundException {
		
		Address cleaned = null;
		
		if( inputAddress.getCountry() != null && !inputAddress.getCountry().equals( "US" )) {
			
			cleaned = new Address();
	        cleaned.setFirstLine( StringUtils.toUpperCase( inputAddress.getFirstLine(), locale ));
	        cleaned.setSecondLine( StringUtils.toUpperCase(  inputAddress.getSecondLine(), locale ));
	        cleaned.setThirdLine( StringUtils.toUpperCase( inputAddress.getThirdLine(), locale ) );
	        cleaned.setCity( StringUtils.toUpperCase( inputAddress.getCity(), locale ) );
	        cleaned.setState( StringUtils.toUpperCase( inputAddress.getState(), locale ) );
	        cleaned.setZip( StringUtils.toUpperCase( inputAddress.getZip(), locale ) );
	        cleaned.setCountry( StringUtils.toUpperCase( inputAddress.getCountry(), locale ) );
	        
		} else {
			
			
			try {
				JAXBContext jc = JAXBContext.newInstance( AddressValidateRequest.class, AddressValidateResponse.class );
		        
		        ObjectFactory objectFactory = new ObjectFactory();
		        AddressValidateRequest.Address unvalidatedAddress = objectFactory.createAddressValidateRequestAddress();
		        unvalidatedAddress.setAddress1( inputAddress.getFirstLine() );
		        unvalidatedAddress.setAddress2( inputAddress.getSecondLine() );
		        unvalidatedAddress.setCity( inputAddress.getCity() );
		        unvalidatedAddress.setState( inputAddress.getState() );
		        unvalidatedAddress.setZip5( inputAddress.getZip() );
		        unvalidatedAddress.setZip4( "" );
		        
		        AddressValidateRequest validateRequest = objectFactory.createAddressValidateRequest();
		        validateRequest.setUSERID( "672HOOTI1751" );
		        validateRequest.setAddress( unvalidatedAddress );
	
		        Marshaller marshaller = jc.createMarshaller();
		        StringWriter sw = new StringWriter();
		        marshaller.marshal( validateRequest, sw );
		        
		        if( this.logger.isDebugEnabled() ) {
		        	this.logger.debug( sw.toString() );
		        }
		        
				URIBuilder builder =  URIBuilder.fromUri( "https://secure.shippingapis.com/ShippingAPI.dll" );
				builder.queryParam( "API", "Verify" );
				builder.queryParam( "XML", sw.toString() );
				URI url = builder.build();
				
				HttpClient client = HttpClients.createDefault();
				HttpGet get = new HttpGet( url );
				HttpResponse response = client.execute( get );
				HttpEntity entity = response.getEntity();
				BufferedReader reader = new BufferedReader(new InputStreamReader( entity.getContent(),"utf-8"), 8 );
				
		        Unmarshaller unmarshaller = jc.createUnmarshaller();
		        AddressValidateResponse validateResponse = (AddressValidateResponse) unmarshaller.unmarshal( reader );
		        AddressValidateResponse.Address uspsAddress = validateResponse.getAddress();
		        
		        AddressValidateResponse.Address.Error error = uspsAddress.getError();
		        if( error == null ) {
		        	if( this.logger.isDebugEnabled() ) {
		        		this.logger.debug( "Validated address [Address1: " + uspsAddress.getAddress1() + ", Address2: " + uspsAddress.getAddress2() + ", City: " + uspsAddress.getCity() + ", State: " + uspsAddress.getState() + ", Zip: " + uspsAddress.getZip5() + "-" + uspsAddress.getZip4() );
		        	}
		        	
		        	cleaned = new Address();
		        	cleaned.setFirstLine( uspsAddress.getAddress1() );
		        	cleaned.setSecondLine( uspsAddress.getAddress2() );
		        	cleaned.setCity( uspsAddress.getCity() );
		        	cleaned.setState( uspsAddress.getState() );
		        	cleaned.setZip( uspsAddress.getZip5() + "-" + uspsAddress.getZip4() );
		        	cleaned.setCountry( "US" );
		        }
		        else {
		        	// some sort of validate address error
		        	this.logger.error( "USPS validate address failed with error: " + error.getDescription() );
		        	throw new AddressNotFoundException();
		        }
		        
		        entity.getContent().close();
		        
			} catch( IOException e ) {
				this.logger.error( "Error while trying to clean address.", e );
			} catch( JAXBException e ) {
				this.logger.error( "Error while trying to clean address.", e );
			}
		}
			
		return cleaned;
	}
	
}
