import * as actionTypes from './RadarRingActionTypes';

// src/js/reducers/index.js
const radarTemplateManagementState = {
  showEdit: true,
  radarRingSets: [],
  selectedRadarRingSet: {}
};

export function setShowEdit(showEdit){
    return {
        type: actionTypes.SETSHOWEDIT,
        payload: showEdit
    };
}

export function addRadarRingSetsToState(radarRingSets){
    return {
       type: actionTypes.SETRADARRINGSETS,
       payload: radarRingSets
   };
}

export function addSelectedRadarRingSetToState(selectedRadarRingSet){
    return {
       type: actionTypes.SETSELECTEDRADARRINGSET,
       payload: selectedRadarRingSet
   };
}

export default function(state = radarTemplateManagementState, action) {
 // alert(JSON.stringify(action));

  switch (action.type) {
    case actionTypes.SETSHOWEDIT:
        return Object.assign({}, state, {
            showEdit: action.payload
        })
        break;
    case actionTypes.SETRADARRINGSETS:
        return Object.assign({}, state, {
            radarRingSets: action.payload
        })
        break;
    case actionTypes.SETSELECTEDRADARRINGSET:
        return Object.assign({}, state, {
            selectedRadarRingSet: action.payload
        })
        break;
    default:
      return state;
  }
}
