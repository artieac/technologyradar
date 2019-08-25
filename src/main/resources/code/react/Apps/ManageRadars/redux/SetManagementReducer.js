import * as actionTypes from './SetManagementActionTypes';

// src/js/reducers/index.js
const setManagementState = {
  showEdit: true,
  setList: [],
  selectedListItem: {}
};

export function setShowEdit(showEdit){
    return {
        type: actionTypes.SETSHOWEDIT,
        payload: showEdit
    };
}

export function addSetListToState(setList){
    return {
       type: actionTypes.SETSETLIST,
       payload: setList
   };
}

export function addSelectedListItemToState(selectedListItem){
    return {
       type: actionTypes.SETSELECTEDLISTITEM,
       payload: selectedListItem
   };
}

export default function(state = setManagementState, action) {
 // alert(JSON.stringify(action));

  switch (action.type) {
    case actionTypes.SETSHOWEDIT:
        return Object.assign({}, state, {
            showEdit: action.payload
        })
        break;
    case actionTypes.SETSETLIST:
        return Object.assign({}, state, {
            setList: action.payload
        })
        break;
    case actionTypes.SETSELECTEDLISTITEM:
        return Object.assign({}, state, {
            selectedListItem: action.payload
        })
        break;
    default:
      return state;
  }
}
