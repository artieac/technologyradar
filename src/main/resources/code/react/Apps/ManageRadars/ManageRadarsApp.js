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
import ManageRadarTemplatesPage from './components/ManageRadarTemplatesPage';
import ManageAssociatedRadarTemplatesPage from './components/ManageAssociatedRadarTemplates/ManageAssociatedRadarTemplatesPage';
import ManageRadarsPage from './components/ManageRadarsPage';
import AddFromPreviousRadarPage from './components/AddFromPrevious/AddFromPreviousRadarPage';
import radarReducer from './redux/RadarReducer';
import radarTemplateReducer from './redux/RadarTemplateReducer';
import userReducer from '../redux/CommonUserReducer';
import ManageTeamsPage from './components/ManageTeamsPage';
import ManageTeamMembersPage from './components/TeamMembers/ManageTeamMembersPage';
import ManageTeamRadarsPage from './components/TeamRadars/ManageTeamRadarsPage';
import { addCurrentUserToState } from '../redux/CommonUserReducer';
import { UserRepository } from '../../Repositories/UserRepository';
import teamReducer from './redux/TeamReducer';
import LinkActionPanelComponent from '../../components/LinkActionPanelComponent';

const manageRadarsAppStore = createStore(combineReducers({radarReducer,radarTemplateReducer, userReducer, teamReducer}), applyMiddleware(thunk));

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
                            <LinkActionPanelComponent
                                title="Manage Your Radar Templates"
                                description = "A Radar Templates defines how you will classify and rate your topics."
                                linkTarget="/manageradars/radarTemplates"
                                buttonText="Radar Templates"/>
                        </div>
                        <div className="col-md-4">
                            <LinkActionPanelComponent
                                title="Associate Radar Templates"
                                description = "See other\'s Radar Templates and mark them so you can also use them."
                                linkTarget="/manageradars/associatedRadarTemplates"
                                buttonText="AssociatedRadarTemplates"/>
                        </div>
                        <div className="col-md-4">
                            <LinkActionPanelComponent
                                title="Manage your Radars"
                                description = "Once you have Radar Templates defined go here to create an instance of a Template."
                                linkTarget="/manageradars/radars"
                                buttonText="Your Radars"/>
                        </div>
                    </div>
                    <div className="row">
                        <div className={ this.state.currentUser.allowTeamMembersToManageRadars==true ? "col-md-4" : "col-md-4 hidden"}>
                            <LinkActionPanelComponent
                                title="Manage your Teams"
                                description = "Create teams to allow others to manage some of your radars."
                                linkTarget="/manageradars/teams"
                                buttonText="Your Teams"/>
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
                    <Route path="/manageradars/radartemplates" component={ ManageRadarTemplatesPage } />
                    <Route path="/manageradars/associatedradartemplates" component={ ManageAssociatedRadarTemplatesPage } />
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