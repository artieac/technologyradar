'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import { connect } from "react-redux";
import createReactClass from 'create-react-class';
import { DropdownButton, Dropdown} from 'react-bootstrap';
import { RadarTypeDropdownItem } from './RadarTypeDropdownItem';

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

        if(this.state.selectedRadarType !== undefined && this.state.selectedRadarType.name !==undefined){
            retVal = this.state.selectedRadarType.name + " v" + this.state.selectedRadarType.version;
        }

        return retVal;
    }

    render(){
        if(this.props.data!==undefined){
            return(
                <div className="dropdown">
                    <button className="btn btn-techradar dropdown-toggle" type="button" id="radarTypeDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        { this.getTitle() }
                    </button>
                    <div className="dropdown-menu" aria-labelledby="radarTypeDropdown">
                        {this.props.data.map(function (currentRow, index) {
                            return <RadarTypeDropdownItem key={ index } dropDownItem={ currentRow } setSelectedItem={this.setSelectedItem }/>
                        }.bind(this))}
                    </div>
                </div>
            );
        }
        else{
            return(
                <div class="dropdown">
                  <button class="btn btn-techradar dropdown-toggle" type="button" id="radarTypeDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    { this.getTitle() }
                  </button>
                </div>
            );
        }
    }
};
