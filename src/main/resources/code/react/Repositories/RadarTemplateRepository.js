export class RadarTemplateRepository {
    createDefaultRadarTemplate(name){
            var retVal = {};
            retVal.id = '';
            retVal.name= name;

            retVal.radarRings = [];
            retVal.radarRings.push(this.createDefaultRadarTemplateDetail(-1, "RadarRingOne", "1"));
            retVal.radarRings.push(this.createDefaultRadarTemplateDetail(-2, "RadarRingTwo", "2"));
            retVal.radarRings.push(this.createDefaultRadarTemplateDetail(-3, "RadarRingThree", "3"));
            retVal.radarRings.push(this.createDefaultRadarTemplateDetail(-4, "RadarRingFour", "4"));

            retVal.radarCategories = [];
            retVal.radarCategories.push(this.createDefaultRadarTemplateDetail(-1, "RadarCategoryOne", "#8FA227"));
            retVal.radarCategories.push(this.createDefaultRadarTemplateDetail(-2, "RadarCategoryTwo", "#8FA227"));
            retVal.radarCategories.push(this.createDefaultRadarTemplateDetail(-3, "RadarCategoryTwo", "#8FA227"));
            retVal.radarCategories.push(this.createDefaultRadarTemplateDetail(-4, "RadarCategoryTwo", "#8FA227"));

            return retVal;
    }

    createDefaultRadarTemplateDetail(id, name, option){
            var retVal = {};
            retVal.id = id;
            retVal.name= name;
            retVal.displayOption = option;
            return retVal;
    }

    getByUserId(userId, successHandler) {
        var getUrl = '/api/User/' + userId + '/RadarTemplates';

        jQuery.ajax({
                url: getUrl,
                async: true,
                dataType: 'json',
                success: function (radarTemplateCollection) {
                    successHandler(radarTemplateCollection);
                }.bind(this)
            });
    }

    getMostRecentByUserId(userId, successHandler) {
        var getUrl = '/api/User/' + userId + '/RadarTemplates?mostRecent=true';

        jQuery.ajax({
                url: getUrl,
                async: true,
                dataType: 'json',
                success: function (radarTemplateCollection) {
                    successHandler(radarTemplateCollection);
                }.bind(this)
            });
    }

    getHistory(userId, radarTemplateId, successHandler){
        var getUrl = '/api/User/' + userId + '/RadarTemplate/' + radarTemplateId;

        jQuery.ajax({
                url: getUrl,
                async: true,
                dataType: 'json',
                success: function (radarTemplateHistory) {
                    successHandler(radarTemplateHistory);
                }.bind(this)
            });
    }

    getOwnedAndAssociatedByUserId(userId, successHandler){
        var url = '/api/User/' + userId + '/RadarTemplates?includeOwned=true&includeAssociated=true';

        jQuery.ajax({
                 url: url,
                 async: true,
                 dataType: 'json',
                 success: function (radarTemplateCollection) {
                     successHandler(radarTemplateCollection);
                 }.bind(this)
             });
     }

    getOtherUsersSharedRadarTemplates(userId, successHandler){
        var url = '/api//RadarTemplates/Shared?excludeUser=' + userId;
       jQuery.ajax({
                url: url,
                async: true,
                dataType: 'json',
                success: function (radarTemplateCollection) {
                    successHandler(radarTemplateCollection);
                }.bind(this)
            });
    }

    getAssociatedRadarTemplates(userId, successHandler){
       jQuery.ajax({
                url: '/api/User/' + userId + '/RadarTemplates/Associated',
                async: true,
                dataType: 'json',
                success: function (radarTemplateCollection) {
                    successHandler(radarTemplateCollection);
                }.bind(this)
            });
    }

    addRadarTemplate(userId, radarTemplate, successHandler) {
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

    updateRadarTemplate(userId, radarTemplate, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/RadarTemplate/' + radarTemplate.id,
              data: JSON.stringify(radarTemplate),
              success: function() {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    deleteRadarTemplate(userId, radarTemplateId, successHandler){
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

    deleteRadarRing(userId, radarTemplateId, radarRingId, successHandler){
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "DELETE",
              url: '/api/User/' + userId + '/RadarTemplate/' + radarTemplateId + '/ring/' + radarRingId,
             success: function() {
                   successHandler();
              }
            });
    }

    associateRadarTemplate(userId, radarTemplateId, shouldAssociate, successHandler, errorHandler) {
         var radarTemplateAssociation = {};
         radarTemplateAssociation.shouldAssociate = shouldAssociate;

         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/RadarTemplate/' + radarTemplateId + '/Associate',
              data: JSON.stringify(radarTemplateAssociation),
              success: function() {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
        }
};