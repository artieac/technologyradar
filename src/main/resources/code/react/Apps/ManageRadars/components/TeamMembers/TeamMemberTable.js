'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import { combineReducers, createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk'
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import TeamMemberRow from './TeamMemberRow';
import NewTeamMemberRow from './NewTeamMemberRow';

class TeamMemberTable extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };
    }

    componentDidMount(){
    }

    render() {
        return (
            <div className="row">
                <div className="row">
                    <div className="col-md-6">User</div>
                    <div></div>
                </div>
                {
                    this.props.team.members.map((currentRow, index) => {
                        return <TeamMemberRow key={index} rowNum={index} rowData={currentRow} teamId={this.props.team.id}/>
                    })
                }
                <NewTeamMemberRow />
            </div>
        );
    }
};

const mapDispatchToProps = dispatch => {
  return {
    }
};


function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(TeamMemberTable);