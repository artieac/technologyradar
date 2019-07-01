import { combineReducers } from 'redux';
import radarReducer from "./RadarReducer";
import radarTypeReducer from "./RadarTypeReducer";
import errorReducer from "./ErrorReducer";

export default combinedReducers({    radarReducer,
                                    radarTypeReducer,
                                    errorReducer});
