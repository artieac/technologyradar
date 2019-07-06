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
import ManageOwnedRadarTypesPage from '../components/admin/ManageRadarTypes/ManageOwnedRadarTypesPage';
import ManageAssociatedRadarTypesPage from '../components/admin/ManageRadarTypes/ManageAssociatedRadarTypesPage';
import ManageRadarsPage from '../components/admin/ManageRadars/ManageRadarsPage';
import AddFromPreviousRadarPage from '../components/admin/AddFromPrevious/AddFromPreviousRadarPage';
import radarReducer from '../../redux/reducers/admin/RadarReducer';
import radarTypeReducer from '../../redux/reducers/admin/RadarTypeReducer';
import errorReducer from '../../redux/reducers/admin/ErrorReducer';

const adminAppStore = createStore(combineReducers({radarReducer,radarTypeReducer, errorReducer}));

class AdminApp extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            radarCollection: [],
            userId: jQuery("#userId").val()
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
                            <div className="panel panel-primary adminMenuPanel">
                                <div className="panel-heading">Manage Your Radar Types</div>
                                <div id="ManageRadarTypesPanel" className="panel-body">
                                    <p>A Radar Type defines how you will classify and rate your topics.</p>
                                    <p>Go here to manage your existing types or add new ones</p>
                                    <Link to='/admin/manageOwnedRadarTypes'>
                                        <button className="btn btn-primary">Radar Types</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="panel panel-primary adminMenuPanel">
                                <div className="panel-heading">Associate Radar Types</div>
                                <div id="AssociateRadarTypesPanel" className="panel-body">
                                    <p>See other's radar types and mark the so you can also use them'.</p>
                                    <p></p>
                                    <br/>
                                    <Link to='/admin/manageAssociatedRadarTypes'>
                                        <button className="btn btn-primary">Associated Radar Types</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="panel panel-primary adminMenuPanel">
                                <div className="panel-heading">Manage your Radars</div>
                                <div id="ManageRadarsPanels" className="panel-body">
                                    <p>Once you have Radar Types defined go here to create an instance of a Radar Type.</p>
                                    <p></p>
                                    <br/>
                                    <Link to='/admin/manageRadars'>
                                        <button className="btn btn-primary">Your Radars</button>
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
    <Provider store={ adminAppStore }>
        <div>
            <Router>
                <Switch>
                    <Route path="/admin/index" component={ AdminApp } />
                    <Route path="/admin/manageOwnedRadarTypes" component={ ManageOwnedRadarTypesPage } />
                    <Route path="/admin/manageAssociatedRadarTypes" component={ ManageAssociatedRadarTypesPage } />
                    <Route path="/admin/manageRadars" component={ ManageRadarsPage } />
                    <Route path="/admin/user/:userId/radar/:radarId/addfromprevious" component={ AddFromPreviousRadarPage }/>
                </Switch>
            </Router>
        </div>
    </Provider>,
    document.getElementById("adminAppContent")
);

module.exports = AdminApp;