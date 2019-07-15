'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import { connect } from "react-redux";
import createReactClass from 'create-react-class';
import UserRow from './UserRow';

class UserTableBody extends React.Component{
    render() {
        if(typeof this.props.users !== 'undefined'){
            return (
                <tbody>
                    {this.props.users.map(function (currentRow, index) {
                        return <UserRow key={currentRow.id} userIndex={index} rowData={currentRow} container={this.props.container} roles={this.props.roles}/>
                        }.bind(this))}
                </tbody>
            );
        }
        else{
            return(
                <tbody>
                </tbody>
            );
        }
    }
};

function mapStateToProps(state) {
  return {
    };
};

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserTableBody);