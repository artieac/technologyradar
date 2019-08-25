import * as actionTypes from './RadarActionTypes';

// src/js/reducers/index.js
const radarManagementState = {
  radars: [],
  currentRadar: {},
  sourceRadar: {},
  radarItemsToAdd: [],
  radarItemsToRemove: [],
  radarTypes: [],
  radarRingSets: [],
  radarCategorySets: []
};

export function addRadarsToState(radars){
    return {
       type: actionTypes.SETRADARCOLLECTION,
       payload: radars
   };
}

export function addRadarTypesToState(radarTypes){
    return {
       type: actionTypes.SETRADARTYPECOLLECTION,
       payload: radarTypes
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

    radarItem.radarCategory = assessmentItem.radarCategory.id;
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

export function addRadarRingSetsToState(radarRingSets){
    return {
        type: actionTypes.SETRADARRINGSETS,
        payload: {radarRingSets}
    };
}

export function addRadarCategorySetsToState(radarCategorySets){
    return {
        type: actionTypes.SETRADARCATEGORYSETS,
        payload: {radarCategorySets}
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
    case actionTypes.SETRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            radarTypes: action.payload
        })
        break;
    case actionTypes.SETRADARRINGSETS:
        return Object.assign({}, state, {
            radarRingSets: action.payload
        })
        break;
    case actionTypes.SETRADARCATEGORYSETS:
        return Object.assign({}, state, {
            radarCategorySets: action.payload
        })
        break;
    default:
      return state;
  }
}

