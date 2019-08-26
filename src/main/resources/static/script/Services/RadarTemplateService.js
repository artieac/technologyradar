theApp.service('RadarTemplateService', function ($resource, $http)
{
    this.getUserRadarTemplatesRequest = function(userId, isAnonymous)
    {
        if(isAnonymous==true)
        {
            return $resource('/api/public/User/' + userId + '/RadarTemplates');
        }
        else
        {
            return $resource('/api/User/' + userId + '/RadarTemplates');
        }
    }

    this.getUserRadarTemplates = function(userId, isAnonymous, successCallback)
    {
        var url =  '/api/public/User/' + userId + '/RadarTemplates';

        $http.get(url)
            .success(function (data)
            {
                successCallback(data);
            });
    }

    this.getUserRadarTemplatesWithAssociated = function(userId, includeAssociated, isAnonymous, successCallback)
    {
        var url =  '';

        if(isAnonymous == true)
        {
            url = '/api/public/User/' + userId;
        }
        else
        {
            url = '/api/User/' + userId;
        }

         url += '/RadarTemplates';

        if(includeAssociated===true)
        {
            url += "?includeAssociated=true";
        }

        $http.get(url)
            .success(function (data)
            {
                successCallback(data);
            });
    }

});