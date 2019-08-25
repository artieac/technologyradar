export class RadarTemplateRepository{
    createDefault() {
        var retVal = {};
        retVal.name = '';
        retVal.description = '';
        retVal.isPublished = false;
        retVal.radarCategorySet  = {};
        retVal.radarRingSet = {};

        return retVal;
    }

    getByUserId(userId, successHandler) {
        var url = '/api/User/' + userId + '/RadarTemplates';

        jQuery.ajax({
                url: url,
                async: true,
                dataType: 'json',
                success: function (setList) {
                    successHandler(setList);
                }
            });
    }

    add(userId, radarTemplate, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "POST",
              url: '/api/User/' + userId + '/RadarTemplate',
              data: JSON.stringify(radarTemplate),
              success: function(radarTemplate) {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    update(userId, radarTemplate, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/RadarTemplate/' + radarCategorySet.id,
              data: JSON.stringify(radarTemplate),
              success: function() {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    delete(userId, radarTemplateId, successHandler){
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "DELETE",
              url: '/api/User/' + userId + '/RadarTemplate/' + radarTemplateId,
             success: function() {
                   responseHandler();
              },
              error: function(xhr, status, err) {
                    responseHandler();
              }
            });
    }

};