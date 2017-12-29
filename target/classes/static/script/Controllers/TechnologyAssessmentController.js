theApp.controller('TechnologyAssessmentController', function ($scope, $resource, $http) {
    $scope.currentUserId = $('#userId').val();
    $scope.selectedAssessment = null;

    $scope.getTechnologyAssessments = function(userId) {
        var getTechnologyAssessmentRequest = $resource ('/api/User/:userId/TechnologyAssessments', { userId: userId});
        $scope.assessmentList = getTechnologyAssessmentRequest.query();
    }

    $scope.addTechnologyAssessment = function(userId){
        $http.post('/api/TechnologyAssessment/' + assessmentId + '/User/' + userId, $scope.newTechnologyAssessment)
            .then(function (data) {
                $scope.getTechnologyAssessmentRequest(userId);
            });

    }

    $scope.deleteAssessment = function(userId, assessmentId){
        $http.delete('/api/TechnologyAssessment/' + assessmentId + '/User/' + userId)
            .then(function (data) {
                $scope.getTechnologyAssessmentRequest(userId);
            });
    }
});
