export class RadarRepository{

    getByUserId(userId, canSeeHistory, successHandler) {
        var url = '/api/User/' + userId + '/Radars';

        if(canSeeHistory==true){
            url += "?getAllVersions=true";
        }

        jQuery.ajax({
                url: url,
                async: true,
                dataType: 'json',
                success: function (radarCollection) {
                    successHandler(radarCollection);
                }
            });
    }

    getByUserIdAndRadarId(userId, radarId, successHandler) {
        var url = '/api/User/' + userId + '/Radar/' + radarId;

        jQuery.ajax({
                url: url,
                async: true,
                dataType: 'json',
                success: function (radar) {
                    successHandler(radar);
                }
            });
    }

    getRadarsByUserIdAndRadarTypeId(userId, radarTypeId, successHandler){
        var url = '/api/User/' + userId + '/Radars?radarTypeId=' + radarTypeId;

        jQuery.ajax({
            url: url,
            async: true,
            dataType: 'json',
            success: function (radars) {
                successHandler(radars);
            }
        });
    }

    publishRadar(userId, radarId, isPublished, successHandler, errorHandler) {
         var radarToUpdate = {};
         radarToUpdate.isPublished = isPublished;

         $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/Radar/' + radarId + '/Publish',
              data: JSON.stringify(radarToUpdate),
              success: function(publishResponse) {
                    successHandler(publishResponse);
               },
              error: function(xhr, status, err) {
                    errorHandler();
              }
            });
    }

    lockRadar(userId, radarId, isLocked, successHandler, errorHandler) {
        var radarToUpdate = {};
        radarToUpdate.isLocked = isLocked;

        $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + userId + '/Radar/' + radarId + '/Lock',
              data: JSON.stringify(radarToUpdate),
              success: function() {
                    successHandler();
               },
               error: function(xhr, status, err) {
                    errorHandler();
             }
            });
    }

    deleteRadar(userId, radarId, successHandler, errorHandler) {
        $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "DELETE",
              url: '/api/User/' + userId + '/Radar/' + radarId,
              success: function(radars) {
                successHandler(radars);
               },
               error: function(xhr, status, err){
                    errorHandler();
                }
            });
    }

    addRadar(userId, radarName, radarType, successHandler, errorHandler) {
        var radarToAdd = {};
        radarToAdd.name = radarName;
        radarToAdd.radarTypeId = radarType.id;
        radarToAdd.radarTypeVersion = radarType.version;

        $.post({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "POST",
              url: '/api/User/' + userId + '/Radar',
              data: JSON.stringify(radarToAdd),
              success: function(radarTypes) {
                successHandler(radarTypes);
               },
               error: function(xhr, status, err){
                    errorHandler();
               }
            });
    }
};