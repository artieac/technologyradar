import * as actionTypes from './ManageUsersActionTypes';

// src/js/reducers/index.js
const manageUserState = {
  users: []
};

export function addUsersToState(users){
    return{
        type: actionTypes.ADDUSERS,
        payload: users
    };
}


export default function(state = manageUserState, action) {
 // alert(JSON.stringify(action));

  switch (action.type) {
    case actionTypes.ADDUSERS:
        return Object.assign({}, state, {
            users: action.payload
        })
        break;
    default:
      return state;
  }
}

