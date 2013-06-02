'use strict';

/* App Module */

var personSearch = angular.module('huniaggregate', [
	'sparqlServices',
	'pagination']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/familyname-search', {templateUrl: 'partials/huniaggregate/people/familyname.html', controller: PersonSearchCtrl}).
      otherwise({redirectTo: '/familyname-search'});
}]);
