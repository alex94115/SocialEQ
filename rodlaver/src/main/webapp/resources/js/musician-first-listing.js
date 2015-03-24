$( document ).ready(function() {
	
	$("#continue").on("click", function( event) {
		$("#digitalContentDiv").show();
		
		$("#digitalContentModal").modal( "show" );
		return false;
	});
	
	/**
	 * Popover Setup 
	 */
	
	var initializationOptions = { data_toggle: "popover",			
									  trigger: "hover", 
									  container: "body",
									  delay: { show: 200, hide: 100 }
								};

	var popoverArray = [
		"#listingTitlePopover",			"#listingPricePopover",			"#listinglongDescriptionPopover",
		"#digitalContentPopover",		"#imagePreviewPopover"			
	];
		
	for( var i = 0; i < popoverArray.length; i++ ) {
		$(popoverArray[i]).popover( initializationOptions );
	}
	
	/**
	 * File Upload Setup 
	 */
	
	$(document).bind('dragover', function (e) {
	    var dropZone = $('#dropzone'),
	        timeout = window.dropZoneTimeout;
	    if (!timeout) {
	        dropZone.addClass('in');
	    } else {
	        clearTimeout(timeout);
	    }
	    var found = false,
	        node = e.target;
	    do {
	        if (node === dropZone[0]) {
	            found = true;
	            break;
	        }
	        node = node.parentNode;
	    } while (node != null);
	    if (found) {
	        dropZone.addClass('hover');
	    } else {
	        dropZone.removeClass('hover');
	    }
	    window.dropZoneTimeout = setTimeout(function () {
	        window.dropZoneTimeout = null;
	        dropZone.removeClass('in hover');
	    }, 100);
	});
	
	// add an event handler for when file uploads complete
	$('#listingForm').bind('fileuploaddone', function (e, data) { 
		console.log( "Fileuploaddone callback." ); 
	});
	

	/**
	 *  Initial UI Setup 
	 */
	
	if( !hasNewListingFieldErrors && hasNewListingUploadErrors ) {
		$("#digitalContentModal").modal( "show" );
	}

	
});