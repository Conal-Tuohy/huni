'use strict';

var baseServiceURL = '/virtual_lab';

//http://corbicula.huni.net.au/dataset/query?query=%0D%0Aprefix+crm%3A+%3Chttp%3A%2F%2Ferlangen-crm.org%2Fcurrent%2F%3E+%0D%0Aprefix+foaf%3A+%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%0D%0Aprefix+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0D%0A%0D%0Aselect+%3Fperson+%3FfirstName+%3FlastName+%3FbirthDate+%3FdeathDate+%3Foccupation%0D%0A%0D%0Awhere+%7B%0D%0A%3Fperson+crm%3AP2_has_type+crm%3AE21_Person+.%0D%0A%3Fperson+crm%3AP2_has_type+%3Foccupation+.%0D%0A%3Fperson+foaf%3AfirstName+%3FfirstName+.%0D%0A%3Fperson+foaf%3AlastName+%3FlastName+.%0D%0A%0D%0A%3Fperson+crm%3AP100i_died_in+%3Fdeath+.%0D%0A%3Fdeath+crm%3AP4_has_time-span+%3FdeathTimespan+.%0D%0A%3FdeathTimespan+rdf%3Avalue+%3FdeathDate+.%0D%0A%0D%0A%3Fperson+crm%3AP98i_was_born+%3Fbirth+.%0D%0A%3Fbirth+crm%3AP4_has_time-span+%3FbirthTimespan+.%0D%0A%3FbirthTimespan+rdf%3Avalue+%3FbirthDate+.%0D%0A%0D%0Afilter+%28contains%28lcase%28str%28%3Foccupation%29%29%2C+lcase%28%27farmer%27%29%29%29%0D%0A%7D%0D%0A%09%09%09&output=xml&stylesheet=%2Fxslt%2Foccupation-sparql-results-to-html.xsl
/* Services */


angular.module('personSearchServices', ['ngResource']).
    factory('PersonSearch', function($resource){
  	  return $resource(baseServiceURL + '', {}, {
		    query: {method:'GET', params:{}, isArray:true}
		  });
});

