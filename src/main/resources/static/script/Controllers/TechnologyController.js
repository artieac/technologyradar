theApp.controller('TechnologyController', function ($scope, $resource, $http) {
    $scope.getTechnologyAssessments = function (technologyId) {
        var getAssessmentItemsRequest = $resource ('/api/technology/:technologyId/assessments');
        $scope.assessmentList = getAssessmentItemsRequest.get({ technologyId: technologyId});
    };

    $scope.getRadarRings = function() {
        var getRingsRequest = $resource('/api/radar/rings');
        $scope.radarRingList = getRingsRequest.query();
    }

    $scope.selectRadarRing = function(radarRing){
        $scope.selectedRadarRing = radarRing;
    }

    $scope.getRadarCategories = function() {
        var getRadarCategoriesRequest = $resource('/api/radar/categories');
        $scope.radarCategoryList = getRadarCategoriesRequest.query();
    }

    $scope.selectRadarCategory = function(radarCategory){
        $scope.selectedRadarCategory = radarCategory;
    }

    $scope.searchTechnology = function(){
        var queryString = "";

        var technologyName = jQuery("#searchName").val();
        if(technologyName){
            queryString += "technologyName=:technologyName"
        }

        var radarCategoryId = -1;
        if($scope.selectedRadarCategory){
            radarCategoryId = $scope.selectedRadarCategory.id;
            queryString += "&radarCategoryId=:radarCategoryId";
        }

        var radarRingId = -1;
        if($scope.selectedRadarRing){
            radarRingId = $scope.selectedRadarRing.id;
            queryString += "&radarRingId=:radarRingId";
        }

        var searchForTechnology = $resource ('/api/technology/search?' + queryString);
        $scope.technologySearchResults = searchForTechnology.query({ technologyName: technologyName, radarCategoryId: radarCategoryId, radarRingId: radarRingId });
    };

    $scope.searchForTechnologyByCategoryId = function(categoryId){
        var searchTechnologyByCategoryId = $resource ('/api/technology?categoryId=:categoryId');
        $scope.technologySearchResults = searchTechnologyByCategoryId.query({ categoryId: categoryId});
    };

    $scope.selectTechnology = function(technology){
        $scope.selectedTechnology = technology;
    }
});
