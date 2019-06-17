export class RadarRepository{

    getByUserId(userId, successHandler) {
        jQuery.ajax({
                url: '/api/User/' + userId + '/Radars',
                async: true,
                dataType: 'json',
                success: function (radarCollection) {
                    successHandler(radarCollection);
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
              success: function() {
                    successHandler();
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
              success: function() {
                successHandler();
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

        $.post({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "POST",
              url: '/api/User/' + userId + '/Radar',
              data: JSON.stringify(radarToAdd),
              success: function() {
                successHandler();
               },
               error: function(xhr, status, err){
                    errorHandler();
               }
            });
    }
};