import * as actionTypes from './RadarTemplateActionTypes';

// src/js/reducers/index.js
const radarTemplateManagementState = {
  radarTemplates: [],
  associatedRadarTemplates: [],
  sharedRadarTemplate: [],
  selectedRadarTemplate: {},
  showEdit: true,
};

export function setShowEdit(showEdit){
    return {
        type: actionTypes.SETSHOWEDIT,
        payload: showEdit
    };
}

export function addRadarTemplatesToState(radarTemplates){
    return {
       type: actionTypes.SETRADARTYPECOLLECTION,
       payload: radarTemplates
   };
}

export function addSelectedRadarTemplateToState(selectedRadarTemplate){
    return {
       type: actionTypes.SETSELECTEDRADARTYPE,
       payload: selectedRadarTemplate
   };
}

export function addSharedRadarTemplatesToState(sharedRadarTemplates){
    return {
       type: actionTypes.SETSHAREDRADARTYPECOLLECTION,
       payload: sharedRadarTemplates
   };
}

export function addAssociatedRadarTemplatesToState(associatedRadarTemplates){
    return {
       type: actionTypes.SETASSOCIATEDRADARTYPES,
       payload: associatedRadarTemplates
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
    case actionTypes.SETSELECTEDRADARTYPE:
        return Object.assign({}, state, {
            selectedRadarTemplate: action.payload
        })
        break;
    case actionTypes.SETSHAREDRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            sharedRadarTemplates: action.payload
        })
        break;
    case actionTypes.SETRADARTYPECOLLECTION:
        return Object.assign({}, state, {
            radarTemplates: action.payload
        })
        break;
    case actionTypes.SETASSOCIATEDRADARTYPES:
        return Object.assign({}, state, {
            associatedRadarTemplates: action.payload
        })
        break;
    default:
      return state;
  }
}
