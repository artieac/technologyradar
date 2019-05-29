export function RadarTypeRepository_getByUserId(userId, successHandler) {
    jQuery.ajax({
            url: '/api/User/' + userId + '/RadarTypes',
            async: true,
            dataType: 'json',
            success: function (radarTypeCollection) {
                successHandler(radarTypeCollection);
            }.bind(this)
        });
}

export function RadarTypeRepository_add(userId, radarType, successHandler) {
     var newItem = {};
     newItem.radarType = radarType;

     $.ajax({
          headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
          },
          type: "POST",
          url: '/api/User/' + userId + '/RadarType',
          data: JSON.stringify(newItem),
          success: function() {
                successHandler();
           },
          error: function(xhr, status, err) {
                errorHandler();
          }
        });
}

export function RadarTypeRepository_update(userId, radarType, successHandler) {
     var updateItem = {};
     updateItem.radarType = radarType;

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


