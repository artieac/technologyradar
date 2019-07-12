'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import manageUsersReducer from '../../redux/ManageUsersReducer';
import { addUsersToState} from '../../redux/ManageUsersReducer';
import { UserRepository } from '../../../../Repositories/UserRepository'
import UserTableBody from './UserTablebody';

class ManageUsersPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.userRepository = new UserRepository();
    }

    componentDidMount(){
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
                        </tr>
                    </thead>
                    <UserTableBody users={this.props.users} container={this}/>
                </table>
            </div>
        );
    }
};


const mapDispatchToProps = dispatch => {
  return {
        storeUsers : users => { dispatch(addUsersToState(users))}
    }
};


function mapStateToProps(state) {
  return {
    	users: state.manageUsersReducer.users
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageUsersPage);