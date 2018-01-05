theApp.factory('RadarInstanceServiceFactory', function($resource, $http) {
    return new RadarInstanceService($resource, $http);
});