$( document ).ready(function() {

	/**
	 * Message Preview Functions 
	 */
	
	var determineWhitespace = function() {
		var whitespace = "";
		if( $("#message").val().length > 0 ) {
			whitespace = " ";
		}
		
		return whitespace;
	};
	
	var updateTweetPreview = function() {
		
		var useDefaultKeyword = !( $("#keyword").val().length > 0 );
		var total = hootit.parseCurrency( $("#price"));
		var whitespace = determineWhitespace();
		
		// cache selectors used more than once
		var $tweetPreview = $("#tweet_preview");
		var $facebookPreview = $("#facebook_preview");
		var $message = $("#message");
		
		var tweetPreviewTokens = [
		     $message.val(),
		     whitespace,
		     "Tweet '",
		     twitterDisplayName,
		     ' '
		];
		
		var facebookPreviewTokens = [
 		     $message.val(),
 		     whitespace,
 		     "Write a post on this page using the hashtag "
 		];		
		
		
		if( !useDefaultKeyword ) {
			tweetPreviewTokens.push( "#" + $("#keyword").val() + "'" );
			facebookPreviewTokens.push( "#" + $("#keyword").val() );
		}
		
		if( hootit.isChecked( $("#isRevenueCampaignFalse") ) ) {
			if( useDefaultKeyword ) {
				tweetPreviewTokens.push( "#YES" );
				facebookPreviewTokens.push( "#YES" );
			}
			tweetPreviewTokens.push( "' to enter" );
			facebookPreviewTokens.push( "' to enter" );
			
		} else if( hootit.isChecked( $("#isRevenueCampaignTrue") ) ) {
			if( useDefaultKeyword ) {
				tweetPreviewTokens.push( "#BUY" );
				facebookPreviewTokens.push( "#BUY" );
			}
			tweetPreviewTokens.push( "' to buy for $" );
			tweetPreviewTokens.push( total );
			
			facebookPreviewTokens.push( " to buy for $" );
			facebookPreviewTokens.push( total );
			
		} else if( hootit.isChecked( $("#donationTypeListing") ) ) {
			if( useDefaultKeyword  ) {
				tweetPreviewTokens.push( "#DONATE" );
				facebookPreviewTokens.push( "#DONATE" );
			}
			tweetPreviewTokens.push( "' to give $" );
			tweetPreviewTokens.push( total );
			
			facebookPreviewTokens.push( " to give $" );
			facebookPreviewTokens.push( total );
		}
		
		tweetPreviewTokens.push( ".\n\nhttps://t.co/abO1cDE3fg" );
		facebookPreviewTokens.push( ".\n\nFull details here: https://socialeq.co/listing/listingDetail/12345" );
		
		// set the tweet preview
		$tweetPreview.text( tweetPreviewTokens.join( "" ) );
		$facebookPreview.text( facebookPreviewTokens.join( "" ) );
		
		// set the remaining characters
		var tweetLength = twttr.txt.getTweetLength( $tweetPreview.val() );
		$("#tweet_length_counter").text( (140 - tweetLength ) );
		
		if ( tweetLength <= 140 ) {
			$("#tweet_length_error").hide();
			hootit.enable( [ $("#submitSale") ] );
		} else {
			$("#tweet_length_error").show();
			hootit.disable( [ $("#submitSale") ] );
		}

		$("#tweet_value").val( $message.val() );
	};
	
	/**
	 * UI Widget Setup Functions 
	 */
	
	var updateForDigitalSellingListing = function( doResetDependents ) {
		hootit.show( [ $("#isRevenueCampaignDiv"), $("#isPhysicalGoodDiv"), $("#isDigitalDownloadDiv"), $("#isLimitedQuantityDiv"), $("#priceDiv"), $("#digitalContentDiv") ] );
		hootit.hide( [ $("#giveawayTypeDiv") ] );
		
		if( hootit.isChecked( $("#isLimitedQuantityFalse") )) {
			hootit.hide( [ $("#quantityDiv") ] );
		} else {
			hootit.show( [ $("#quantityDiv") ] );
		}

		if( doResetDependents ) {
			// by default
			$("#isLimitedQuantityFalse").prop( "checked", true );
		}
		
		updateTweetPreview();
	};
	
	var updateForPhysicalSellingListing = function () {
		hootit.show( [ $("#isRevenueCampaignDiv"), $("#isPhysicalGoodDiv"),  $("#priceDiv"), $("#quantityDiv") ] );
		hootit.hide( [ $("#giveawayTypeDiv"), $("#isDigitalDownloadDiv"), $("#isLimitedQuantityDiv"), $("#digitalContentDiv") ] );
		
		updateTweetPreview();
	};
	
	var updateForOptInCampaignListing = function ( doResetDependents ) {
		hootit.show( [ $("#isRevenueCampaignDiv"), $("#giveawayTypeDiv"), $("#isPhysicalGoodDiv"), $("#quantityDiv"), $("#digitalContentDiv") ] );
		hootit.hide( [  $("#isLimitedQuantityDiv"), $("#priceDiv") ] );
		
		if( $("#isPhysicalGoodTrue").prop( "checked" ) ) {
			hootit.hide( [  $("#isDigitalDownloadDiv") ] );
		} else {
			hootit.show( [  $("#isDigitalDownloadDiv") ] );
		}
		
		$("#price").val( null );
		
		if( doResetDependents ) {
			// by default
			$("#isLimitedQuantityFalse").prop( "checked", true );
		}
		
		
		
		updateTweetPreview();
	};
	
	var updateForInstantWinCampaignListing = function ( doResetDependents ) {
		hootit.show( [ $("#isRevenueCampaignDiv"), $("#giveawayTypeDiv"), $("#isPhysicalGoodDiv") ] );
		hootit.hide ( [ $("#isLimitedQuantityDiv"), $("#quantityDiv"), $("#priceDiv") ] );
		
		if( $("#isPhysicalGoodTrue").prop( "checked" ) ) {
			hootit.hide( [ $("#isDigitalDownloadDiv") ] );
		} else {
			hootit.show( [ $("#isDigitalDownloadDiv") ] );
		}
		
		// by default
		$("#isLimitedQuantityFalse").prop( "checked", true );
		$("#price").val( null );
		$("#quantity").val( null );
		
		updateTweetPreview();
	};

	
	var setupListingWidgets = function( doResetDependents ) {
		
		if( hootit.isChecked( $("#isRevenueCampaignTrue") ) ) {
			
			// selling campaign
			if( hootit.isChecked( $("#isPhysicalGoodTrue") ) ) {
				updateForPhysicalSellingListing( doResetDependents );
			} else {
				updateForDigitalSellingListing( doResetDependents );
			}
			
		} else if( hootit.isChecked( $("#isRevenueCampaignFalse") ) ) {
			// giveaway campaign
			if( hootit.isChecked( $("#isInstantGiveawayTrue") ) ) {
				updateForInstantWinCampaignListing( doResetDependents );
			} else {
				updateForOptInCampaignListing( doResetDependents );
			}
			
		}
	};
	
	/**
	 * UI Change Event Listener Setup 
	 */
	
	// right slider position
	var onSwitches = [
		"#isRevenueCampaignFalse",
		"#isInstantGiveawayTrue",
		"#isLimitedQuantityTrue",
		"#isPhysicalGoodFalse",
		"#isDigitalDownloadFalse",
	];
	
	// attach on change handlers for on switches (don't reset dependents)
	for( var i = 0; i < onSwitches.length; i++ ) {
		$(onSwitches[i]).on( "change", function( event ) {
			setupListingWidgets( false );
		});
	}
	
	// left slider position
	var offSwitches = [
		"#isRevenueCampaignTrue",
		"#isInstantGiveawayFalse",
		"#isLimitedQuantityFalse",
		"#isPhysicalGoodTrue",
		"#isDigitalDownloadTrue",
	];

	// attach on change handlers for on switches (do reset dependents)
	for( var i = 0; i < offSwitches.length; i++ ) {
		$(offSwitches[i]).on( "change", function( event ) {
			setupListingWidgets( true );
		});
	}
	
	$("#continue").on("click", function( event) {
		if( $("#isPhysicalGoodFalse").is( ":checked" ) && $("#isDigitalDownloadTrue").is( ":checked" ) ) {
			$("#digitalContentDiv").show();
		} else {
			$("#digitalContentDiv").hide();
		}
		
		$("#digitalContentModal").modal( "show" );
		return false;
		
	});
	
	$("#message").keyup( updateTweetPreview );
	$("#price").keyup( updateTweetPreview );
	$("#keyword").keyup( updateTweetPreview );
	
	// Initialize the toggle switches
	$(".Switch").on("click", function() {
		$(this).toggleClass("On").toggleClass("Off");
	});
	
	$("input[name='publishTo']").on("change", function( event ) {
		if( $("#publishToTwitter").is(":checked") ) {
			$("#tweetPreviewDiv").show( "slow" );
		} else {
			$("#tweetPreviewDiv").hide( "slow" );
		}

		if( $("#publishToFacebook").is(":checked") ) {
			$("#facebookPreviewDiv").show( "slow" );
		} else {
			$("#facebookPreviewDiv").hide( "slow" );
		}
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
		"#publishToPopover",			"#listingTypePopover",			"#giveawayTypePopover",
		"#isFundraiserPopover",			"#isFundraiserRewardPopover", 	"#goodTypePopover",
		"#digitalTypePopover",			"#isLimitedQuantityPopover",	"#listingTitlePopover",			
		"#listinglongDescriptionPopover", "#listingPricePopover",		"#listingQuantityPopover",		
		"#listingKeywordPopover",		"#listingMessagePopover",		"#digitalContentPopover",		
		"#imagePreviewPopover",			"#listingBackgroundImagePopover", "#termsAndConditionsPopover"
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
	
	setupListingWidgets();
	
	if( !hasNewListingFieldErrors && hasNewListingUploadErrors ) {
		$("#digitalContentModal").modal( "show" );
	}

	
});