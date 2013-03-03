'use strict';

/* Services */

angular.module('virtualabServices', ['ngResource']).
    factory('Project', function($resource){
  return $resource('projects/:projectId.json', {}, {
    query: {method:'GET', params:{projectId:'projects'}, isArray:true}
  });
});

angular.module('opensocialServices', ['ngResource']).
    factory('OpenSocial', function($resource){
  return null;
});

angular.module('landingServices', ['ngResource']).
    factory('Landing', function($resource){
	  return $resource('projects/:projectId.json', {}, {
		    query: {method:'GET', params:{projectId:'projects'}, isArray:true}
		  });
});

angular.module('globaldataServices', ['ngResource']).
    factory('GlobalData', function($resource){
  	  return $resource('projects/:projectId.json', {}, {
		    query: {method:'GET', params:{projectId:'projects'}, isArray:true}
		  });
});

angular.module('advancedsearchServices', ['ngResource']).
    factory('AdvancedSearch', function($resource){
  return null;
});

angular.module('workspaceServices', ['ngResource']).
    factory('Workspace', function($resource){
  return null;
});

angular.module('datasetdirectoryServices', ['ngResource']).
    factory('DatasetDirectory', function($resource){
  return null;
});

angular.module('projectdirectoryServices', ['ngResource']).
    factory('ProjectDirectory', function($resource){
  	  return $resource('/app/projects/directory.json', {}, {
		    query: {method:'GET', params:{}, isArray:true}
		  });
});

angular.module('toolcatalogServices', ['ngResource']).
    factory('ToolCatalog', function($resource){
  return null;
});

angular.module('toolkitServices', ['ngResource']).
    factory('ToolKit', function($resource){
	  return $resource('/app/toolLibrary/catalog.json', {}, {
		    query: {method:'GET', params:{}, isArray:true}
		  });
});

angular.module('toolmanagerServices', ['ngResource']).
    factory('ToolManager', function($resource){
  return null;
});

angular.module('usermanagerServices', ['ngResource']).
    factory('UserManager', function($resource){
  return null;
});

angular.module('profileditorServices', ['ngResource']).
    factory('ProfileEditor', function($resource){
  return null;
});

angular.module('registrationServices', ['ngResource']).
    factory('Registration', function($resource){
  return null;
});

angular.module('contactServices', ['ngResource']).
    factory('Contact', function($resource){
  return null;
});

angular.module('loginServices', ['ngResource']).
    factory('Login', function($resource){
  return null;
});

angular.module('memberpartnersServices', ['ngResource']).
    factory('MemberPartners', function($resource){
  return null;
});

angular.module('aboutServices', ['ngResource']).
    factory('About', function($resource){
  return null;
});

angular.module('settingsServices', ['ngResource']).
    factory('Settings', function($resource){
  return null;
});

angular.module('helpServices', ['ngResource']).
    factory('Help', function($resource){
  return null;
});
