'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import { connect } from "react-redux";
import createReactClass from 'create-react-class';
import { DropdownButton, Dropdown} from 'react-bootstrap';
import { RadarTemplateDropdownItem } from './RadarTemplateDropdownItem';

export class RadarTemplateDropdown extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            selectedRadarTemplate: {}
         };

         this.setSelectedItem = this.setSelectedItem.bind(this);
    }

    setSelectedItem(selectedItem){
        this.setState({selectedRadarTemplate: selectedItem});
        this.props.selectionNotification(selectedItem);
    }

    getTitle(){
        var retVal = "Select Template";

        if(this.state.selectedRadarTemplate !== undefined && this.state.selectedRadarTemplate.name !==undefined){
            retVal = this.state.selectedRadarTemplate.name;
        }

        return retVal;
    }

    render(){
        if(this.props.data!==undefined){
            return(
                <div className="dropdown">
                    <button className="btn btn-techradar dropdown-toggle" type="button" id="radarTemplateDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        { this.getTitle() }
                    </button>
                    <div className="dropdown-menu" aria-labelledby="radarTemplateDropdown">
                        <ul>
                            {this.props.data.map(function (currentRow, index) {
                                return <RadarTemplateDropdownItem key={ index } dropDownItem={ currentRow } setSelectedItem={this.setSelectedItem }/>
                            }.bind(this))}
                        </ul>
                    </div>
                </div>
            );
        }
        else{
            return(
                <div class="dropdown">
                  <button class="btn btn-techradar dropdown-toggle" type="button" id="radarTemplateDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    { this.getTitle() }
                  </button>
                </div>
            );
        }
    }
};
