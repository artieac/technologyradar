'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import { UserRepository } from '../../../../Repositories/UserRepository';
import { addTeamsToState } from '../../redux/TeamReducer';
import { TeamRepository } from '../../../../Repositories/TeamRepository';
import TeamsTable from './TeamsTable';

class ManageTeamsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.userRepository = new UserRepository();
        this.teamRepository = new TeamRepository();

        this.getUserDetails = this.getUserDetails.bind(this);
        this.handleGetUserSuccess = this.handleGetUserSuccess.bind(this);
        this.handleGetTeamsResponse = this.handleGetTeamsResponse.bind(this);
    }

    componentDidMount(){
        this.getUserDetails();
    }

    getUserDetails(){
        this.userRepository.getUser(this.handleGetUserSuccess);
    }

    handleGetUserSuccess(currentUser){
        this.props.storeCurrentUser(currentUser);
        this.teamRepository.getAllByUser(currentUser.id, this.handleGetTeamsResponse);
    }

    handleGetTeamsResponse(teams){
        this.props.storeTeams(teams);
        this.forceUpdate();
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Teams</label>
                </div>
                <p>Add an instance of your technology radar to track any changes since the last time you did this</p>
                <TeamsTable teams={this.props.userTeams}/>
            </div>
        );
    }
};


const mapDispatchToProps = dispatch => {
  return {
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))},
        storeTeams : teams => { dispatch(addTeamsToState(teams))}
    }
};


function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser,
    	userTeams: state.teamReducer.userTeams
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageTeamsPage);