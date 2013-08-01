
//http://stackoverflow.com/questions/13762228/confused-about-service-vs-factory
var simpleSearchServices = angular.module('simpleSearchServices', []);

/* 
* HuniOntology
*/

simpleSearchServices.service('HuniOntology', [ '$rootScope', '$http', function($rootScope, $http) {

    var HuniOntology;

    var self = this;

    self.entities = {
        'Artefact': {
            'search': [ 'name', 'abstract' ],
        },
        'Bibliography': {
            'search': [ 'title', 'subtitle', 'description', 'synopsis' ],
        },
        'Concept': {
            'search': [ 'name', 'abstract' ],
        },
        'Company': {
            'search': [ 'name', 'address' ],
        },
        'Corporate Body': {
            'search': [ 'name', 'abstract' ],
        },
        'Event': {
            'search': [ 'name', 'abstract' ],
        },
        'Film': {
            'search': [ 'title', 'description' ],
        },
        'Organisation': {
            'search': [ 'name', 'abstract' ],
        },
        'Person': {
            'search': [ 'given_name', 'family_name', 'occupation', 'abstract' ],
        },
        'Place': {
            'search': [ 'name', 'abstract' ],
        },
        'Production': {
            'search': [ 'release_title', 'alternative_title', 'description', 'synopsis' ],
        },
        'Venue': {
            'search': [ 'name', 'address', 'suburb' ],
        },
        'default': {
            'search': [ 'given_name', 'family_name', 'occupation', 'name', 
                        'abstract', 'address', 'description', 'synopsis',
                        'title', 'release_title'
            ],
        }
    };

    self.getResultFields = function(entity) {
        entity = typeof entity !== 'undefined' ? entity : 'default';
        return self.entities[entity].result;
    }

    self.getQueryFields = function(entity) {
        entity = typeof entity !== 'undefined' ? entity : 'default';
        return self.entities[entity].search;
    }

    self.getEntities = function() {
        var entities = [];
        for (var elem in self.entities) {
            if (elem !== 'default') {
                entities.push(elem);
            };
        }
        return entities;
    }

    return HuniOntology;
}]);

/* 
* SolrSearchService
*/
simpleSearchServices.service('SolrSearchService', [ '$rootScope', '$http', function($rootScope, $http) {

    var SolrSearchService;

    var self = this;

    //var SOLR_BASE_URL = 'http://huni.esrc.unimelb.edu.au/solr/dev';
    var SOLR_BASE_URL = 'http://huni.esrc.unimelb.edu.au/solr/huni';
    
    self.rows = 10;
    self.results = '';

    // return the current result set
    self.getResultSet = function() {
        return self.results;
    }

    // return the current query
    // this will be something like an array of arrays
    self.getQuery = function() {
        return self.query;
    }

    // return the current facet data
    self.getFacetResults = function() {
        return self.facets;
    }

    // get the latest data blob
    self.getEntities = function() {
        return self.entities;
    }

    // get the total number of documents in the index
    self.getTotalDocuments = function() {
        return self.totalDocuments
    }

    /*
     * Assemble the query string - the bit after /select?q=
     *
     * @params: what
     * "what" is expected to be an array of arrays:
     *  elements within an array are 'OR'ed together
     *  whilst the content of the arrays are 'AND'ed together
     *
     *
     */
    self.AssembleQuery = function(what) {
        //console.log('Assemble query:');
        //console.log(what);

        var q = '';
        for (var i = 0 ; i < what.query.length ; i++) {
            terms = what.query[i];

            var qset = '(';
            //console.log(terms);
            for (var j = 0; j < terms.length ; j++) {
                field = terms[j].field;
                user_query = terms[j].data;

                // query needs quoting when not a wildcard so as
                //  to have multi term matches working
                if (user_query !== '*' && (field == 'text' || field == 'text_rev')) {
                    user_query = user_query.replace(/ /g, ' AND ');
                } else if (user_query !== '*') {
                    user_query = '"' + user_query + '"';
                }
                            
                if ( j === 0) {
                    qset += field + ':' + user_query;
                } else {
                    qset += ' OR ' + field + ':' + user_query;
                }

            }
            qset += ')';
            if ( i < (what.query.length -1)) {
                q += qset + ' AND ';
            } else {
                q += qset;
            }
        }
        //console.log('q: ' + q);
        return q;
    };

    /*
     * Perform a search - read the documentation for AssembleQuery 
     * to find out what to pass in
     */
    self.doit = function(what) {
        self.query = what;
        var q = self.AssembleQuery(what);

        var query = 'q=' + encodeURI(q) + '&fl=' + what.result_fields + '&rows=' + self.rows;
        query += '&start=' + what.start + '&wt=json&json.wrf=JSON_CALLBACK';
        q = SOLR_BASE_URL + '/select?' + query;
        console.log('query: ' + q);

        // run the search
        $http.jsonp(q).then(function(data) {
            // save the data
            self.results = {
                'previous': false,
                'next': false,
                'total': data.data.response.numFound,
                'showing': (data.data.response.numFound < self.rows) ? data.data.response.numFound : self.rows,
                'start': (data.data.response.numFound === 0 ) ? 0 : what.start + 1,
                'end': (what.start + self.rows < data.data.response.numFound) ? what.start + self.rows : data.data.response.numFound,
                'docs': data.data.response.docs,
                'view': window.innerHeight - 100
            }
            if (parseInt(self.results.start) === 1) {
                self.results.previous = true;
            }
            if (parseInt(self.results.end) === parseInt(self.results.total)) {
                self.results.next = true;
            }
            //console.log(self.results);
            $rootScope.$broadcast('searchResultsUpdated');
        },
        function(reason) {
        	alert('Failed: ' + reason);
        }
       );
    }

    /*
     * Perform a facet query
     *
     * @params:
     * what: an array of fields to facet on
     * q: the actual query
     *   between the two you get the facets for that query
     */
    self.faceton = function(what, q) {
        q = typeof q !== 'undefined' ? q : '*:*';
        var query = 'q=' + q + '&rows=0&facet=true&facet.limit=-1';
        //console.log('*** facet ' + query);
        var facets;
        for (var i = 0; i < what.length ; i++) {
            query += '&facet.field=' + what[i];
        }

        query += '&start=0&wt=json&json.wrf=JSON_CALLBACK';

        var q = SOLR_BASE_URL + '/select?' + query;
        console.log('facet query: ' + q);

        // run the search
        $http.jsonp(q).then(function(data) {
            // save the data
            var facets = data.data.facet_counts.facet_fields;
            for (var facet in facets) {
                facets[facet] = objectify_results(facets[facet]);
            }
            self.facets = facets;

            $rootScope.$broadcast('facetResultsUpdated');
        });
    }

    self.documentsInIndex = function() {
        var query = 'q=*:*&rows=0&wt=json&json.wrf=JSON_CALLBACK';
        var q = SOLR_BASE_URL + '/select?' + encodeURI(query);
        
        // run the search
        $http.jsonp(q).then(function(data) {
            self.totalDocuments = data.data.response.numFound;
            $rootScope.$broadcast('totalDocumentsUpdated');
        }); 
    }

    self.NextPage = function(start, total) {
        start = start - 1
        if ((start + self.rows) < total) {
            return (start + self.rows);
        } else {
            return start;
        }
    }

    self.PreviousPage = function(start) {
        start = start - 1
        if (start < self.rows) {
            return start;
        } else {
            return (start - self.rows);
        }

    }

    // filter the current result set
    var replaceEmptyWithFalse = function(docs) {
        var massaged_docs = Array();
        for (var doc in docs) { 
            var obj = docs[doc];
            for (var prop in obj) {      
                if (obj[prop] == '') {
                    obj[prop] = false;
                };
            }; 
            massaged_docs.push(obj);
        };
        return massaged_docs;
    };

    var objectify_results = function(what) {
        var i = 0;
        var j = 0;
        var total = what.length;
        var results = new Array();

        for ( i ; i < total; i += 2 ) {
            if (what[i] == '') {
                continue;
            } else if ( what[i] == 'et al.') {
                continue;
            } else {
                results[j] = { facet: what[i], count: what[i+1] };
                j += 1;
            }
        };
        return results.sort();
    };

    return SolrSearchService;
}]);

