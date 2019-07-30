'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { TeamRepository } from '../../../../Repositories/TeamRepository';
import { addTeamsToState } from '../../redux/TeamReducer';

class NewTeamMemberRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            teamNameInput: ""
        };

        this.teamRepository = new TeamRepository();
    }

    render(){
        return(
            <div className="row">
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser
    };
};

const mapDispatchToProps = dispatch => {
  return {
      storeTeams : teams => { dispatch(addTeamsToState(teams))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NewTeamMemberRow);