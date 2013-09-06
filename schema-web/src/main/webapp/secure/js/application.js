define([ 'jquery', 'lodash', 'backbone' ], 
function($, _, Backbone) {

	window.Graphonomy = {
		user : null,
		baseApiUrl : '/api',
		errorCallback : function(entity, xhr, options) {
			hidePageLoader();
			if (xhr == 'abort') {
				return;
			} else if (xhr.status == 401 || entity.status == 401) {
				console.log('User session invalid. Redirecting to login.');
				window.location = "/";
			} else {
				console.log('Graphonomy.errorCallback');
				noty({
		    		text: 'An error occurred during this request. Please retry.',
		    		type: 'error',
		    		timeout: 5000
		    	});        			
			}
		}
	};

	return {graphonomy : Graphonomy};
});
