'use strict';

/* App Module */

var personSearch = angular.module('personsearchbrowse', [
	'sparqlServices']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/familyname-search', {templateUrl: 'partials/search/familyname.html', controller: PersonSearchCtrl}).
      otherwise({redirectTo: '/familyname-search'});
}]);
