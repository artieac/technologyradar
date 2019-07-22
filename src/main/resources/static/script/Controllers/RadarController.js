theApp.controller('RadarController', function ($scope, $resource, $http, RadarInstanceService, RadarItemService, RadarSubjectService, RadarTypeService)
{
    $scope.allOptionName = "All";
    $scope.currentUserId = $('#userId').val();
    $scope.selectedRadarInstance = {};
    $scope.selectedRadarInstanceItem = {};
    $scope.showAddItemSection = false;
    $scope.isAnonymous = ($('#isAnonymous').val() == 'true');
    $scope.canEditRadar = false;
    $scope.showFullViewOption = false;

    $scope.clickAddItemButton = function()
    {
        $scope.showAddItemSection = !$scope.showAddItemSection;
    }

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
            $scope.radarSharingLink += "/public/home/user/" + userId + "/radar/" + $scope.selectedRadarInstance.id;
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

    $scope.getRadarData = function (userId, radarId, isAnonymous)
    {
        RadarInstanceService.getRadarInstance(userId, radarId, isAnonymous, $scope.renderRadar);

        if($scope.isAnonymous==false){
            RadarInstanceService.canEditRadar(radarId, $scope.canEditRadarResponse);
        }
    }

    $scope.canEditRadarResponse = function(canEditRadar){
        $scope.canEditRadar = canEditRadar;
    }

    $scope.getUserRadars = function (userId, selectedRadarType, isAnonymous)
    {
        $scope.getRadarSharingLink(userId);

        if (isAnonymous == true)
        {
            RadarInstanceService.getPublishedRadarsByUserAndRadarTypes(userId, selectedRadarType, $scope.setRadarInstances);
        }
        else
        {
            RadarInstanceService.getAllRadarsByUserAndRadarTypes(userId, selectedRadarType, $scope.setRadarInstances);
        }
    }

    $scope.setRadarInstances = function(radarInstances)
    {
        $scope.showFullViewOption = false;
        $scope.radarInstanceList = radarInstances;
        $scope.selectedRadarInstance = {};

        var radarInstanceId = $("#radarInstanceId").val();

        if (!$scope.isNullOrUndefined($scope.radarInstanceList))
        {
            if($scope.radarInstanceList.length > 1 && $scope.selectedRadarType.name!=$scope.allOptionName)
            {
                $scope.showFullViewOption = true;
            }

            if(!$scope.isNullOrUndefined(radarInstanceId) && radarInstanceId !== '')
            {
                for (var i = 0; i < $scope.radarInstanceList.length; i++)
                {
                    if ($scope.radarInstanceList[i].id == radarInstanceId)
                    {
                        $scope.radarInstanceDropdownSelected($scope.currentUserId, $scope.radarInstanceList[i]);
                        break;
                    }
                }
            }
        }
    }

    $scope.radarInstanceDropdownSelected = function (userId, radarInstance)
    {
        $scope.selectedRadarInstance = radarInstance;
        $scope.getRadarSharingLink(userId);
        $scope.getRadarData(userId, radarInstance.id, $scope.isAnonymous);
        $scope.radarRings = radarInstance.radarType.radarRings;
        $scope.radarCategories = radarInstance.radarType.radarCategories;
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

    $scope.selectRadarRing = function (radarRing)
    {
        $scope.selectedRadarRing = radarRing;
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
                RadarItemService.addRadarItemExistingTechnology(userId,
                    $scope.selectedRadarInstance.id,
                    $scope.selectedRadarCategory.id,
                    $scope.selectedRadarRing.id,
                    $scope.selectedConfidence,
                    $scope.selectedRadarInstanceItem.details,
                    $scope.selectedRadarInstanceItem.technology.id,
                    $scope.saveSuccessCallback, $scope.saveFailureCallback);
            }
            else
            {
                RadarItemService.addRadarItemNewTechnology(userId,
                    $scope.selectedRadarInstance.id,
                    $scope.selectedRadarCategory.id,
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
            RadarItemService.updateRadarItem(userId,
                $scope.selectedRadarInstance.id,
                $scope.selectedRadarCategory.id,
                $scope.selectedRadarRing.id,
                $scope.selectedConfidence,
                $scope.selectedRadarInstanceItem.details,
                $scope.selectedRadarInstanceItem.technology,
                $scope.selectedRadarInstanceItem,
                $scope.saveSuccessCallback,
                $scope.saveFailureCallback);
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
        $scope.selectedRadarCategory = $scope.selectedRadarInstanceItem.radarCategory;
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
        RadarItemService.deleteRadarItem(userId, radarId, radarItemId, $scope.deleteCallbackFunction);
    }

    $scope.deleteCallbackFunction = function(userId, radarId)
    {
        $scope.getRadarData(userId, radarId, $scope.isAnonymous);
    }

    $scope.publishRadar = function(userId, radarId)
    {
        RadarInstanceService.publishRadar(userId, radarId);
    }

    $scope.lockRadar = function(userId, radarId)
    {
        RadarInstanceService.lockRadar(userId, radarId);
    }

    $scope.getDropdownLabel = function(radarInstance)
    {
        var retVal = "";

        if(radarInstance !==undefined && radarInstance.name!==undefined)
        {
            retVal = radarInstance.name + " - " + radarInstance.formattedAssessmentDate;
        }

        return retVal;
    }

    $scope.getRadarTypes = function(currentUserId){
        RadarTypeService.getUserRadarTypesWithAssociated(currentUserId, true, $scope.isAnonymous, $scope.setRadarTypes);
    }

    $scope.setRadarTypes = function(radarTypes){
        $scope.radarTypes = radarTypes;

        var radarTypeId = $("#radarTypeId").val();
        var radarTypeVersion = $("#radarTypeVersion").val();

        if (!$scope.isNullOrUndefined($scope.radarTypes) &&
            !$scope.isNullOrUndefined(radarTypeId) &&
            radarTypeId !== '' &&
            !$scope.isNullOrUndefined(radarTypeVersion) &&
            radarTypeVersion !== '')
        {
            for (var i = 0; i < $scope.radarTypes.length; i++)
            {
                if ($scope.radarTypes[i].id == radarTypeId && $scope.radarTypes[i].version == radarTypeVersion)
                {
                    $scope.radarTypeDropdownSelected($scope.currentUserId, $scope.radarTypes[i]);
                    break;
                }
            }
        }
    }

    $scope.radarTypeDropdownSelected = function(currentUserId, radarType){
        $scope.selectedRadarType = radarType;
        $scope.getUserRadars(currentUserId, radarType, $scope.isAnonymous);
    }

    $scope.radarTypeAllSelected = function(currentUserId){
        $scope.selectedRadarType = {};
        $scope.selectedRadarType.id = "";
        $scope.selectedRadarType.name = $scope.allOptionName;
        $scope.selectedRadarType.version = -1;
        $scope.showFullViewOption = false;
        $scope.getUserRadars(currentUserId, $scope.selectedRadarType, $scope.isAnonymous);
    }

    $scope.showCurrentRadarSelected = function(currentUserId)
    {
        $scope.canEditRadar = false;
        $scope.selectedRadarInstance = {};
        $scope.selectedRadarInstance.name = "Full View";
        $scope.selectedRadarInstance.formattedAssessmentDate = "";

        RadarInstanceService.getRadarFullView(currentUserId, $scope.selectedRadarType.id, $scope.selectedRadarType.version, $scope.renderRadar);
    }
});