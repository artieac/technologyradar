'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { TeamRepository } from '../../../../Repositories/TeamRepository';
import { addTeamsToState } from '../../redux/TeamReducer';

class NewTeamRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            teamNameInput: ""
        };

        this.teamRepository = new TeamRepository();

        this.handleTeamNameChange = this.handleTeamNameChange.bind(this);
        this.handleAddTeam = this.handleAddTeam.bind(this);
        this.handleAddTeamResponse = this.handleAddTeamResponse.bind(this);
    }

    handleTeamNameChange(){
        this.setState({teamNameInput:event.target.value});
    }

    handleAddTeam(){
        if(this.state.teamNameInput!=""){
            this.teamRepository.addTeam(this.props.currentUser.id, this.state.teamNameInput, this.handleAddTeamResponse);
        }
        else{
            alert("You must enter a team name.");
        }
    }

    handleAddTeamResponse(success, teams){
        if(success==true){
            this.props.storeTeams(teams);
        }
    }

    render(){
        return(
            <div className="row">
                <div className="col-md-6">
                    <input type="text" ref="teamName" required="required" onChange={(event) => { this.handleTeamNameChange(event)} } />
                </div>
                <div className="col-md-2">
                    <input type="button" className="btn btn-techradar" value="Add Team" onClick={(event) => { this.handleAddTeam(event) } } />
                </div>
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

export default connect(mapStateToProps, mapDispatchToProps)(NewTeamRow);