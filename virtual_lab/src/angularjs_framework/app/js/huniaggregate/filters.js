'use strict';

/* Filters */

angular.module('hexEncodeModule', []).
	filter('hexEncode', function() {
		return function(input) {
			var output = encodeURIComponent(input);
			return output.replace('.', '%2E');
	};
});
