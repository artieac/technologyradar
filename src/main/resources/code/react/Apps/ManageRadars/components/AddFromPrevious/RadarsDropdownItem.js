'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";

export default class RadarsDropdownItem extends React.Component{
    constructor(props){
        super(props);
         this.state = {
         };

         this.handleOnClick = this.handleOnClick.bind(this);
    }

    handleOnClick(){
        this.getSourceRadarInstance(this.props.userId, this.props.dropDownItem.id);
    }

    getSourceRadarInstance(userId, radarId, radarTypeId){
        fetch( '/api/User/' + userId + '/Radar/' + radarId)
            .then(response => response.json())
            .then(json => this.props.setSourceRadarInstance({ sourceRadar: json}));
    }

    render(){
        return (
            <li>
                <a className="dropdown-item" onClick={this.handleOnClick}>{ this.props.dropDownItem.name } - { this.props.dropDownItem.formattedAssessmentDate }</a>
            </li>
        );
    }
}