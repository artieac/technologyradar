export function RadarRepository_publishRadar(userId, radarId, isPublished, successHandler, errorHandler) {
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

export function RadarRepository_lockRadar(userId, radarId, isLocked, successHandler, errorHandler) {
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

export function RadarRepository_deleteRadar(userId, radarId, successHandler, errorHandler) {
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

export function RadarRepository_addRadar(userId, radarName, radarType, successHandler, errorHandler) {
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