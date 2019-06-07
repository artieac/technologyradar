import * as actionTypes from './adminActionTypes';

// src/js/reducers/index.js
const adminAppState = {
  radarTypeCollection: [],
  radarCollection: [],
  currentRadar: {},
  sourceRadar: {},
  radarItemsToAdd: [],
  radarItemsToRemove: [],
  sharedRadarTypeCollection: [],
  selectedRadarType: {}
};

export function addRadarTypeCollectionToState(radarTypeCollection){
    return {
       type: actionTypes.SETRADARTYPECOLLECTION,
       payload: radarTypeCollection
   };
}

export function addSelectedRadarTypeToState(radarType){
    return {
       type: actionTypes.SETSELECTEDRADARTYPE,
       payload: radarType
   };
}
export function addRadarCollectionToState(radarCollection){
    return {
       type: actionTypes.SETRADARCOLLECTION,
       payload: radarCollection
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

function createRadarItemForExistingTechnology(assessmentItem)
{
    var radarItem = {};

    radarItem.radarRing = assessmentItem.radarRing.id;
    radarItem.confidenceLevel = assessmentItem.confidenceFactor;
    radarItem.assessmentDetails = assessmentItem.details;
    radarItem.technologyId = assessmentItem.technology.id;

    return radarItem;
};

export function handleAddRadarItem(targetAssessmentItem) {
    return {
        type : actionTypes.HANDLEADDRADARITEM,
        payload: createRadarItemForExistingTechnology(targetAssessmentItem)
    };
}

export function handleRemoveRadarItem(targetAssessmentItem) {
    return {
        type : actionTypes.HANDLEREMOVERADARITEM,
        payload: targetAssessmentItem.id
    };
}

export function clearRemoveRadarItems(){
    return {
        type: actionTypes.CLEARADDITEMS,
        payload: {}
    };
}

export function clearAddRadarItems(){
    return {
        type: actionTypes.CLEARREMOVEITEMS,
        payload: {}
    };
}

export function addSharedRadarTypeCollectionToState(sharedRadarTypeCollection){
    return {
       type: actionTypes.SETSHAREDRADARTYPECOLLECTION,
       payload: sharedRadarTypeCollection
   };
}

export const adminAppReducer = (state = adminAppState, action) => {
 // alert(JSON.stringify(action));

  switch (action.type) {
    case actionTypes.SETSELECTEDRADARTYPE:
        return Object.assign({}, state, {
            selectedRadarType: action.payload
        })
        break;
    case actionTypes.SETSHAREDRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            sharedRadarTypeCollection: action.payload
        })
        break;
    case actionTypes.SETRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            radarTypeCollection: action.payload
        })
        break;
    case actionTypes.SETRADARCOLLECTION:
        return Object.assign({}, state, {
            radarCollection: action.payload
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
    case actionTypes.HANDLEADDRADARITEM:
        return Object.assign({}, state, {
            radarItemsToAdd: state.radarItemsToAdd.concat(action.payload)
        })
        break;
    case actionTypes.CLEARADDITEMS:
         return Object.assign({}, state, {
                 radarItemsToAdd: []
             })
     case actionTypes.HANDLEREMOVERADARITEM:
        return Object.assign({}, state, {
            radarItemsToRemove: state.radarItemsToRemove.concat(action.payload)
        })
        break;
    case actionTypes.CLEARREMOVEITEMS:
        return Object.assign({}, state, {
                radarItemsToRemove: []
            })
        break;
    default:
      return state;
  }
}

