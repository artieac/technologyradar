theApp.controller('AddFromPreviousController', function ($scope, $resource, $http, RadarInstanceService)
{
    $scope.currentUserId = $('#userId').val();
    $scope.destinationRadarInstanceId = $("#radarInstanceId").val();
    $scope.sourceRadarInstance = {};
    $scope.destinationRadarInstance = {};

    $scope.getDestinationRadarInstance = function()
    {
        RadarInstanceService.getRadarInstance($scope.currentUserId, $scope.destinationRadarInstanceId, $scope.setDestinationRadar);
    }

    $scope.setDestinationRadar = function(data)
    {
        $scope.destinationRadarInstance = data;
    }

    $scope.getRadarsByUser = function(userId)
    {
        RadarInstanceService.getAllRadarsByUser(userId, $scope.setRadarInstances);
    }

    $scope.setRadarInstances = function(radarInstances)
    {
        $scope.radarInstances = radarInstances;

        var targetIndex = -1;

        for(var i = 0; i < $scope.radarInstances.length; i++)
        {
            if($scope.radarInstances[i].id == $scope.destinationRadarInstanceId)
            {
                targetIndex = i;
                break;
            }
        }

        if(targetIndex > 0)
        {
            $scope.radarInstances.splice(targetIndex, 1);
        }
    }

    $scope.radarInstanceDropdownSelected = function (userId, radarInstance)
    {
        $scope.sourceRadarInstance = radarInstance;
        RadarInstanceService.getRadarInstance(userId, radarInstance.id, $scope.setRadarInstance);
    }

    $scope.setRadarInstance = function(data)
    {
        $scope.sourceRadarInstance = data;
    }

    $scope.updateRadarItem = function(userId, radarItem, categoryId)
    {
        $scope.itemBeingUpdated = radarItem;

        if(radarItem.isAdded === true)
        {
            $scope.isSaving = true;
            RadarInstanceService.addRadarItem(userId, $scope.destinationRadarInstanceId, categoryId, radarItem.assessmentItem, $scope.saveRadarItemSuccess, $scope.saveRadarItemFailure);
        }
        else
        {
            $scope.isSaving = true;
            RadarInstanceService.deleteRadarItem(userId, $scope.destinationRadarInstanceId, radarItem.assessmentItem.id, $scope.saveRadarItemSuccess, $scope.saveRadarItemFailure);
        }
    }

    $scope.saveRadarItemSuccess = function(data)
    {
        $scope.isSaving = false;
        $scope.getDestinationRadarInstance();
    }

    $scope.saveRadarItemFailure = function(data)
    {
        $scope.isSaving = false;
        $scope.itemBeingUpdated.isAdded = !$scope.itemBeingUpdated.isAdded;
    }
});
