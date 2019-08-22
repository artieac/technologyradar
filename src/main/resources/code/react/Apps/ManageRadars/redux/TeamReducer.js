import * as actionTypes from './TeamActionTypes';

// src/js/reducers/index.js
const teamState = {
  userTeams: [],
  currentTeam: {}
};

export function addTeamsToState(userTeams){
    return {
        type: actionTypes.SETTEAMS,
        payload: userTeams
    };
}

export function addCurrentTeamToState(currentTeam){
    return {
        type: actionTypes.SETCURRENTTEAM,
        payload: currentTeam
    };
}

export default function(state = teamState, action) {
  switch (action.type) {
    case actionTypes.SETCURRENTTEAM:
        return Object.assign({}, state, {
            currentTeam: action.payload
        })
        break;
    default:
      return state;
  }
}