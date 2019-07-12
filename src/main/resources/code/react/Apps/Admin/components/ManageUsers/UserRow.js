'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";

class UserRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

    }

    componentDidUpdate(){
    }

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td>{ this.props.rowData.email}</td>
                 <td>{ this.props.rowData.role.name}</td>
                 <td>{ this.props.rowData.userType}</td>
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
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserRow);