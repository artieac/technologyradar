'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { DropdownButton, Dropdown} from 'react-bootstrap';
import { RadarCollectionDropDownItem } from './RadarCollectionDropdownItem';

export class RadarTypeDropdown extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            selectedRadarType: {}
         };

         this.setSelectedItem = this.setSelectedItem.bind(this);
    }

    setSelectedItem(selectedItem){
        this.setState({selectedRadarType: selectedItem});
        this.props.selectionNotification(selectedItem);
    }

    getTitle(){
        var retVal = "Select";

        if(this.state.selectedRadarType !== undefined){
            retVal = this.state.selectedRadarType.name;
        }

        return retVal;
    }

    render(){
        if(this.props.data!==undefined){
            return(
                <DropdownButton title={this.getTitle()} id="radarTypeDropdown">
                    {this.props.data.map(function (currentRow) {
                        return <RadarCollectionDropDownItem key={ currentRow.id } dropDownItem={ currentRow } userId={this.props.userId} setSelectedItem={this.setSelectedItem }/>
                    }.bind(this))}
                </DropdownButton>
            );
        }
        else{
            return(
                <DropdownButton title="Select" id="radarTypeDropdown">
                </DropdownButton>
            );
        }
    }
};
