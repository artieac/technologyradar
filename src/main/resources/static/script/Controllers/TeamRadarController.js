theApp.controller('TeamRadarController', function ($scope, $resource, $http, RadarUserService, RadarInstanceService, RadarItemService, RadarSubjectService, TeamService)
{
    $scope.currentUserId = $('#userId').val();
    $scope.showAddItemSection = false;
    $scope.canEditRadar = false;
    $scope.publicRadarLink = "";
    $scope.userTeams = [];

    $scope.getUserTeams = function(currentUserId){
        TeamService.getUserTeams(currentUserId, $scope.setUserTeams);
    }

    $scope.setUserTeams = function(userTeams){
        $scope.userTeams = userTeams;

        var teamId = $("#teamId").val();

        if (!$scope.isNullOrUndefined($scope.userTeams) &&
            !$scope.isNullOrUndefined(teamId) &&
            teamId !== '')
        {
            for (var i = 0; i < $scope.userTeams.length; i++)
            {
                if ($scope.userTeams[i].id == teamId)
                {
                    $scope.userTeamDropdownSelected($scope.currentUserId, $scope.userTeams[i]);
                    break;
                }
            }
        }
    }

    $scope.userTeamDropdownSelected = function(currentUserId, userTeam){
        $scope.selectedUserTeam = userTeam;
        $scope.setTeamRadars(userTeam.radars);
    }

     $scope.setTeamRadars = function(teamRadars){
        $scope.teamRadars = teamRadars;

        var radarId = $("#radarId").val();

        if (!$scope.isNullOrUndefined($scope.teamRadars))
        {
            if(!$scope.isNullOrUndefined(radarId) &&
                radarId !== '' && radarId > -1)
            {
                for (var i = 0; i < $scope.teamRadars.length; i++)
                {
                    if ($scope.teamRadars[i].id == radarId)
                    {
                        $scope.teamRadarsDropdownSelected($scope.currentUserId, $scope.teamRadars[i]);
                        break;
                    }
                }
            }
            else
            {
                $scope.teamRadarsDropdownSelected($scope.currentUserId, $scope.teamRadars[0]);
            }
        }
     }

     $scope.teamRadarsDropdownSelected = function (userId, radar)
     {
         $scope.selectedTeamRadar = radar;
         $scope.getRadarData(radar.radarUser.id, radar.id, false);
         $scope.radarRings = radar.radarTemplate.radarRings;
         $scope.radarCategories = radar.radarTemplate.radarCategories;
     }

     $scope.getRadarData = function (userId, radarId)
     {
        RadarInstanceService.getRadarInstance(userId, radarId, false, $scope.renderRadar);
        RadarInstanceService.canEditRadar(radarId, $scope.canEditRadarResponse);
     }

     $scope.renderRadar = function (radarData)
     {
         var radar_arcs = [];

         $scope.radarData = radarData;

         for (var i = 0; i < radarData.radarArcs.length; i++)
         {
             radar_arcs.push({"r": radarData.rangeWidth * (i + 1), "name": radarData.radarArcs[i].radarRing.name});
         }
         init(radarData.height, radarData.width, radarData.quadrants, radar_arcs, $scope.selectedTeamRadar);
     }

     $scope.canEditRadarResponse = function(canEditRadar){
         $scope.canEditRadar = canEditRadar;
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
                    $scope.selectedTeamRadar.id,
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
                    $scope.selectedTeamRadar.id,
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
                $scope.selectedTeamRadar.id,
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

        if ($scope.selectedTeamRadar != null && isFormValid === true && $scope.isSaving === false)
        {
            retVal = true;
        }

        return retVal;
    }
});