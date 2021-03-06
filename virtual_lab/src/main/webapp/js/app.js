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
                             'projectdirectoryServices',
                             'researcherServices',
                             'historyServices',
                             'toolLibraryServices',
                             'toolmanagerServices',
                             'usermanagerServices',
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
                             'dataProviderServices',
                             'dataProviderFilters',
                             'httpInterceptors',
                             
                             'ui.bootstrap',
                             'ui.select2'

                             ]).
  config(['$routeProvider', function($routeProvider) {
	$routeProvider.
		when('/landing', {templateUrl: 'partials/landing.html', controller: LandingCtrl}).
		when('/global-data', {templateUrl: 'partials/global-data.html', controller: GlobalDataCtrl}).
		when('/advanced-search', {templateUrl: 'partials/advanced-search.html', controller: AdvancedSearchCtrl}).
		when('/workspace', {templateUrl: 'partials/workspace.html', controller: WorkspaceCtrl}).
		when('/huni-aggregate', {templateUrl: 'partials/huni-aggregate.html', controller: HuNIAggregateCtrl}).
		when('/humanities-datasources', {templateUrl: 'partials/humanities-datasources.html', controller: HumanitiesDataSourcesCtrl}).
		when('/tool-manager', {templateUrl: 'partials/tool-manager.html', controller: ToolManagerCtrl}).
		when('/user-manager', {templateUrl: 'partials/user-manager.html', controller: UserManagerCtrl}).
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

		when('/data-providers', {templateUrl: 'partials/data-provider/list.html', controller: DataProviderListCtrl}).
		when('/data-providers/:providerId', {templateUrl: 'partials/data-provider/detail.html', controller: DataProviderDetailCtrl}).

		when('/tool-library', {templateUrl: 'partials/tool-library.html', controller: ToolLibraryCtrl}).

	    when('/results', {templateUrl: 'partials/simple-search/results.html', controller: ResultsController}).
	    
	    when('/deep-search', {templateUrl: 'partials/deep-search/results.html', controller: FacetBrowserController}).

	    otherwise({redirectTo: '/landing'});
}]);

