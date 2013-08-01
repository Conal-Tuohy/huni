'use strict';

/* Filters */

angular.module('virtualabFilters', []).filter('checkmark', function() {
  return function(input) {
    return input ? '\u2713' : '\u2718';
  };
});

angular.module('hexEncodeModule', []).
filter('hexEncode', function() {
	return function(input) {
		var output = encodeURIComponent(input);
		return output.replace('.', '%2E');
};
});

