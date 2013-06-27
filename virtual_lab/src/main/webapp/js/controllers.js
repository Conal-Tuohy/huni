'use strict';

/* Controllers */

angular.module('pagination', ['ui.bootstrap']);
//angular.module('dialog', ['ui.bootstrap']);

function ProjectListCtrl($scope, Project) {
	$scope.projects = Project.query();
	$scope.orderProp = 'age';
}

//ProjectListCtrl.$inject = ['$scope', 'Project'];

function ProjectDetailCtrl($scope, $routeParams, Project) {
	$scope.project = Project.get({
		projectId : $routeParams.projectId
	}, function(project) {
		$scope.mainImageUrl = project.images[0];
	});

	$scope.setImage = function(imageUrl) {
		$scope.mainImageUrl = imageUrl;
	};
}

//ProjectDetailCtrl.$inject = ['$scope', '$routeParams', 'Project'];

//------------------------------------

function LandingCtrl($scope, $routeParams, Landing) {
}

//LandingCtrl.$inject = ['$scope', '$routeParams', 'Landing'];

//------------------------------------

function GlobalDataCtrl($scope, $routeParams, GlobalData) {
}

//GlobalDataCtrl.$inject = ['$scope', '$routeParams', 'GlobalData'];

//------------------------------------

function AdvancedSearchCtrl($scope, $routeParams, AdvancedSearch) {
}

//AdvancedSearchCtrl.$inject = ['$scope', '$routeParams', 'AdvancedSearch'];

//------------------------------------

function WorkspaceCtrl($scope, $routeParams, Workspace) {
	//CommonContainer.init();
}

//WorkspaceCtrl.$inject = ['$scope', '$routeParams', 'Workspace'];

//------------------------------------

function DatasetDirectoryCtrl($scope, $routeParams, DatasetDirectory) {
}

//DatasetDirectoryCtrl.$inject = ['$scope', '$routeParams', 'DatasetDirectory'];

//------------------------------------

function ProjectDirectoryCtrl($scope, $routeParams, ProjectDirectory) {

	this.scope = $scope;

	this.scope.projects = ProjectDirectory.query();

	this.scope.projectDirectory = function() {
		return this.projects;
	};
}

//ProjectDirectoryCtrl.$inject = ['$scope', '$routeParams', 'ProjectDirectory'];

//------------------------------------

function ToolKitCtrl($scope, $routeParams, ToolKit) {

	this.scope = $scope;
	var self = this;
	
	// Arrange a list of tools by categories and call it a tool kit.
	ToolKit.tools(function(tools) {		
		var toolKit = [];
		var count = tools.length;
		if (count > 0) {
			tools.sort(function (item0, item1) {
				var aCategory = item0.categories[0];
				var bCategory = item1.categories[0];
				return ((aCategory < bCategory) ? -1
						: ((aCategory > bCategory) ? 1 : 0));
			});
			var currentCategoryName = tools[0].categories[0];
			var currentCategory = {
				"name" : currentCategoryName,
				"tools" : []
			};
			toolKit.push(currentCategory);
			// Extract the list of unique category
			for ( var index = 0; index < count; index++) {
				var item = tools[index];
				var categoryName = item.categories[0];
				if (categoryName != currentCategoryName) {
					currentCategoryName = categoryName;
					currentCategory = {
						"name" : currentCategoryName,
						"tools" : []
					};
					toolKit.push(currentCategory);
				}
				currentCategory.tools.push({
					"name" : item.name,
					"description" : item.description,
					"url" : item.url
				});
			}
		}
		self.scope.toolKit = toolKit;
	});


//	this.scope.selectTool = function(event) {
//		var target = event.target;
//		var url = target.href;
//		var gadgetURL = url.replace('index-3column.html#/', '');
//		// Prevent navigation due to link click.
//		event.stopPropagation();
//		event.preventDefault();
//
//		var siteId = 'gadgetSite';
//		var gadgetElement = document.getElementById(siteId);
//		var renderParms = {};
//		renderParms[osapi.container.RenderParam.WIDTH] = '100%';
//	    renderParms['view'] = 'canvas';
//		var gadgetSite = CommonContainer.newGadgetSite(gadgetElement);
//		CommonContainer.navigateGadget(gadgetSite, gadgetURL, {}, renderParms);
//	};
}

//ToolKitCtrl.$inject = ['$scope', '$routeParams', 'ToolKit'];

//------------------------------------

function HistoryCtrl($scope, $routeParams, History) {

	this.scope = $scope;

	this.scope.history = History.query();

	this.scope.actions = function() {
		return this.history;
	};
}

//ProjectDirectoryCtrl.$inject = ['$scope', '$routeParams', 'ProjectDirectory'];

//------------------------------------

function ToolCatalogCtrl($scope, $routeParams, ToolCatalog) {
}

//ToolCatalogCtrl.$inject = ['$scope', '$routeParams', 'ToolCatalog'];

//------------------------------------

function ToolManagerCtrl($scope, $routeParams, ToolManager) {
}

//ToolManagerCtrl.$inject = ['$scope', '$routeParams', 'ToolManager'];

//------------------------------------

function UserManagerCtrl($scope, $routeParams, UserManager) {
}

//UserManagerCtrl.$inject = ['$scope', '$routeParams', 'UserManager'];
//------------------------------------

function ProfileEditorCtrl($scope, $routeParams, ProfileEditor) {
}

//ProfileEditorCtrl.$inject = ['$scope', '$routeParams', 'ProfileEditor'];

//------------------------------------

function RegistrationCtrl($scope, $routeParams, Registration) {
}

//RegistrationCtrl.$inject = ['$scope', '$routeParams', 'Registration'];

//------------------------------------

function ContactCtrl($scope, $routeParams, Contact) {
}

//ContactCtrl.$inject = ['$scope', '$routeParams', 'Contact'];

//------------------------------------

function LoginCtrl($scope, $routeParams, Login) {
}

//LoginCtrl.$inject = ['$scope', '$routeParams', 'Login'];

//------------------------------------

function MemberPartnersCtrl($scope, $routeParams, MemberPartners) {
}

//MemberPartnersCtrl.$inject = ['$scope', '$routeParams', 'MemberPartners'];

//------------------------------------

function AboutCtrl($scope, $routeParams, About) {
}

//AboutCtrl.$inject = ['$scope', '$routeParams', 'About'];

//------------------------------------

function HuNIAggregateCtrl($scope, $routeParams) {
}

//HuNIAggregateCtrl.$inject = ['$scope', '$routeParams'];

//------------------------------------

function HumanitiesDataSourcesCtrl($scope, $routeParams) {
}

//HuNIAggregateCtrl.$inject = ['$scope', '$routeParams'];

//------------------------------------

function SettingsCtrl($scope, $routeParams, Settings) {
}

//SettingsCtrl.$inject = ['$scope', '$routeParams', 'Settings'];

//------------------------------------

function HelpCtrl($scope, $routeParams, Help) {
}

//HelpCtrl.$inject = ['$scope', '$routeParams', 'Help'];

//==================================================

// Search controllers

var peopleSparqlTemplate = 	 
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


	var personSparqlTemplate =
		'PREFIX cidoc: <http://erlangen-crm.org/current/>'
		+ ' PREFIX foaf: <http://xmlns.com/foaf/0.1/>'
		+ ' PREFIX skos: <http://www.w3.org/2004/02/skos/core#>'
		+ ' PREFIX oa: <http://www.openannotation.org/ns/>'
		+ ' SELECT ?s ?p ?o ?g ?resource'
		+ ' WHERE'
		+ ' {'
		+ '		BIND (<{{personId}}> AS ?resource) .'
		+ '		{'
		+ '			GRAPH ?g'
		+ '			{ '
		+ '				{'
		+ '					?s ?p ?o.'
		+ '					FILTER( ?s = <{{personId}}> || ?o = <{{personId}}> )'
		+ '				}'
		+ '				UNION { ?s ?p ?o. <{{personId}}> cidoc:P98i_was_born ?s }'
		+ '				UNION { ?s ?p ?o. <{{personId}}> cidoc:P100i_died_in ?s }'
		+ '				UNION { ?s ?p ?o. <{{personId}}> cidoc:P98i_was_born / cidoc:P4_has_time-span ?s }'
		+ '				UNION { ?s ?p ?o. <{{personId}}> cidoc:P100i_died_in / cidoc:P4_has_time-span ?s }'
		+ '				UNION { ?s ?p ?o. <{{personId}}> cidoc:cidoc:P95i_was_formed_by ?s }'
		+ '				UNION { ?s ?p ?o. <{{personId}}> cidoc:P99i_was_dissolved_by ?s }'
		+ '				UNION { ?s ?p ?o. <{{personId}}> cidoc:cidoc:P95i_was_formed_by / cidoc:P4_has_time-span ?s }'
		+ '				UNION { ?s ?p ?o. <{{personId}}> cidoc:P99i_was_dissolved_by / cidoc:P4_has_time-span ?s }'
		+ '			}'
		+ '		}'
		+ '		UNION'
		+ '		{'
		+ ' 		<{{personId}}> cidoc:P2_has_type ?s graph ?g'
		+ ' 		{ ?s ?p ?o }'
		+ ' 	}'
		+ ' }'
		+ ' LIMIT 10000'
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
		var sparqlTemplate = peopleSparqlTemplate;
		var sparqlStr = sparqlTemplate.replace(/{{surName}}/, surName);
		$scope.exportUrl = serviceURL + '?query=' + encodeURIComponent(sparqlStr) + '&output=csv';
		
		// Perform the request to populate the HTML results table.
		sparqlTemplate = peopleSparqlTemplate + pagingSparqlTemplate;
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

function PersonRecordCtrl($scope, $routeParams, SparqlSearch) {

	var sparqlStr = personSparqlTemplate.replace(/{{personId}}/g, $routeParams.personId);
	$scope.response = SparqlSearch.query({'query' : sparqlStr, 'output' : 'json'}, function(response) {	
			var person = {};
			person.occupations = [];
			person.firstNames = [];
			person.primaryTopics = [];
			var tripleCount = response.results.bindings.length;
			for (var tripleIndex0 = 0; tripleIndex0 < tripleCount; tripleIndex0++) {
				var currentTriple0 = response.results.bindings[tripleIndex0];
				// Locate a person identifier
				if (currentTriple0.o.value == 'http://erlangen-crm.org/current/E21_Person') {
					var personSubject = currentTriple0.s.value;
					var originalRecordId = currentTriple0.g.value;
					person.originalRecordId = originalRecordId;
					for (var tripleIndex1 = 0; tripleIndex1 < tripleCount; tripleIndex1++) {
						var currentTriple1 = response.results.bindings[tripleIndex1];
						if (currentTriple1.s.value == personSubject) {
							// Navigate to the first name associated with the identifier
							if (currentTriple1.p.value == 'http://xmlns.com/foaf/0.1/firstName') {
								var firstName = currentTriple1.o.value;
								person.firstNames.push(firstName);
							}
							// Navigate to the last name associated with the identifier
							if (currentTriple1.p.value == 'http://xmlns.com/foaf/0.1/lastName') {
								person.lastName = currentTriple1.o.value;
							}
							// Navigate to the birth date associated with the person identifier							
							if (currentTriple1.p.value == 'http://erlangen-crm.org/current/P98i_was_born') {
								var birthObject = currentTriple1.o.value;
								for (var tripleIndex2 = 0; tripleIndex2 < tripleCount; tripleIndex2++) {
									var currentTriple2 = response.results.bindings[tripleIndex2];
									if (currentTriple2.s.value == birthObject) {
										if (currentTriple2.p.value == 'http://erlangen-crm.org/current/P4_has_time-span') {
											var timeSpanObject = currentTriple2.o.value;
											for (var tripleIndex3 = 0; tripleIndex3 < tripleCount; tripleIndex3++) {
												var currentTriple3 = response.results.bindings[tripleIndex3];
												if (currentTriple3.s.value == timeSpanObject) {
													if (currentTriple3.p.value == 'http://www.w3.org/1999/02/22-rdf-syntax-ns#value') {
														person.birthDate = currentTriple3.o.value;
													}
												}
											}
										}
									}
								}
							}
							if (currentTriple1.p.value == 'http://erlangen-crm.org/current/P100i_died_in') {
								var deathObject = currentTriple1.o.value;
								for (var tripleIndex5 = 0; tripleIndex5 < tripleCount; tripleIndex5++) {
									var currentTriple5 = response.results.bindings[tripleIndex5];
									if (currentTriple5.s.value == deathObject) {
										if (currentTriple5.p.value == 'http://erlangen-crm.org/current/P4_has_time-span') {
											var timeSpanObject = currentTriple5.o.value;
											for (var tripleIndex6 = 0; tripleIndex6 < tripleCount; tripleIndex6++) {
												var currentTriple6 = response.results.bindings[tripleIndex6];
												if (currentTriple6.s.value == timeSpanObject) {
													if (currentTriple6.p.value == 'http://www.w3.org/1999/02/22-rdf-syntax-ns#value') {
														person.deathDate = currentTriple6.o.value;
													}
												}
											}
										}
									}
								}
							}
							// Navigate to the multiple occupations associated with the person identifier
							if (currentTriple1.p.value == 'http://erlangen-crm.org/current/P2_has_type') {
								var occupationObject = currentTriple1.o.value;
								for (var tripleIndex4 = 0; tripleIndex4 < tripleCount; tripleIndex4++) {
									var currentTriple4 = response.results.bindings[tripleIndex4];
									if (currentTriple4.s.value == occupationObject) {
										if (currentTriple4.p.value == 'http://www.w3.org/2004/02/skos/core#prefLabel') {
											var occupation = currentTriple4.o.value;
											person.occupations.push(occupation);
										}
									}
								}
							}
							// Navigate to the books etc. associated with the person identifier
							if (currentTriple1.p.value == 'http://xmlns.com/foaf/0.1/isPrimaryTopicOf') {
								var primaryTopic = currentTriple1.o.value;
								person.primaryTopics.push(primaryTopic);
							}
						}
					}
				}
			}
			response.length = 0;
			response.person = person;
		});	
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

//------------------------------------

function GroupRecordCtrl($scope, $routeParams, Group) {
}

//GroupRecordCtrl.$inject = ['$scope', '$routeParams', 'Group'];

//==================================================

// Modal dialog box controllers

//------------------------------------

//function FeedbackModalCtrl($scope, $location, FeedbackStore) {
//
//	$scope.context = function () {
//		return $location.path();
//	}
//
//	$scope.feedback = function() {
//		FeedbackStore.setFeedbackAccepted($location.path(), true);
//		$('#feedbackModal').modal('hide')
//	}
//}
//
//FeedbackModalCtrl.$inject = ['$scope', '$location', 'FeedbackStore'];
//
////------------------------------------
//
//function FeedbackButtonCtrl($scope, $location, FeedbackStore) {
//
//	$scope.feedbackAccepted = function() {
//		return FeedbackStore.getFeedbackAccepted($location.path());
//	}
//}
//
//FeedbackButtonCtrl.$inject = ['$scope', '$location', 'FeedbackStore'];

function FeedbackButtonCtrl($scope, $dialog, $location, FeedbackStore) {

	$scope.openDialog = function() {
		var dlg = $dialog.dialog({
					backdrop : true,
					keyboard : true,
					backdropClick : true,
					templateUrl :  'partials/feedback-modal.html',
					controller : 'FeedbackModalCtrl'
				});
		dlg.open().then(function(result) {
			if (result) {
				alert('dialog closed with result: ' + result);
			}
		});
	};

	$scope.feedbackAccepted = function() {
		return FeedbackStore.getFeedbackAccepted($location.path());
	}
}

FeedbackButtonCtrl.$inject = [ '$scope', '$dialog', '$location', 'FeedbackStore' ];
//------------------------------------

function FeedbackModalCtrl($scope, dialog, $location, FeedbackStore) {
	
	$scope.rating = 0;
	$scope.comment = '';
	
	$scope.feedback = function(result) {
		if (result) {
			FeedbackStore.setFeedbackAccepted($location.path(), true);
			var feedbackRating = $scope.rating;
		}
		dialog.close(result);
	};

	$scope.context = function() {
		return $location.path();
	}
}

//FeedbackModalCtrl.$inject = [ '$scope', 'dialog', '$location', 'FeedbackStore' ];
//------------------------------------


//==================================================

