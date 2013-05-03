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
                             'helpServices'
                             ]).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/projects', {templateUrl: 'partials/project-list.html',   controller: ProjectListCtrl}).
      when('/projects/:projectId', {templateUrl: 'partials/project-detail.html', controller: ProjectDetailCtrl}).
      when('/landing', {templateUrl: 'partials/landing.html', controller: LandingCtrl}).
      when('/global-data', {templateUrl: 'partials/global_data.html', controller: GlobalDataCtrl}).
      when('/advanced-search', {templateUrl: 'partials/advanced-search.html', controller: AdvancedSearchCtrl}).
      when('/workspace', {templateUrl: 'partials/workspace.html', controller: WorkspaceCtrl}).
      when('/dataset-directory', {templateUrl: 'partials/dataset_directory.html', controller: DatasetDirectoryCtrl}).
      when('/tool-catalog', {templateUrl: 'partials/tool_catalog.html', controller: ToolCatalogCtrl}).
      when('/tool-manager', {templateUrl: 'partials/tool_manager.html', controller: ToolManagerCtrl}).
      when('/user-manager', {templateUrl: 'partials/user_manager.html', controller: UserManagerCtrl}).
      when('/profile-editor', {templateUrl: 'partials/profile_editor.html', controller: ProfileEditorCtrl}).
      when('/registration', {templateUrl: 'partials/registration.html', controller: RegistrationCtrl}).
      when('/contact', {templateUrl: 'partials/contact.html', controller: ContactCtrl}).
      when('/login', {templateUrl: 'partials/login.html', controller: LoginCtrl}).
      when('/members-partners', {templateUrl: 'partials/members_partners.html', controller: MemberPartnersCtrl}).
      when('/about', {templateUrl: 'partials/about.html', controller: AboutCtrl}).
      when('/settings', {templateUrl: 'partials/settings.html', controller: SettingsCtrl}).
      when('/help', {templateUrl: 'partials/help.html', controller: HelpCtrl}).
      otherwise({redirectTo: '/landing'});
}]);
