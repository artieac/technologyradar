theApp.service('AddItemService', function ($resource, $http, RadarSubjectService, RadarItemService)
{
    this.technologySearchResults = [];
    this.selectedRadar = {};
    this.selectedRadarItem = {};
    this.selectedRadarRing = {};
    this.selectedRadarCategory = {};
    this.selectedConfidence = 5;

    this.searchForTechnologyByName = function ()
    {
        var technologyName = jQuery("#newTechnologyName").val();
        this.technologySearchResults = RadarSubjectService.searchRequest(technologyName, null, null, null, $scope.isAnonymous).query();
    };

    this.selectTechnology = function (technology)
    {
        this.selectedRadar.technology = technology;
        this.technologySearchResults = null;
    };

    this.clearRadarItemSelection = function ()
    {
        this.selectedRadarItem = {};
    }

    this.selectRadarRing = function (radarRing)
    {
        this.selectedRadarRing = radarRing;
    }

    this.selectRadarCategory = function (radarCategory)
    {
        this.selectedRadarCategory = radarCategory;
    }

    $scope.confidenceLevels = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

    this.selectConfidence = function (confidence)
    {
        this.selectedConfidence = confidence;
    }

    this.addRadarItem = function (userId, successCallback, failureCallback)
    {
        if (this.selectedRadarItem.id === undefined)
        {
            if (!this.isNullOrUndefined(this.selectedRadarItem.technology.id))
            {
                RadarItemService.addRadarItemExistingTechnology(userId,
                    this.selectedRadar.id,
                    this.selectedRadarCategory.id,
                    this.selectedRadarRing.id,
                    this.selectedConfidence,
                    this.selectedRadarItem.details,
                    this.selectedRadarItem.technology.id,
                    successCallback, failureCallback);
            }
            else
            {
                RadarItemService.addRadarItemNewTechnology(userId,
                    this.selectedRadar.id,
                    this.selectedRadarCategory.id,
                    this.selectedRadarRing.id,
                    this.selectedConfidence,
                    this.selectedRadarItem.details,
                    this.selectedRadarItem.technology.name,
                    this.selectedRadarItem.technology.url,
                    this.selectedRadarCategory.id,
                    successCallback, failureCallback);
            }
        }
        else
        {
            RadarItemService.updateRadarItem(userId,
                this.selectedRadar.id,
                this.selectedRadarCategory.id,
                this.selectedRadarRing.id,
                this.selectedConfidence,
                this.selectedRadarItem.details,
                this.selectedRadarItem.technology,
                this.selectedRadarItem,
                successCallback, failureCallback);
        }
    };
});
