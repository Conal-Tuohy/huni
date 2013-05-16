'use strict';

/* App Module */

var restTestTool = angular.module('rest-test-tool', [
 	'restServices0',
 	'restServices1',
 	'restServices2',        
 	'restServices3'         
 	]).
 	config(['$routeProvider', function($routeProvider) {
 	$routeProvider.
 		when('/page3', {templateUrl: 'partials/page3.html', controller: Page3Ctrl}).
 		when('/page2', {templateUrl: 'partials/page2.html', controller: Page2Ctrl}).
 		when('/page1', {templateUrl: 'partials/page1.html', controller: Page1Ctrl}).
 		when('/page0', {templateUrl: 'partials/page0.html', controller: Page0Ctrl}).
 		otherwise({redirectTo: '/page0'});
}]);
