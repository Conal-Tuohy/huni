'use strict';

/* Controllers */

function ProjectListCtrl($scope, Project) {
	$scope.projects = Project.query();
	$scope.orderProp = 'age';
}

//ProjectListCtrl.$inject = ['$scope', 'Project'];

function ProjectDetailCtrl($scope, $routeParams, Project) {
	$scope.project = Project.get({
		projectId : $routeParams.projectId
	}, function(project) {
		$scope.mainImageUrl = project.images[0];
	});

	$scope.setImage = function(imageUrl) {
		$scope.mainImageUrl = imageUrl;
	};
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
	CommonContainer.init();
}

//WorkspaceCtrl.$inject = ['$scope', '$routeParams', 'Workspace'];

//------------------------------------

function DatasetDirectoryCtrl($scope, $routeParams, DatasetDirectory) {
}

//DatasetDirectoryCtrl.$inject = ['$scope', '$routeParams', 'DatasetDirectory'];

//------------------------------------

function ProjectDirectoryCtrl($scope, $routeParams, ProjectDirectory) {

	this.scope = $scope;

	this.scope.projects = ProjectDirectory.query();

	this.scope.projectDirectory = function() {
		return this.projects;
	};
}

//ProjectDirectoryCtrl.$inject = ['$scope', '$routeParams', 'ProjectDirectory'];

//------------------------------------

function ToolKitCtrl($scope, $routeParams, ToolKit) {

	this.scope = $scope;
	var self = this;
	
	// Arrange a list of tools by categories and call it a tool kit.
	ToolKit.tools(function(tools) {		
		var toolKit = [];
		var count = tools.length;
		if (count > 0) {
			tools.sort(function (item0, item1) {
				var aCategory = item0.categories[0];
				var bCategory = item1.categories[0];
				return ((aCategory < bCategory) ? -1
						: ((aCategory > bCategory) ? 1 : 0));
			});
			var currentCategoryName = tools[0].categories[0];
			var currentCategory = {
				"name" : currentCategoryName,
				"tools" : []
			};
			toolKit.push(currentCategory);
			// Extract the list of unique category
			for ( var index = 0; index < count; index++) {
				var item = tools[index];
				var categoryName = item.categories[0];
				if (categoryName != currentCategoryName) {
					currentCategoryName = categoryName;
					currentCategory = {
						"name" : currentCategoryName,
						"tools" : []
					};
					toolKit.push(currentCategory);
				}
				currentCategory.tools.push({
					"name" : item.name,
					"description" : item.description,
					"url" : item.url
				});
			}
		}
		self.scope.toolKit = toolKit;
	});


	this.scope.selectTool = function(event) {
		var target = event.target;
		var url = target.href;
		var gadgetURL = url.replace('index-3column.html#/', '');
		// Prevent navigation due to link click.
		event.stopPropagation();
		event.preventDefault();

		var siteId = 'gadgetSite';
		var gadgetElement = document.getElementById(siteId);
		var renderParms = {};
		renderParms[osapi.container.RenderParam.WIDTH] = '100%';
	    renderParms['view'] = 'canvas';
		var gadgetSite = CommonContainer.newGadgetSite(gadgetElement);
		CommonContainer.navigateGadget(gadgetSite, gadgetURL, {}, renderParms);
	};
}

//ToolKitCtrl.$inject = ['$scope', '$routeParams', 'ToolKit'];

//------------------------------------

function HistoryCtrl($scope, $routeParams, History) {

	this.scope = $scope;

	this.scope.history = History.query();

	this.scope.actions = function() {
		return this.history;
	};
}

//ProjectDirectoryCtrl.$inject = ['$scope', '$routeParams', 'ProjectDirectory'];

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
