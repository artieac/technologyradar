import { combineReducers } from 'redux';
import radarReducer from "./RadarReducer";
import radarTemplateReducer from "./RadarTemplateReducer";
import errorReducer from "./ErrorReducer";

export default combinedReducers({    radarReducer,
                                    radarTemplateReducer,
                                    errorReducer});
