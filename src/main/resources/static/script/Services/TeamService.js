theApp.service('TeamService', function ($resource, $http)
{
    this.getUserTeams = function(userId, responseHandler)
    {
        var url =  '/api/User/' + userId + '/TeamMembership';

        $http.get(url)
            .success(function (data)
            {
                responseHandler(data);
            });
    }
});