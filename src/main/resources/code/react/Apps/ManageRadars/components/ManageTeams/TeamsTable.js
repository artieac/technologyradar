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
import TeamRow from './TeamRow';
import NewTeamRow from './NewTeamRow';

class TeamsTable extends React.Component{
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
                    <div className="col-md-6">Name</div>
                    <div className="col-md-2"></div>
                    <div className="col-md-2"></div>
                </div>
                {
                    this.props.teams.map((currentRow, index) => {
                        return <TeamRow key={index} rowNum={index} rowData={currentRow} />
                    })
                }
                <NewTeamRow />
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

export default connect(mapStateToProps, mapDispatchToProps)(TeamsTable);