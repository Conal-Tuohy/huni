'use strict';

/* App Module */

angular.module('virtualab', ['virtualabFilters', 'irtualabServices']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/projects', {templateUrl: 'partials/project-list.html',   controller: ProjectListCtrl}).
      when('/projects/:projectId', {templateUrl: 'partials/project-detail.html', controller: ProjectDetailCtrl}).
      otherwise({redirectTo: '/projects'});
}]);
