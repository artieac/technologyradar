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
     $.ajax({
          headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
          },
          type: "POST",
          url: '/api/User/' + userId + '/RadarType',
          data: JSON.stringify(radarType),
          success: function() {
                successHandler();
           },
          error: function(xhr, status, err) {
                errorHandler();
          }
        });
}

export function RadarTypeRepository_update(userId, radarType, successHandler) {
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

export function RadarTypeRepository_createDefaultRadarType(name){
        var retVal = {};
        retVal.id = -1;
        retVal.name= name;

        retVal.radarRings = [];
        retVal.radarRings.push(RadarTypeRepository_createDefaultRadarTypeDetail("RadarRingOne", "1"));
        retVal.radarRings.push(RadarTypeRepository_createDefaultRadarTypeDetail("RadarRingTwo", "2"));
        retVal.radarRings.push(RadarTypeRepository_createDefaultRadarTypeDetail("RadarRingThree", "3"));
        retVal.radarRings.push(RadarTypeRepository_createDefaultRadarTypeDetail("RadarRingFour", "4"));

        retVal.radarCategories = [];
        retVal.radarCategories.push(RadarTypeRepository_createDefaultRadarTypeDetail("RadarCategoryOne", "#8FA227"));
        retVal.radarCategories.push(RadarTypeRepository_createDefaultRadarTypeDetail("RadarCategoryTwo", "#8FA227"));
        retVal.radarCategories.push(RadarTypeRepository_createDefaultRadarTypeDetail("RadarCategoryTwo", "#8FA227"));
        retVal.radarCategories.push(RadarTypeRepository_createDefaultRadarTypeDetail("RadarCategoryTwo", "#8FA227"));

        return retVal;
}

export function RadarTypeRepository_createDefaultRadarTypeDetail(name, option){
        var retVal = {};
        retVal.id = -1;
        retVal.name= name;
        retVal.displayOption = option;
        return retVal;
}

