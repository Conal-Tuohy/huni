'use strict';

// Path to SPARQL end-point. Global since its needed elsewhere.
var serviceURL = 'http://corbicula.huni.net.au/dataset/query';

/* Services */

angular.module('sparqlServices', ['ngResource']).
    factory('SparqlSearch', function($resource){
  	  return $resource(serviceURL, {}, {
		    query: {method:'GET', params:{}, isArray:false}
		  });
});

