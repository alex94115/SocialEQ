var hootit = (function() {

	$(function () {
	  var token = $("meta[name='_csrf']").attr("content");
	  var header = $("meta[name='_csrf_header']").attr("content");
	  $(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	  });
	});

	// public functions
    return {
        attachClickHandler : function( element, handler ) {
    		if( element !== null ) {
    			if( element.addEventListener ) {
    				element.addEventListener( "click", handler, false );
    			} else if( element.attachEvent ) {
    				element.attachEvent( "onclick", handler );
    			}
    		}
    	},
    	isChecked : function( element ) {
    		return element.is( ":checked" );
    	},
    	isNumber : function( n ) {
    		return !isNaN(parseFloat(n)) && isFinite(n);
    	},
    	enable : function( elements ) {
    		var i;
    		for( i = 0; i < elements.length; i +=1 ) {
    			elements[i].prop( "disabled", false );
    		}
    	},
    	disable : function( elements ) {
    		var i;
    		for( i = 0; i < elements.length; i +=1 ) {
    			elements[i].prop( "disabled", true );
    		}
    	},
    	hide: function( elements ) {
    		var i;
    		for( i = 0; i < elements.length; i +=1 ) {
    			elements[i].hide( "slow" );
    		}
    	},
    	show: function( elements ) {
    		var i;
    		for( i = 0; i < elements.length; i +=1 ) {
    			elements[i].show( "slow" );
    		}
    	},
    	parseCurrency : function( element ) {
    		var total = parseFloat( element.val() );
    		if( !hootit.isNumber( total ) ) { 
    			total = 0.00;
    		} else {
    			total = total.toFixed(2);
    		}
    		
    		return total;
    	},
    	validateEmail: function( email1, email2 ) {
    		
    		var lastAtPos = email1.lastIndexOf('@');
    	    var lastDotPos = email1.lastIndexOf('.');
    	    return (email1 === email2 && lastAtPos < lastDotPos && lastAtPos > 0 && email1.indexOf('@@') == -1 && lastDotPos > 2 && (email1.length - lastDotPos) > 2);
    	},
    	setIsUserLoggedIn : function( isUserLoggedIn ) {
    		this.isUserLoggedIn = isUserLoggedIn;
    	}
    };
})();