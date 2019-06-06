import * as actionTypes from './adminActionTypes';

// src/js/reducers/index.js
const adminAppState = {
  radarTypeCollection: [],
  radarCollection: [],
  currentRadar: {},
  sourceRadar: {},
  radarItemsToAdd: [],
  radarItemsToRemove: [],
  associatedRadarTypeCollection: []
};

export function addRadarTypeCollectionToState(radarTypeCollection){
    return {
       type: actionTypes.SETRADARTYPECOLLECTION,
       payload: radarTypeCollection
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

export function addAssociatedRadarTypeCollectionToState(associatedRadarTypeCollection){
    return {
       type: actionTypes.SETASSOCIATEDRADARTYPECOLLECTION,
       payload: associatedRadarTypeCollection
   };
}

export const adminAppReducer = (state = adminAppState, action) => {
 // alert(JSON.stringify(action));

  switch (action.type) {
    case actionTypes.SETASSOCIATEDRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            associatedRadarTypeCollection: action.payload
        })
    case actionTypes.SETRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            radarTypeCollection: action.payload
        })
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
    case actionTypes.HANDLEADDRADARITEM:
        return Object.assign({}, state, {
            radarItemsToAdd: state.radarItemsToAdd.concat(action.payload)
        })
    case actionTypes.CLEARADDITEMS:
         return Object.assign({}, state, {
                 radarItemsToAdd: []
             })
     case actionTypes.HANDLEREMOVERADARITEM:
        return Object.assign({}, state, {
            radarItemsToRemove: state.radarItemsToRemove.concat(action.payload)
        })
    case actionTypes.CLEARREMOVEITEMS:
        return Object.assign({}, state, {
                radarItemsToRemove: []
            })
    default:
      return state;
  }
}

