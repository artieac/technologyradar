theApp.controller('RadarInstanceController', function ($scope, $resource, $http, RadarInstanceService)
{
    $scope.currentUserId = $('#userId').val();
    $scope.currentRadar = null;
    $scope.selectedRadarInstance = {};

    $scope.getRadarsByUser = function(userId)
    {
        RadarInstanceService.getAllRadarsByUser(userId, $scope.setRadarInstances);
    }

    $scope.setRadarInstances = function(radarInstances)
    {
        $scope.radarInstances = radarInstances;
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
                $scope.getRadarsByUser(userId);
            });
    }

    $scope.getRadarInstance = function(radarId, userId)
    {
        RadarInstanceService.getRadarInstanceRequest(userId, radarId).get(function(data)
        {
            $scope.currentRadar = data;
        });
    }

    $scope.publishRadar = function(userId, radarInstance)
    {
        RadarInstanceService.publishRadar(userId, radarInstance, $scope.revertPublish);
    }

    $scope.reverPublish = function(radarInstance)
    {
        radarInstance.isPublished = false;
    }

    $scope.lockRadar = function(userId, radarInstance)
    {
        RadarInstanceService.lockRadar(userId, radarInstance, $scope.revertLock);
    }

    $scope.revertLock = function(radarInstance)
    {
        radarInstance.isLocked = false;
    }
});
