theApp.service('RadarSubjectService', function ($resource, $http) {
    this.searchByNameRequest = function (radarSubjectName)
    {
        return $resource('/api/RadarSubject/search?name=:radarSubjectName');
    };

    this.searchRequest = function(radarSubjectName, selectedRadarType, selectedCateogory, selectedRadarRing, publicOnly)
    {
        var queryString = "";

        if(radarSubjectName)
        {
            queryString += "name=" + radarSubjectName;
        }

        if(selectedRadarType)
        {
            queryString +="&radarTypeId=" + selectedRadarType.id;
        }

        if(selectedCateogory)
        {
            queryString += "&radarCategoryId=" + selectedCateogory.id;
        }

        if(selectedRadarRing)
        {
            queryString += "&radarRingId=" + selectedRadarRing.id;
        }

        return $resource ('/api/public/RadarSubject/search?' + queryString);
    };

    this.searchByCategoryId = function(categoryId)
    {
        return $resource ('/api/RadarSubject?categoryId=:categoryId');
    };
});