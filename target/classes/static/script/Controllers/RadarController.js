theApp.controller('RadarController', function ($scope, $resource, $http)
{
    $scope.currentUserId = $('#userId').val();
    $scope.selectedRadarInstance = {};
    $scope.selectedRadarInstanceItem = {};

    $scope.copyText = function()
    {
        var copySource = document.getElementById("sharingLink");
        copySource.select();
        document.execCommand("Copy");
    }

    $scope.getRadarSharingLink = function(userId)
    {
        $scope.radarSharingLink = "http://" + window.location.hostname + ":" + window.location.port + "/public/home/radars/" + userId;

        if($scope.selectedRadarInstance !== null && $scope.selectedRadarInstance !== undefined &&
           $scope.selectedRadarInstance.id !== null && $scope.selectedRadarInstance.id !== undefined)
        {
            $scope.radarSharingLink += "/" + $scope.selectedRadarInstance.id;
        }
    }

    $scope.isNullOrUndefined = function(testObject)
    {
        var retVal = false;

        if((testObject === null) ||
            (testObject === undefined))
        {
            retVal = true;
        }

        return retVal;
    }

    $scope.getRadarData = function (userId, radarId)
    {
        var getRadarDataRequest = $resource('/api/public/User/' + userId + '/Radar/' + radarId);
        getRadarDataRequest.get(function (data)
        {
            $scope.renderRadar(data);
        });
    };


    $scope.getUserRadars = function (userId)
    {
        $scope.getRadarSharingLink(userId);

        var getRadarInstancesRequest = $resource('/api/public/User/:userId/Radars', {userId: userId});

        $scope.radarInstanceList = getRadarInstancesRequest.query(function (data)
        {
            var radarInstanceId = $("#radarInstanceId").val();

            if (!$scope.isNullOrUndefined(radarInstanceId) &&
                radarInstanceId !== '')
            {
                for (var i = 0; i < data.length; i++)
                {
                    if (data[i].id == radarInstanceId)
                    {
                        $scope.radarInstanceDropdownSelected(userId, data[i]);
                        break;
                    }
                }
            }
        })
    }

    $scope.radarInstanceDropdownSelected = function (userId, radarInstance)
    {
        $scope.selectedRadarInstance = radarInstance;
        $scope.getRadarSharingLink(userId);
        $scope.getRadarData(userId, radarInstance.id);
    }

    $scope.renderRadar = function (radarData)
    {
        var radar_arcs = [];

        $scope.radarData = radarData;

        for (var i = 0; i < radarData.radarArcs.length; i++)
        {
            radar_arcs.push({"r": radarData.rangeWidth * (i + 1), "name": radarData.radarArcs[i].radarRing.name});
        }
        init(radarData.height, radarData.width, radarData.quadrants, radar_arcs, $scope.selectRadarInstanceItem);
    }

    $scope.getRadarRings = function ()
    {
        var getRingsRequest = $resource('/api/radar/rings');
        $scope.radarRingList = getRingsRequest.query();
    }

    $scope.selectRadarRing = function (radarRing)
    {
        $scope.selectedRadarRing = radarRing;
    }

    $scope.getRadarCategories = function ()
    {
        var getRadarCategoriesRequest = $resource('/api/radar/categories');
        $scope.radarCategoryList = getRadarCategoriesRequest.query();
    }

    $scope.selectRadarCategory = function (radarCategory)
    {
        $scope.selectedRadarCategory = radarCategory;
    }

    $scope.confidenceLevels = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    $scope.selectedConfidence = 5;

    $scope.selectConfidence = function (confidence)
    {
        $scope.selectedConfidence = confidence;
    }

    $scope.isSaving = false;

    $scope.addRadarItem = function (userId)
    {
        var radarSaveItem = {};

        $scope.isSaving = true;

        radarSaveItem.radarRing = $scope.selectedRadarRing.id;
        radarSaveItem.confidenceLevel = $scope.selectedConfidence;
        radarSaveItem.assessmentDetails = $scope.selectedRadarInstanceItem.details;

        if ($scope.selectedRadarInstanceItem.id === undefined)
        {
            if(!$scope.isNullOrUndefined($scope.selectedRadarInstanceItem.technology.id))
            {
                radarSaveItem.technologyId = $scope.selectedRadarInstanceItem.technology.id;
            }
            else
            {
                radarSaveItem.technologyName = $scope.selectedRadarInstanceItem.technology.name;
                radarSaveItem.radarCategory = $scope.selectedRadarCategory.id;
                radarSaveItem.url = $scope.selectedRadarInstanceItem.technology.url;
            }

            $http.post('/api/User/' + userId + '/Radar/' + $scope.selectedRadarInstance.id + '/Item', radarSaveItem)
                .success(function (data)
                {
                    $scope.renderRadar(data);
                    $scope.clearRadarItemSelection();
                    $scope.isSaving = false;
                })
                .error(function (data)
                {
                    $scope.isSaving = false;
                });
        }
        else
        {
            radarSaveItem.technologyId = $scope.selectedRadarInstanceItem.technology.id;

            $http.post('/api/User/' + userId + '/Radar/' + $scope.selectedRadarInstance.id + '/Item/' + $scope.selectedRadarInstanceItem.id, radarSaveItem)
                .success(function (data)
                {
                    $scope.renderRadar(data);
                    $scope.clearRadarItemSelection();
                    $scope.isSaving = false;
                })
                .error(function (data)
                {
                    $scope.isSaving = false;
                });
        }
    }

    $scope.canAddRadarItem = function (isFormValid)
    {
        var retVal = false;

        if ($scope.selectedRadarInstance != null && isFormValid === true && $scope.isSaving === false)
        {
            retVal = true;
        }

        return retVal;
    }

    $scope.searchForTechnologyByName = function ()
    {
        var technologyName = jQuery("#newTechnologyName").val();
        var searchTechnologyByNameRequest = $resource('/api/technology/search?technologyName=:technologyName');
        $scope.technologySearchResults = searchTechnologyByNameRequest.query({technologyName: technologyName});
    };

    $scope.selectTechnology = function (technology)
    {
        $scope.selectedRadarCategory = technology.radarCategory;
        $scope.selectedRadarInstanceItem.technology = technology;
        $scope.technologySearchResults = null;
    };

    $scope.clearRadarItemSelection = function ()
    {
        $scope.selectedRadarInstanceItem = {};
    }

    $scope.selectRadarInstanceItem = function (radarInstanceItem)
    {
        $scope.selectedRadarInstanceItem = radarInstanceItem;
        $scope.selectTechnology(radarInstanceItem.technology);
        $scope.selectedRadarRing = $scope.selectedRadarInstanceItem.radarRing;
        $scope.selectedConfidence = $scope.selectedRadarInstanceItem.confidenceFactor;
        $scope.$digest();
    }

    $scope.isExistingRadarInstanceItemSelected = function ()
    {
        var retVal = false;

        if ($scope.selectedRadarInstanceItem !== null &&
            $scope.selectedRadarInstanceItem !== undefined &&
            $scope.selectedRadarInstanceItem.id !== null &&
            $scope.selectedRadarInstanceItem.id !== undefined)
        {
            retVal = true;
        }

        return retVal;
    }

    $scope.deleteRadarItem = function (userId, radarId, radarItemId)
    {
        $http.delete('/api/User/' + userId + '/Radar/' + radarId + '/Item/' + radarItemId)
            .then(function (data)
            {
                $scope.getRadarData(userId, radarId);
            });

    }
});