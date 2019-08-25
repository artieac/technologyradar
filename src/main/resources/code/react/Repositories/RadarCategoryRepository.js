export class RadarCategoryRepository{
    createDefault() {
        var retVal = {};
        retVal.name = '';
        retVal.description = '';
        retVal.isPublished = false;
        retVal.radarCategories = [];
        retVal.radarCategories.push(this.createDefaultRadarCategory(-1, "Radar Category 1", "Radar Category 1", 1));
        retVal.radarCategories.push(this.createDefaultRadarCategory(-1, "Radar Category 2", "Radar Category 2", 2));
        retVal.radarCategories.push(this.createDefaultRadarCategory(-1, "Radar Category 3", "Radar Category 3", 3));
        retVal.radarCategories.push(this.createDefaultRadarCategory(-1, "Radar Category 4", "Radar Category 4", 4));

        return retVal;
    }

    createDefaultRadarCategory(id, name, description, option){
            var retVal = {};
            retVal.id = id;
            retVal.name= name;
            retVal.description = description;
            retVal.displayOption = option;
            return retVal;
    }

    getByUserId(userId, successHandler) {
        var url = '/api/User/' + userId + '/RadarCategorySets';

        jQuery.ajax({
                url: url,
                async: true,
                dataType: 'json',
                success: function (setList) {
                    successHandler(setList);
                }
            });
    }

    add(userId, radarCategorySet, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "POST",
              url: '/api/User/' + userId + '/RadarCategorySet',
              data: JSON.stringify(radarCategorySet),
              success: function(radarCategorySet) {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    update(userId, radarCategorySet, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/RadarCategorySet/' + radarCategorySet.id,
              data: JSON.stringify(radarCategorySet),
              success: function() {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    deleteRadarType(userId, radarCategorySetId, successHandler){
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "DELETE",
              url: '/api/User/' + userId + '/RadarCategorySet/' + radarCategorySetId,
             success: function() {
                   responseHandler();
              },
              error: function(xhr, status, err) {
                    responseHandler();
              }
            });
    }

};