theApp.controller('RadarInstanceController', function ($scope, $resource, $http, RadarInstanceService)
{
    $scope.currentUserId = $('#userId').val();
    $scope.radarInstanceId = $('#radarInstanceId').val();
    $scope.currentRadar = null;

    $scope.getUserRadars = function(userId)
    {
        $scope.radarInstances = RadarInstanceService.getRadarsByUserRequest(userId).query();
    }

    $scope.addRadar = function(userId)
    {
        $http.post(RadarInstanceService.addRadarRequest(userId), $scope.newRadar)
            .then(function (data)
            {
                $scope.getUserRadars(userId);
            });
    }

    $scope.deleteRadarInstance = function(userId, radarId)
    {
        $http.delete(RadarInstanceService.deleteRadarRequest(userId, radarId))
            .then(function (data)
            {
                $scope.getUserRadars(userId);
            });
    }

    $scope.getRadarInstance = function(radarId, userId)
    {
        RadarInstanceService.getRadarInstanceRequest(userId, radarId).get(function(data)
        {
            $scope.currentRadar = data;
        });
    }
});
