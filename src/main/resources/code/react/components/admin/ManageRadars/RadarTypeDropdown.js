'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
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

        if(this.state.selectedRadarType !== undefined){
            retVal = this.state.selectedRadarType.name + " v" + this.state.selectedRadarType.version;
        }

        return retVal;
    }

    render(){
        if(this.props.data!==undefined){
            return(
                <div className="dropdown">
                    <button className="btn btn-primary dropdown-toggle" type="button" id="radarTypeDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        { this.getTitle() }
                    </button>
                    <div className="dropdown-menu" aria-labelledby="radarTypeDropdown">
                        {this.props.data.map(function (currentRow, index) {
                            return <RadarTypeDropdownItem key={ index } dropDownItem={ currentRow } userId={this.props.userId} setSelectedItem={this.setSelectedItem }/>
                        }.bind(this))}
                    </div>
                </div>
            );
        }
        else{
            return(
                <div class="dropdown">
                  <button class="btn btn-primary dropdown-toggle" type="button" id="radarTypeDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    { this.getTitle() }
                  </button>
                </div>
            );
        }
    }
};
