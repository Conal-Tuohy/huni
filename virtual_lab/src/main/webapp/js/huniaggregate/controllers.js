'use strict';

/* Controllers */

angular.module('pagination', ['ui.bootstrap']);

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
	+     'OPTIONAL {?person cidoc:P4hasTimeSpan / cidoc:P4_has_time-span / rdf:value ?birthDate}'
	+     'OPTIONAL {?person cidoc:P2_has_type / skos:prefLabel ?typeName}'
	+ ' }'
	;

var placeSparqlTemplate = 	 
	'PREFIX cidoc: <http://erlangen-crm.org/current/>'
	+ ' PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>'
	+ ' PREFIX foaf: <http://xmlns.com/foaf/0.1/>'
	+ ' PREFIX skos: <http://www.w3.org/2004/02/skos/core#>'
	+ ' SELECT ?place ?placeName ?establishmentDate ?typeName'
	+ ' WHERE'
	+ ' {'
	+     '?place a cidoc:E53_Place .'
	+     '?place cidoc:E44_Place_Appellation ?placeName .'
	+     'FILTER(?placeName = "{{placeName}}") .'
	+     'OPTIONAL {?place cidoc:P4hasTimeSpan / cidoc:P4_has_time-span / rdf:value ?establishmentDate}'
	+     'OPTIONAL {?place cidoc:P2_has_type / skos:prefLabel ?typeName}'
	+ ' }'
	;

var objectSparqlTemplate = 	 
	'PREFIX cidoc: <http://erlangen-crm.org/current/>'
	+ ' PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>'
	+ ' PREFIX foaf: <http://xmlns.com/foaf/0.1/>'
	+ ' PREFIX skos: <http://www.w3.org/2004/02/skos/core#>'
	+ ' SELECT ?object ?objectName ?creationDate ?location ?typeName'
	+ ' WHERE'
	+ ' {'
	+     '?object a cidoc:E24PhysicalManMadeThing .'
	+     '?object cidoc:E41Appellation ?objectName .'
	+     'FILTER(?objectName = "{{objectName}}") .'
	+     'OPTIONAL {?object cidoc:P4hasTimeSpan / cidoc:P4_has_time-span / rdf:value ?creationDate}'
	+     'OPTIONAL {?object cidoc:P53hasCurrentOrFormerLocation / rdf:value ?location}'
	+     'OPTIONAL {?object cidoc:P2_has_type / skos:prefLabel ?typeName}'
	+ ' }'
	;

var eventSparqlTemplate = 	 
	'PREFIX cidoc: <http://erlangen-crm.org/current/>'
	+ ' PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>'
	+ ' PREFIX foaf: <http://xmlns.com/foaf/0.1/>'
	+ ' PREFIX skos: <http://www.w3.org/2004/02/skos/core#>'
	+ ' SELECT ?event ?eventName ?eventDate ?eventLocation ?typeName'
	+ ' WHERE'
	+ ' {'
	+     '?event a cidoc:E5Event .'
	+     '?event cidoc:E41Appellation ?eventName .'
	+     'FILTER(?eventName = "{{eventName}}") .'
	+     'OPTIONAL {?event cidoc:P4hasTimeSpan / cidoc:P4_has_time-span / rdf:value ?eventDate}'
	+     'OPTIONAL {?event cidoc:P53hasCurrentOrFormerLocation / rdf:value ?location}'
	+     'OPTIONAL {?event cidoc:P2_has_type / skos:prefLabel ?typeName}'
	+ ' }'
	;

var pagingSparqlTemplate =	
	' OFFSET {{queryOffset}} LIMIT {{queryLimit}}';

var queryLimit = 10;
var pageIndex = 0;

//------------------------------------

function PersonSearchCtrl($scope, $routeParams, SparqlSearch) {

	  $scope.noOfPages = 5;
	  $scope.currentPage = pageIndex + 1;
	  $scope.maxSize = 5;

	$scope.peopleByFamilyName = function() {
		var surName = $scope.familyName;
		
		// Populate the export button URL
		var sparqlTemplate = personSparqlTemplate;
		var sparqlStr = sparqlTemplate.replace(/{{surName}}/, surName);
		$scope.exportUrl = serviceURL + '?query=' + encodeURIComponent(sparqlStr) + '&output=csv';
		
		// Perform the request to populate the HTML results table.
		sparqlTemplate = personSparqlTemplate + pagingSparqlTemplate;
		var queryOffset = queryLimit * pageIndex;
		sparqlStr = sparqlTemplate.replace(/{{surName}}/, surName)
			.replace(/{{queryOffset}}/, queryOffset + "")
			.replace(/{{queryLimit}}/, queryLimit + "");
		$scope.response = SparqlSearch.query({'query' : sparqlStr, 'output' : 'json'});
	};
	
	$scope.turnPage = function (pageNo) {
		pageIndex = pageNo - 1;
		$scope.peopleByFamilyName();
	};	  
}

//PersonSearchCtrl.$inject = ['$scope', '$routeParams', 'SparqlSearch'];

//------------------------------------

function PlaceSearchCtrl($scope, $routeParams, SparqlSearch) {

	  $scope.noOfPages = 5;
	  $scope.currentPage = pageIndex + 1;
	  $scope.maxSize = 5;

	$scope.placesByPlaceName = function() {
		var placeName = $scope.placeName;
		
		// Populate the export button URL
		var sparqlTemplate = placeSparqlTemplate;
		var sparqlStr = sparqlTemplate.replace(/{{placeName}}/, placeName);
		$scope.exportUrl = serviceURL + '?query=' + encodeURIComponent(sparqlStr) + '&output=csv';
		
		// Perform the request to populate the HTML results table.
		sparqlTemplate = placeSparqlTemplate + pagingSparqlTemplate;
		var queryOffset = queryLimit * pageIndex;
		sparqlStr = sparqlTemplate.replace(/{{placeName}}/, placeName)
			.replace(/{{queryOffset}}/, queryOffset + "")
			.replace(/{{queryLimit}}/, queryLimit + "");
		$scope.response = SparqlSearch.query({'query' : sparqlStr, 'output' : 'json'});
	};
	
	$scope.turnPage = function (pageNo) {
		pageIndex = pageNo - 1;
		$scope.placesByPlaceName();
	};	  
}

//PlaceSearchCtrl.$inject = ['$scope', '$routeParams', 'SparqlSearch'];

//------------------------------------

function ObjectSearchCtrl($scope, $routeParams, SparqlSearch) {

	  $scope.noOfPages = 5;
	  $scope.currentPage = pageIndex + 1;
	  $scope.maxSize = 5;

	$scope.objectsByObjectName = function() {
		var objectName = $scope.objectName;
		
		// Populate the export button URL
		var sparqlTemplate = objectSparqlTemplate;
		var sparqlStr = sparqlTemplate.replace(/{{objectName}}/, objectName);
		$scope.exportUrl = serviceURL + '?query=' + encodeURIComponent(sparqlStr) + '&output=csv';
		
		// Perform the request to populate the HTML results table.
		sparqlTemplate = objectSparqlTemplate + pagingSparqlTemplate;
		var queryOffset = queryLimit * pageIndex;
		sparqlStr = sparqlTemplate.replace(/{{objectName}}/, objectName)
			.replace(/{{queryOffset}}/, queryOffset + "")
			.replace(/{{queryLimit}}/, queryLimit + "");
		$scope.response = SparqlSearch.query({'query' : sparqlStr, 'output' : 'json'});
	};
	
	$scope.turnPage = function (pageNo) {
		pageIndex = pageNo - 1;
		$scope.objectsByObjectName();
	};	  
}

//ObjectSearchCtrl.$inject = ['$scope', '$routeParams', 'SparqlSearch'];
//------------------------------------

function EventSearchCtrl($scope, $routeParams, SparqlSearch) {

	  $scope.noOfPages = 5;
	  $scope.currentPage = pageIndex + 1;
	  $scope.maxSize = 5;

	$scope.eventsByEventName = function() {
		var eventName = $scope.eventName;
		
		// Populate the export button URL
		var sparqlTemplate = eventSparqlTemplate;
		var sparqlStr = sparqlTemplate.replace(/{{eventName}}/, eventName);
		$scope.exportUrl = serviceURL + '?query=' + encodeURIComponent(sparqlStr) + '&output=csv';
		
		// Perform the request to populate the HTML results table.
		sparqlTemplate = eventSparqlTemplate + pagingSparqlTemplate;
		var queryOffset = queryLimit * pageIndex;
		sparqlStr = sparqlTemplate.replace(/{{eventName}}/, eventName)
			.replace(/{{queryOffset}}/, queryOffset + "")
			.replace(/{{queryLimit}}/, queryLimit + "");
		$scope.response = SparqlSearch.query({'query' : sparqlStr, 'output' : 'json'});
	};
	
	$scope.turnPage = function (pageNo) {
		pageIndex = pageNo - 1;
		$scope.eventsByEventName();
	};	  
}

//EventSearchCtrl.$inject = ['$scope', '$routeParams', 'SparqlSearch'];
