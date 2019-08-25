export class RadarRingRepository{
    createDefault() {
        var retVal = {};
        retVal.name = '';
        retVal.description = '';
        retVal.isPublished = false;
        retVal.radarRings = [];
        retVal.radarRings.push(this.createDefaultRadarRing(-1, "Radar Ring 1", "Radar Ring 1", 1));
        retVal.radarRings.push(this.createDefaultRadarRing(-1, "Radar Ring 2", "Radar Ring 2", 2));
        retVal.radarRings.push(this.createDefaultRadarRing(-1, "Radar Ring 3", "Radar Ring 3", 3));
        retVal.radarRings.push(this.createDefaultRadarRing(-1, "Radar Ring 4", "Radar Ring 4", 4));

        return retVal;
    }

    createDefaultRadarRing(id, name, description, option){
            var retVal = {};
            retVal.id = id;
            retVal.name= name;
            retVal.description = description;
            retVal.displayOption = option;
            return retVal;
    }

    getByUserId(userId, successHandler) {
        var url = '/api/User/' + userId + '/RadarRingSets';

        jQuery.ajax({
                url: url,
                async: true,
                dataType: 'json',
                success: function (radarRingSets) {
                    successHandler(radarRingSets);
                }
            });
    }

    addRadarRingSet(userId, radarRingSet, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "POST",
              url: '/api/User/' + userId + '/RadarRingSet',
              data: JSON.stringify(radarRingSet),
              success: function(radarRingSet) {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    updateRadarRingSet(userId, radarRingSet, successHandler) {
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/RadarRingSet/' + radarRingSet.id,
              data: JSON.stringify(radarRingSet),
              success: function() {
                    successHandler();
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    deleteRadarType(userId, radarRingSetId, successHandler){
         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "DELETE",
              url: '/api/User/' + userId + '/RadarRingSet/' + radarRingSetId,
             success: function() {
                   responseHandler();
              },
              error: function(xhr, status, err) {
                    responseHandler();
              }
            });
    }

};