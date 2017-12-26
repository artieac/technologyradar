theApp.controller('TechnologyAssessmentController', function ($scope, $resource, $http) {
    $scope.currentUserId = $('#userId').val();
    $scope.selectedAssessment = null;

    $scope.getTechnologyAssessments = function(userId) {
        var getTechnologyAssessmentRequest = $resource ('/api/User/:userId/TechnologyAssessments', { userId: userId});
        $scope.assessmentList = getTechnologyAssessmentRequest.query();
    }

    $scope.addTechnologyAssessment = function(userId){
        $http.post('/api/User/' + userId + '/TechnologyAssessment', $scope.newTechnologyAssessment)
            .then(function (data) {
                $scope.getTechnologyAssessmentRequest(userId);
            });

    }

    $scope.deleteAssessment = function(userId, assessmentId){
        $http.delete('/api/User/' + userId + '/TechnologyAssessment/' + assessmentId)
            .then(function (data) {
                $scope.getTechnologyAssessmentRequest(userId);
            });
    }
});
