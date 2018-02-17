theApp.service('RadarInstanceService', function ($resource, $http)
{
    this.radarList = {};
    this.radarRings = {};
    this.radarCategories = {};
    this.radarData = {};

    this.isNullOrUndefined = function(testObject)
    {
        var retVal = false;

        if((testObject === null) ||
            (testObject === undefined))
        {
            retVal = true;
        }

        return retVal;
    };

    this.getPublishedRadarsByUser = function(userId, successCallback)
    {
        $http.get('/api/public/User/' + userId + '/Radars')
            .success(function (data)
            {
                successCallback(data);
            });
    };

    this.getAllRadarsByUser = function(userId, successCallback)
    {
        $http.get('/api/User/' + userId + '/Radars')
            .success(function (data)
            {
                successCallback(data);
            });
    };

    this.getRadarRingsRequest = function ()
    {
        return $resource('/api/radar/rings');
    };

    this.getRadarCategoriesRequest = function ()
    {
        return $resource('/api/radar/categories');
    };

    this.getRadarInstance = function(userId, radarId, callbackFunction)
    {
        var getRequest = $resource('/api/public/User/' + userId + '/Radar/' + radarId);

        getRequest.get(function(data)
        {
            callbackFunction(data);
        });
    }

    this.publishRadar = function(userId, radarInstance, errorCallback)
    {
        var parameters = {};
        parameters.isPublished = radarInstance.isPublished;

        $http.put('/api/User/' + userId + '/Radar/' + radarInstance.id + '/Publish', parameters)
            .error(function (data)
            {
                errorCallback();
            });
    };

    this.lockRadar = function(userId, radarInstance, errorCallback)
    {
        var parameters = {};
        parameters.isLocked = radarInstance.isLocked;

        $http.put('/api/User/' + userId + '/Radar/' + radarInstance.id + '/Lock', parameters)
            .error(function (radarInstance)
            {
                errorCallback();
            });
    };


    this.deleteRadar =  function (userId, radarId, callbackFunction)
    {
        $http.delete('/api/User/' + userId + '/Radar/' + radarId)
            .then(function (data)
            {
                callbackFunction(userId, radarId);
            });
    };
});
