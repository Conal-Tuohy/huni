'use strict';

var baseServiceURL = '/virtual_lab';
//var baseServiceURL = '/app';

/* Services */

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

angular.module('projectdirectoryServices', ['ngResource']).
    factory('ProjectDirectory', function($resource){
  	  return $resource(baseServiceURL + '/projects/directory.json', {}, {
		    query: {method:'GET', params:{}, isArray:true}
		  });
});

angular.module('virtualabServices', ['ngResource']).
    factory('Project', function($resource){
  return $resource('projects/:projectId.json', {}, {
    query: {method:'GET', params:{projectId:'projects'}, isArray:true}
  });
});

angular.module('historyServices', ['ngResource', 'credentialsServices']).
    factory('History', function($resource, CredentialService){
  	  return $resource(baseServiceURL + '/rest/historyitems/:historyItemId', {}, {
		    query: {method:'GET', params:{}, isArray:true, headers: {Authorization: CredentialService.basic()}}
		  });
});

angular.module('toolLibraryServices', ['ngResource']).
factory('ToolLibrary', function($resource){
	return $resource(baseServiceURL + '/rest/toollibraryitems', {}, {
		list: {method:'GET', params:{}, isArray:true},
		provider: {method:'GET'}
	});
});

//angular.module('toolkitServices', ['ngResource']).
//    factory('ToolKit', function($resource){
//	  return $resource(baseServiceURL + '/toolLibrary/catalog.json', {}, {
//		    tools: {method:'GET', params:{}, isArray:true}
//		  });
//});

angular.module('researcherServices', ['ngResource', 'credentialsServices']).
    factory('Researcher', function($resource, CredentialService){
    	// Need to supply user name to retrieve Researcher details
	  return $resource(baseServiceURL + '/rest/researchers/:items?find=ByUserNameEquals&userName=:userName', {}, {
		    researcher: {method:'GET', params:{items: 'info', userName:''}, headers: {Authorization: CredentialService.basic()}, isArray:false},
		    projects: {method:'GET', params:{items: 'projects', userName:''}, isArray:true, headers: {Authorization: CredentialService.basic()},},
		    createProject: {method:'POST', params:{items: 'projects', userName:''}, isArray:false, headers: {Authorization: CredentialService.basic()},},
		    tools: {method:'GET', params:{items: 'tools', userName:''}, isArray:true, headers: {Authorization: CredentialService.basic()}},
		    notebook: {method:'GET', params:{items: 'notebook', userName:''}, isArray:true, headers: {Authorization: CredentialService.basic()}}
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

angular.module('queryStoreServices', []).
	service('QueryStore', function(){
		
		var queryStr = '*';

        return {
            fetchQuery: function () {
                return queryStr;
            },
            storeQuery: function(valueStr) {
            	queryStr = valueStr;
            }
        };;
});

angular.module('feedbackStatus', []).
	service('FeedbackStatus', function(){
		
		var feedbackAccepted = {};

        return {
            getFeedbackAccepted: function (context) {
                return feedbackAccepted[context];
            },
            setFeedbackAccepted: function(context, isAccepted) {
            	feedbackAccepted[context] = isAccepted;
            }
        };
});

//http://localhost:8080/virtual_lab/rest/feedbackitems
angular.module('feedbackServices', ['ngResource']).
 factory('FeedbackService', function($resource){
	  return $resource(baseServiceURL + '/rest/feedbackitems/:feedbackItemId', {}, {
		    query: {method:'GET', params:{}, isArray:true}
		  });
});

//http://localhost:8080/virtual_lab/rest/registrations
angular.module('registrationServices', ['ngResource']).
factory('RegistrationService', function($resource){
	  return $resource(baseServiceURL + '/rest/registrations/:registrationId', {}, {
		    query: {method:'GET', params:{}, isArray:true}
		  });
});

//http://localhost:8080/virtual_lab/rest/institutions
angular.module('institutionServices', ['ngResource']).
factory('InstitutionService', function($resource){
		var serviceUrl = baseServiceURL + '/rest/institutions/:institutionId';
	  return $resource(serviceUrl, {}, {
		    query: {method:'GET', params:{institutionId: ""}, isArray:true}
		  });
});

angular.module('credentialsServices', ['ngCookies']).
	service('CredentialService', function($cookies){
		
        return {        	
            setUserName: function(user) {
            	$cookies.userName = user;
            },
            getUserName: function() {
            	return $cookies.userName;
            },
            setPassword: function(passwd) {
            	this.password = passwd;
            },
            // Authorization: Basic YWRtaW46YWRtaW4=
            basic: function() {
            	var credentials = this.userName + ":" + this.password;
            	var base64Credentials = window.btoa(credentials);
            	return "Basic " + base64Credentials;
            }
        };
});

angular.module('profileServices', []).
	service('ProfileService', function(){
		
        return {
            getProfile: function () {
                return this.profile;
            },
            setProfile: function(profile) {
            	this.profile = profile;
            }
        };  
});


//This service by name is used to retrieve a user's own profile that they can view and modify.
//However it is also purposed for user authentication in the login dialog box.
//http://localhost:8080/virtual_lab/rest/users
angular.module('userServices', ['ngResource', 'credentialsServices']).
factory('UserService', function($resource, CredentialService){
		var serviceUrl = baseServiceURL + '/rest/users/validate/:userName/:password';
	  return $resource(serviceUrl, {}, {
	    	login: {method:'GET', headers: {Authorization: CredentialService.basic()}},
	    	validateUser: {method:'GET', params:{userName: "", password: ""}, isArray:false}
		  });
});

// Inspect response for debugging purposes.
angular.module('httpInterceptors', []).
factory('HttpInterceptor', function($q){
	   return {
	        response: function (response) {
	            // do something on success
	            if(response.headers()['content-type'] === "application/json; charset=utf-8"){
	                // Validate response if not ok reject
	                var data = examineJSONResponse(response); // assumes this function is available

	                if(!data)
	                    return $q.reject(response);
	            }
	            return response;
	        },
	        responseError: function (response) {
	            // do something on error
	            return $q.reject(response);
	        }
	    };
	}).
config(function ($httpProvider) {
    $httpProvider.interceptors.push('HttpInterceptor');
});

angular.module('dataProviderServices', ['ngResource']).
factory('DataProvider', function($resource){
	return $resource(baseServiceURL + '/dataProviders/:dataProviderId.json', {}, {
		list: {method:'GET', params:{dataProviderId:'data-providers'}, isArray:true},
		provider: {method:'GET'}
	});
});



