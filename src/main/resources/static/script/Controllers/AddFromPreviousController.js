theApp.controller('AddFromPreviousController', function ($scope, $resource, $http, RadarInstanceService, RadarItemService)
{
    $scope.currentUserId = $('#userId').val();
    $scope.destinationRadarInstanceId = $("#radarInstanceId").val();
    $scope.sourceRadarInstance = {};
    $scope.destinationRadarInstance = {};
    $scope.destinationTechnologies = [];
    $scope.deletionTechnologies = [];

    $scope.isNullOrUndefined = function(testObject)
    {
        var retVal = false;

        if((testObject === null) ||
            (testObject === undefined))
        {
            retVal = true;
        }

        return retVal;
    };

    $scope.getDestinationRadarInstance = function()
    {
        RadarInstanceService.getRadarInstance($scope.currentUserId, $scope.destinationRadarInstanceId, $scope.setDestinationRadar);
    }

    $scope.addToDestinationTechnology = function(technologyId)
    {
        var newItem = {};
        newItem.technologyId = technologyId;
        $scope.destinationTechnologies.push(newItem);
        return newItem;
    }

    $scope.setDestinationRadar = function(data)
    {
        $scope.destinationRadarInstance = data;

        for (var i = 0; i < $scope.destinationRadarInstance.quadrants.length; i++)
        {
            for (var j = 0; j < $scope.destinationRadarInstance.quadrants[i].items.length; j++)
            {
                $scope.addToDestinationTechnology(
                    $scope.destinationRadarInstance.quadrants[i].items[j].assessmentItem.technology.id);
            }
        }
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

        for (var i = 0; i < $scope.sourceRadarInstance.quadrants.length; i++)
        {
            for (var j = 0; j < $scope.sourceRadarInstance.quadrants[i].items.length; j++)
            {
                var destinationTechnology = $scope.destinationTechnologies.find(function (obj) { return obj.technologyId === $scope.sourceRadarInstance.quadrants[i].items[j].assessmentItem.technology.id; });

                if(!$scope.isNullOrUndefined(destinationTechnology))
                {
                    $scope.sourceRadarInstance.quadrants[i].items[j].isAdded = true;
                    destinationTechnology.quadrantItem = $scope.sourceRadarInstance.quadrants[i].items[j];
                }
            }
        }
    }

    $scope.addItemsToRadar = function(userId, radarInstanceId)
    {
        var itemsToAdd = [];

        $scope.isSaving = true;

        for(var i = 0; i < $scope.sourceRadarInstance.quadrants.length; i++)
        {
            for(var j = 0; j < $scope.sourceRadarInstance.quadrants[i].items.length; j++)
            {
                if($scope.sourceRadarInstance.quadrants[i].items[j].shouldBeAdded === true)
                {
                    var itemToAdd = $scope.sourceRadarInstance.quadrants[i].items[j].assessmentItem;

                    itemsToAdd.push(RadarItemService.createRadarItemForExistingTechnology(itemToAdd.radarRing.id,
                                                                                                itemToAdd.confidenceFactor,
                                                                                                itemToAdd.details,
                                                                                                itemToAdd.technology.id));
                }
            }
        }

        RadarItemService.addRadarItems(userId, radarInstanceId, itemsToAdd, $scope.saveRadarItemSuccess, $scope.saveRadarItemFailure);
    }

    $scope.saveRadarItemSuccess = function(data)
    {
        $scope.isSaving = false;
        $scope.setDestinationRadar(data);

        for (var i = 0; i < $scope.sourceRadarInstance.quadrants.length; i++)
        {
            for (var j = 0; j < $scope.sourceRadarInstance.quadrants[i].items; j++)
            {
                if ($scope.sourceRadarInstance.quadrants[i].items[j].shouldBeAdded === true)
                {
                    var addedItem = $scope.addToDestinationTechnology(
                        $scope.sourceRadarInstance.quadrants[i].items[j].assessmentItem.technology.id);
                    addedItem.isAdded = true;
                }
            }
        }
    }

    $scope.saveRadarItemFailure = function(data)
    {
        $scope.isSaving = false;
    }

    $scope.removeItemsFromRadar = function(userId, radarInstanceId)
    {
        var itemsToRemove = [];

        $scope.deletionTechnologies = [];
        $scope.isSaving = true;

        for(var i = 0; i < $scope.destinationRadarInstance.quadrants.length; i++)
        {
            for(var j = 0; j < $scope.destinationRadarInstance.quadrants[i].items.length; j++)
            {
                if($scope.destinationRadarInstance.quadrants[i].items[j].shouldBeRemoved === true)
                {
                    var itemToRemove = $scope.destinationRadarInstance.quadrants[i].items[j].assessmentItem;
                    itemsToRemove.push(itemToRemove.id);
                    $scope.deletionTechnologies.push(itemToRemove.technology.id);
                }
            }
        }

        RadarItemService.deleteRadarItems(userId, radarInstanceId, itemsToRemove, $scope.removeRadarItemSuccess, $scope.removeRadarItemFailure);
    }

    $scope.removeRadarItemSuccess = function(data)
    {
        $scope.isSaving = false;
        $scope.getDestinationRadarInstance();

        for(var i = 0; i < $scope.deletionTechnologies.length; i++)
        {
            var destinationTechnology = $scope.destinationTechnologies.find(function (obj) { return obj.quadrantItem.assessmentItem.id === $scope.deletionTechnologies[i];});

            if(!$scope.isNullOrUndefined(destinationTechnology))
            {
                destinationTechnology.quadrantItem.isAdded = false;
                $scope.destinationTechnologies.splice($scope.destinationTechnologies.indexOf(destinationTechnology), 1);
            }
        }
    }

    $scope.removeRadarItemFailure = function(data)
    {
        $scope.isSaving = false;
    }
});
