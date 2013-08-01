/*
  Return True if something passed in 
*/
var simpleSearchFilters = angular.module('simpleSearchFilters', []);

simpleSearchFilters.filter('isEmpty', function() {
    return function(input) {

        if (input === "None") {
            return false;
        }
        if (input === undefined) {
            return false;
        }

        if (input.length > 0) {
            return true;
        } else {
            return false;
        };
    };
});