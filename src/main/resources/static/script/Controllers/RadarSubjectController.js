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
        $scope.radarSubjectSearchResults = RadarSubjectService.searchRequest(radarSubjectName, $scope.selectedRadarTemplate, $scope.selectedRadarCategory, $scope.selectedRadarRing, $scope.isAnonymous).query();
    };

    $scope.selectRadarSubject = function(radarSubject)
    {
        $scope.radarSubject = radarSubject;
    }

    $scope.selectRadarTemplate = function(radarTemplate)
    {
        $scope.selectedRadarTemplate = radarTemplate;

        $scope.selectedRadarCategory = null;
        $scope.radarCategories = radarTemplate.radarCategories;

        $scope.selectedRadarRing = null;
        $scope.radarRings = radarTemplate.radarRings;
    }

    $scope.getPublishedRadarTemplates = function()
    {
        var getPublishedRadarTemplates = $resource('/api/public/RadarTemplates/Published');
        $scope.publishedRadarTemplates = getPublishedRadarTemplates.query();
    }

    $scope.getAssociatedRadarTemplates = function()
    {
        var getAssociatedRadarTemplates = $resource('/api/User/:userId/RadarTemplates?includeOwned=false&includeAssociated=true');
        $scope.associatedRadarTemplates = getAssociatedRadarTemplates.query({userId: $scope.currentUserId});
    }

    $scope.getUserRadarTemplates = function()
    {
        var getOwnedRadarTemplates = $resource('/api/User/:userId/RadarTemplates/Radared');
        $scope.userOwnedRadarTemplates = getOwnedRadarTemplates.query({userId: $scope.currentUserId});
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
