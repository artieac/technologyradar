'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { DropdownButton, Dropdown} from 'react-bootstrap';

export default class RadarsDropdownItem extends React.Component{
    handleOnClick(){
        this.getSourceRadarInstance(this.props.userId, this.props.dropDownItem.id);
    }

    getSourceRadarInstance(userId, radarId, radarTypeId){
        fetch( '/api/User/' + userId + '/Radar/' + radarId)
            .then(response => response.json())
            .then(json => this.props.setSourceRadarInstance({ sourceRadar: json}));
    }

    render(){
        return (<Dropdown.Item eventKey={this.props.dropDownItem.id} onClick={this.handleOnClick.bind(this)}>{ this.props.dropDownItem.name }</Dropdown.Item>);
    }
}