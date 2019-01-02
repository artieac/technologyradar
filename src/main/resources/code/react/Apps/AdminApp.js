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
import ManageRadars from '../components/admin/ManageRadars';
import AddFromPreviousRadar from '../components/admin/AddFromPreviousRadar';
import { adminAppReducer } from '../../redux/reducers/adminAppReducer';

const adminAppStore = createStore(adminAppReducer);

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
                 <div className="contentPageTitle">
                    <label>Manage your Technology Radar</label>
                </div>
                <table className="table table-striped">
                    <tbody>
                        <tr>
                            <td>
                                <Link to='/admin/manageRadars'>
                                    <button className="btn btn-primary">Manage Radars</button>
                                </Link>
                            </td>
                            <td>Go here to add an additional radar instance to your account.  This way you can track how your opinions have changed over time.</td>
                        </tr>
                        <tr>
                            <td>
                                <button className="btn btn-primary">
//                                    <Link to='/admin/grantAccess'>Grant Access</Link>
                                </button>
                            </td>
                            <td>Go here to allow other users to edit your Radars.</td>
                        </tr>
                    </tbody>
                </table>
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
                    <Route path="/admin/manageRadars" component={ ManageRadars } />
                    <Route path="/admin/user/:userId/radar/:radarId/addfromprevious" component={ AddFromPreviousRadar }/>
                </Switch>
            </Router>
        </div>
    </Provider>,
    document.getElementById("adminAppContent")
);

module.exports = AdminApp;