import * as actionTypes from './RadarActionTypes';

// src/js/reducers/index.js
const radarManagementState = {
  radars: [],
  currentRadar: {},
  sourceRadar: {},
  radarTemplates: []
};

export function addRadarsToState(radars){
    return {
       type: actionTypes.SETRADARCOLLECTION,
       payload: radars
   };
}

export function addRadarTemplatesToState(radarTemplates){
    return {
       type: actionTypes.SETRADARTYPECOLLECTION,
       payload: radarTemplates
   };
}

export function setCurrentRadarInstanceToState(radarInstance) {
    return {
        type : actionTypes.SETCURRENTRADARINSTANCE,
        payload: radarInstance
    };
}

export function setSourceRadarInstanceToState(radarInstance) {
    return {
        type : actionTypes.SETSOURCERADARINSTANCE,
        payload: radarInstance
    };
}

export default function(state = radarManagementState, action) {
 // alert(JSON.stringify(action));

  switch (action.type) {
    case actionTypes.SETRADARCOLLECTION:
        return Object.assign({}, state, {
            radars: action.payload
        })
        break;
    case actionTypes.SETCURRENTRADARINSTANCE:
        return Object.assign({}, state, {
            currentRadar: action.payload
        })
        break;
    case actionTypes.SETSOURCERADARINSTANCE:
        return Object.assign({}, state, {
            sourceRadar: action.payload
        })
        break;
    case actionTypes.SETRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            radarTemplates: action.payload
        })
        break;
    default:
      return state;
  }
}

