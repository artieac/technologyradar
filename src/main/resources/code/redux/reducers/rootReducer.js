import { combineReducers } from 'redux';
import { createStore } from "redux";
import radarCollectionReducer from "./radarCollectionReducer";

const allReducers = {
  radarCollectionReducer: radarCollectionReducer
}

const rootReducer = combineReducers(allReducers);
