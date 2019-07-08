theApp.service('RadarTypeService', function ($resource, $http)
{
    this.getUserRadarTypesRequest = function(userId, isAnonymous)
    {
        if(isAnonymous==true)
        {
            return $resource('/api/public/User/' + userId + '/RadarTypes');
        }
        else
        {
            return $resource('/api/User/' + userId + '/RadarTypes');
        }
    }

    this.getUserRadarTypes = function(userId, isAnonymous, successCallback)
    {
        var url =  '';

        if(isAnonymous == true)
        {
            url = '/api/public/User/' + userId + '/RadarTypes';
        }
        else
        {
            url = '/api/User/' + userId + '/RadarTypes?allVersions=true';
        }

        $http.get(url)
            .success(function (data)
            {
                successCallback(data);
            });
    }
});