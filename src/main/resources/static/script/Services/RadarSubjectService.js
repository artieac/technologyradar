theApp.service('RadarSubjectService', function ($resource, $http) {
    this.searchRequest = function(radarSubjectName, selectedRadarTemplate, selectedCateogory, selectedRadarRing, isAnonymous)
    {
        var queryString = "";

        if(radarSubjectName)
        {
            queryString += "name=" + radarSubjectName;
        }

        if(selectedRadarTemplate)
        {
            queryString +="&radarTemplateId=" + selectedRadarTemplate.id;
        }

        if(selectedCateogory)
        {
            queryString += "&radarCategoryId=" + selectedCateogory.id;
        }

        if(selectedRadarRing)
        {
            queryString += "&radarRingId=" + selectedRadarRing.id;
        }

        if(isAnonymous==true)
        {
            return $resource ('/api/public/RadarSubject/search?' + queryString);
        }
        else
        {
            return $resource ('/api/public/RadarSubject/search?' + queryString);
        }
    };
});