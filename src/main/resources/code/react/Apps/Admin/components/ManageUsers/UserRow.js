'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import RoleDropdownItem from "./RoleDropdownItem";

class UserRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.changeRowSelection = this.changeRowSelection.bind(this);
        this.getRadarUrl = this.getRadarUrl.bind(this);
    }

    componentDidUpdate(){
    }

    changeRowSelection(newRole){
        this.props.rowData.role = newRole;
        this.forceUpdate();
    }

    getRadarUrl(){
        return "/home/user/" + this.props.rowData.id + "/radars";
    }

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td>{ this.props.rowData.email}</td>
                 <td>
                    <div className="dropdown">
                         <button className="btn btn-techradar dropdown-toggle" type="button" id="roleDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                             { this.props.rowData.role.name }
                             <span className="caret"></span>
                         </button>
                         <ul className="dropdown-menu" aria-labelledby="roleDropdown">
                            {this.props.roles.map(function (currentRow, index) {
                                return <RoleDropdownItem key={index} rowData={currentRow} container={this} />
                                }.bind(this))}
                         </ul>
                     </div>
                </td>
                <td>{ this.props.rowData.userType.name}</td>
                <td>
                    <a className="btn btn-techradar" href={ this.getRadarUrl()}>Radars</a>
                 </td>
             </tr>
        );
    }
};


function mapStateToProps(state) {
  return {
    };
};

const mapDispatchToProps = dispatch => {
  return {
        setSelectedUser : selectedUser => { dispatch(setSelectedUser(selectedUser))},
     }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserRow);