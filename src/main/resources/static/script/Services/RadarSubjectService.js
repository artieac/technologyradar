theApp.service('RadarSubjectService', function ($resource, $http) {
    this.searchByNameRequest = function (radarSubjectName)
    {
        return $resource('/api/RadarSubject/search?name=:radarSubjectName');
    };

    this.searchRequest = function(radarSubjectName, selectedCateogory, selectedRadarRing)
    {
        var queryString = "";

        if(radarSubjectName)
        {
            queryString += "name=" + radarSubjectName;
        }

        if(selectedCateogory)
        {
            queryString += "&radarCategoryId=" + selectedCateogory.id;
        }

        if(selectedRadarRing)
        {
            queryString += "&radarRingId=" + selectedRadarRing.id;
        }

        return $resource ('/api/RadarSubject/search?' + queryString);
    };

    this.searchByCategoryId = function(categoryId)
    {
        return $resource ('/api/RadarSubject?categoryId=:categoryId');
    };
});