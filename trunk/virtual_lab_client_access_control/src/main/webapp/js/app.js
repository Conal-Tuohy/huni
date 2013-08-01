'use strict';

/* App Module */

var virtualLab = angular.module('virtualab', [
                             'virtualabFilters', 
                             'virtualabServices',
                             'opensocialServices',
                             'landingServices',
                             'globaldataServices',
                             'advancedsearchServices',
                             'workspaceServices',
                             'datasetdirectoryServices',
                             'projectdirectoryServices',
                             'toolkitServices',
                             'historyServices',
                             'toolcatalogServices',
                             'toolmanagerServices',
                             'usermanagerServices',
                             'profileditorServices',
                             'registrationServices',
                             'contactServices',
                             'loginServices',
                             'memberpartnersServices',
                             'aboutServices',
                             'settingsServices',
                             'helpServices',
                             
                             'sparqlServices',
                             'hexEncodeModule',
                             
                             'simpleSearchServices',
                             'simpleSearchFilters',
                             
                             'queryStoreServices',
                             'feedbackStatus',
                             'feedbackServices',
                             'registrationServices',
                             'institutionServices',
                             'credentialsServices',
                             'profileServices',
                             'userServices',
                             'httpInterceptors',
                             
                             'ui.bootstrap',
                             'ui.select2'

                             ]).
  config(['$routeProvider', function($routeProvider) {
	$routeProvider.
		when('/landing', {templateUrl: 'partials/landing.html', controller: LandingCtrl}).
		when('/projects', {templateUrl: 'partials/project-list.html', controller: ProjectListCtrl}).
		when('/projects/:projectId', {templateUrl: 'partials/project-detail.html', controller: ProjectDetailCtrl}).
		when('/global-data', {templateUrl: 'partials/global-data.html', controller: GlobalDataCtrl}).
		when('/advanced-search', {templateUrl: 'partials/advanced-search.html', controller: AdvancedSearchCtrl}).
		when('/workspace', {templateUrl: 'partials/workspace.html', controller: WorkspaceCtrl}).
		when('/dataset-directory', {templateUrl: 'partials/dataset-directory.html', controller: DatasetDirectoryCtrl}).
		when('/huni-aggregate', {templateUrl: 'partials/huni-aggregate.html', controller: HuNIAggregateCtrl}).
		when('/humanities-datasources', {templateUrl: 'partials/humanities-datasources.html', controller: HumanitiesDataSourcesCtrl}).
		when('/tool-catalog', {templateUrl: 'partials/tool-catalog.html', controller: ToolCatalogCtrl}).
		when('/tool-manager', {templateUrl: 'partials/tool-manager.html', controller: ToolManagerCtrl}).
		when('/user-manager', {templateUrl: 'partials/user-manager.html', controller: UserManagerCtrl}).
		when('/profile-editor', {templateUrl: 'partials/profile-editor.html', controller: ProfileEditorCtrl}).
		when('/registration', {templateUrl: 'partials/registration.html', controller: RegistrationCtrl}).
		when('/contact', {templateUrl: 'partials/contact.html', controller: ContactCtrl}).
		when('/login', {templateUrl: 'partials/login.html', controller: LoginCtrl}).
		when('/members-partners', {templateUrl: 'partials/members-partners.html', controller: MemberPartnersCtrl}).
		when('/about', {templateUrl: 'partials/about.html', controller: AboutCtrl}).
		when('/settings', {templateUrl: 'partials/settings.html', controller: SettingsCtrl}).
		when('/help', {templateUrl: 'partials/help.html', controller: HelpCtrl}).
		
		when('/familyname-search', {templateUrl: 'partials/huniaggregate/people/familyname.html', controller: PersonSearchCtrl}).
		when('/person', {templateUrl: 'partials/huniaggregate/people/person.html', controller: PersonRecordCtrl}).
		when('/group', {templateUrl: 'partials/huniaggregate/groups/group.html', controller: GroupRecordCtrl}).
		when('/placename-search', {templateUrl: 'partials/huniaggregate/places/placename.html', controller: PlaceSearchCtrl}).
		when('/objectname-search', {templateUrl: 'partials/huniaggregate/objects/objectname.html', controller: ObjectSearchCtrl}).
		when('/eventname-search', {templateUrl: 'partials/huniaggregate/events/eventname.html', controller: EventSearchCtrl}).

	    when('/results', {templateUrl: 'partials/simple-search/results.html', controller: ResultsController}).
	    
	    when('/deep-search', {templateUrl: 'partials/deep-search/results.html', controller: FacetBrowserController}).

	    otherwise({redirectTo: '/landing'});
}]);
