theApp.controller('RadarSubjectController', function ($scope, $resource, $http, RadarInstanceService, RadarSubjectService)
{
    $scope.radarSubjectDetailsSelectionId = $('#radarSubjectId').val();

    $scope.getRadarSubject = function (radarSubjectId)
    {
        var getRadarSubjectsRequest = $resource ('/api/RadarSubject/:radarSubjectId/assessments');
        $scope.radarSubjectsList = getRadarSubjectsRequest.get({ radarSubjectId: radarSubjectId});
    };

    $scope.selectRadarRing = function(radarRing)
    {
        $scope.selectedRadarRing = radarRing;
    }

    $scope.selectRadarCategory = function(radarCategory)
    {
        $scope.selectedRadarCategory = radarCategory;
    }

    $scope.searchRadarSubjects = function()
    {
        var radarSubjectName = jQuery("#searchName").val();
        $scope.radarSubjectSearchResults = RadarSubjectService.searchRequest(radarSubjectName, $scope.selectedRadarCategory, $scope.selectedRadarRing).query();
    };

    $scope.searchForRadarSubjectByCategoryId = function(categoryId)
    {
        var searchRadarSubjectsByCategoryId = $resource ('/api/radarsubject?categoryId=:categoryId');
        $scope.radarSubjectSearchResults = searchRadarSubjectsByCategoryId.query({ categoryId: categoryId});
    };

    $scope.selectRadarSubject = function(radarSubject)
    {
        $scope.radarSubject = radarSubject;
    }

    $scope.selectRadarType = function(radarType)
    {
        $scope.selectedRadarType = radarType;

        $scope.selectedRadarCategory = null;
        $scope.radarCategories = radarType.radarCategories;

        $scope.selectedRadarRing = null;
        $scope.radarRings = radarType.radarRings;
    }

    $scope.getAllPublishedRadarTypes = function()
    {
        var getAllPublishedRadarTypes = $resource('/api/RadarTypes');
        $scope.allPublishedRadarTypes = getAllPublishedRadarTypes.query();
    }

    $scope.getAssociatedRadarTypes = function(userId)
    {
        var getAssociatedRadarTypes = $resource('/api/User/:userId/RadarTypes?includeOwned=false&includeAssociated=true');
        $scope.getAssociatedRadarTypes = getAssociatedRadarTypes.query({userId: userId});
    }

    $scope.getOwnedRadarTypes = function(userId)
    {
        var getOwnedRadarTypes = $resource('/api/User/:userId/RadarTypes?includeOwned=true');
        $scope.getOwnedRadarTypes = getOwnedRadarTypes.query();
    }

    $scope.getRadarSubjectAssessments = function(){
        var getRadarSubjectAssessments = $resource('/api/RadarSubject/:radarSubjectId/assessments');
        $scope.radarSubjectAssessments = getRadarSubjectAssessments.get({radarSubjectId : this.radarSubjectDetailsSelectionId});
    }
});
