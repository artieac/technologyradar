import * as actionTypes from './TeamActionTypes';

// src/js/reducers/index.js
const teamState = {
  userTeams: []
};

export function addTeamsToState(userTeams){
    return {
        type: actionTypes.SETTEAMS,
        payload: userTeams
    };
}

export default function(state = teamState, action) {
  switch (action.type) {
    case actionTypes.SETTEAMS:
        return Object.assign({}, state, {
            userTeams: action.payload
        })
        break;
    default:
      return state;
  }
}