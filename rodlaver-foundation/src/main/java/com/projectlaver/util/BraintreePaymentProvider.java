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
package com.projectlaver.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Customer;
import com.braintreegateway.PayPalDetails;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.braintreegateway.ValidationErrorCode;
import com.braintreegateway.ValidationErrors;
import com.ibm.icu.util.Calendar;
import com.projectlaver.domain.Message;
import com.projectlaver.domain.Payment;
import com.projectlaver.domain.User;

public class BraintreePaymentProvider extends AbstractPaymentProvider {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	BraintreeGateway braintreeGateway;
	
	@Override
	public AttemptPaymentDTO initiateCheckout( AttemptPaymentDTO dto ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++initiateCheckout" ));
		}
		
		ClientTokenRequest request = new ClientTokenRequest();
		String clientToken = this.braintreeGateway.clientToken().generate( request );
		
		if( StringUtils.isNotBlank( clientToken )) {
			dto.setProviderClientToken( clientToken );
			
			/**
			 *  Calculate the Gross PayPal Amount -- this is actually just a rounded total 
			 *  of the listing's amount. 
			 */
			BigDecimal grossPaymentAmount = super.getGrossPaymentAmount( dto.getPaymentAmount() );
			dto.setGrossPaymentAmount( grossPaymentAmount );

			/**
			 * Calculate the 'Seller' PayPal Amount -- this is calculated as their actual net amount.
			 */
			BigDecimal netSellerAmount = super.getNetSellerAmount( dto.getPaymentAmount(), dto.getSellerRevenueRatio() );
			dto.setNetSellerAmount( netSellerAmount );
			
			/**
			 * Calculate the net 'Rod Laver' Amount -- this is the difference between the gross payment & the seller's net.
			 */
			dto.setNetRodLaverAmount( grossPaymentAmount.subtract( netSellerAmount ) );
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---initiateCheckout: braintreeClientToken: %s", clientToken ));
		}
		
		return dto;
	}

	/**
	 * Returns a map containing up to two objects: 1) a paymentQueryResultDTO, and optionally 2) an address.
	 * If the paymentQueryResultDTO contains a payment status of CREATED, then the payment is incomplete and the
	 * user should be given the opportunity to checkout again. If the status is anything but CREATED, it's considered
	 * COMPLETE (so need to make sure that alternate flows are implemented through Exceptions or something).
	 */
	@Override
	public Map<String, Object> completeCheckout( InitiatedPaymentDTO initiatedPayment, Payment payment ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++completeCheckout(), initatedPayment: %s", ToStringBuilder.reflectionToString( initiatedPayment ) ) );
		}
		
		// Setup a few local variables 
		String memo = this.getMemo( payment.getPayee().getUsername(), payment.getListing().getTitle(), payment.getListing().getKeyword() );
		User payee = payment.getPayee();
		User payer = payment.getPayer();
		Message message = payment.getMessage();
		Boolean doStoreInVault = initiatedPayment.getDoStoreInVault() != null ? initiatedPayment.getDoStoreInVault() : false; 
		
		TransactionRequest request = new TransactionRequest();
		request
			.amount( payment.getTotalAmount() )
			.serviceFeeAmount( payment.getRodLaverAmount().setScale( 2, RoundingMode.CEILING ) ) // the service fee equals "our" amount
			.merchantAccountId( payee.getId().toString() )
			.paymentMethodNonce( initiatedPayment.getCheckoutToken() )
			.orderId( memo ) 
			.purchaseOrderNumber( message.getId().toString() )
			// creates a new customer
			.customer()
				.id( payer.getId().toString() )
				.email( payer.getEmailAddress() )
				.done()
			.options()
				.submitForSettlement( true ) // cause the money to change hands
				.storeInVault( doStoreInVault ) // store this user's credit card in the vault
				.done();
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Attempting a braintree payment for a NEW customer [id: %d, email: %s] making a payment to seller [id: %d, email: %s]", 
				payer.getId(), payer.getEmailAddress(), payee.getId(), payee.getEmailAddress() ));
		}
		
		Result<Transaction> transactionResult = this.braintreeGateway.transaction().sale( request );
		
		// see if the charge failed
		if( !transactionResult.isSuccess() ) {
			
			/**
			 * See if it errored because we tried to create a customer who has already been 
			 * created.
			 * 
			 * TODO keep explicit record of whether the user has been created with Braintree 
			 * to avoid the multiple network RT's
			 */
			if( this.didErrorOnCustomerIdReuse( transactionResult.getErrors() ) ) {

				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Attempt payment failed because we tried to re-register an existing customer with Braintree. Retrying." );
				}

				
				// retry the charge but reference the existing customer instead of trying to create one
				TransactionRequest retryRequest = new TransactionRequest();
				retryRequest 
					.amount( payment.getTotalAmount() )
					.serviceFeeAmount( payment.getRodLaverAmount().setScale( 2, RoundingMode.CEILING ) ) // the service fee equals "our" amount
					.merchantAccountId( payment.getPayee().getId().toString() )
					.paymentMethodNonce( initiatedPayment.getCheckoutToken() )
					.orderId( memo ) 
					.purchaseOrderNumber( payment.getMessage().getId().toString() )
					// references an existing customer
					.customerId( payment.getPayer().getId().toString() )
					.options()
						.submitForSettlement( true ) // cause the money to change hands
						.storeInVault( doStoreInVault ) // store this user's payment method in the vault
						.done();
				
				transactionResult = this.braintreeGateway.transaction().sale( retryRequest );
			}
				
		}
		
		Map<String, Object> result = new HashMap<String, Object>(); 
		if( transactionResult.isSuccess() ) {
			
			// payment succeeded
			Transaction transaction = transactionResult.getTarget();
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "Payment succeeded, transaction: %s", ToStringBuilder.reflectionToString( transaction ) ) );
			}
			
			// Add the payment details to a PaymentQueryResultDTO
			PaymentQueryResultDTO dto = new PaymentQueryResultDTO();
			dto.setPaymentStatus( PaymentStatus.COMPLETED );
			dto.setPayKey( transaction.getCreditCard().getToken() ); // this becomes the token to make future purchases
			dto.setCorrelationId( transaction.getId() );
			dto.setAuthorizationTransactionId( transaction.getProcessorAuthorizationCode() );
			
			/**
			 * Add the token to the initiatedPayment. This is a hack, but
			 * since PayPal works differently there's only a preapprovalKey
			 * on the initiatedPayment in some cases. To match that expectation,
			 * we copy the token to the initiatedPayment here, which will cause
			 * the logic to "completePreapproval" to run.
			 */
			if( doStoreInVault ) {
				initiatedPayment.setPreapprovalKey( transaction.getCreditCard().getToken() );
			}
			
			result.put( "paymentQueryResultDTO", dto );
			
		} else {
			
			// either invalid or rejected
			if( this.logger.isErrorEnabled() ) {
				this.logger.error( String.format( "Payment failed. Braintree message: %s", transactionResult.getMessage() ) );
			}
			
			initiatedPayment.setPaymentErrorReason( transactionResult.getMessage() );
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "---completeCheckout()" );
		}
		
		return result;
	}

	@Override
	public AttemptPaymentDTO attemptPaymentWithPreapprovalKey( AttemptPaymentDTO dto ) {

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++attemptPaymentWithPreapprovalKey(), dto: %s", ToStringBuilder.reflectionToString( dto ) ) );
		}
		
		this.calculatePaymentAmounts( dto );
		
		TransactionRequest request = new TransactionRequest();
		request
			.amount( dto.getPaymentAmount() )
			.serviceFeeAmount( dto.getNetRodLaverAmount().setScale( 2, RoundingMode.CEILING ) ) // the service fee equals "our" amount
			.merchantAccountId( dto.getSellerId().toString() )
			.paymentMethodToken( dto.getPreapprovalKey() )
			.orderId( dto.getMemo() )
			.purchaseOrderNumber( dto.getReplyId().toString() )
			.options()
				.submitForSettlement( true )
				.done();
		
		Result<Transaction> result = this.braintreeGateway.transaction().sale( request );

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Braintree result: %s", ToStringBuilder.reflectionToString( result ) ) );
		}
		
		if( result.isSuccess() ) {
			
			Transaction transaction = result.getTarget();
			
			// preapproved payment worked
			dto.setPaymentStatus( PaymentStatus.COMPLETED );
			dto.setPayKey( transaction.getCreditCard().getToken() );
			dto.setCorrelationId( transaction.getId() );
			dto.setAuthorizationTransactionId( transaction.getProcessorAuthorizationCode() );
			
		} else {
			
			// preapproved payment failed
			this.logger.error( String.format( "Error making Braintree payment with token: %s, error message is: %s", dto.getPreapprovalKey(), result.getMessage() ) );
			
			// payment attributes
			dto.setPayKey( null );
			dto.setPaymentStatus( PaymentStatus.FAILED );
		}
		

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---attemptPaymentWithPreapprovalKey(), dto: %s", ToStringBuilder.reflectionToString( dto ) ) );
		}

		return dto;
	}

	/**
	 * Sets the preapproval status, type, and start and end dates.
	 */
	@Override
	public Map<String, Object> completePreapproval( String preapprovalKey ) {

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++completePreapproval(), preapprovalKey: %s", preapprovalKey ) );
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put( "isPreapprovalApproved", true );
		result.put( "preapprovalStatus", "ACTIVE" );
		result.put( "preapprovalType", PreapprovalType.BRAINTREE );
		
		// presume that it's good for a year
		Calendar cal = Calendar.getInstance();
		result.put( "startingDate", cal.getTime() );
		cal.add( Calendar.YEAR, 1 );
		result.put( "endingDate", cal.getTime() );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---completePreapproval()" ) );
		}
		
		return result;
	}


	@Override
	public Map<String, String> requestPreapproval( String returnUrl, String cancelUrl) {
		
		throw new RuntimeException( "Method not supported." );
		
	}
	
	/**
	 * Helper methods
	 */
	
	void calculatePaymentAmounts( AttemptPaymentDTO dto ) {
		
		/**
		 *  Calculate the Gross PayPal Amount -- this is actually just a rounded total 
		 *  of the listing's amount. 
		 */
		BigDecimal grossPaymentAmount = super.getGrossPaymentAmount( dto.getPaymentAmount() );
		dto.setGrossPaymentAmount( grossPaymentAmount );

		/**
		 * Calculate the 'Seller' PayPal Amount -- this is calculated as their actual net amount.
		 */
		BigDecimal netSellerAmount = super.getNetSellerAmount( dto.getPaymentAmount(), dto.getSellerRevenueRatio() );
		dto.setNetSellerAmount( netSellerAmount );
		
		/**
		 * Calculate the net 'Rod Laver' Amount -- this is the difference between the gross payment & the seller's net.
		 */
		dto.setNetRodLaverAmount( grossPaymentAmount.subtract( netSellerAmount ) );
		
	}
	
	/**
	 * Looks through the errors to see if it contains the "customer id is in use" error
	 * 
	 * @param validationErrors
	 * @return
	 */
	Boolean didErrorOnCustomerIdReuse( ValidationErrors validationErrors ) {
		
		List<ValidationError> errors = validationErrors.getAllDeepValidationErrors();
		for( ValidationError error : errors ) {
			if( error.getCode().equals( ValidationErrorCode.CUSTOMER_ID_IS_IN_USE ) ) {
				return true;
			}
		}
		
		return false;
	}

}
