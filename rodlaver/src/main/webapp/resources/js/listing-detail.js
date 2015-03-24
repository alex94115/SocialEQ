$( document ).ready(function() {
	
	if(document.getElementById("preCheckoutButton")) {
		
		// initially, the add to cart button is disabled
		$("#preCheckoutButton").attr( "disabled", true );

		
		// setup event listeners that will enable the add to cart button
		var isProductSelected = false;
		var isQuantitySelected = false; 
		
		$("#selectProduct").on("change", function( event ) {
			isProductSelected = true;
			
			if( isProductSelected && isQuantitySelected ) {
				$("#preCheckoutButton").attr( "disabled", false );
			}
		});
		
		$("#selectQty").on("change", function( event ) {
			isQuantitySelected = true;
			
			if( isProductSelected && isQuantitySelected ) {
				$("#preCheckoutButton").attr( "disabled", false );
			}
		});
		
		// event listener that acts on a click of the add to cart button
		$("#preCheckoutButton").on( "click", function( event ) {
			
			$("#preCheckoutButton").attr( "disabled", true );
			event.preventDefault();
			
			var product   = $("#selectProduct").find(":selected").val();
			var quantity  = $("#selectQty").find(":selected").val();
				
			$.ajax({
				type : "POST",
				url : "continueToCheckout.json",
		        data : "product=" + product + 
		               "&quantity=" + quantity,
		        success : function(response) {
		        	if( response.status === "valid" ) {
		        		
		        		// disable the select product and qty widgets
		        		hootit.disable( [ $("#selectProduct"), $("#selectQty") ] );
		            	
		        		// slowly fade out the "add to cart" button
		        		$("#preCheckoutButton").html( "In Cart" ).delay( 3000 ).fadeOut( 1000 );
		            	
		        		$("#checkoutButton").fadeIn(1000);
		            	
		        		braintree.setup( response.providerClientToken, "dropin", {
		        			container: "dropin",
		      			  	form: "paymentForm",
		      			});
		        		
		        	} else if( response.status === "insufficientQuantity" ) {
		        		alert( "Dang! You asked for a quantity that exceeds what's available right now. Please try a smaller quantity or check back again later." );
		        		
		            } else if( response.status === "invalid" ) {
		            	alert( "Missing required data." );
		            }
				},
				error : function (xhRequest, ErrorText, thrownError) {
					alert('Error: '  + '  ' + thrownError);
				}
			});
		});
		
	} else if(document.getElementById("checkoutButton")) {
		
		$("#checkoutButton").attr( "disabled", true );

		 $.ajax({
			type : "POST",
			url : "continueToCheckout.json",
	        data : "",
	        success : function(response) {
	        	
		        	if( response.status === "valid" ) {
		            	
		        		braintree.setup( response.providerClientToken, "dropin", {
		        			container: "dropin",
		      			  	form: "paymentForm",
		      			});
		        		
		        		//Stripe.setPublishableKey( response.providerClientToken );
//		        		var handler = StripeCheckout.configure({
//		        			key: response.providerClientToken,
//		        			image: "../../resources/images/site/favicon-196x196.png",
//		        			name: "SocialEQ",
//		        			description: response.productDescription,
//		        			amount: response.amountCents,
//		        			email: response.userEmail,
//		        			token: function( token ) {
//		        				// add the token value (token.id) to a form and submit it
//		        				$("input#tokenInput").val( token.id );
//		        				$("#paymentForm").submit();
//		        			}
//		        		});
		        		
//		        		$("a#checkoutButton").on("click", function(e) {
//		        			handler.open();
//		        			e.preventDefault();
//		        		});
		        		
		        		
		        		$("#checkoutButton").attr( "disabled", false );
	            	
		            } else if( response.status === "invalid" ) {
		            	alert( "Missing required data." );
		            } 
			},
			error : function (xhRequest, ErrorText, thrownError) {
				alert('Error: '  + '  ' + thrownError);
			}
		 });
		     
	}

     // initialize twitter widget.js
	 window.twttr = (function (d, s, id) {
	  var t, js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id; js.src= "https://platform.twitter.com/widgets.js";
	  fjs.parentNode.insertBefore(js, fjs);
	  return window.twttr || (t = { _e: [], ready: function (f) { t._e.push(f) } });
	 }(document, "script", "twitter-wjs"));
    
});