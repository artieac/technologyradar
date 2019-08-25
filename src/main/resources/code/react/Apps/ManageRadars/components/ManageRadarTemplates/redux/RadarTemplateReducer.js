import * as actionTypes from './RadarTemplateActionTypes';

// src/js/reducers/index.js
const radarTemplateManagementState = {
  radarRingSets: [],
  selectedRadarRingSet: {},
  radarCategorySets: [],
  selectedRadarCategorySet: {}
};

export function addRadarRingSetsToState(radarRingSets){
    return {
       type: actionTypes.SETRADARRINGSETS,
       payload: radarRingSets
   };
}

export function addSelectedRadarRingSetToState(radarRingSet){
    return {
       type: actionTypes.SETSELECTEDRADARRINGSET,
       payload: radarRingSet
   };
}

export function addRadarCategorySetsToState(radarCategorySets){
    return {
       type: actionTypes.SETRADARCATEGORYSETS,
       payload: radarCategorySets
   };
}

export function addSelectedRadarCategorySetToState(radarCategorySet){
    return {
       type: actionTypes.SETSELECTEDRADARCATEGORYSET,
       payload: radarCategorySet
   };
}

export default function(state = radarTemplateManagementState, action) {
 // alert(JSON.stringify(action));

  switch (action.type) {
    case actionTypes.SETRADARRINGSETS:
        return Object.assign({}, state, {
            radarRingSets: action.payload
        })
        break;
    case actionTypes.SETSELECTEDRADARRINGSET:
        return Object.assign({}, state, {
            radarRingSet: action.payload
        })
        break;
    case actionTypes.SETRADARCATEGORYSETS:
        return Object.assign({}, state, {
            radarCategorySets: action.payload
        })
        break;
    case actionTypes.SETSELECTEDRADARCATEGORYSET:
        return Object.assign({}, state, {
            radarCategorySet: action.payload
        })
        break;
    default:
      return state;
  }
}
