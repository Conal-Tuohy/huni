'use strict';

/* App Module */

var newProjectWizard = angular.module('new_project_wizard', [
          'newlabWizardServices0',
          'newlabWizardServices1',
          'newlabWizardServices2'         
          ]).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/page2', {templateUrl: 'partials/page2.html', controller: Page2Ctrl}).
      when('/page1', {templateUrl: 'partials/page1.html', controller: Page1Ctrl}).
      when('/page0', {templateUrl: 'partials/page0.html', controller: Page0Ctrl}).
     otherwise({redirectTo: '/page0'});
}]);
