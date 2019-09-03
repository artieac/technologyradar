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

        saveRadars(userId, team, responseHandler){
            var teamToUpdate = {};
            teamToUpdate.name = team.name;
            teamToUpdate.radars = [];

            for(var i = 0; i < team.radars.length; i++)
            {
                teamToUpdate.radars.push(team.radars[i].id);
            }

            $.post({
                  headers: {
                          'Accept': 'application/json',
                          'Content-Type': 'application/json'
                  },
                  type: "POST",
                  url: '/api/User/' + userId + '/Team/' + team.id + '/Radars',
                  data: JSON.stringify(teamToUpdate),
                  success: function(team) {
                    responseHandler(true, team);
                   },
                   error: function(xhr, status, err){
                        responseHandler(false);
                   }
                });
        }

        saveMembers(userId, team, responseHandler){
            var teamToUpdate = {};
            teamToUpdate.name = team.name;
            teamToUpdate.members = [];

            for(var i = 0; i < team.members.length; i++)
            {
                teamToUpdate.members.push(team.members[i].id);
            }

            $.post({
                  headers: {
                          'Accept': 'application/json',
                          'Content-Type': 'application/json'
                  },
                  type: "POST",
                  url: '/api/User/' + userId + '/Team/' + team.id + '/Members',
                  data: JSON.stringify(teamToUpdate),
                  success: function(team) {
                    responseHandler(true, team);
                   },
                   error: function(xhr, status, err){
                        responseHandler(false);
                   }
                });
        }
}
