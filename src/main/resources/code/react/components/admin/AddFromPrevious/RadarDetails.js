'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import RadarQuadrant from './RadarQuadrant';

export default class RadarDetails extends React.Component{
    render(){
        if(this.props.radarInstance !== undefined && this.props.radarInstance.quadrants !== undefined){
            return(
                <div className="col-lg-4">
                    {this.props.radarInstance.quadrants.map(function (currentRow) {
                        return <RadarQuadrant key={currentRow.quadrant} quadrant = { currentRow } handleOnClick = { this.props.handleOnClick }/>
                    }.bind(this))}
                </div>
            );
        }
        else{
            return(<div className="col-lg-4"></div>);
        }
    }
}