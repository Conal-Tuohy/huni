'use strict';

/* jasmine specs for controllers go here */

//describe('controllers', function(){
//  beforeEach(module('myApp.controllers'));
//
//
//  it('should ....', inject(function() {
//    //spec body
//  }));
//
//  it('should ....', inject(function() {
//    //spec body
//  }));
//});

describe('Simple Search controllers', function(){
	
	var $scope = {
			$on: function() {
				
			}
	}
	
	var solrSearchService= {
			getFacetResults : function() {
				return {
					prov_site_long: [0, 1, 2, 3],
					type: [0, 1, 2, 3, 4] 
				}
			},
			faceton: function() {
	            $rootScope.$broadcast('facetResultsUpdated');
			}
	}

  beforeEach(function() {
	  // Do nothing
  });

//  it('should call the on facetResultsUpdated event handler and set nproviders to 4', inject(function($rootScope, $controller) {
//	  var scope = $rootScope.$new();
//	  var ctrl = $controller('DocumentStatisticsCtrl', {$scope: scope});
//	  ctrl.init();
//	  expect(scope.nproviders).toBe(4);
//	  //expect(scope.nentity_types).toBe(5);
//  }));

//  it('should ....', inject(function() {
//    //spec body
//  }));
  
});
