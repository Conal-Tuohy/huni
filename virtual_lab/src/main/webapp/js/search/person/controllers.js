'use strict';

/* Controllers */

var personSparqlTemplate = 
	 
	'PREFIX cidoc: <http://erlangen-crm.org/current/>'
	+ ' PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>'
	+ ' PREFIX foaf: <http://xmlns.com/foaf/0.1/>'
	+ ' PREFIX skos: <http://www.w3.org/2004/02/skos/core#>'
	+ ' SELECT ?person ?firstName ?lastName ?birthDate ?typeName'
	+ ' WHERE'
	+ ' {'
	+     '?person a cidoc:E21_Person .'
	+     '?person foaf:firstName ?firstName .'
	+     '?person foaf:lastName ?lastName .'
	+     'FILTER(?lastName = "{{surName}}") .'
	+     'OPTIONAL {?person cidoc:P98i_was_born / cidoc:P4_has_time-span / rdf:value ?birthDate}'
	+     'OPTIONAL {?person cidoc:P2_has_type / skos:prefLabel ?typeName}'
	+ ' }'
	;

//------------------------------------

function PersonSearchCtrl($scope, $routeParams, SparqlSearch) {

	$scope.peopleByFamilyName = function(surName) {
    	var sparqlStr = personSparqlTemplate.replace(/{{surName}}/, surName);		
		$scope.response = SparqlSearch.query({'query': sparqlStr, 'output' : 'json'});
	};
}

//PersonSearchCtrl.$inject = ['$scope', '$routeParams', 'PersonSearch'];


