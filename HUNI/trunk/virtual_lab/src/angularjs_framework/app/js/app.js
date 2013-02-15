'use strict';

/* App Module */

angular.module('virtualab', ['virtualabFilters', 'virtualabServices']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/projects', {templateUrl: 'partials/project-list.html',   controller: projectListCtrl}).
      when('/projects/:projectId', {templateUrl: 'partials/project-detail.html', controller: projectDetailCtrl}).
      otherwise({redirectTo: '/projects'});
}]);
