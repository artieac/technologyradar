import React from 'react';import { createStore, applyMiddleware } from 'redux'
import thunkMiddleware from 'redux-thunk'
import { createLogger } from 'redux-logger'
import radarCollectionReducer from 'radarCollectionReducer';

let radarCollectionStore = createStore(radarCollectionReducer);

const loggerMiddleware = createLogger()
​
export default function radarCollectionStoreOld(preloadedState) {
  return createStore(
    radarCollectionReducer,
    preloadedState,
    applyMiddleware(
      thunkMiddleware,
      loggerMiddleware
    )
  )
}