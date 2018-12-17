var theApp = angular.module('theApp', ['ngResource']);

theApp.filter('encodeURIComponent', function () {
    return window.encodeURIComponent;
});

theApp.run(function($rootScope) {
    $rootScope.isNullOrUndefined = function(testObject)
    {
        var retVal = false;

        if((testObject === null) ||
            (testObject === undefined))
            retVal = true;
        }

        return retVal;
    };
});
