'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import { UserRepository } from '../../../../Repositories/UserRepository';
import { addTeamsToState } from '../../redux/TeamReducer';
import { TeamRepository } from '../../../../Repositories/TeamRepository';

class ManageTeamMembersPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: this.props.match.params.userId,
            teamId: this.props.match.params.teamId
        };

        this.userRepository = new UserRepository();
        this.teamRepository = new TeamRepository();

        this.handleGetUserResponse = this.handleGetUserResponse.bind(this);
        this.handleGetTeamResponse = this.handleGetTeamResponse.bind(this);
    }

    componentDidMount(){
        this.userRepository.getUser(this.handleGetUserSuccess);
        this.teamRepository.getTeam(userId, teamId, this.handleGetTeamResponse);
    }

    handleGetUserResponse(currentUser){
        this.props.storeCurrentUser(currentUser);
    }

    handleGetTeamResponse(team){
        this.props.storeCurrentTeam(team);
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage { this.props.currentTeam.name } Team Members</label>
                </div>
                <p>Add other users to your team.  This will allow them to edit selected Radars.</p>
            </div>
        );
    }
};


const mapDispatchToProps = dispatch => {
  return {
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))},
    }
};


function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageTeamMembersPage);