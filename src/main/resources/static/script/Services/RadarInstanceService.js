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

    this.addRadarRequest = function (userId)
    {
        var retVal = '/api/User/' + userId + '/Radar';
        return retVal;
    }

    this.deleteRadarRequest = function (userId, radarId)
    {
        var retVal = '/api/User/' + userId + '/Radar/' + radarId;
        return retVal;
    };

    this.deleteRadarItemRequest = function (userId, radarId, radarItemId)
    {
        var retVal = '/api/User/' + userId + '/Radar/' + radarId + '/Item/' + radarItemId;
        return retVal;
    };

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

    this.addRadarItem = function(userId, radarInstanceId, radarRingId, confidenceLevel, assessmentDetails, technologyId, successCallback, errorCallback)
    {
        var radarItem = {};

        radarItem.radarRing.id = radarRingId;
        radarItem.confidenceLevel = confidenceLevel;
        radarItem.assessmentDetails = assessmentDetails;
        radarItem.technology.id = technologyId;

        RadarInstanceService.addRadarItem(userId, radarInstanceId, -1, radarItem, successCallback, errorCallback);
    };

    this.addRadarItem = function(userId, radarInstanceId, radarRingId, confidenceLevel, assessmentDetails, technologyName, technologyUrl, radarCategoryId, successCallback, errorCallback)
    {
        var radarItem = {};

        radarItem.radarRing.id = radarRingId;
        radarItem.confidenceLevel = confidenceLevel;
        radarItem.assessmentDetails = assessmentDetails;
        radarItem.technology.name = technologyName;
        radarItem.technology.url = technologyUrl;

        RadarInstanceService.addRadarItem(userId, radarInstanceId, radarCategoryId, radarItem, successCallback, errorCallback);
    };

    this.addRadarItem = function(userId, radarInstanceId, radarCategoryId, radarItem, successCallback, errorCallback)
    {
        var radarSaveItem = {};

        radarSaveItem.radarRing = radarItem.radarRing.id;
        radarSaveItem.confidenceLevel = radarItem.confidenceFactor;
        radarSaveItem.assessmentDetails = radarItem.details;

        if(!this.isNullOrUndefined(radarItem.technology.id))
        {
            radarSaveItem.technologyId = radarItem.technology.id;
        }
        else
        {
            radarSaveItem.technologyName = radarItem.technology.name;
            radarSaveItem.radarCategory = radarCategoryId;
            radarSaveItem.url = radarItem.technology.url;
        }

        $http.post('/api/User/' + userId + '/Radar/' + radarInstanceId + '/Item', radarSaveItem)
            .success(function (data)
            {
                successCallback(data);
            })
            .error(function (data)
            {
                errorCallback(data);
            });
    };

    this.updateRadarItem = function(userId, radarInstanceId, radarRingId, confidence, radarItemDetails, technologyId, radarInstanceItem, successCallback, errorCallback)
    {
        var radarSaveItem = {};

        radarSaveItem.radarRing = radarRingId;
        radarSaveItem.confidenceLevel = confidence;
        radarSaveItem.assessmentDetails = radarItemDetails;
        radarSaveItem.technologyId = technologyId;

        $http.post('/api/User/' + userId + '/Radar/' + radarInstanceId + '/Item/' + radarInstanceItem.id, radarSaveItem)
            .success(function (data)
            {
                successCallback(data);
            })
            .error(function (data)
            {
                errorCallback(data);
            });
    };

    this.deleteRadarItem =  function (userId, radarId, radarItemId, callbackFunction)
    {
        $http.delete('/api/User/' + userId + '/Radar/' + radarId + '/Item/' + radarItemId)
            .then(function (data)
            {
                callbackFunction(userId, radarId);
            });
    };
});
