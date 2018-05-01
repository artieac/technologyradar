// src/js/reducers/index.js
const initialRadarCollectionState = {
  data: []
};

export const RADARCOLLECTION_GETBYUSERID = "RADARCOLLECTION_GETBYUSERID";

const radarCollectionReducer = (state = initialRadarCollectionState, action) => {
  switch (action.type) {
    case 'RADARCOLLECTION_GETBYUSERID':
      return action.data;
    default:
      return state;
  }
};

export function getRadarCollectionByUserId(userId) {
  return fetch( '/api/User/' + userId + '/Radars',)
    .then(response => response.json())
    .then(json => dispatch(resolvedGtRadarCollectionByUserId(json)))
}

export function resolvedGtRadarCollectionByUserId(data) {
  return {
    type: 'RADARCOLLECTION_GETBYUSERID',
    data: data
  }
}

export default radarCollectionReducer;
module.exports = radarCollectionReducer;