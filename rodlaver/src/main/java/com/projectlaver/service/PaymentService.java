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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectlaver.domain.Address;
import com.projectlaver.domain.ContentFile;
import com.projectlaver.domain.EffectiveDatedEntity;
import com.projectlaver.domain.EffectivePaymentStatus;
import com.projectlaver.domain.EffectiveVoucherStatus;
import com.projectlaver.domain.Listing;
import com.projectlaver.domain.Message;
import com.projectlaver.domain.Payment;
import com.projectlaver.domain.User;
import com.projectlaver.domain.Voucher;
import com.projectlaver.repository.ContentFileRepository;
import com.projectlaver.repository.PaymentRepository;
import com.projectlaver.repository.VoucherRepository;
import com.projectlaver.util.PaymentStatus;
import com.projectlaver.util.VoucherStatus;

@Service
@Transactional(readOnly = false)
public class PaymentService {

	private static List<PaymentStatus> validPaymentStatuses = new ArrayList<PaymentStatus>();

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private ContentFileRepository contentFileRepository;
	
	@Autowired
	private MessageService messageService;
	
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Public instance methods
	 */

	@Transactional(readOnly = false)
	public Boolean save( Payment payment ) {
		boolean created = false;

		Payment saved = this.paymentRepository.save( payment );
		if( saved != null ) {
			created = true;
		}

		return created;
	}

	@Transactional(readOnly = true)
	public Payment findValidPaymentByListingIdAndBuyerId( Long listingId, Long buyerId ) {
		return this.paymentRepository.findByListingIdAndBuyerIdInStatus( listingId, buyerId, this.getValidPaymentStatuses() );
	}
	
	@Transactional(readOnly = true)
	public Payment findValidPaymentByReplyId( Long replyId ) {
		return this.paymentRepository.findByReplyIdInStatus( replyId, this.getValidPaymentStatuses() );
	}

	/**
	 * Used by transaction history to find all this user's payments (either as the payer or payee). 
	 * 
	 * @param userId
	 * @param p
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Payment> findAllByUserId( Long userId, Pageable p ) {
		Page<Payment> page = this.paymentRepository.findAllByUserId( userId, p );
		List<Payment> payments = page.getContent();
		for( Payment payment : payments ) {
			// deproxy the current status
			List<EffectivePaymentStatus> effectivePaymentStatusList = new ArrayList<EffectivePaymentStatus>();
			EffectivePaymentStatus status = this.deproxy( payment.getCurrentEffectivePaymentStatus() );
			effectivePaymentStatusList.add( status );
			payment.setEffectivePaymentStatus( effectivePaymentStatusList );

			// deproxy the payer, payee, and message
			payment.setPayer( this.deproxy( payment.getPayer() ) );
			payment.setPayee( this.deproxy( payment.getPayee() ) );
			payment.setMessage( this.deproxy( payment.getMessage() ) );
		}
		return page;
	}

	@Transactional(readOnly = true)
	public Payment findById( Long id ) {
		return this.paymentRepository.findOne(id);
	}

	/**
	 * @param paymentId
	 * @param buyerOrSellerId the user id of either the buyer (payer) or seller (payee)
	 * @return
	 */
	@Transactional(readOnly = true)
	public Payment securedFindPaymentFetchEagerly( Long paymentId, Long buyerOrSellerId ) {
		Payment result = this.paymentRepository.findByIdWithEagerFetching( paymentId, buyerOrSellerId );
		if( result != null ) {
			EffectivePaymentStatus eps = this.paymentRepository.findCurrentEffectivePaymentStatusByPaymentId( paymentId );
			List<EffectivePaymentStatus> epsList = new ArrayList<EffectivePaymentStatus>();
			epsList.add( eps );
			result.setEffectivePaymentStatus( epsList );

			// get the content files sorted by their original file names
			Listing listing = result.getListing();
			Set<ContentFile> contentFiles = this.contentFileRepository.findByListingId( listing.getId() );
			listing.setContentFiles( contentFiles );
			
		}

		return result;
	}
	
	@Transactional(readOnly = false) 
	public Map<String, Object> redeemVoucher( Long sellerId, String serialNumber ) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Voucher voucher = this.voucherRepository.findBySellerIdAndSerialNumber( sellerId, serialNumber );
		
		if( voucher != null ) {
			
			Payment payment = voucher.getPayment(); 
			
			// get the current payment status
			EffectivePaymentStatus eps = payment.getCurrentEffectivePaymentStatus();
			
			if( eps != null && eps.getStatus().equals( PaymentStatus.COMPLETED ) ) {
				
				EffectiveVoucherStatus evs = voucher.getCurrentEffectiveVoucherStatus();
				
				if( evs != null && evs.getStatus().equals( VoucherStatus.UNREDEEMED )) {
					
					// Update the effective voucher status to be redeemed
					EffectiveVoucherStatus updatedEvs = new EffectiveVoucherStatus(  new Date(), EffectiveDatedEntity.END_OF_TIME_DATE, VoucherStatus.REDEEMED, voucher );
					voucher.appendEffectiveVoucherStatus( updatedEvs );
					this.save( payment );
					
					result.put( "isValid", Boolean.TRUE );
					result.put( "userMessage", "Voucher redemption suceeded." );
					
				} else {
					// current voucher status is not UNREDEEMED
					result.put( "isValid", Boolean.FALSE );
					result.put( "userMessage", String.format( "This voucher could not be redeemed because its status is: %s", evs.getStatus() ) );
				}
				
			} else {
				// current payment status is not COMPLETED
				result.put( "isValid", Boolean.FALSE );
				result.put( "userMessage", String.format( "This voucher could not be redeemed because the associated transaction status is: %s", eps.getStatus() ) );
			}
			
			
		} else {
			// could not find a voucher from that seller with that serial number
			result.put( "isValid", Boolean.FALSE );
			result.put( "userMessage", "This voucher could not be redeemed because the serial number was not found." );
		}
		
		return result;
	}
	
	@Transactional(readOnly = false)
	public Payment savePaymentWithAddress( Payment payment, Address address ) {
		
		User buyer = payment.getPayer();
		
		Set<Address> addresses = buyer.getAddresses();
		if( addresses == null ) {
			addresses = new HashSet<Address>();
		} 
		
		// Associate the Address to the Payment
		payment.setShippingAddress( address );

		// Associate the User to the Address and vice versa
		addresses.add( address );
		address.setUser( buyer );

		// persist the shipping address (expect this to cascade the Address)
		this.save( payment );

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Persisted the payment and associated shipping address.");
		}

		// reset the message's batch processor to Null so the next batch job run will pick it up again
		Message message = this.messageService.findByPaymentId( payment.getId() );
		message.setBatchProcessor( null );
		this.messageService.update( message );
		
		return payment;
	}
	
	@Transactional(readOnly = false)
	public Payment savePaymentWithExistingAddress( Payment payment, Long addressId ) {
		
		User buyer = payment.getPayer();
		
		Set<Address> addresses = buyer.getAddresses();
		
		Address paymentAddress = null;
		for( Address address : addresses ) {
			if( address.getId().equals( addressId )) {
				paymentAddress = address;
				break;
			}
		}
		
		if( paymentAddress == null ) {
			throw new RuntimeException( "Unexpected addressId: " + addressId + " passed in for payment:" + payment.getId() );
		}
		
		return this.savePaymentWithAddress( payment, paymentAddress );
		
	}
	
	@Transactional(readOnly = true )
	public Payment findByStatusAndBuyerAndContentFilename( Long payerId, String contentFilename, List<PaymentStatus> paymentStatuses ) {
		return this.paymentRepository.findByStatusAndBuyerAndContentFilename( payerId, contentFilename, paymentStatuses );
	}

	@Transactional(readOnly = true)
	public List<PaymentStatus> getValidPaymentStatuses() {

		// synchronized to avoid concurrent initialization
		synchronized( PaymentService.validPaymentStatuses ) { 
		
			if( PaymentService.validPaymentStatuses.isEmpty() ) {
				PaymentService.validPaymentStatuses.add( PaymentStatus.COMPLETED );
				PaymentService.validPaymentStatuses.add( PaymentStatus.PAYMENT_PROCESSING );
			}
			
			return PaymentService.validPaymentStatuses;
		}
	}
	
	private <T> T deproxy (T obj) {
        if (obj == null) {
			return obj;
		}
        if (obj instanceof HibernateProxy) {
            // Unwrap Proxy;
            //      -- loading, if necessary.
            HibernateProxy proxy = (HibernateProxy) obj;
            LazyInitializer li = proxy.getHibernateLazyInitializer();
            return (T)  li.getImplementation();
        }
        return obj;
    }
	

}
