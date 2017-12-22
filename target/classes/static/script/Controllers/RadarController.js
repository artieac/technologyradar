theApp.controller('RadarController', function ($scope, $resource, $http) {
    $scope.getRadarData = function (teamId, assessmentId) {
        var getRadarDataRequest = $resource('/api/radar/team/:teamId/assessment/:assessmentId', { teamId: teamId, assessmentId: assessmentId });
        getRadarDataRequest.get(function(data){

            // What we return here is the data that will be accessible
            // to us after the promise resolves
            $scope.radarData = data;
        });
    };

    $scope.getRadarTeams = function() {
        var getTeamsRequest = $resource('/api/radar/teams');
        $scope.teamList = getTeamsRequest.query();
    }

    $scope.teamDropdownSelected = function(team) {
        $scope.selectedTeam = team;

        var getTeamAssessmentsRequest = $resource ('/api/radar/team/:teamId/assessments', { teamId: team.id});
        $scope.assessmentList = getTeamAssessmentsRequest.query(function(data){
            if(data.length == 0)
            {
                var getRadarDataRequest = $resource('/api/radar/team/:teamId/assessment/0', { teamId: $scope.selectedTeam.id});
                getRadarDataRequest.get(function(data){
                    $scope.renderRadar(data);
                });
            }
        });
    }

    $scope.assessmentDropdownSelected = function(assessment) {
        $scope.selectedAssessment = assessment;

        /// I know i can just use the item passed into this, makign an extra call now because I know I want to
        // switch the team/assessment lists into just names/ids
        var getRadarDataRequest = $resource('/api/radar/team/:teamId/assessment/:assessmentId', { teamId: $scope.selectedTeam.id, assessmentId: assessment.id });
        getRadarDataRequest.get(function(data){
            $scope.renderRadar(data);
        });
    }

    $scope.renderRadar = function(radarData) {
        $scope.radarData = radarData;
        
        var radar_arcs = [];

        for(var i = 0; i < radarData.radarArcs.length; i++) {
            radar_arcs.push({"r": radarData.rangeWidth * (i + 1), "name":radarData.radarArcs[i].radarState.name});
        }
        init(radarData.height,radarData.width,radarData.quadrants, radar_arcs);
    }

    $scope.getRadarStates = function() {
        var getStatesRequest = $resource('/api/radar/states');
        $scope.radarStateList = getStatesRequest.query();
    }

    $scope.selectRadarState = function(radarState){
        $scope.selectedRadarState = radarState;
    }

    $scope.getRadarCategories = function() {
        var getRadarCategoriesRequest = $resource('/api/radar/categories');
        $scope.radarCategoryList = getRadarCategoriesRequest.query();
    }

    $scope.selectRadarCategory = function(radarCategory){
        $scope.selectedRadarCategory = radarCategory;
    }

    $scope.addRadarItem = function () {
        $scope.newRadarItem.radarState = $scope.selectedRadarState.id;
        $scope.newRadarItem.radarCategory = $scope.selectedRadarCategory.id;
        $http.post('/api/radar/team/' + $scope.selectedTeam.id + '/assessment/' + $scope.selectedAssessment.id + '/additem', $scope.newRadarItem)
            .success(function (data) {
                $scope.renderRadar(data);
            });
    }
});