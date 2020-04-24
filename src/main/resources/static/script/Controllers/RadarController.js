theApp.controller('RadarController', function ($scope, $resource, $http, RadarInstanceService, RadarItemService, RadarSubjectService, RadarTemplateService)
{
    $scope.allOptionName = "All";
    $scope.fullViewOptionName = "Full View";
    $scope.currentUserId = $('#userId').val();
    $scope.selectedRadarInstance = {};
    $scope.selectedRadarInstanceItem = {};
    $scope.showAddItemSection = false;
    $scope.isAnonymous = ($('#isAnonymous').val() == 'true');
    $scope.canEditRadar = false;
    $scope.showFullViewOption = false;
    $scope.publicRadarLink = "";
    $scope.mostRecentRadarsLink = "";
    $scope.selectedRadarTemplate = {};

    $scope.clickAddItemButton = function()
    {
        $scope.showAddItemSection = !$scope.showAddItemSection;
    }

    $scope.copyLink = function(linkType){
        var copySource = document.getElementById("sharingLink");

        if(linkType==0)
        {
            copySource.value = $scope.publicRadarLink;
        }
        else
        {
            copySource.value = $scope.mostRecentRadarLink;
        }

        document.execCommand("Copy");
    }

    $scope.getRadarSharingLink = function(userId)
    {
       if($scope.selectedRadarInstance !== null && $scope.selectedRadarInstance !== undefined &&
            $scope.selectedRadarInstance.id !== null && $scope.selectedRadarInstance.id !== undefined)
        {
            $scope.publicRadarLink = "/public/home/user/" + userId + "/radar/" + $scope.selectedRadarInstance.id;
        }
        else
        {
            $scope.publicRadarLink = "/public/home/radars/" + userId;
        }

        if($scope.selectedRadarTemplate !== null && $scope.selectedRadarTemplate !== undefined &&
            $scope.selectedRadarTemplate.id !== null && $scope.selectedRadarTemplate.id !== undefined)
        {
            $scope.mostRecentRadarsLink = "/public/home/user/" + userId + "/radartemplate/" + $scope.selectedRadarTemplate.id + "/radars?mostrecent=true";
        }
        else
        {
            $scope.mostRecentRadarsLink = "/public/home/radars/" + userId;
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

    $scope.getUserRadars = function (userId, selectedRadarTemplate, isAnonymous)
    {
        $scope.getRadarSharingLink(userId);

        if (isAnonymous == true)
        {
            RadarInstanceService.getPublishedRadarsByUserAndRadarTemplates(userId, selectedRadarTemplate, $scope.setRadarInstances);
        }
        else
        {
            RadarInstanceService.getAllRadarsByUserAndRadarTemplates(userId, selectedRadarTemplate, $scope.setRadarInstances);
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
            if($scope.radarInstanceList.length > 1
               && $scope.selectedRadarTemplate.name!=$scope.allOptionName)
            {
                if($scope.radarInstanceList[0].radarUser.canSeeFullView)
                {
                    $scope.showFullViewOption = true;
                }
            }

            if(!$scope.isNullOrUndefined(radarInstanceId) && radarInstanceId !== '')
            {
                if(radarInstanceId==="-1")
                {
                    $scope.showCurrentRadarTemplateFullView($scope.currentUserId);
                }
                else
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
    }

    $scope.radarInstanceDropdownSelected = function (userId, radarInstance)
    {
        $scope.selectedRadarInstance = radarInstance;
        $scope.getRadarSharingLink(userId);
        $scope.getRadarData(userId, radarInstance.id, $scope.isAnonymous);
        $scope.radarRings = radarInstance.radarTemplate.radarRings;
        $scope.radarCategories = radarInstance.radarTemplate.radarCategories;
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
        $scope.technologySearchResults = RadarSubjectService.searchRequest(technologyName, null, null, null, $scope.isAnonymous).query();
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

    $scope.getDropdownLabel = function(radarInstance)
    {
        var retVal = "";

        if(radarInstance !==undefined && radarInstance.name!==undefined)
        {
            retVal = radarInstance.name + " - " + radarInstance.formattedAssessmentDate;
        }

        return retVal;
    }

    $scope.getRadarTemplates = function(currentUserId){
        RadarTemplateService.getUserRadarTemplatesWithAssociated(currentUserId, true, $scope.isAnonymous, $scope.setRadarTemplates);
    }

    $scope.setRadarTemplates = function(radarTemplates){
        $scope.radarTemplates = radarTemplates;

        var radarTemplateId = $("#radarTemplateId").val();

        if (!$scope.isNullOrUndefined($scope.radarTemplates) &&
            !$scope.isNullOrUndefined(radarTemplateId) &&
            radarTemplateId !== '')
        {
            for (var i = 0; i < $scope.radarTemplates.length; i++)
            {
                if ($scope.radarTemplates[i].id == radarTemplateId)
                {
                    $scope.radarTemplateDropdownSelected($scope.currentUserId, $scope.radarTemplates[i]);
                    break;
                }
            }
        }
    }

    $scope.radarTemplateDropdownSelected = function(currentUserId, radarTemplate){
        $scope.selectedRadarTemplate = radarTemplate;
        $scope.getUserRadars(currentUserId, radarTemplate, $scope.isAnonymous);
    }

    $scope.radarTemplateAllSelected = function(currentUserId){
        $scope.selectedRadarTemplate = {};
        $scope.selectedRadarTemplate.id = "";
        $scope.selectedRadarTemplate.name = $scope.allOptionName;
        $scope.showFullViewOption = false;
        $scope.getUserRadars(currentUserId, $scope.selectedRadarTemplate, $scope.isAnonymous);
    }

    $scope.showCurrentRadarTemplateFullView = function(currentUserId)
    {
        $scope.canEditRadar = false;
        $scope.selectedRadarInstance = {};
        $scope.selectedRadarInstance.id = -1;
        $scope.selectedRadarInstance.name = $scope.fullViewOptionName;
        $scope.selectedRadarInstance.formattedAssessmentDate = "";

        $scope.publicRadarLink = "/public/home/user/" + currentUserId + '/radartemplate/' + $scope.selectedRadarTemplate.id + '/radar/fullview';

        RadarInstanceService.getRadarFullView(currentUserId, $scope.selectedRadarTemplate.id, $scope.renderRadar);
    }
});