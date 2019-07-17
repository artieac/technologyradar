theApp.controller('RadarSubjectController', function ($scope, $resource, $http, RadarInstanceService, RadarSubjectService)
{
    $scope.isAnonymous = ($('#isAnonymous').val() == 'true');
    $scope.radarSubjectDetailsSelectionId = $('#radarSubjectId').val();
    $scope.currentUserId = $("#currentUserId").val();

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
        $scope.radarSubjectSearchResults = RadarSubjectService.searchRequest(radarSubjectName, $scope.selectedRadarType, $scope.selectedRadarCategory, $scope.selectedRadarRing, $scope.isAnonymous).query();
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

    $scope.getPublishedRadarTypes = function()
    {
        if($scope.isAnonymous==true)
        {
            var getPublishedRadarTypes = $resource('/api/public/RadarTypes/Shared');
            $scope.publishedRadarTypes = getPublishedRadarTypes.query();
        }
        else
        {
            var getPublishedRadarTypes = $resource('/api/public/RadarTypes/Shared');
            $scope.publishedRadarTypes = getPublishedRadarTypes.query();
        }
    }

    $scope.getAssociatedRadarTypes = function()
    {
        var getAssociatedRadarTypes = $resource('/api/User/:userId/RadarTypes?includeOwned=false&includeAssociated=true');
        $scope.associatedRadarTypes = getAssociatedRadarTypes.query({userId: $scope.currentUserId});
    }

    $scope.getUserRadarTypes = function()
    {
        var getOwnedRadarTypes = $resource('/api/User/:userId/RadarTypes?includeOwned=true&includeAssociated=true');
        $scope.userOwnedRadarTypes = getOwnedRadarTypes.query({userId: $scope.currentUserId});
    }

    $scope.getRadarSubjectAssessments = function()
    {
        if($scope.isAnonymous==true)
        {
            var getRadarSubjectAssessments = $resource('/api/public/RadarSubject/:radarSubjectId/assessments');
            $scope.radarSubjectAssessments = getRadarSubjectAssessments.get({radarSubjectId : this.radarSubjectDetailsSelectionId});
        }
        else
        {
            var getRadarSubjectAssessments = $resource('/api/public/RadarSubject/:radarSubjectId/assessments');
            $scope.radarSubjectAssessments = getRadarSubjectAssessments.get({radarSubjectId : this.radarSubjectDetailsSelectionId});
        }
    }
});
