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

    this.getPublishedRadarsByUserAndRadarTemplates = function(userId, radarTemplate, successCallback)
    {
        var url =  '/api/public/User/' + userId + '/Radars';

         if(radarTemplate!==undefined)
         {
            url += "?radarTemplateId=" + radarTemplate.id;
         }

        $http.get(url)
            .success(function (data)
            {
                successCallback(data);
            });
    };

    this.getAllRadarsByUserAndRadarTemplates = function(userId, radarTemplate, successCallback)
    {
        var url =  '/api/User/' + userId + '/Radars';

         if(radarTemplate!==undefined)
         {
            url += "?radarTemplateId=" + radarTemplate.id;
         }

        $http.get(url)
            .success(function (data)
            {
                successCallback(data);
            });
    };

    this.getRadarInstance = function(userId, radarId, isAnonymous, callbackFunction)
    {
        var getRequest = {};

        if(isAnonymous==true)
        {
            getRequest = $resource('/api/public/User/' + userId + '/Radar/' + radarId);
        }
        else
        {
            getRequest = $resource('/api/User/' + userId + '/Radar/' + radarId);
        }

        getRequest.get(function(data)
        {
            callbackFunction(data);
        });
    }

    this.getFulLViewUrl = function(userId, radarTemplateId)
    {
        return '/api/public/User/' + userId + '/RadarTemplate/' + radarTemplateId + '/Radar/FullView';
    }

    this.getRadarFullView = function(userId, radarTemplateId, callbackFunction)
    {
        var getRequest = {};

        getRequest = $resource(this.getFulLViewUrl(userId, radarTemplateId));

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

    this.canEditRadar = function(radarId, callbackFunction)
    {
        var url = '/api/User/Radar/' + radarId + '/CanEdit';


        $http.get(url)
            .success(function (data)
            {
                callbackFunction(data);
            });
    };
});
