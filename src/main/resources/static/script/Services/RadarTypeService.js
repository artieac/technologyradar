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

    this.getUserRadarTypesRequest = function(userId, publishedOnly)
    {
        if(publishedOnly==true)
        {
            return $resource('/api/public/User/' + userId + '/RadarTypes');
        }
        else
        {
            return $resource('/api/User/' + userId + '/RadarTypes');
        }
    }
});