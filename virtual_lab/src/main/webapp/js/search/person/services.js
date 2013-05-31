'use strict';

var serviceURL = 'http://corbicula.huni.net.au/dataset/query';

/* Services */


angular.module('sparqlServices', ['ngResource']).
    factory('SparqlSearch', function($resource){
  	  return $resource(serviceURL, {}, {
		    query: {method:'GET', params:{}, isArray:false}
		  });
});

