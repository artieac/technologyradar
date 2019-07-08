'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { DropdownButton, Dropdown} from 'react-bootstrap';
import RadarsDropdownItem from './RadarsDropdownItem';

export default class RadarsDropdown extends React.Component{
    constructor(props){
        super(props);
         this.state = {
         };
    }

    getTitle(){
        var retVal = "Select Radar";

        if(this.props.itemSelection !== undefined){
            retVal = this.props.itemSelection.radarName;
        }

        return retVal;
    }

    render(){
        const { setSourceRadarInstance } = this.props;

        if(this.props.data!==undefined){
            return(
                <DropdownButton title={this.getTitle()} id="radarCollection">
                    {this.props.data.map(function (currentRow) {
                        return <RadarsDropdownItem key={ currentRow.id } dropDownItem={ currentRow } userId={this.props.userId} setSourceRadarInstance={setSourceRadarInstance}/>
                    }.bind(this))}
                </DropdownButton>
            );
        }
        else{
            return(
                <SplitButton title="Select" id="radarCollection">
                </SplitButton>
            );
        }
    }
}