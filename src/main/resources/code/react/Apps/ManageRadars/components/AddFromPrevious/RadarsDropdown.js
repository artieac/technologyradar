'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import RadarsDropdownItem from './RadarsDropdownItem';

export default class RadarsDropdown extends React.Component{
    constructor(props){
        super(props);
         this.state = {
         };

         this.getTitle = this.getTitle.bind(this);
    }

    getTitle(){
        var retVal = "Select Radar";

        if(this.props.itemSelection !== undefined){
            retVal = this.props.itemSelection.radarName + " - " + this.props.itemSelection.formattedAssessmentDate;
        }

        return retVal;
    }

    render(){
        const { setSourceRadarInstance } = this.props;

        if(this.props.data!==undefined){
            return(
                <div className="dropdown">
                    <button className="btn btn-techradar dropdown-toggle" type="button" id="previousRadarDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        { this.getTitle() }
                        <span className="caret"></span>
                    </button>
                    <div className="dropdown-menu" aria-labelledby="previousRadarDropdown">
                        <ul>
                            {this.props.data.map(function (currentRow) {
                                return <RadarsDropdownItem key={ currentRow.id } dropDownItem={ currentRow } userId={this.props.userId} setSourceRadarInstance={setSourceRadarInstance}/>
                            }.bind(this))}
                        </ul>
                    </div>
                </div>
            );
        }
        else{
            return(
                <div></div>
            );
        }
    }
}