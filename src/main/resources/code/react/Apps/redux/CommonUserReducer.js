import * as actionTypes from './CommonUserActionTypes';

// src/js/reducers/index.js
const commonUserState = {
  currentUser: {},
  targetUser: {}
};

export function addCurrentUserToState(currentUser){
    return {
        type: actionTypes.ADDCURRENTUSERTOSTATE,
        payload: currentUser
    };
}

export function addTargetUserToState(targetUser){
    return {
         type: actionTypes.ADDTARGETUSERTOSTATE,
         payload: targetUser
     };
}

export default function(state = commonUserState, action) {
  switch (action.type) {
    case actionTypes.ADDCURRENTUSERTOSTATE:
        return Object.assign({}, state, {
            currentUser: action.payload
        })
        break;
    case actionTypes.ADDTARGETUSERTOSTATE:
        return Object.assign({}, state, {
            targetUser: action.payload
        })
        break;
    default:
      return state;
  }
}
