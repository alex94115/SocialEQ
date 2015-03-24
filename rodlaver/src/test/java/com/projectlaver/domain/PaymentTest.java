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

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.PaymentStatus;

public class PaymentTest {

	@Test
	public void testCurrentStatus() throws Exception {
		Payment payment = new Payment();
		
		EffectivePaymentStatus effectivePaymentStatus = new EffectivePaymentStatus();
		effectivePaymentStatus.setStatus( PaymentStatus.COMPLETED );
		effectivePaymentStatus.setStart( new Date() );
		effectivePaymentStatus.setEnd( EffectiveDatedEntity.END_OF_TIME_DATE );
		
		List<EffectivePaymentStatus> effectivePaymentStatuses = new ArrayList<EffectivePaymentStatus>();
		effectivePaymentStatuses.add( effectivePaymentStatus );
		payment.setEffectivePaymentStatus( effectivePaymentStatuses );
		
		EffectivePaymentStatus result = payment.getCurrentEffectivePaymentStatus();
		Assert.assertEquals( PaymentStatus.COMPLETED, result.getStatus() );
	}
	
	@Test
	public void testAppendStatus() throws Exception {
		Payment payment = new Payment();
		
		Date start = new Date();
		start.setTime( System.currentTimeMillis() - 10000 );
		
		payment.appendEffectivePaymentStatus( 
				this.createEffectivePaymentStatus( PaymentStatus.COMPLETED, start, EffectiveDatedEntity.END_OF_TIME_DATE ));
		
		Date laterStart = new Date();
		payment.appendEffectivePaymentStatus( 
				this.createEffectivePaymentStatus( PaymentStatus.REFUNDED, laterStart, EffectiveDatedEntity.END_OF_TIME_DATE ));
		
		
		EffectivePaymentStatus result = payment.getEffectivePaymentStatusAsOf( start );
		Assert.assertEquals( PaymentStatus.COMPLETED, result.getStatus() );
		
		EffectivePaymentStatus laterResult = payment.getCurrentEffectivePaymentStatus();
		Assert.assertEquals( PaymentStatus.REFUNDED, laterResult.getStatus() );
	}
	
	@Test
	public void testStatusAsOf() {
		Payment payment = new Payment();
		
		Long now = new Date().getTime();
		Date start = new Date( now - 1 );
		Date end = new Date( now + 1);
		
		Long later = new Date().getTime() + 10000;
		Date laterStart = new Date( later - 1 );
		Date laterEnd = new Date( later + 1 );
		
		List<EffectivePaymentStatus> effectivePaymentStatuses = new ArrayList<EffectivePaymentStatus>();
		payment.setEffectivePaymentStatus( effectivePaymentStatuses );

		effectivePaymentStatuses.add( this.createEffectivePaymentStatus( PaymentStatus.COMPLETED, start, end) );
		effectivePaymentStatuses.add( this.createEffectivePaymentStatus( PaymentStatus.REFUNDED, laterStart, laterEnd) );
		
		EffectivePaymentStatus result = payment.getEffectivePaymentStatusAsOf( new Date( now ) );
		Assert.assertEquals( PaymentStatus.COMPLETED, result.getStatus() );
		
		EffectivePaymentStatus laterResult = payment.getEffectivePaymentStatusAsOf( new Date( later ));
		Assert.assertEquals( PaymentStatus.REFUNDED, laterResult.getStatus() );
	}

	@Test
	public void test() {
		
		Date now = new Date();
		Date start = EffectiveDatedEntity.START_OF_TIME_DATE;
		Date end = EffectiveDatedEntity.END_OF_TIME_DATE;
		
		Assert.assertTrue( start.before( end ));
		Assert.assertTrue( end.after( start ));
		
		Assert.assertTrue( now.after( start ) );
		Assert.assertTrue( now.before( end ));
		
		Assert.assertTrue( start.before( now ));
		Assert.assertTrue( end.after( now ));
		
		Assert.assertTrue( start.equals( start ));
		Assert.assertTrue( now.equals( now ));
		Assert.assertTrue( end.equals( end ));
		
	}
	
	@Test
	public void testPaymentAmounts() {
		Message message = new Message();
		
		Listing listing = new Listing();
		listing.setType( ListingType.SELLING );
		listing.setGoodsType( GoodsType.DIGITAL );
		listing.setAmount( new BigDecimal( "1.00000000" ));
		
		Inventory inventory = new Inventory();
		Integer quantity = 1;
		
		User buyer = new User();
		User seller = new User();
		String payKey = "ABC123";
		String correlationId = "123ABC";
		BigDecimal rodlaverAmount = new BigDecimal( "0.1" );
		BigDecimal sellerAmount = new BigDecimal( "0.9" );
		
		EffectivePaymentStatus effectivePaymentStatus = new EffectivePaymentStatus();
		effectivePaymentStatus.setStatus( PaymentStatus.PAYMENT_PROCESSING );
		effectivePaymentStatus.setStart( new Date() );
		effectivePaymentStatus.setEnd( EffectiveDatedEntity.END_OF_TIME_DATE );
		effectivePaymentStatus.setVersion( 0 );
		
		Payment payment = new Payment( message, listing, inventory, quantity, buyer, seller, payKey, correlationId, rodlaverAmount, sellerAmount, listing.getAmount(), effectivePaymentStatus );
		
		assertEquals( PaymentStatus.PAYMENT_PROCESSING, payment.getCurrentEffectivePaymentStatus().getStatus() );
	}
	
	@Test
	public void testEncryptingCounter() {
		final String password = "I AM SHERLOCKED";  
        final String salt = KeyGenerators.string().generateKey();

        TextEncryptor encryptor = Encryptors.text(password, salt);      
        System.out.println("Salt: \"" + salt + "\"");

        String textToEncrypt = "1000000000000000001";
        System.out.println("Original text: \"" + textToEncrypt + "\"");

        String encryptedText = encryptor.encrypt(textToEncrypt);
        System.out.println("Encrypted text: \"" + encryptedText + "\"");

        // Could reuse encryptor but wanted to show reconstructing TextEncryptor
        TextEncryptor decryptor = Encryptors.text(password, salt);
        String decryptedText = decryptor.decrypt(encryptedText);
        System.out.println("Decrypted text: \"" + decryptedText + "\"");

        if(textToEncrypt.equals(decryptedText)) {
            System.out.println("Success: decrypted text matches");
        } else {
            System.out.println("Failed: decrypted text does not match");
        }       
	}
	
	EffectivePaymentStatus createEffectivePaymentStatus( PaymentStatus status, Date start, Date end) {
		EffectivePaymentStatus effectivePaymentStatus = new EffectivePaymentStatus();
		effectivePaymentStatus.setStatus( status );
		effectivePaymentStatus.setStart( start );
		effectivePaymentStatus.setEnd( end );
		return effectivePaymentStatus;
	}

}
