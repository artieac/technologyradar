'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import { UserRepository } from '../../../../Repositories/UserRepository';
import { addTeamsToState } from '../../redux/TeamReducer';
import { TeamRepository } from '../../../../Repositories/TeamRepository';

class ManageTeamRadarsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.userRepository = new UserRepository();
        this.teamRepository = new TeamRepository();
    }

    componentDidMount(){
        this.getUserDetails();
    }

    getUserDetails(){
        this.userRepository.getUser(this.handleGetUserSuccess);
    }

    handleGetUserSuccess(currentUser){
        this.props.storeCurrentUser(currentUser);
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Team Radars</label>
                </div>
                <p>Choose which radars this team can edit.</p>
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

export default connect(mapStateToProps, mapDispatchToProps)(ManageTeamRadarsPage);