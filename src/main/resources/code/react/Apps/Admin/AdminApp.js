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
import ManageUsersPage from './components/ManageUsers/ManageUsersPage';
import manageUsersReducer from './redux/ManageUsersReducer';

const adminAppStore = createStore(combineReducers({manageUsersReducer}));

class AdminApp extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };
    }

    render(){
        return (
            <div>
                <div className="row">
                    <div className="col-md-12">
                        <div className="contentPageTitle">
                            <label>Administration Tool</label>
                        </div>
                    </div>
                </div>
                 <div className="row">
                    <div className="col-md-4">
                        <div className="panel panel-techradar adminMenuPanel">
                            <div className="panel-heading-techradar">Manage Your Radar Types</div>
                            <div id="ManageRadarTypesPanel" className="panel-body">
                                <p>Manage the users of this application</p>
                                <br/>
                                <Link to='/admin/manageusers'>
                                    <button className="btn btn-techradar">Manage Users</button>
                                </Link>
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
                    <Route path="/admin/manageusers" component={ ManageUsersPage } />
                </Switch>
            </Router>
        </div>
    </Provider>,
    document.getElementById("adminAppContent")
);

module.exports = AdminApp;