theApp.controller('TechnologyAssessmentController', function ($scope, $resource, $http) {
    $scope.currentUserId = $('#userId').val();
    $scope.assessmentId = $('#assessmentId').val();
    $scope.selectedAssessment = null;

    $scope.getTechnologyAssessments = function(userId) {
        var getTechnologyAssessmentRequest = $resource ('/api/TechnologyAssessments/User/:userId', { userId: userId});
        $scope.assessmentList = getTechnologyAssessmentRequest.query();
    }

    $scope.addTechnologyAssessment = function(assessmentId){
        $http.post('/api/TechnologyAssessment/' + assessmentId + '/User/' + userId, $scope.newTechnologyAssessment)
            .then(function (data) {
                $scope.getTechnologyAssessmentRequest(userId);
            });

    }

    $scope.deleteAssessmentItem = function(assessmentId, assessmentItemId, userId){
        $http.delete('/api/TechnologyAssessment/' + assessmentId + '/Item/' + assessmentItemId + '/User/' + userId)
            .then(function (data) {
                $scope.getAssessment(assessmentId, userId);
            });
    }

    $scope.getAssessment = function(assessmentId, userId){
        var getRadarDataRequest = $resource('/api/TechnologyAssessment/' + assessmentId + '/User/' + userId);
        getRadarDataRequest.get(function(data){
            $scope.currentAssessment = data;
        });
    }
});
