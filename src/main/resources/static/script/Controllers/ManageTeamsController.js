theApp.controller('ManageTeamsController', function ($scope, $resource, $http) {
    $scope.teamListElements = {selectedTeam: 0};
    $scope.teamListElements = { selectedAssessment: 0};
    $scope.selectedTeamIndex;
    $scope.selectedTeamAssessmentIndex;

    $scope.getAllTeams = function (technologyId) {
        var getAllTeams = $resource ('/api/assessmentteams');
        $scope.assessmentTeams = getAllTeams.query();
    };

    $scope.addTeam = function () {
        $http.post('/api/assessmentteam', $scope.newTeam)
            .success(function (data) {
                $scope.assessmentTeams = data;
            });
    }

    $scope.selectEditedTeam = function(selectedIndex){
        $scope.selectedTeamIndex = selectedIndex;

        $scope.getTeamAssessments($scope.assessmentTeams[selectedIndex]);
    }

    $scope.updateTeam = function () {
        var editedTeam = $scope.assessmentTeams[$scope.selectedTeamIndex];
        $http.put('/api/assessmentteam/' + editedTeam.id, editedTeam)
            .success(function (data) {
                $scope.assessmentTeams = data;
            });
    }

    $scope.getTeamAssessments = function(assessmentTeam){
        var getTeamAssessments = $resource ('/api/assessmentteam/:teamId/assessments', { teamId: assessmentTeam.id});
        $scope.teamAssessments = getTeamAssessments.query();
    }

    $scope.addTeamAssessment = function () {
        $http.post('/api/assessmentteam/' + $scope.assessmentTeams[$scope.selectedTeamIndex].id + '/assessment', $scope.newTeamAssessment)
            .success(function (data) {
                $scope.teamAssessments = data;
            });
    }

    $scope.selectEditedTeamAssessment = function(selectedIndex){
        $scope.selectedTeamAssessmentIndex = selectedIndex;
    }

    $scope.updateTeamAssessment = function () {
        var editedTeamAssessment = $scope.teamAssessments[$scope.selectedTeamAssessmentIndex];
        $http.put('/api/assessmentteam/' + $scope.assessmentTeams[$scope.selectedTeamIndex].id + '/assessment/' + editedTeamAssessment.id, editedTeamAssessment)
            .success(function (data) {
                $scope.teamAssessments = data;
            });
    }
});
