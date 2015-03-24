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

import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jets3t.service.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.projectlaver.controller.SelfRegistrationController;
import com.projectlaver.domain.Listing;
import com.projectlaver.domain.MessageStateChange;
import com.projectlaver.domain.Payment;
import com.projectlaver.util.EmailImageDTO;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;

@Service
@Transactional(readOnly = false)
public class EmailService {
	
	@Value("${emailservice.from.address}")
	private String fromAddress;
	
	@Value("${emailservice.socialeq.banner.image.url}")
	private String bannerImageUrl;
	
	@Value("${emailservice.socialeq.banner.image.resource.name}")
	private String bannerImageResourceName;
	
	@Value("${emailservice.socialeq.banner.image.content.type}")
	private String bannerImageContentType;
	
	@Value("${aws.s3.public.bucket.base.url}")
	private String s3PublicBucketBaseUrl;
	
	@Value("${application.url}")
	private String applicationUrl;
    
    /**
     * Instance variables
     */
    
    private final Log logger = LogFactory.getLog(getClass());
    
    // cache the banner image to avoid repeatedly reading it from S3
    private byte[] bannerBytes;

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private SpringTemplateEngine templateEngine;
    
    public EmailService() {}

    @Transactional(readOnly = true)
    public Boolean sendEmail( String[] recipients, String subject, String body ) {
    	
    	try {
	    	MimeMessage msg = this.mailSender.createMimeMessage();
	        msg.setFrom(new InternetAddress( this.fromAddress ));
	        
	        Address[] addresses = new Address[ recipients.length ];
	        for( int i = 0; i < recipients.length; i++ ) {
	        	addresses[i] = new InternetAddress( recipients[i] );
	        }
	        
	        msg.setRecipients(Message.RecipientType.TO, addresses );
	        msg.setSubject( subject );
	        msg.setContent( body,"text/plain");
	    	
	    	this.mailSender.send( msg );
	    	if( this.logger.isDebugEnabled() ) {
            	logger.debug("Email sent!");
            }
    	} catch( MessagingException e ) {
          logger.error("The email was not sent.");
          logger.error("Error message: " + e.getMessage());
          throw new RuntimeException( e );
    	}
    	
    	return true;
    }
    
    @Transactional(readOnly = true)
    public Boolean sendBuyerPurchaseReceipt( MessageStateChange msc, final Locale locale ) {
    	
    	return this.sendSaleReceipt( msc, locale, false );
    }
    
    @Transactional(readOnly = true)
    public Boolean sendSellerSaleReceipt( MessageStateChange msc, final Locale locale ) {
    	
    	return this.sendSaleReceipt( msc, locale, true );
    }
    
    @Transactional(readOnly = true)
    public String validateEmailAddress( String email1, String email2 ) {
    	
    	String result = null;
    	
    	if( !StringUtils.equals( email1, email2 )) {
			result = "Email addresses must match";
		}
    	
		try {
			// Use the Internet Address class to confirm that this is a usable email address
			InternetAddress ia = new InternetAddress( email1, true );
		} catch( AddressException ae ) {
			result = ae.getLocalizedMessage();
		}
		
		return result;
    }
    
    /**
     * Helper methods
     */
    
    Boolean sendSaleReceipt( MessageStateChange msc, final Locale locale, Boolean isForSeller ) {
    
    	
    	Boolean result = false;
	
    	Payment payment = this.paymentService.findValidPaymentByReplyId( msc.getMessage().getId() );
    	if( payment != null ) {
			
			Listing listing = payment.getListing();
			
			if( !listing.getType().equals( ListingType.SELLING ) ) {
				
				// TODO Ticket-#515 Email Receipts are Not Implemented for non-Selling listings
				this.logger.error( "Not implemented to send receipts for non-digital purchase." );
				
				// return true to indicate that it's "finished" and no more send attempts need to be made.
				return true;
			}
    	
    	
	    	try {
	    		
	    		/**
	    		 * Handle the images that go in the email
	    		 */
	    		
		    	EmailImageDTO bannerImage = new EmailImageDTO();
		    	if( this.bannerBytes == null ) {
		    		this.bannerBytes = ServiceUtils.readInputStreamToBytes( new URL( this.bannerImageUrl ).openStream() );
		    	}
		    	bannerImage.setImageBytes( bannerBytes );
		    	bannerImage.setImageResourceName( this.bannerImageResourceName );
		    	bannerImage.setImageContentType( this.bannerImageContentType );
		    	
		    	EmailImageDTO listingImage = new EmailImageDTO();
		    	byte[] listingImageBytes = ServiceUtils.readInputStreamToBytes( new URL( this.s3PublicBucketBaseUrl + payment.getListing().getImageFilename() ).openStream() );
		    	listingImage.setImageBytes( listingImageBytes );
		    	listingImage.setImageResourceName( payment.getListing().getImageFilename() );
		    	listingImage.setImageContentType( URLConnection.guessContentTypeFromName( payment.getListing().getImageFilename() ) );
	    	
		    	/**
		    	 *  Prepare the evaluation context variables
		    	 */
		        final Context ctx = new Context( locale ); 
		        
		        // recipient
		        ctx.setVariable( "isRecipientBuyer", !isForSeller );
		        ctx.setVariable( "isRecipientSeller", isForSeller );
		        
		        // buyer info
		        ctx.setVariable( "buyerName", payment.getPayer().getScreenFormattedUsername() );
		        
		        // payment info
		        ctx.setVariable( "paymentId", payment.getId() );
		        ctx.setVariable( "paymentTotal", payment.getTotalAmount() );
		        ctx.setVariable( "orderDate", payment.getCreated() );
		        
		        // listing info
		        ctx.setVariable( "listingTitle", listing.getTitle() );
		        ctx.setVariable( "listingImageResourceName", listingImage.getImageResourceName() );  // so that we can reference it from HTML
		        ctx.setVariable( "isDigitalSale", listing.getGoodsType().equals( GoodsType.DIGITAL ) );
		        
		        // seller info
		        ctx.setVariable( "sellerName", payment.getPayee().getMerchantName() );
		        
		        // SocialEQ branding
		        ctx.setVariable( "bannerImageResourceName", bannerImage.getImageResourceName() ); // so that we can reference it from HTML
		        
		        // Transaction detail link
		        ctx.setVariable( "transactionDetailUrl", String.format( "%s/listing/transactionDetail/%d", this.applicationUrl, payment.getId() ));
		
		        /**
		         * Recipient and mail subject
		         */
		        
		        String mailSubject = isForSeller ? "You Made a Sale via SocialEQ" : "Your Receipt from  SocialEQ";
		        String recipientEmailAddress = isForSeller ? payment.getPayee().getEmailAddress() :  payment.getPayer().getEmailAddress();
		        
		        /**
		         *  Prepare message using a Spring helper 
		         */
		        
		        final MimeMessage mimeMessage = this.mailSender.createMimeMessage(); 
		        final MimeMessageHelper message = new MimeMessageHelper( mimeMessage, true, "UTF-8" ); // true = multipart 
		        message.setSubject( mailSubject ); 
		        message.setFrom( this.fromAddress ); 
		        message.setTo( recipientEmailAddress ); 
		
		        // Create the HTML body using Thymeleaf 
		        final String htmlContent = this.templateEngine.process( "transaction-receipt", ctx );
		        message.setText(htmlContent, true); // true = isHtml 
		
		        // Add the inline images, referenced from the HTML code as "cid:${imageResourceName}" 
		        final InputStreamSource bannerImageSource = new ByteArrayResource( bannerImage.getImageBytes() ); 
		        message.addInline( bannerImage.getImageResourceName(), bannerImageSource, bannerImage.getImageContentType() );
		        
		        final InputStreamSource listingImageSource = new ByteArrayResource( listingImage.getImageBytes() ); 
		        message.addInline( listingImage.getImageResourceName(), listingImageSource, listingImage.getImageContentType() );
		
		        // Send mail 
		        this.mailSender.send(mimeMessage);
		        result = true;
		        
	    	} catch( Exception e ) {
	            logger.error("The email was not sent.");
	            logger.error("Error message: " + e.getMessage());
	            throw new RuntimeException( e );
	    	}
    	}
	    	
    	return result;
    }

}
