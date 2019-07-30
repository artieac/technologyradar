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
}
