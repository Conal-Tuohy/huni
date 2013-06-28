'use strict';

/* App Module */

var virtualLab = angular.module('personSearch', [
                             'personSearchServices',
                             ]).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/landing', {templateUrl: 'partials/index.html', controller: LandingCtrl}).
      when('/person-search', {templateUrl: 'partials/search/person_results.html', controller: PersonSearchCtrl}).
      otherwise({redirectTo: '/landing'});
}]);
