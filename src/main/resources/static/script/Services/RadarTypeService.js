theApp.service('RadarTypeService', function ($resource, $http)
{
    this.getRadarInstanceRadarRingsRequest = function (radarId)
    {
        return $resource('/api/radar/' + radarId + '/rings');
    };

    this.getRadarInstanceRadarCategoriesRequest = function (radarId)
    {
        return $resource('/api/radar/' + radarId + '/categories');
    };

    this.getRadarTypeRadarRingsRequest = function (radarTypeId)
    {
        return $resource('/api/radartype/' + radarTypeId + '/rings');
    };

    this.getRadarTypeRadarCategoriesRequest = function (radarTypeId)
    {
        return $resource('/api/radartype/' + radarTypeId + '/categories');
    };

    this.getRadarTypeRequest = function(radarTypeId)
    {
        return $resource('/api/radartype/' + radarTypeId);
    }
}