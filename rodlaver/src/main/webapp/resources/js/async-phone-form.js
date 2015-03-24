$( document ).ready(function() {
	
	var pleaseWaitModal;
	pleaseWaitModal = pleaseWaitModal || (function () {
	    var pleaseWaitDiv = $( '<div class="modal fade" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false">' +
	    		               '  <div class="modal-dialog">' +
	    		               '    <div class="modal-content">' +
	    		               '      <div class="modal-header">' +
	    		               '        <h4>Processing...</h4></div>' +
	    		               '      </div' +
	    		               '      <div class="modal-body">' +
	    		               '        <div class="progress progress-striped active" role="progressbar">' +
	    		               '          <div class="progress-bar progress-bar-success" style="width:100%;"></div>' +
	    		               '        </div>' +
	    		               '      </div> '+
	    		               '    </div>' +
	    		               '  </div>' +
	    		               '</div>');
	    return {
	        showPleaseWait: function() {
	        	$("#mobile-phone-modal").modal('hide');
	            pleaseWaitDiv.modal('show');
	        },
	        hidePleaseWait: function () {
	        	$("#mobile-phone-modal").modal('show');
	            pleaseWaitDiv.modal('hide');
	        },

	    };
	})();
	
	
	$("#submitMobileNumber").on("click", function( event ) {
		
		//pleaseWaitModal.showPleaseWait();
		
		$("#mobileFormErrorHeaderDiv").addClass('hidden');
		$("#mobileNumberErrorHeaderDiv").addClass('hidden');
		$("#mobileNumberErrorDiv").addClass('hidden');
		
		var mobileNumber  =  $("#mobileNumber").val();
	    
		$.ajax({
	        type : "POST",
	        url : "verifyMobilePhone",
	                data : "mobileNumber=" + mobileNumber, 
	                success : function( response ) {
	                	//alert( response );
	                	if(response.status === "valid") {
                			$("#mobileNumberErrorHeaderDiv").addClass('hidden');
                			$("#inputMobileNumberDiv").addClass('hidden');
                			$("#submitMobileNumber").addClass('hidden');
                			
                			$("#inputVerificationCodeDiv").removeClass('hidden');
                			$("#submitVerificationCode").removeClass('hidden');
	                		
	                		//pleaseWaitModal.hidePleaseWait();
	                		
	                	} else if( response.status === "invalid"){
	                		$("#mobileFormErrorHeaderDiv").removeClass('hidden');
	                		$("#mobileNumberErrorHeaderDiv").removeClass('hidden');
	                		$("#inputMobileNumberDiv").removeClass('hidden');
	                		$("#mobileNumberErrorDiv").removeClass('hidden');
	                		$("#submitMobileNumber").removeClass('hidden');
	                		//pleaseWaitModal.hidePleaseWait();
	                	} else {
	                		document.forms.mobilePhoneData.action = "addMobilePhoneFailed";
	                		document.forms.mobilePhoneData.submit();
	                	}
	        },
	        
	        error:function (xhRequest, ErrorText, thrownError) {
	            alert('Error: '  + '  ' + thrownError);
	        }
	    });
	});

	$("#submitVerificationCode").on("click", function( event ) {
		$("#mobileFormErrorHeaderDiv").addClass('hidden');
		$("#verificationCodeErrorHeaderDiv").addClass('hidden');
		$("#verificationCodeErrorDiv").addClass('hidden');
		var verificationCode  =  $("#verificationCode").val();
	    
		$.ajax({
	        type : "POST",
	        url : "postMobilePhoneVerificationCode",
	                data : "verificationCode=" + verificationCode, 
	                success : function(response) {
	                	//alert( response );
	                	if(response.status === "valid"){
	                		document.forms.mobilePhoneData.submit();
	                	} else if( response.status === "invalid"){
	                		$("#mobileFormErrorHeaderDiv").removeClass('hidden');
	                		$("#verificationCodeErrorHeaderDiv").removeClass('hidden');
	                		$("#verificationCodeErrorDiv").removeClass('hidden');
	                	} else {
	                		document.forms.mobilePhoneData.action = "addMobilePhoneFailed";
	                		document.forms.mobilePhoneData.submit();
	                	}
	        },
	        
	        error:function (xhRequest, ErrorText, thrownError) {
	            alert('Error: '  + '  ' + thrownError);
	        }
	    });
		
	} );

	$("#cancelAddMobile").on("click",  function( event ) {
		$("#input-mobile-number-div").removeClass('hidden');

		$("#form-mobile-number-error-div").addClass('hidden');
		$("#form-verification-code-error-div").addClass('hidden');
		$("#mobileNumber").val("");
		$("#mobileNumber-error-div").addClass('hidden');
		$("#input-verification-code-div").addClass('hidden');
		$("#verificationCode").val("");
		$("#verificationCode-error-div").addClass('hidden');
	});
	
	
});