'use strict'
import jQuery from 'jquery';
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import { combineReducers, createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk'
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import createReactClass from 'create-react-class';
import OwnedRadarTypesPage from './components/OwnedRadarTypes/OwnedRadarTypesPage';
import ManageAssociatedRadarTypesPage from './components/ManageAssociatedRadarTypes/ManageAssociatedRadarTypesPage';
import ManageRadarsPage from './components/ManageRadars/ManageRadarsPage';
import AddFromPreviousRadarPage from './components/AddFromPrevious/AddFromPreviousRadarPage';
import radarReducer from './redux/RadarReducer';
import radarTypeReducer from './redux/RadarTypeReducer';
import errorReducer from './redux/ErrorReducer';

const manageRadarsAppStore = createStore(combineReducers({radarReducer,radarTypeReducer, errorReducer}));

class ManageRadarsApp extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            radarCollection: [],
            targetUserId: jQuery("#targetUserId").val()
        };
    }

    render(){
        return (
            <div>
                <div className="row">
                    <div className="col-md-12">
                        <div className="contentPageTitle">
                            <label>Manage your Technology Radar</label>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4">
                            <div className="panel panel-techradar adminMenuPanel">
                                <div className="panel-heading-techradar">Manage Your Radar Types</div>
                                <div id="ManageRadarTypesPanel" className="panel-body">
                                    <p>A Radar Type defines how you will classify and rate your topics.</p>
                                    <p>Go here to manage your existing types or add new ones</p>
                                    <Link to='/manageradars/radarTypes'>
                                        <button className="btn btn-techradar">Radar Types</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="panel panel-techradar adminMenuPanel">
                                <div className="panel-heading-techradar">Associate Radar Types</div>
                                <div id="AssociateRadarTypesPanel" className="panel-body">
                                    <p>See other's radar types and mark the so you can also use them'.</p>
                                    <p></p>
                                    <br/><br/>
                                    <Link to='/manageradars/associatedRadarTypes'>
                                        <button className="btn btn-techradar">Associated Radar Types</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="panel panel-techradar adminMenuPanel">
                                <div className="panel-heading-techradar">Manage your Radars</div>
                                <div id="ManageRadarsPanels" className="panel-body">
                                    <p>Once you have Radar Types defined go here to create an instance of a Radar Type.</p>
                                    <p></p>
                                    <br/>
                                    <Link to='/manageradars/radars'>
                                        <button className="btn btn-techradar">Your Radars</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

ReactDOM.render(
    <Provider store={ manageRadarsAppStore }>
        <div>
            <Router>
                <Switch>
                    <Route path="/manageradars/index" component={ ManageRadarsApp } />
                    <Route path="/manageradars/radartypes" component={ OwnedRadarTypesPage } />
                    <Route path="/manageradars/associatedradartypes" component={ ManageAssociatedRadarTypesPage } />
                    <Route path="/manageradars/radars" component={ ManageRadarsPage } />
                    <Route path="/manageradars/user/:userId/radar/:radarId/addfromprevious" component={ AddFromPreviousRadarPage }/>
                </Switch>
            </Router>
        </div>
    </Provider>,
    document.getElementById("manageRadarsAppContent")
);

module.exports = ManageRadarsApp;