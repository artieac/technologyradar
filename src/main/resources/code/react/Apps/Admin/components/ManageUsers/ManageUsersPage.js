'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import manageUsersReducer from '../../redux/ManageUsersReducer';
import { addUsersToState, addRolesToState} from '../../redux/ManageUsersReducer';
import { UserRepository } from '../../../../Repositories/UserRepository'
import UserTableBody from './UserTablebody';
import { RoleRepository } from '../../../../Repositories/RoleRepository'

class ManageUsersPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.userRepository = new UserRepository();
        this.roleRepository = new RoleRepository();

        this.handleGetAllRolesResponse = this.handleGetAllRolesResponse.bind(this);
    }

    componentDidMount(){
        this.roleRepository.getAll(this.handleGetAllRolesResponse);
    }

    handleGetAllRolesResponse(roles){
        this.props.storeRoles(roles);
        this.userRepository.getAll(this.props.storeUsers);
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Radar Users</label>
                </div>
                <p>Add an instance of your technology radar to track any changes since the last time you did this</p>
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Email</th>
                            <th width="20%">Role</th>
                            <th width="20%">Enrollment</th>
                            <th width="10%"></th>
                        </tr>
                    </thead>
                    <UserTableBody users={this.props.users} container={this} roles={this.props.roles}/>
                </table>
            </div>
        );
    }
};


const mapDispatchToProps = dispatch => {
  return {
        storeUsers : users => { dispatch(addUsersToState(users))},
        storeRoles : roles => { dispatch(addRolesToState(roles))}
    }
};


function mapStateToProps(state) {
  return {
    	users: state.manageUsersReducer.users,
    	roles: state.manageUsersReducer.roles
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageUsersPage);