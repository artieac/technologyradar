import * as actionTypes from './adminActionTypes';

// src/js/reducers/index.js
const adminAppState = {
  radarCollection: [],
  currentRadar: {},
  sourceRadar: {}
};

export function addRadarCollectionToState(radarCollection){
    return {
       type: actionTypes.SETRADARCOLLECTION,
       payload: radarCollection
   };
}

export function setCurrentRadarInstanceToState(radarInstance){
    return {
        type: actionTypes.SETCURRENTRADARINSTANCE,
        payload: radarInstance
    };
}

export function setSourceRadarInstanceToState(radarInstance){
    return {
        type: actionTypes.SETSOURCERADARINSTANCE,
        payload: radarInstance
    };
}

export function getSourceRadarInstance(userId, radarId){
    fetch( '/api/User/' + userId + '/Radar/' + radarId)
        .then(response => response.json())
        .then(json => this.props.setSourceRadarInstance({ sourceRadar: json}));
}

const adminAppReducer = (state = adminAppState, action) => {
  switch (action.type) {
    case actionTypes.SETRADARCOLLECTION:
        return Object.assign({}, state, {
            radarCollection: action.payload
        })
    case actionTypes.SETCURRENTRADARINSTANCE:
        return Object.assign({}, state, {
            currentRadar: action.payload
        })
    case actionTypes.SETSOURCERADARINSTANCE:
        return Object.assign({}, state, {
            sourceRadar: action.payload
        })
    default:
      return state;
  }
}

export default adminAppReducer;
