theApp.controller('RadarSubjectController', function ($scope, $resource, $http, RadarInstanceService, RadarSubjectService)
{
    $scope.currentUserId = $('#userId').val();
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

    $scope.getAssociatedRadarTypes = function()
    {
        var getAssociatedRadarTypes = $resource('/api/User/:userId/RadarTypes?includeOwned=false&includeAssociated=true');
        $scope.getAssociatedRadarTypes = getAssociatedRadarTypes.query({userId: $scope.currentUserId});
    }

    $scope.getUserRadarTypes = function()
    {
        var getOwnedRadarTypes = $resource('/api/User/:userId/RadarTypes?includeOwned=true&includeAssociated=true');
        $scope.userOwnedRadarTypes = getOwnedRadarTypes.query({userId: $scope.currentUserId});
    }

    $scope.getRadarSubjectAssessments = function(){
        var getRadarSubjectAssessments = $resource('/api/RadarSubject/:radarSubjectId/assessments');
        $scope.radarSubjectAssessments = getRadarSubjectAssessments.get({radarSubjectId : this.radarSubjectDetailsSelectionId});
    }
});
