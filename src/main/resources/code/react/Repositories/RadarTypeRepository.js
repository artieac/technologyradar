export class RadarTypeRepository {
    createDefaultRadarType(name){
            var retVal = {};
            retVal.id = -1;
            retVal.name= name;

            retVal.radarRings = [];
            retVal.radarRings.push(this.createDefaultRadarTypeDetail(-1, "RadarRingOne", "1"));
            retVal.radarRings.push(this.createDefaultRadarTypeDetail(-2, "RadarRingTwo", "2"));
            retVal.radarRings.push(this.createDefaultRadarTypeDetail(-3, "RadarRingThree", "3"));
            retVal.radarRings.push(this.createDefaultRadarTypeDetail(-4, "RadarRingFour", "4"));

            retVal.radarCategories = [];
            retVal.radarCategories.push(this.createDefaultRadarTypeDetail(-1, "RadarCategoryOne", "#8FA227"));
            retVal.radarCategories.push(this.createDefaultRadarTypeDetail(-2, "RadarCategoryTwo", "#8FA227"));
            retVal.radarCategories.push(this.createDefaultRadarTypeDetail(-3, "RadarCategoryTwo", "#8FA227"));
            retVal.radarCategories.push(this.createDefaultRadarTypeDetail(-4, "RadarCategoryTwo", "#8FA227"));

            return retVal;
    }

    createDefaultRadarTypeDetail(id, name, option){
            var retVal = {};
            retVal.id = id;
            retVal.name= name;
            retVal.displayOption = option;
            return retVal;
    }

    getByUserId(userId, successHandler) {
        jQuery.ajax({
                url: '/api/User/' + userId + '/RadarTypes',
                async: true,
                dataType: 'json',
                success: function (radarTypeCollection) {
                    successHandler(radarTypeCollection);
                }.bind(this)
            });
    }

    getOtherUsersSharedRadarTypes(userId, successHandler){
       jQuery.ajax({
                url: '/api/User/' + userId + '/RadarTypes?includeOwned=false&includeSharedByOthers=true',
                async: true,
                dataType: 'json',
                success: function (radarTypeCollection) {
                    successHandler(radarTypeCollection);
                }.bind(this)
            });
    }

    getAssociatedRadarTypes(userId, successHandler){
       jQuery.ajax({
                url: '/api/User/' + userId + '/RadarTypes?includeOwned=false&includeAssociated=true',
                async: true,
                dataType: 'json',
                success: function (radarTypeCollection) {
                    successHandler(radarTypeCollection);
                }.bind(this)
            });
    }

    getOwnedAndAssociatedByUserId(userId, successHandler){
        jQuery.ajax({
                 url: '/api/User/' + userId + '/RadarTypes?includeOwned=true&includeAssociated=true',
                 async: true,
                 dataType: 'json',
                 success: function (radarTypeCollection) {
                     successHandler(radarTypeCollection);
                 }.bind(this)
             });
     }

    addRadarType(userId, radarType, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "POST",
              url: '/api/User/' + userId + '/RadarType',
              data: JSON.stringify(radarType),
              success: function(radarType) {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    updateRadarType(userId, radarType, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/RadarType/' + radarType.id,
              data: JSON.stringify(radarType),
              success: function() {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    deleteRadarType(userId, radarType, successHandler){
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "DELETE",
              url: '/api/User/' + userId + '/RadarType/' + radarType.id,
             success: function() {
                   successHandler();
              }
            });
    }

    deleteRadarRing(userId, radarTypeId, radarRingId, successHandler){
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "DELETE",
              url: '/api/User/' + userId + '/RadarType/' + radarTypeId + '/ring/' + radarRingId,
             success: function() {
                   successHandler();
              }
            });
    }

    associateRadarType(userId, radarTypeId, shouldAssociate, successHandler, errorHandler) {
         var radarTypeAssociation = {};
         radarTypeAssociation.shouldAssociate = shouldAssociate;

         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/RadarType/' + radarTypeId + '/Associate',
              data: JSON.stringify(radarTypeAssociation),
              success: function() {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
        }
};