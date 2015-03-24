$( document ).ready(function() {

	var errorDivs = ([ "#firstLine-error-div", "#secondLine-error-div", "#thirdLine-error-div", "#city-error-div", "#state-error-div", "#zip-error-div", "#country-error-div" ]);
	
	$("#validateAddressButton").on( "click", function() {
		
		// clear all the errors
		for( var i = 0; i < errorDivs.length; i +=1 ) {
			$(errorDivs[i]).hide();
			$(errorDivs[i]).parent().removeClass("has-error");
		}
		$("#addressNotFound").hide();
		
		if( $('input[name=existingAddressRadio]:checked', '#existingAddress').val() !== undefined ) {
			$("#existingAddress").submit();
			
		} else {
			
			var firstLine  = $("#firstLine").val();
			var secondLine = $("#secondLine").val();
			var thirdLine  = $("#thirdLine").val();
			var city	   = $("#city").val();
			var state	   = $("#state").val();
			var zip        = $("#zip").val();
			var country    = $("#country").find(":selected").val();
			
			
			$.ajax({
			    type : "POST",
			    url : "validateAddressForm.json",
	            data : "firstLine=" + firstLine + 
	                   "&secondLine=" + secondLine +
	                   "&thirdLine=" + thirdLine +
	                   "&city=" + city +
	                   "&state=" + state +
	                   "&zip=" + zip +
	                   "&country=" + country,
	            success : function(response) {
	            	if( response.status === "valid" ) {
	            		$("#addressData").submit();
	            		
	            	} else if( response.status === "addressValidationSuggestion" ) {
	            		console.log( "received a addressValidationSuggestion response." );
	            		$("#inputShippingAddress").hide();
	            		
	            		var userAddress = response.userAddress;
	            		$("#userAddressLabel").append( '<input type="radio" name="addressRadio" id="userInputAddress" value="user" />' );
	            		$("#userInputAddress").after( ' ' + userAddress.compactFormat );

	            		var uspsAddress = response.uspsAddress;
	            		$("#suggestedAddressLabel").append( '<input type="radio" name="addressRadio" id="suggestedAddress" value="suggested" checked="checked" />' );
	            		$("#suggestedAddress").after( ' ' + uspsAddress.compactFormat );

						$("#validateAddressButton").hide();
						$("#submitAddressButton").show();
	            		$("#addressSuggestion").show();
	            		
	            	}
	            	else if( response.status === "invalid" ) {
	            		for ( var i = 0; i < response.errorMessageList.length; i++ ) {
	                        var errorDiv = "#" + response.errorMessageList[i].field + "-error-div";
	                        $(errorDiv).show();
	                        $(errorDiv).parent().addClass("has-error");
	                    }
	            	} else if( response.status === "notfound" ) {
	            		$("#addressNotFound").show();
	            	}
			    },
			    
			    error:function (xhRequest, ErrorText, thrownError) {
			        alert('Error: '  + '  ' + thrownError);
			    }
			});
		}
	});
	
	$("#submitAddressButton").on( "click", function() {
		$("#addressData").submit();
	});

	// hide these initially
	for( var i = 0; i < errorDivs.length; i +=1 ) {
		$(errorDivs[i]).hide( "slow" );
	}
	
	$("#addressSuggestion").hide();
	$("#submitAddressButton").hide();
	$("#addressNotFound").hide();
	
	$("#showInputAddressModal").on("click", function() {
		$("#shipping-address-modal").modal("show");
	});
	
	var uagent = navigator.userAgent;
	var isDownloadEnabled = uagent.match(/iPhone|iPod|iPad|Android|Blackberry.*WebKit/i) ? false : true;
	
	if( isDownloadEnabled ) {
		$("#fileDownloadButton").on("click", function( event ) {
			event.preventDefault();
			$(".multi-download").multiDownload( { delay: 3000 } );
		});
	} else {
		$("#fileDownloadButton").on("click", function( event ) {
			$("#downloadExplanationModal").modal("show");
		});
	}
	
});