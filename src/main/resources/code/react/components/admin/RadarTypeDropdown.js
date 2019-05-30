'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import { SplitButton, MenuItem } from 'react-bootstrap';

class RadarTypeDropDown extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            selectedRadarType: {}
         };

         this.setSelectedItem = this.setSelectedItem.bind(this);
    }

    setSelectedItem(selectedItem){
        this.setState({selectedRadarType: selectedItem});
        this.props.parent.selectionNotification(selectedItem);
    }

    getTitle(){
        var retVal = "";

        if(this.state.selectedRadarType !== undefined){
            retVal = this.state.selectedRadarType.name;
        }

        return retVal;
    }

    render(){
        if(this.props.data!==undefined){
            return(
                <SplitButton title={this.getTitle()} id="radarTypeDropdown">
                    {this.props.data.map(function (currentRow) {
                        return <RadarCollectionDropDownItem key={ currentRow.id } dropDownItem={ currentRow } userId={this.props.userId} setSelectedItem={this.setSelectedItem }/>
                    }.bind(this))}
                </SplitButton>
            );
        }
        else{
            return(
                <SplitButton title="Select" id="radarTypeDropdown">
                </SplitButton>
            );
        }
    }
}

class RadarCollectionDropDownItem extends React.Component{
    handleOnClick(){
        this.props.setSelectedItem(this.props.dropDownItem);
    }

    render(){
        return (
            <MenuItem eventKey={this.props.dropDownItem.id} onClick={this.handleOnClick.bind(this)}>{ this.props.dropDownItem.name }</MenuItem>
        );
    }
}

export { RadarTypeDropDown, RadarCollectionDropDownItem};