theApp.service('RadarUserService', function ($resource, $http)
{
    this.getDataOwnerDetails = function(userId)
    {
        return $resource('/api/public/User/' + userId);
    }
});