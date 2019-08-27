import * as actionTypes from './TeamActionTypes';

// src/js/reducers/index.js
const teamState = {
  userTeams: [],
  currentTeam: {},
  showTeamMembers: false,
  showTeamRadars: false
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

export function setShowTeamMembers(showTeamMembers){
    return {
        type: actionTypes.SETSHOWTEAMMEMBERS,
        payload: showTeamMembers
    };
}

export function setShowTeamRadars(showTeamRadars){
    return {
        type: actionTypes.SETSHOWTEAMRADARS,
        payload: showTeamRadars
    };
}

export default function(state = teamState, action) {
  switch (action.type) {
    case actionTypes.SETTEAMS:
        return Object.assign({}, state, {
            userTeams: action.payload
        })
        break;
    case actionTypes.SETCURRENTTEAM:
        return Object.assign({}, state, {
            currentTeam: action.payload
        })
        break;
    case actionTypes.SETSHOWTEAMMEMBERS:
        return Object.assign({}, state, {
            showTeamMembers: action.payload,
            showTeamRadars: false
        })
        break;
    case actionTypes.SETSHOWTEAMRADARS:
        return Object.assign({}, state, {
            showTeamRadars: action.payload,
            showTeamMembers: false
        })
        break;
    default:
      return state;
  }
}