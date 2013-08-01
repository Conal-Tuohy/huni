'use strict';

/* Controllers */

function ProjectListCtrl($scope, Project) {
  $scope.projects = Project.query();
  $scope.orderProp = 'age';
}

//ProjectListCtrl.$inject = ['$scope', 'Project'];



function ProjectDetailCtrl($scope, $routeParams, Project) {
  $scope.project = Project.get({projectId: $routeParams.projectId}, function(project) {
    $scope.mainImageUrl = project.images[0];
  });

  $scope.setImage = function(imageUrl) {
    $scope.mainImageUrl = imageUrl;
  };
}

//ProjectDetailCtrl.$inject = ['$scope', '$routeParams', 'Project'];

//------------------------------------

function Page0Ctrl($scope, $routeParams, Page0) {
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

