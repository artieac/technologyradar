theApp.service('TechnologyService', function ($resource, $http) {
    this.searchForTechnologyByNameRequest = function (technologyName)
    {
        return $resource('/api/technology/search?technologyName=:technologyName');
    };

    this.searchTechnologyRequest = function(technologyName, selectedCateogory, selectedRadarRing)
    {
        var queryString = "";

        if(technologyName)
        {
            queryString += "technologyName=" + technologyName;
        }

        if(selectedCateogory)
        {
            queryString += "&radarCategoryId=" + selectedCateogory.id;
        }

        if(selectedRadarRing)
        {
            queryString += "&radarRingId=" + selectedRadarRing.id;
        }

        return $resource ('/api/technology/search?' + queryString);
    };

    this.searchForTechnologyByCategoryId = function(categoryId)
    {
        return $resource ('/api/technology?categoryId=:categoryId');
    };
});