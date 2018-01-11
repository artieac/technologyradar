theApp.controller('TechnologyController', function ($scope, $resource, $http, RadarInstanceService, TechnologyService)
{
    $scope.getTechnologyAssessments = function (technologyId)
    {
        var getAssessmentItemsRequest = $resource ('/api/technology/:technologyId/assessments');
        $scope.assessmentList = getAssessmentItemsRequest.get({ technologyId: technologyId});
    };

    $scope.getRadarRings = function()
    {
        $scope.radarRingList = RadarInstanceService.getRadarRingsRequest().query();
    }

    $scope.selectRadarRing = function(radarRing)
    {
        $scope.selectedRadarRing = radarRing;
    }

    $scope.getRadarCategories = function()
    {
        $scope.radarCategoryList = RadarInstanceService.getRadarCategoriesRequest().query();
    }

    $scope.selectRadarCategory = function(radarCategory)
    {
        $scope.selectedRadarCategory = radarCategory;
    }

    $scope.searchTechnology = function()
    {
        var technologyName = jQuery("#searchName").val();
        $scope.technologySearchResults = TechnologyService.searchTechnologyRequest(technologyName, $scope.selectedRadarCategory, $scope.selectedRadarRing).query();
    };

    $scope.searchForTechnologyByCategoryId = function(categoryId)
    {
        var searchTechnologyByCategoryId = $resource ('/api/technology?categoryId=:categoryId');
        $scope.technologySearchResults = searchTechnologyByCategoryId.query({ categoryId: categoryId});
    };

    $scope.selectTechnology = function(technology)
    {
        $scope.selectedTechnology = technology;
    }
});
