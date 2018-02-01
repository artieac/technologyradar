theApp.service('RadarInstanceService', function ($resource, $http)
{
    this.radarList = {};
    this.radarRings = {};
    this.radarCategories = {};
    this.radarData = {};

    this.getRadarsByUserRequest = function (userId)
    {
        return $resource('/api/public/User/:userId/Radars', {userId: userId});
    };

    this.getRadarsByUserRequestUrl = function (userId)
    {
        return '/api/public/User/' + userId + '/Radars';
    };

    this.getRadarRingsRequest = function ()
    {
        return $resource('/api/radar/rings');
    };

    this.getRadarCategoriesRequest = function ()
    {
        return $resource('/api/radar/categories');
    };

    this.getRadarInstanceRequest = function (userId, radarId)
    {
        return $resource('/api/public/User/' + userId + '/Radar/' + radarId);
    };

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

    this.publishRadar = function(userId, radarId)
    {
        var retVal = '/api/User/' + userId + '/Radar/' + radarId + '/Publish';
        return retVal;
    };

    this.lockRadar = function(userId, radarId)
    {
        var retVal = '/api/User/' + userId + '/Radar/' + radarId + '/Lock';
        return retVal;
    };
});
