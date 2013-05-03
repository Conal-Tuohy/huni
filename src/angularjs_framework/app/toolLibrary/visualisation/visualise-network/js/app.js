'use strict';

/* App Module */

var visualiseNetworkTool = angular.module('visualise-network-tool', [
          'visualiseNetworkToolServices0',
          'visualiseNetworkToolServices1',
          'visualiseNetworkToolServices2'         
          ]).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/page2', {templateUrl: 'partials/page2.html',   controller: Page2Ctrl}).
      when('/page1', {templateUrl: 'partials/page1.html', controller: Page1Ctrl}).
      when('/page0', {templateUrl: 'partials/page0.html', controller: Page0Ctrl}).
     otherwise({redirectTo: '/page0'});
}]);
