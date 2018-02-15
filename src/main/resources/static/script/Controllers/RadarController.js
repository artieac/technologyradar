theApp.controller('RadarController', function ($scope, $resource, $http, RadarInstanceService, TechnologyService)
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
        $scope.radarSharingLink = "http://" + window.location.hostname;

        if(window.location.port !== "80" && window.location.port !== "443")
        {
            $scope.radarSharingLink += ":" +  window.location.port;
        }

        if($scope.selectedRadarInstance !== null && $scope.selectedRadarInstance !== undefined &&
            $scope.selectedRadarInstance.id !== null && $scope.selectedRadarInstance.id !== undefined)
        {
            $scope.radarSharingLink += "/public/home/radar/" + userId + "/" + $scope.selectedRadarInstance.id;
            ;
        }
        else
        {
            $scope.radarSharingLink += "/public/home/radars/" + userId;
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
        RadarInstanceService.getRadarInstance(userId, radarId, $scope.renderRadar);
    }

    $scope.getUserRadars = function (userId, publishedOnly)
    {
        $scope.getRadarSharingLink(userId);

        if (publishedOnly === true)
        {
            RadarInstanceService.getPublishedRadarsByUser(userId, $scope.setRadarInstances);
        }
        else
        {
            RadarInstanceService.getAllRadarsByUser(userId, $scope.setRadarInstances);
        }
    }

    $scope.setRadarInstances = function(radarInstances)
    {
        $scope.radarInstanceList = radarInstances;

        var radarInstanceId = $("#radarInstanceId").val();

        if (!$scope.isNullOrUndefined($scope.radarInstanceList) &&
            !$scope.isNullOrUndefined(radarInstanceId) &&
            radarInstanceId !== '')
        {
            for (var i = 0; i < $scope.radarInstanceList.length; i++)
            {
                if ($scope.radarInstanceList[i].id == radarInstanceId)
                {
                    $scope.radarInstanceDropdownSelected(userId, $scope.radarInstanceList[i]);
                    break;
                }
            }
        }
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
        $scope.radarRingList = RadarInstanceService.getRadarRingsRequest().query();
    }

    $scope.selectRadarRing = function (radarRing)
    {
        $scope.selectedRadarRing = radarRing;
    }

    $scope.getRadarCategories = function ()
    {
        $scope.radarCategoryList = RadarInstanceService.getRadarCategoriesRequest().query();
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
        $scope.isSaving = true;

        if ($scope.selectedRadarInstanceItem.id === undefined)
        {
            if (!$scope.isNullOrUndefined($scope.selectedRadarInstanceItem.technology.id))
            {
                RadarInstanceService.addRadarItemExistingTechnology(userId,
                    $scope.selectedRadarInstance.id,
                    $scope.selectedRadarRing.id,
                    $scope.selectedConfidence,
                    $scope.selectedRadarInstanceItem.details,
                    $scope.selectedRadarInstanceItem.technology.id,
                    $scope.saveSuccessCallback, $scope.saveFailureCallback);
            }
            else
            {
                RadarInstanceService.addRadarItemNewTechnology(userId,
                    $scope.selectedRadarInstance.id,
                    $scope.selectedRadarRing.id,
                    $scope.selectedConfidence,
                    $scope.selectedRadarInstanceItem.details,
                    $scope.selectedRadarInstanceItem.technology.name,
                    $scope.selectedRadarInstanceItem.technology.url,
                    $scope.selectedRadarCategory.id,
                    $scope.saveSuccessCallback, $scope.saveFailureCallback);
            }
        }
        else
        {
            RadarInstanceService.updateRadarItem(userId, $scope.selectedRadarInstance, $scope.selectedRadarRing.id, $scope.selectedConfidence, $scope.selectedRadarInstanceItem.details, $scope.selectedRadarInstanceItem.technology, $scope.selectedRadarInstanceItem, $scope.saveSuccessCallback, $scope.saveFailureCallback);
        }
    };

    $scope.saveSuccessCallback = function(data)
    {
        $scope.renderRadar(data);
        $scope.clearRadarItemSelection();
        $scope.isSaving = false;
    }

    $scope.saveFailureCallback = function(data)
    {
        $scope.isSaving = false;
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
        $scope.technologySearchResults = TechnologyService.searchTechnologyRequest(technologyName, null, null).query();
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
        RadarInstanceService.deleteRadarItem(userId, radarId, radarItemId, $scope.deleteCallbackFunction);
    }

    $scope.deleteCallbackFunction = function(userId, radarId)
    {
        $scope.getRadarData(userId, radarId);
    }
});