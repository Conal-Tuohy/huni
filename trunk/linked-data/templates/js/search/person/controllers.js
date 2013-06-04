'use strict';

/* Controllers */

//------------------------------------

function PersonSearchCtrl($scope, $routeParams, PersonSearch) {

	this.scope = $scope;

	this.scope.peopleSearch = PersonSearch.query();

	this.scope.people = function() {
		return this.peopleSearch;
	};
}

//PersonSearchCtrl.$inject = ['$scope', '$routeParams', 'PersonSearch'];


