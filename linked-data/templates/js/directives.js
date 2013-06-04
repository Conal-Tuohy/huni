'use strict';

/* Directives */

angular.module('toolKitListModule', []).directive('toolKitList', function factory() {
	  var directiveDefinitionObject = {
			    link: function(scope, element, attributes) {
			    },
			    templateUrl: 'partials/tool-kit.html',
			    transclude: true
			  };
	  return directiveDefinitionObject;	  
});