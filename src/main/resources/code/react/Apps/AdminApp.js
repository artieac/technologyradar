'use strict'
import jQuery from 'jquery';
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import createReactClass from 'create-react-class';
import ManageRadars from '../components/admin/ManageRadars';

class AdminApp extends React.Component{
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
                                <button className="btn btn-primary">
                                    <Link to='/admin/manageRadars'>Manage Radars</Link>
                                </button>
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
    <Router>
        <div>
            <Route exact path="/admin/index" component={ AdminApp } />
            <Route path="/admin/manageRadars" component={ ManageRadars } />
        </div>
    </Router>,
    document.getElementById("adminAppContent")
);

module.exports = AdminApp;