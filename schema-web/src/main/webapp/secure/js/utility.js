define([ 'jquery', 'lodash', 'backbone' ], 
function($, _, Backbone) {
	
	var showPageLoader = function() {
		$('.modal-loader').show();
	};
	
	var hidePageLoader = function() {
		$('.modal-loader').hide();
	}

	return {
	    showPageLoader : showPageLoader,
	    hidePageLoader : hidePageLoader
	};
});
