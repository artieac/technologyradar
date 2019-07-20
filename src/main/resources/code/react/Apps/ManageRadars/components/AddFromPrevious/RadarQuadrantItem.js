'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";

export default class RadarQuadrantItem extends React.Component{
    handleCheckboxClick(event){
        this.props.handleOnClick(this.props.quadrantItem.assessmentItem);
    }

    render(){
        return(
            <tr>
                <td><input type="checkbox" name="addToRadar" onChange= {(event) => { this.handleCheckboxClick(event) }}/></td>
                <td>{ this.props.quadrantItem.assessmentItem.radarRing.name }</td>
                <td><a href="">{ this.props.quadrantItem.name}</a></td>
            </tr>
        );
    }
}