class RadarCollectionStore{
    constructor(props){
        this.radarCollection = [];
    }

    onGetByUserId(userId, successFunction, errorFunction) {
        var returnValue = new Array();

        jQuery.ajax({
            url: '/api/User/' + userId + '/Radars',
            async: true,
            dataType: 'json',
            success: successFunction (requestData),
            error: errorFunction(xhr, status, err)
        });

        return returnValue;
    }

    onGetByUserIdTwo(userId) {
        jQuery.ajax({
            url: '/api/User/' + userId + '/Radars',
            async: true,
            dataType: 'json',
           success: function (requestData) {
               console.log(requestData);
               this.radarCollection = requestData;
           }.bind(this),
           error: function(xhr, status, err) {
               console.error(url, status, err.toString());
           }.bind(this)
        });

        return returnValue;
    }
}

export default RadarCollectionStore;