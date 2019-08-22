import * as actionTypes from './CommonUserActionTypes';

// src/js/reducers/index.js
const commonUserState = {
  currentUser: {}
};

export function addCurrentUserToState(currentUser){
    return {
        type: actionTypes.ADDCURRENTUSERTOSTATE,
        payload: currentUser
    };
}

export default function(state = commonUserState, action) {
  switch (action.type) {
    case actionTypes.ADDCURRENTUSERTOSTATE:
        return Object.assign({}, state, {
            currentUser: action.payload
        })
        break;
    default:
      return state;
  }
}
