'use strict';

var baseServiceURL = '/virtual_lab';
//var baseServiceURL = '/app';

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
  	  return $resource(baseServiceURL + '/projects/directory.json', {}, {
		    query: {method:'GET', params:{}, isArray:true}
		  });
});

angular.module('historyServices', ['ngResource']).
    factory('History', function($resource){
  	  return $resource(baseServiceURL + '/rest/historyitems/:historyItemId', {}, {
		    query: {method:'GET', params:{}, isArray:true}
		  });
});

angular.module('toolcatalogServices', ['ngResource']).
    factory('ToolCatalog', function($resource){
  return null;
});

angular.module('toolkitServices', ['ngResource']).
    factory('ToolKit', function($resource){
	  return $resource(baseServiceURL + '/toolLibrary/catalog.json', {}, {
		    tools: {method:'GET', params:{}, isArray:true}
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

//Path to SPARQL end-point. Global since its needed elsewhere.
var serviceURL = 'http://corbicula.huni.net.au/dataset/query';

angular.module('sparqlServices', ['ngResource']).
    factory('SparqlSearch', function($resource){
  	  return $resource(serviceURL, {}, {
		    query: {method:'GET', params:{}, isArray:false}
		  });
});


