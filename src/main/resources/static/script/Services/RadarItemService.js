theApp.service('RadarItemService', function ($resource, $http)
{
    this.createRadarItemForExistingTechnology = function(radarRingId, confidenceFactor, details, technologyId)
    {
        var radarItem = {};

        radarItem.radarRing = radarRingId;
        radarItem.confidenceLevel = confidenceFactor;
        radarItem.assessmentDetails = details;
        radarItem.technologyId = technologyId;

        return radarItem;
    };

    this.addRadarItemExistingTechnology = function(userId, radarInstanceId, radarRingId, confidenceFactor, details, technologyId, successCallback, errorCallback)
    {
        var radarItem = this.createRadarItemForExistingTechnology(radarRingId, confidenceFactor, details, technologyId);
        this.addRadarItem(userId, radarInstanceId, radarItem, successCallback, errorCallback);
    };

    this.createRadarItemForNewTechnology = function(radarRingId, confidenceFactor, details, technologyName, technologyUrl, radarCategoryId)
    {
        var radarItem = {};

        radarItem.radarRing = radarRingId;
        radarItem.confidenceLevel = confidenceFactor;
        radarItem.assessmentDetails = details;
        radarItem.technologyName = technologyName;
        radarItem.radarCategory = radarCategoryId;
        radarItem.url = technologyUrl;

        return radarItem;
    }

    this.addRadarItemNewTechnology = function(userId, radarInstanceId, radarRingId, confidenceFactor, details, technologyName, technologyUrl, radarCategoryId, successCallback, errorCallback)
    {
        var radarItem = this.createRadarItemForNewTechnology(radarRingId, confidenceFactor, details, technologyName, technologyUrl, radarCategoryId);
        this.addRadarItem(userId, radarInstanceId, radarItem, successCallback, errorCallback);
    };

    this.addRadarItem = function(userId, radarInstanceId, radarItem, successCallback, errorCallback)
    {
        $http.post('/api/User/' + userId + '/Radar/' + radarInstanceId + '/Item', radarItem)
            .success(function (data)
            {
                successCallback(data);
            })
            .error(function (data)
            {
                errorCallback(data);
            });
    };

    this.addRadarItems = function(userId, radarInstanceId, radarItems, successCallback, errorCallback)
    {
        var itemsToAdd = {};
        itemsToAdd.radarItems = radarItems;

        $http.post('/api/User/' + userId + '/Radar/' + radarInstanceId + '/Items', itemsToAdd)
            .success(function (data)
            {
                successCallback(data);
            })
            .error(function (data)
            {
                errorCallback(data);
            });
    };

    this.updateRadarItem = function(userId, radarInstanceId, radarRingId, confidenceFactor, details, technologyId, radarInstanceItem, successCallback, errorCallback)
    {
        var radarSaveItem = this.createRadarItemForExistingTechnology(radarRingId, confidenceFactor, details, technologyId);

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

    this.deleteRadarItems = function(userId, radarId, radarItems, successCallback, errorCallback)
    {
        var itemsToDelete = {};
        itemsToDelete.radarItems = radarItems;

        $http.post('/api/User/' + userId + '/Radar/' + radarId + '/Items/Delete', itemsToDelete)
            .success(function (data)
            {
                successCallback(data);
            })
            .error(function (data)
            {
                errorCallback(data);
            });
    }
});

