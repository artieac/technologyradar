import * as actionTypes from './ManageUsersActionTypes';

// src/js/reducers/index.js
const manageUserState = {
  users: [],
  roles: [],
  selectedUser: {}
};

export function addUsersToState(users){
    return{
        type: actionTypes.ADDUSERS,
        payload: users
    };
}

export function addRolesToState(roles){
    return{
        type: actionTypes.ADDROLES,
        payload: roles
    };
}

export function setSelectedUser(selectedUser){
    return{
        type: actionTypes.SETSELECTEDUSER,
        payload: selectedUser
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
    case actionTypes.ADDROLES:
        return Object.assign({}, state, {
            roles: action.payload
        })
        break;
    case actionTypes.SETSELECTEDUSER:
        return Object.assign({}, state, {
            selectedUser: action.payload
        })
        break;
    default:
      return state;
  }
}

