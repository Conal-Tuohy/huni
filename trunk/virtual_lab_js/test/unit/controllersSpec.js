'use strict';

// See: http://pivotal.github.io/jasmine/


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
				};
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

describe('Feedback Modal Controller', function() {

	var scope = null;
	var dialog = null;
	var location = null;
	var feedbackStore = null;
	var feedbackService = null;
	var feedbackAccepted = null;
	var contextPath = null;
	var locationValue = null;
	
	beforeEach(function() {
		scope = {};

		dialog = 
			{
				close: function(value) {
					// do nothing;
				}
			}
		spyOn(dialog, 'close');
		
		location = 
			{
				path: function() {
					return locationValue;
				}
			};
		spyOn(location, 'path').andReturn('/some_location');
		
		feedbackStore = 
			{
				setFeedbackAccepted: function( inContextPath, inStatus) {
	            	feedbackAccepted = inStatus;
	            	contextPath = inContextPath
				}
			};
		spyOn(feedbackStore, 'setFeedbackAccepted').andCallThrough();
		
		feedbackService = function()
		{
		};
		feedbackService.prototype.$save = function() {};
		spyOn(feedbackService.prototype, '$save');

		feedbackAccepted = false;
		contextPath = '';

	});


	it('should obtain the feedback context from the path when feedback is accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(true);
		expect(location.path).toHaveBeenCalled();
	});

	it('should call the FeedbackStore service to save the feedback context when feedback is accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(true);
		expect(feedbackStore.setFeedbackAccepted).toHaveBeenCalled();
	});

	it('should store the feedback accepted state when feedback is accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(true);
		expect(feedbackAccepted).toBe(true);
	});

	it('should store the feedback context when feedback is accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(true);
		expect(contextPath).toBe('/some_location');
	});

	it('should save the complete feedback result by RESTful web services call when feedback is accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(true);
		expect(feedbackService.prototype.$save).toHaveBeenCalled();
	});

	it('should close the dialog when feedback is accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(true);
		expect(dialog.close).toHaveBeenCalled();
	});
	

	it('should not obtain the feedback context from the path when feedback is not accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(false);
		expect(location.path).not.toHaveBeenCalled();
	});

	it('should not call the FeedbackStore service to save the feedback context when feedback is not accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(false);
		expect(feedbackStore.setFeedbackAccepted).not.toHaveBeenCalled();
	});

	it('should not store the feedback accepted state when feedback is not accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(false);
		expect(feedbackAccepted).not.toBe(true);
	});

	it('should not store the feedback context when feedback is not accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(false);
		expect(contextPath).not.toBe('/some_location');
	});

	it('should not save the complete feedback result by RESTful web services call when feedback is not accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(false);
		expect(feedbackService.prototype.$save).not.toHaveBeenCalled();
	});

	it('should close the dialog when feedback is not accepted.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.feedback(false);
		expect(dialog.close).toHaveBeenCalled();
	});
	
	it('should obtain the feedback context from the path when context is obtained.', function() {	        
        var ctrl = new FeedbackModalCtrl(scope, dialog, location, feedbackStore, feedbackService);
        scope.context(true);
		expect(location.path).toHaveBeenCalled();
	});


});

describe('Registration Modal Controller', function() {

	var scope = null;
	var dialog = null;
	var registrationService = null;
	
	beforeEach(function() {
		scope = {};

		dialog = 
			{
				close: function(value) {
					// do nothing;
				}
			}
		spyOn(dialog, 'close');
		
		
		registrationService = function()
		{
		};
		registrationService.prototype.$save = function() {};
		spyOn(registrationService.prototype, '$save');

	});


	it('should save the complete registration result by RESTful web services call.', function() {	        
        var ctrl = new RegistrationModalCtrl(scope, dialog, registrationService);
        scope.apply(true);
		expect(registrationService.prototype.$save).toHaveBeenCalled();
	});

	it('should close the dialog when registration is accepted.', function() {	        
        var ctrl = new RegistrationModalCtrl(scope, dialog, registrationService);
        scope.apply(true);
		expect(dialog.close).toHaveBeenCalled();
	});
	
	it('should not save the complete registration result by RESTful web services call when registration is not accepted.', function() {	        
        var ctrl = new RegistrationModalCtrl(scope, dialog, registrationService);
        scope.apply(false);
		expect(registrationService.prototype.$save).not.toHaveBeenCalled();
	});

	it('should close the dialog when registration is not accepted.', function() {	        
        var ctrl = new RegistrationModalCtrl(scope, dialog, registrationService);
        scope.apply(false);
		expect(dialog.close).toHaveBeenCalled();
	});


});
