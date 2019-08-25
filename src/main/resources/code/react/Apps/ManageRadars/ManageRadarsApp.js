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
import { connect } from "react-redux";
import OwnedRadarTypesPage from './components/OwnedRadarTypes/OwnedRadarTypesPage';
import ManageAssociatedRadarTypesPage from './components/ManageAssociatedRadarTypes/ManageAssociatedRadarTypesPage';
import ManageRadarsPage from './components/ManageRadars/ManageRadarsPage';
import AddFromPreviousRadarPage from './components/AddFromPrevious/AddFromPreviousRadarPage';
import radarReducer from './redux/RadarReducer';
import radarTypeReducer from './redux/RadarTypeReducer';
import userReducer from '../redux/CommonUserReducer';
import ManageTeamsPage from './components/ManageTeams/ManageTeamsPage';
import ManageTeamMembersPage from './components/TeamMembers/ManageTeamMembersPage';
import ManageTeamRadarsPage from './components/TeamRadars/ManageTeamRadarsPage';
import { addCurrentUserToState } from '../redux/CommonUserReducer';
import { UserRepository } from '../../Repositories/UserRepository';
import teamReducer from './redux/TeamReducer';
import setManagementReducer from './redux/SetManagementReducer';
import ManageRadarRingsPage from './components/ManageRadarRings/ManageRadarRingsPage';
import ManageRadarCategoriesPage from './components/ManageRadarCategories/ManageRadarCategoriesPage';
import ManageRadarTemplatesPage from  './components/ManageRadarTemplates/ManageRadarTemplatesPage';
import radarTemplateReducer from './components/ManageRadarTemplates/redux/RadarTemplateReducer';

const manageRadarsAppStore = createStore(combineReducers({  radarReducer,
                                                            radarTypeReducer,
                                                            userReducer,
                                                            teamReducer,
                                                            setManagementReducer,
                                                            radarTemplateReducer}), applyMiddleware(thunk));

class ManageRadarsApp extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            radarCollection: [],
            targetUserId: jQuery("#targetUserId").val(),
            currentUser: {}
        };

        this.userRepository = new UserRepository();
        this.handleGetUserResponse = this.handleGetUserResponse.bind(this);
    }

    componentDidMount(){
        this.userRepository.getUser(this.handleGetUserResponse);
     }

    handleGetUserResponse(currentUser){
        this.setState({ currentUser: currentUser});
        this.forceUpdate();
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
                                <div className="panel-heading-techradar">Manage Your Radar Templates</div>
                                <div id="ManageRadarTypesPanel" className="panel-body">
                                    <p>A Radar Templates defines how you will classify and rate your topics.</p>
                                    <p>Go here to manage your existing templates or add new ones</p>
                                    <Link to='/manageradars/radarTemplates'>
                                        <button className="btn btn-techradar">Radar Templates</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="panel panel-techradar adminMenuPanel">
                                <div className="panel-heading-techradar">Manage Your Radar Rings</div>
                                <div id="ManageRadarTemplatesPanel" className="panel-body">
                                    <p>A Radar Rings defines how you will rate your topics.  These are the measuring sticks that you'll use to share you how feel about something'.'</p>
                                    <p>Go here to manage your existing Radar rings or add new ones</p>
                                    <Link to='/manageradars/radarRings'>
                                        <button className="btn btn-techradar">Radar Rings</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="panel panel-techradar adminMenuPanel">
                                <div className="panel-heading-techradar">Manage Your Radar Categories</div>
                                <div id="ManageRadarTemplatesPanel" className="panel-body">
                                    <p>A Radar Categories defines how you will group your topics.  These are how yo classify the things you will be rating with the Radar Rings'.'</p>
                                    <p>Go here to manage your existing Radar Categories or add new ones</p>
                                    <Link to='/manageradars/radarCategories'>
                                        <button className="btn btn-techradar">Radar Categories</button>
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
                    </div>
                    <div className="row">
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
                        <div className={ this.state.currentUser.allowTeamMembersToManageRadars==true ? "col-md-4" : "col-md-4 hidden"}>
                            <div className="panel panel-techradar adminMenuPanel">
                                <div className="panel-heading-techradar">Manage your Teams</div>
                                <div id="ManageTeamsPanel" className="panel-body">
                                    <p>Create teams to allow others to manage some of your radars.</p>
                                    <p></p>
                                    <br/>
                                    <Link to='/manageradars/teams'>
                                        <button className="btn btn-techradar">Your Teams</button>
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
                    <Route path="/manageradars/radarTemplates" component={ ManageRadarTemplatesPage } />
                    <Route path="/manageradars/radarCategories" component={ ManageRadarCategoriesPage } />
                    <Route path="/manageradars/radarRings" component={ ManageRadarRingsPage } />
                    <Route path="/manageradars/associatedradartypes" component={ ManageAssociatedRadarTypesPage } />
                    <Route path="/manageradars/radars" component={ ManageRadarsPage } />
                    <Route path="/manageradars/user/:userId/radar/:radarId/addfromprevious" component={ AddFromPreviousRadarPage }/>
                    <Route path="/manageradars/teams" component={ ManageTeamsPage }/>
                    <Route path="/manageradars/user/:userId/team/:teamId/members" component={ ManageTeamMembersPage }/>
                    <Route path="/manageradars/user/:userId/team/:teamId/radars" component={ ManageTeamRadarsPage }/>
                </Switch>
            </Router>
        </div>
    </Provider>,
    document.getElementById("manageRadarsAppContent")
);