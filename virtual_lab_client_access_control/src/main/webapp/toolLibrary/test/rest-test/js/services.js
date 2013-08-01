'use strict';

/* Services */

angular.module('restServices0', ['ngResource']).
    factory('Page0', function($resource){
//    	return $resource('phones/:phoneId.json', {}, {
//    	    query: {method:'GET', params:{phoneId:'phones'}, isArray:true}
//    	  });
    	return $resource('/virtual_lab/rest/historyitems/:historyItemId');
});

angular.module('restServices1', ['ngResource']).
    factory('Page1', function($resource){
    	  return null;
});

angular.module('restServices2', ['ngResource']).
    factory('Page2', function($resource){
    	  return null;
});

angular.module('restServices3', ['ngResource']).
    factory('Page3', function($resource){
    	  return null;
});
