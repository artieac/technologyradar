theApp.controller('RadarController', function ($scope, $resource, $http) {

    $scope.currentUserId = $('#userId').val();
    $scope.selectedAssessmentItem = {};

    $scope.getRadarData = function (teamId, assessmentId) {
        var getRadarDataRequest = $resource('/api/radar/team/:teamId/assessment/:assessmentId', { teamId: teamId, assessmentId: assessmentId });
        getRadarDataRequest.get(function(data){

            // What we return here is the data that will be accessible
            // to us after the promise resolves
            $scope.radarData = data;
        });
    };


    $scope.getTechnologyAssessments = function(userId) {
        var getTechnologyAssessmentRequest = $resource ('/api/User/:userId/TechnologyAssessments', { userId: userId});
        $scope.assessmentList = getTechnologyAssessmentRequest.query(function(data){
            var assessmentId = $("#assessmentId").val();

            if(assessmentId!==null &&
                assessmentId!==undefined &&
                assessmentId!==''){
                for(var i = 0; i < data.length; i++) {
                    if(data[i].id==assessmentId){
                        $scope.assessmentDropdownSelected(userId, data[i]);
                        break;
                    }
                }
            }
        })
    }

    $scope.assessmentDropdownSelected = function(userId, assessment) {
        $scope.selectedAssessment = assessment;

        /// I know i can just use the item passed into this, makign an extra call now because I know I want to
        // switch the team/assessment lists into just names/ids
        var getRadarDataRequest = $resource('/api/User/' + userId + '/TechnologyAssessment/' + assessment.id);
        getRadarDataRequest.get(function(data){
            $scope.renderRadar(data);
        });
    }

    $scope.renderRadar = function(radarData) {
        var radar_arcs = [];

        $scope.radarData = radarData;

        for(var i = 0; i < radarData.radarArcs.length; i++) {
            radar_arcs.push({"r": radarData.rangeWidth * (i + 1), "name":radarData.radarArcs[i].radarRing.name});
        }
        init(radarData.height,radarData.width,radarData.quadrants, radar_arcs, $scope);
    }

    $scope.getRadarRings = function() {
        var getRingsRequest = $resource('/api/radar/rings');
        $scope.radarRingList = getRingsRequest.query();
    }

    $scope.selectRadarRing = function(radarRing){
        $scope.selectedRadarRing = radarRing;
    }

    $scope.getRadarCategories = function() {
        var getRadarCategoriesRequest = $resource('/api/radar/categories');
        $scope.radarCategoryList = getRadarCategoriesRequest.query();
    }

    $scope.selectRadarCategory = function(radarCategory){
        $scope.selectedRadarCategory = radarCategory;
    }

    $scope.confidenceLevels = [1,2,3,4,5,6,7,8,9,10]
    $scope.selectedConfidence = 5;

    $scope.selectConfidence = function(confidence){
        $scope.selectedConfidence = confidence;
    }

    $scope.isSaving = false;

    $scope.addRadarItem = function (userId) {
        var radarSaveItem = {};

        $scope.isSaving = true;

        if($scope.selectedAssessmentItem.id === undefined)
        {
            radarSaveItem.technologyName = $scope.selectedAssessmentItem.technology.name;
            radarSaveItem.radarCategory = $scope.selectedRadarCategory.id;
            radarSaveItem.url = $scope.selectedAssessmentItem.technology.url;
            radarSaveItem.radarRing = $scope.selectedRadarRing.id;

            if($scope.selectedAssessmentItem.confidenceFactor === undefined)
            {
                $scope.selectedAssessmentItem.confidenceFactor = 5;
            }

            radarSaveItem.confidenceLevel = $scope.selectedAssessmentItem.confidenceFactor;
            radarSaveItem.assessmentDetails = $scope.selectedAssessmentItem.details;

            $http.post('/api/User/' + userId + '/TechnologyAssessment/' + $scope.selectedAssessment.id + '/Item', radarSaveItem)
                .success(function (data) {
                    $scope.renderRadar(data);
                    $scope.clearAssessmentItemSelection();
                    $scope.isSaving = false;
                });
        }
        else
        {
            radarSaveItem.radarRing = $scope.selectedRadarRing.id;
            radarSaveItem.confidenceLevel = $scope.selectedAssessmentItem.confidenceFactor;
            radarSaveItem.assessmentDetails = $scope.selectedAssessmentItem.details;
            radarSaveItem.evaluator = $scope.selectedAssessmentItem.assessor;
            radarSaveItem.radarCategoryId = $scope.selectedRadarCategory.id;

            $http.post('/api/User/' + userId + '/TechnologyAssessment/' + $scope.selectedAssessment.id + '/Item', radarSaveItem)
                .success(function (data) {
                    $scope.renderRadar(data);
                    $scope.clearAssessmentItemSelection();
                    $scope.isSaving = false;
                });

        }
    }

    $scope.canAddRadarItem = function(isFormValid){
        var retVal = false;

        if($scope.selectedAssessment != null && isFormValid === true && $scope.isSaving===false){
            retVal = true;
        }

        return retVal;
    }

    $scope.searchForTechnologyByName = function(){
        var technologyName = jQuery("#newTechnologyName").val();
        var searchTechnologyByNameRequest = $resource ('/api/technology/search?technologyName=:technologyName');
        $scope.technologySearchResults = searchTechnologyByNameRequest.query({ technologyName: technologyName});
    };

    $scope.selectTechnology = function(technology){
        $scope.selectedRadarCategory = technology.radarCategory;
        $scope.selectedAssessmentItem.technology = technology;
        $scope.technologySearchResults = null;
    };

    $scope.clearAssessmentItemSelection = function() {
        $scope.selectedAssessmentItem = {};
    }

    $scope.selectAssessmentItem = function(assessmentItem) {
        $scope.selectedAssessmentItem = assessmentItem;
        $scope.selectTechnology(assessmentItem.technology);
        $scope.selectedRadarRing = $scope.selectedAssessmentItem.radarRing;
        $scope.selectedConfidence = $scope.selectedAssessmentItem.confidenceFactor;
        $scope.$digest();
    }

    $scope.clickAssessmentItem = function(id) {
        for(var i = 0; i < $scope.selectedAssessment.technologyAssessmentItems.length; i++){
            if($scope.selectedAssessment.technologyAssessmentItems[i].id == id){
                var assessmentItem = $scope.selectedAssessment.technologyAssessmentItems[i];
                $scope.selectAssessmentItem(assessmentItem);
                break;
            }
        }
    }

    $scope.clickSeeOtherUsers = function() {
        window.location.href = "/technology/" + $scope.selectedAssessmentItem.technology.id;
    }
});