import * as actionTypes from './RadarTypeActionTypes';

// src/js/reducers/index.js
const radarTypeManagementState = {
  radarTypes: [],
  associatedRadarTypes: [],
  sharedRadarType: [],
  selectedRadarType: {},
  currentUser: {},
  showHistory: false,
  showEdit: true,
  radarTypeHistory: []
};

export function addRadarTypeHistoryToState(radarTypeHistory){
    return {
        type: actionTypes.SETRADARTYPEHISTORY,
        payload: radarTypeHistory
    };
}

export function setShowHistory(showHistory){
    return {
        type: actionTypes.SETSHOWHISTORY,
        payload: showHistory
    };
}

export function setShowEdit(showEdit){
    return {
        type: actionTypes.SETSHOWEDIT,
        payload: showEdit
    };
}

export function addCurrentUserToState(currentUser){
    return {
        type: actionTypes.SETCURRENTUSER,
        payload: currentUser
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
    case actionTypes.SETRADARTYPEHISTORY:
        return Object.assign({}, state, {
            radarTypeHistory: action.payload
        })
        break;
    case actionTypes.SETSHOWHISTORY:
        return Object.assign({}, state, {
            showHistory: action.payload,
            showEdit: !action.payload
        })
        break;
    case actionTypes.SETSHOWEDIT:
        return Object.assign({}, state, {
            showEdit: action.payload,
            showHistory: !action.payload
        })
        break;
    case actionTypes.SETCURRENTUSER:
        return Object.assign({}, state, {
            currentUser: action.payload
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
