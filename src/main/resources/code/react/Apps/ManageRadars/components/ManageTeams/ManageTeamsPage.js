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
import TeamRadars from './TeamRadars';
import TeamMembers from './TeamMembers';
import { RadarRepository } from '../../../../Repositories/RadarRepository';
import { addRadarsToState } from '../../redux/RadarReducer';

class ManageTeamsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.userRepository = new UserRepository();
        this.teamRepository = new TeamRepository();
        this.radarRepository = new RadarRepository();

        this.getUserDetails = this.getUserDetails.bind(this);
        this.handleGetUserSuccess = this.handleGetUserSuccess.bind(this);
        this.handleGetTeamsResponse = this.handleGetTeamsResponse.bind(this);
        this.loadTeams = this.loadTeams.bind(this);
    }

    componentDidMount(){
        this.getUserDetails();
    }

    getUserDetails(){
        this.userRepository.getUser(this.handleGetUserSuccess);
    }

    handleGetUserSuccess(currentUser){
        this.props.storeCurrentUser(currentUser);
        this.loadTeams(currentUser);
        this.radarRepository.getByUserId(this.props.currentUser.id, true, this.props.storeUserRadars);
    }

    loadTeams(currentUser){
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
                <div className="row">
                    <div className="col-md-6">
                        <TeamsTable teams={this.props.userTeams}/>
                    </div>
                    <div className="col-md-6">
                        <div className={ this.props.showTeamRadars ? "row" : "hidden"}>
                            <TeamRadars userRadars={this.props.userRadars} detailsContainer={this}/>
                        </div>
                        <div className={ this.props.showTeamMembers ? "row" : "hidden"}>
                            <TeamMembers detailsContainer={this}/>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
};


const mapDispatchToProps = dispatch => {
  return {
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))},
        storeTeams : teams => { dispatch(addTeamsToState(teams))},
        storeUserRadars: radars => { dispatch(addRadarsToState(radars))}

    }
};


function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser,
    	userTeams: state.teamReducer.userTeams,
    	showTeamRadars: state.teamReducer.showTeamRadars,
    	showTeamMembers: state.teamReducer.showTeamMembers,
    	userRadars: state.radarReducer.radars
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageTeamsPage);