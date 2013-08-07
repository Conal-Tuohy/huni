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
  config(['$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {

    var access = routingConfig.accessLevels;

	$routeProvider.
		when(
			'/landing', 
			{templateUrl: 'partials/landing.html', 
			controller: LandingCtrl},
            access: access.public
			).
		when(
			'/projects', {templateUrl: 
			'partials/project-list.html', 
			controller: ProjectListCtrl},
            access: access.anon
			).
		when(
			'/projects/:projectId', 
			{templateUrl: 'partials/project-detail.html', 
			controller: ProjectDetailCtrl},
            access: access.anon
			).
		when(
			'/global-data', 
			{templateUrl: 'partials/global-data.html', 
			controller: GlobalDataCtrl},
            access: access.anon
			).
		when(
			'/advanced-search', 
			{templateUrl: 'partials/advanced-search.html', 
			controller: AdvancedSearchCtrl},
            access: access.anon
			).
		when(
			'/workspace', 
			{templateUrl: 'partials/workspace.html', 
			controller: WorkspaceCtrl},
            access: access.anon
			).
		when(
			'/dataset-directory', 
			{templateUrl: 'partials/dataset-directory.html', 
			controller: DatasetDirectoryCtrl},
            access: access.anon
			).
		when(
			'/huni-aggregate', 
			{templateUrl: 'partials/huni-aggregate.html', 
			controller: HuNIAggregateCtrl},
            access: access.anon
			).
		when(
			'/humanities-datasources', 
			{templateUrl: 'partials/humanities-datasources.html', 
			controller: HumanitiesDataSourcesCtrl},
            access: access.anon
			).
		when(
			'/tool-catalog', 
			{templateUrl: 'partials/tool-catalog.html', 
			controller: ToolCatalogCtrl},
			).
		when(
			'/tool-manager', 
			{templateUrl: 'partials/tool-manager.html', 
			controller: ToolManagerCtrl},
            access: access.anon
			).
		when(
			'/user-manager', 
			{templateUrl: 'partials/user-manager.html', 
			controller: UserManagerCtrl},
            access: access.anon
			).
		when(
			'/profile-editor', 
			{templateUrl: 'partials/profile-editor.html', 
			controller: ProfileEditorCtrl},
            access: access.anon
			).
		when(
			'/registration', 
			{templateUrl: 'partials/registration.html', 
			controller: RegistrationCtrl},
            access: access.anon
			).
		when(
			'/contact', 
			{templateUrl: 'partials/contact.html', 
			controller: ContactCtrl},
            access: access.anon
			).
		when(
			'/login', 
			{templateUrl: 'partials/login.html', 
			controller: LoginCtrl},
            access: access.anon
			).
		when(
			'/members-partners', 
			{templateUrl: 'partials/members-partners.html', 
			controller: MemberPartnersCtrl},
            access: access.anon
			).
		when(
			'/about', 
			{templateUrl: 'partials/about.html', 
			controller: AboutCtrl},
            access: access.anon
			).
		when(
			'/settings', 
			{templateUrl: 'partials/settings.html', 
			controller: SettingsCtrl},
            access: access.anon
			).
		when(
			'/help', 
			{templateUrl: 'partials/help.html', 
			controller: HelpCtrl},
            access: access.anon
			).
		
		when(
			'/familyname-search', 
			{templateUrl: 'partials/huniaggregate/people/familyname.html', 
			controller: PersonSearchCtrl},
            access: access.anon
			).
		when(
			'/person', 
			{templateUrl: 'partials/huniaggregate/people/person.html', 
			controller: PersonRecordCtrl},
            access: access.anon
			).
		when(
			'/group', 
			{templateUrl: 'partials/huniaggregate/groups/group.html', 
			controller: GroupRecordCtrl},
            access: access.anon
			).
		when(
			'/placename-search', 
			{templateUrl: 'partials/huniaggregate/places/placename.html', 
			controller: PlaceSearchCtrl},
            access: access.anon
			).
		when(
			'/objectname-search', 
			{templateUrl: 'partials/huniaggregate/objects/objectname.html', 
			controller: ObjectSearchCtrl},
            access: access.anon
			).
		when(
			'/eventname-search', 
			{templateUrl: 'partials/huniaggregate/events/eventname.html', 
			controller: EventSearchCtrl},
            access: access.anon
			).

	    when(
	    	'/results', 
	    	{templateUrl: 'partials/simple-search/results.html', 
	    	controller: ResultsController},
            access: access.anon
	    	).
	    
	    when(
	    	'/deep-search', 
	    	{templateUrl: 'partials/deep-search/results.html', 
	    	controller: FacetBrowserController},
            access: access.anon
	    	).

	    otherwise({redirectTo: '/landing'});
	    
	    $locationProvider.html5Mode(true);
	    
	    var interceptor = ['$location', '$q', function($location, $q) {
	        function success(response) {
	            return response;
	        }
	
	        function error(response) {
	
	            if(response.status === 401) {
	                $location.path('/login');
	                return $q.reject(response);
	            }
	            else {
	                return $q.reject(response);
	            }
	        }
	
	        return function(promise) {
	            return promise.then(success, error);
	        }
	    }];
	
	    $httpProvider.responseInterceptors.push(interceptor);
}])
    .run(['$rootScope', '$location', 'Auth', function ($rootScope, $location, Auth) {

        $rootScope.$on("$routeChangeStart", function (event, next, current) {
            $rootScope.error = null;
            if (!Auth.authorize(next.access)) {
                if(Auth.isLoggedIn()) $location.path('/');
                else                  $location.path('/login');
            }
        });

    }]);
