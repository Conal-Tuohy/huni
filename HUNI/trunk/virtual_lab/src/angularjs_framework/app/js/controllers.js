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
  }
}

//ProjectDetailCtrl.$inject = ['$scope', '$routeParams', 'Project'];
