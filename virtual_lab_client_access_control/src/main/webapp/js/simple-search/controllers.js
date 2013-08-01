
function DocumentStatisticsCtrl($rootScope, $scope, $loc, solrSearchService) {

	$scope.$on('facetResultsUpdated', function() {
        var facets = solrSearchService.getFacetResults();
        //console.log(facets);
        $scope.providers = facets.prov_site_long;
        $scope.nproviders = $scope.providers.length;
        $scope.entity_types = facets.type;
        $scope.nentity_types = $scope.entity_types.length;
    });

    $scope.$on('totalDocumentsUpdated', function() {
        $scope.ndocuments = solrSearchService.getTotalDocuments();
    });

    // init - get list and total, available document count
    $scope.init = function() {
        solrSearchService.faceton(['prov_site_long', 'type']);        
        solrSearchService.documentsInIndex();
    };
}
DocumentStatisticsCtrl.$inject = ['$rootScope', '$scope', '$location', 'SolrSearchService'];

/* 
 *   Controller for keyword entry box on landing page. 
*/
function SimpleSearchController($rootScope, $scope, $loc, solrSearchService, huniOntology) {

    // search
    $scope.search = function() {

        var search_request = {
            start: '0',
            result_fields: '*'
        };

        //console.log("User search: " + $scope.query);
        if ($scope.query === undefined) {
            // the user didn't type anything so search *
            $scope.query = '*';
        };

        // when searching, the user has no concept of entity type available
        //  so, we have to effectively search all relevant fields for
        //  every entity type... /sigh
        //  
        //  Thankfully - copyfield gives us only two to look in
        var q = [];
        q.push({field: 'text', data: $scope.query});
        q.push({field: 'text_rev', data: $scope.query});
        search_request.query = [] 
        search_request.query.push(q);
        
        // and finally, put in the query string;
        search_request.user_query = $scope.query;

        //console.log("Search query:");
        //console.log(search_request);
        solrSearchService.doit(search_request);
        
        // send us to the new view then do the search
        $loc.path('/results');
    }
}

SimpleSearchController.$inject = ['$rootScope', '$scope', '$location', 'SolrSearchService', 'HuniOntology'];

/* 
 *   Controller for search page including a keyword entry box search results 
*/
function ResultsController($scope, $loc, solrSearchService, huniOntology) {

    // initialise the controller
    $scope.init = function() {
        // initialisation
        $scope.providers_search = [];
        $scope.types_search = [];
        $scope.applied_type_filters = [];
        $scope.applied_provider_filters = [];

        $scope.results = solrSearchService.getResultSet();
        $scope.query = solrSearchService.getQuery();

        // this is effectively a page reload
        if ($scope.query === undefined) {
            $loc.path('/#/search');
        }

        // the list of fields to query
        $scope.query_fields = [ 'text', 'text_rev' ];

        try {
            // figure out the facet counts of things
            constructQuery();
        } catch (TypeError) {
            $loc.path('/#/search');
        }
    };

    // Handle search result updates
    $scope.$on('searchResultsUpdated', function() {
        $scope.results = solrSearchService.getResultSet();
        $scope.query = solrSearchService.getQuery();
        //console.log($scope.search);
    });

    // Handle facet updates
    $scope.$on('facetResultsUpdated', function() {
        var facets = solrSearchService.getFacetResults();
        //console.log(facets);

        // filter out the applied types
        var t = setSelected($scope.applied_type_filters, facets.type);
        $scope.types = t;
        var p = setSelected($scope.applied_provider_filters, facets.prov_site_long);
        $scope.providers = p;
    });

    /*
     * If a filter has been applied, set it's selected flag to true
     *
     * For example: 
     * $scope.applied_type_filters = [ 'Artefact' ]
     *
     * For facets.type.facet === 'Artefact'
     *   set facets.type.selected === true
     *
     */
    var setSelected = function(applied_filters, data) {
        // with no filters applied, return the original set
        if (applied_filters == undefined || applied_filters.length == 0) {
            return data.sort()
        }

        // see if any of the facet results are in the applied list
        // if so - set their selected flag to true
        for (var i = 0 ; i < data.length ; i ++) {
            if (applied_filters.indexOf(data[i].facet) !== -1) {
                data[i].selected = true;
            }
        }
        return data.sort();
    }


    // get next page of results
    $scope.NextPage = function() {
        $scope.query.start = solrSearchService.NextPage($scope.results.start, $scope.results.total);
        //console.log("next: " + $scope.query.start);
        solrSearchService.doit($scope.query);
    };
    
    // get previous page of results
    $scope.PreviousPage = function() {
        $scope.query.start = solrSearchService.PreviousPage($scope.results.start);
        //console.log("previous: " + $scope.query.start);
        solrSearchService.doit($scope.query);
    };

    $scope.search = function() {
        //console.log('Results controller, search method');
        constructQuery();
    }

    // Add / remove a type filter from the result set
    //  filter needs to be: { field: 'foo', data: 'bar'' }
    $scope.typeFilter = function(filter) {
        //console.log('Results controller, update method');

        // update the result set
        $scope.update($scope.types_search, $scope.applied_type_filters, filter);
    }

    // Add / remove a data provider filter from the result set
    //  filter needs to be: { field: 'foo', data: 'bar'' }
    $scope.providerFilter = function(filter) {
        //console.log('Results controller, providerFilter method');

        // update the result set
        $scope.update($scope.providers_search, $scope.applied_provider_filters, filter);
    }

    $scope.update = function(which, applied, filter) {
        if (filter.value === true) {
            // add the provider if not already there
            which = pushStack(which, filter.data);
            applied = pushStack(applied, filter.data);
        } else {
            // remove the provider if it's in the list
            which = popStack(which, filter.data);
            applied = popStack(applied, filter.data);
        }
        constructQuery();
    }

    var pushStack = function(stack, data) {
        if (stack.indexOf(data) === -1) {
            stack.push(data);
        }
        return stack;
    }
    var popStack = function(stack, data) {
        var field_pos = stack.indexOf(data);
        if (field_pos !== -1) {
            stack.splice(field_pos,1)
        }
        return stack;
    }

    var constructQuery = function() {
        // if user_query comes in blank - set it to *
        if ($scope.query.user_query === '') { 
            $scope.query.user_query = '*';
        }
        if ($scope.query.user_query === undefined) {
            $scope.query.user_query = '*'
        }

        // our query placeholder as we're putting it all together
        var query = [];
        
        // sort out any type filters
        if ($scope.types_search.length > 0) {
            var new_query = [];
            for (var i = 0 ; i < $scope.types_search.length ; i++) {
                new_query.push({ field: 'type', data: $scope.types_search[i] });
            }
            query.push(new_query);
        }

        // sort out any data provider filters
        if ($scope.providers_search.length > 0) {
            var new_query = [];
            for (var i = 0 ; i < $scope.providers_search.length ; i++) {
                new_query.push({ field: 'prov_site_long', data: $scope.providers_search[i] });
            }
            query.push(new_query);
        }

        // sort out the query fields
        var new_query = [];
        for (var n = 0; n < $scope.query_fields.length; n++) {
            var fieldname = $scope.query_fields[n];
            new_query.push({ field: fieldname, data: $scope.query.user_query });
        }   
        query.push(new_query);

        // update the search results
        $scope.query.query = query;
        $scope.query.start = 0;
        solrSearchService.doit($scope.query);

        var query = solrSearchService.getQuery();
        q = solrSearchService.AssembleQuery(query);
        solrSearchService.faceton(['prov_site_long', 'type'], q);
    };

}
ResultsController.$inject = ['$scope', '$location', 'SolrSearchService', 'HuniOntology'];
