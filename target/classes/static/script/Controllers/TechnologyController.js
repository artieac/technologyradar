theApp.controller('TechnologyController', function ($scope, $resource, $http) {
    $scope.getTechnologyAssessments = function (technologyId) {
        var getAssessmentItemsRequest = $resource ('/api/technology/:technologyId/assessments');
        $scope.assessmentList = getAssessmentItemsRequest.get({ technologyId: technologyId});
    };
});
