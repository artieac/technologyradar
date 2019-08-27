export class TeamRepository {
     getAllByUser(userId, successHandler) {
        var getUrl = '/api/User/' + userId + '/Teams';

        jQuery.ajax({
                url: getUrl,
                async: true,
                dataType: 'json',
                success: function (teams) {
                    successHandler(teams);
                }.bind(this)
            });
        }

     getTeam(userId, teamId, successHandler) {
        var getUrl = '/api/User/' + userId + '/Team/' + teamId;

        jQuery.ajax({
                url: getUrl,
                async: true,
                dataType: 'json',
                success: function (teams) {
                    successHandler(teams);
                }.bind(this)
            });
        }

        addTeam(userId, teamName, responseHandler) {
            var teamToAdd = {};
            teamToAdd.name = teamName;

            $.post({
                  headers: {
                          'Accept': 'application/json',
                          'Content-Type': 'application/json'
                  },
                  type: "POST",
                  url: '/api/User/' + userId + '/Team',
                  data: JSON.stringify(teamToAdd),
                  success: function(teams) {
                    responseHandler(true, teams);
                   },
                   error: function(xhr, status, err){
                        responseHandler(false);
                   }
                });
        }

        updateTeamRadar(userId, teamId, radarId, allowAccess, responseHandler){
            var radarToUpdate = {};
            radarToUpdate.radarId = radarId;
            radarToUpdate.allowAccess = allowAccess;

            $.post({
                  headers: {
                          'Accept': 'application/json',
                          'Content-Type': 'application/json'
                  },
                  type: "POST",
                  url: '/api/User/' + userId + '/Team/' + teamId + '/Radar',
                  data: JSON.stringify(radarToUpdate),
                  success: function(team) {
                    responseHandler(true, team);
                   },
                   error: function(xhr, status, err){
                        responseHandler(false);
                   }
                });
        }
}
