import React from 'react';
import * as actionTypes from './adminActionTypes';

// src/js/reducers/index.js
const adminAppState = {
  radarCollection: [],
  currentRadar: {},
  sourceRadar: {}
};

function addRadarCollectionToState(radarCollection){
    return {
       type: actionTypes.SETRADARCOLLECTION,
       payload: radarCollection
   };
}

function setCurrentRadarInstanceToState(radarInstance) {
    return {
        type : actionTypes.SETCURRENTRADARINSTANCE,
        payload: radarInstance
    };
}

function setSourceRadarInstanceToState(radarInstance) {
    return {
        type : actionTypes.SETSOURCERADARINSTANCE,
        payload: radarInstance
    };
}

function handleRadarItemCheck(shouldAdd) {
    console.log(JSON.stringify(shouldAdd));
    return {
        type : actionTypes.HANDLERADARITEMCHECK,
        payload: shouldAdd
    };
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

export { adminAppReducer, setCurrentRadarInstanceToState, setSourceRadarInstanceToState, handleRadarItemCheck };