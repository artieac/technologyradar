theApp.controller('RadarInstanceController', function ($scope, $resource, $http)
{
    $scope.currentUserId = $('#userId').val();
    $scope.radarInstanceId = $('#radarInstanceId').val();
    $scope.currentRadar = null;

    $scope.getUserRadars = function(userId)
    {
        var getTechnologyAssessmentRequest = $resource ('/api/User/:userId/Radars', { userId: userId});
        $scope.radarInstances = getTechnologyAssessmentRequest.query();
    }

    $scope.addRadar = function(userId)
    {
        $http.post('/api/User/' + userId + '/Radar', $scope.newRadar)
            .then(function (data)
            {
                $scope.getUserRadars(userId);
            });
    }

    $scope.deleteRadar = function(radarId, userId)
    {
        $http.delete('/api/User/:userId/Radar/' + radarId)
            .then(function (data)
            {
                $scope.getAssessment(assessmentId, userId);
            });
    }

    $scope.getRadarInstance = function(radarId, userId)
    {
        var getRadarDataRequest = $resource('/api/User/' + userId + '/Radar/' + radarId);
        getRadarDataRequest.get(function(data)
        {
            $scope.currentRadar = data;
        });
    }
});
