import * as actionTypes from './RadarTypeActionTypes';

// src/js/reducers/index.js
const radarTypeManagementState = {
  radarTypes: [],
  associatedRadarTypes: [],
  sharedRadarType: [],
  selectedRadarType: {},
  showEdit: true,
};

export function setShowEdit(showEdit){
    return {
        type: actionTypes.SETSHOWEDIT,
        payload: showEdit
    };
}

export function addRadarTypesToState(radarTypes){
    return {
       type: actionTypes.SETRADARTYPECOLLECTION,
       payload: radarTypes
   };
}

export function addSelectedRadarTypeToState(selectedRadarType){
    return {
       type: actionTypes.SETSELECTEDRADARTYPE,
       payload: selectedRadarType
   };
}

export function addSharedRadarTypesToState(sharedRadarTypes){
    return {
       type: actionTypes.SETSHAREDRADARTYPECOLLECTION,
       payload: sharedRadarTypes
   };
}

export function addAssociatedRadarTypesToState(associatedRadarTypes){
    return {
       type: actionTypes.SETASSOCIATEDRADARTYPES,
       payload: associatedRadarTypes
   };
}

export default function(state = radarTypeManagementState, action) {
 // alert(JSON.stringify(action));

  switch (action.type) {
    case actionTypes.SETSHOWEDIT:
        return Object.assign({}, state, {
            showEdit: action.payload
        })
        break;
    case actionTypes.SETSELECTEDRADARTYPE:
        return Object.assign({}, state, {
            selectedRadarType: action.payload
        })
        break;
    case actionTypes.SETSHAREDRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            sharedRadarTypes: action.payload
        })
        break;
    case actionTypes.SETRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            radarTypes: action.payload
        })
        break;
    case actionTypes.SETASSOCIATEDRADARTYPES:
        return Object.assign({}, state, {
            associatedRadarTypes: action.payload
        })
        break;
    default:
      return state;
  }
}
