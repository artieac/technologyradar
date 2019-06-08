import { combineReducers } from 'redux';
import radarReducer from "./RadarReducer";
import radarTypeReducer from "./RadarTypeReducer";

export default combinedReducers({    radarReducer,
                                    radarTypeReducer});