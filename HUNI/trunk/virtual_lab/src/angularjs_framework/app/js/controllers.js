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

//------------------------------------

function LandingCtrl($scope, $routeParams, Landing) {
}

//LandingCtrl.$inject = ['$scope', '$routeParams', 'Landing'];

//------------------------------------

function GlobalDataCtrl($scope, $routeParams, GlobalData) {
}

//GlobalDataCtrl.$inject = ['$scope', '$routeParams', 'GlobalData'];

//------------------------------------

function AdvancedSearchCtrl($scope, $routeParams, AdvancedSearch) {
}

//AdvancedSearchCtrl.$inject = ['$scope', '$routeParams', 'AdvancedSearch'];

//------------------------------------

function WorkspaceCtrl($scope, $routeParams, Workspace) {
}

//WorkspaceCtrl.$inject = ['$scope', '$routeParams', 'Workspace'];

//------------------------------------

function DatasetDirectoryCtrl($scope, $routeParams, DatasetDirectory) {
}

//DatasetDirectoryCtrl.$inject = ['$scope', '$routeParams', 'DatasetDirectory'];

//------------------------------------

function ToolCatalogCtrl($scope, $routeParams, ToolCatalog) {
}

//ToolCatalogCtrl.$inject = ['$scope', '$routeParams', 'ToolCatalog'];

//------------------------------------

function ToolManagerCtrl($scope, $routeParams, ToolManager) {
}

//ToolManagerCtrl.$inject = ['$scope', '$routeParams', 'ToolManager'];

//------------------------------------

function UserManagerCtrl($scope, $routeParams, UserManager) {
}

//UserManagerCtrl.$inject = ['$scope', '$routeParams', 'UserManager'];
//------------------------------------

function ProfileEditorCtrl($scope, $routeParams, ProfileEditor) {
}

//ProfileEditorCtrl.$inject = ['$scope', '$routeParams', 'ProfileEditor'];

//------------------------------------

function RegistrationCtrl($scope, $routeParams, Registration) {
}

//RegistrationCtrl.$inject = ['$scope', '$routeParams', 'Registration'];

//------------------------------------

function ContactCtrl($scope, $routeParams, Contact) {
}

//ContactCtrl.$inject = ['$scope', '$routeParams', 'Contact'];

//------------------------------------

function LoginCtrl($scope, $routeParams, Login) {
}

//LoginCtrl.$inject = ['$scope', '$routeParams', 'Login'];

//------------------------------------

function MemberPartnersCtrl($scope, $routeParams, MemberPartners) {
}

//MemberPartnersCtrl.$inject = ['$scope', '$routeParams', 'MemberPartners'];

//------------------------------------

function AboutCtrl($scope, $routeParams, About) {
}

//AboutCtrl.$inject = ['$scope', '$routeParams', 'About'];

//------------------------------------

function SettingsCtrl($scope, $routeParams, Settings) {
}

//SettingsCtrl.$inject = ['$scope', '$routeParams', 'Settings'];

//------------------------------------

function HelpCtrl($scope, $routeParams, Help) {
}

//HelpCtrl.$inject = ['$scope', '$routeParams', 'Help'];
