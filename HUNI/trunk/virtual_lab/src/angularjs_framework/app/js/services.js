'use strict';

/* Services */

angular.module('virtualabServices', ['ngResource']).
    factory('Project', function($resource){
  return $resource('projects/:projectId.json', {}, {
    query: {method:'GET', params:{projectId:'projects'}, isArray:true}
  });
});

angular.module('opensocialServices', []).
    factory('OpenSocial', function($resource){
  return null;
});

angular.module('landingServices', ['ngResource']).
    factory('Landing', function($resource){
	  return $resource('projects/:projectId.json', {}, {
		    query: {method:'GET', params:{projectId:'projects'}, isArray:true}
		  });
});

angular.module('globaldataServices', []).
    factory('GlobalData', function($resource){
  	  return $resource('projects/:projectId.json', {}, {
		    query: {method:'GET', params:{projectId:'projects'}, isArray:true}
		  });
});

angular.module('advancedsearchServices', []).
    factory('AdvancedSearch', function($resource){
  return null;
});

angular.module('workspaceServices', []).
    factory('Workspace', function($resource){
  return null;
});

angular.module('datasetdirectoryServices', []).
    factory('DatasetDirectory', function($resource){
  return null;
});

angular.module('toolcatalogServices', []).
    factory('ToolCatalog', function($resource){
  return null;
});

angular.module('toolmanagerServices', []).
    factory('ToolManager', function($resource){
  return null;
});

angular.module('usermanagerServices', []).
    factory('UserManager', function($resource){
  return null;
});

angular.module('profileditorServices', []).
    factory('ProfileEditor', function($resource){
  return null;
});

angular.module('registrationServices', []).
    factory('Registration', function($resource){
  return null;
});

angular.module('contactServices', []).
    factory('Contact', function($resource){
  return null;
});

angular.module('loginServices', []).
    factory('Login', function($resource){
  return null;
});

angular.module('memberpartnersServices', []).
    factory('MemberPartners', function($resource){
  return null;
});

angular.module('aboutServices', []).
    factory('About', function($resource){
  return null;
});

angular.module('settingsServices', []).
    factory('Settings', function($resource){
  return null;
});

angular.module('helpServices', []).
    factory('Help', function($resource){
  return null;
});
