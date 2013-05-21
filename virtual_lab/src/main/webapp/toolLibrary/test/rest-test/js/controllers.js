'use strict';

/* Controllers */

function HistoryListCtrl($scope, History) {
  $scope.history = History.query();
  $scope.orderProp = 'executionTime';
}

//HistoryListCtrl.$inject = ['$scope', 'History'];



function HistoryDetailCtrl($scope, $routeParams, History) {
  $scope.history = History.get({historyId: $routeParams.historyId}, function(history) {
  });
}

//HistoryDetailCtrl.$inject = ['$scope', '$routeParams', 'History'];

//------------------------------------

function Page0Ctrl($scope, $http, $routeParams, Page0) {
	  
	  $scope.setListText = function() {		  
		  $http.get($scope.queryPath).success(function(data) {
			    $scope.queryResult = data;
			  });
		  };

//	  $scope.setListText = function() {
//		  $scope.queryResult = Page0.query();	  
//	  };
	  
	  $scope.setCreateText = function(postPath, postData) {
		  $http({method: 'POST', url: postPath, data: postData}).success(function(data) {
		    $scope.postResult = data;
		  });
	  };

}

//Page0Ctrl.$inject = ['$scope', '$routeParams', 'Page0'];

//------------------------------------

function Page1Ctrl($scope, $routeParams, Page1) {
}

//Page1Ctrl.$inject = ['$scope', '$routeParams', 'Page1'];

//------------------------------------

function Page2Ctrl($scope, $routeParams, Page2) {
}

//Page2Ctrl.$inject = ['$scope', '$routeParams', 'Page2'];

//------------------------------------

function Page3Ctrl($scope, $routeParams, Page3) {
}

//Page3Ctrl.$inject = ['$scope', '$routeParams', 'Page3'];

