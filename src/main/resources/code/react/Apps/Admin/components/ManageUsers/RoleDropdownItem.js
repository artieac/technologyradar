'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { DropdownButton, Dropdown} from 'react-bootstrap';

export default class RoleDropdownItem extends React.Component{
    handleOnClick(event){
        this.props.container.changeRowSelection(this.props.rowData);
    }

    render(){
        return (
            <li>
                <a onClick={(event) => this.handleOnClick(event)}>{this.props.rowData.name}</a>
            </li>
        );
    }
}