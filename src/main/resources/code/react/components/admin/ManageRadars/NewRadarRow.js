'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { DropdownButton, Dropdown} from 'react-bootstrap';
import { RadarCollectionDropDownItem } from './RadarCollectionDropdownItem';
import { RadarRepository_addRadar} from '../../../Repositories/RadarRepository';
import { RadarTypeDropdown } from './RadarTypeDropdown';

export class NewRadarRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: '',
            selectedRadarType: {}
        };

        this.handleRadarNameChange = this.handleRadarNameChange.bind(this);
        this.handleAddSuccess = this.handleAddSuccess.bind(this);
        this.handleAddError = this.handleAddError.bind(this);
        this.handleAddRadar = this.handleAddRadar.bind(this);
        this.handleDropdownSelectionNotify = this.handleDropdownSelectionNotify.bind(this);
    }


    handleRadarNameChange(event){
        this.setState({radarNameInput:event.target.value});
    }

    handleAddSuccess() {

    }

    handleAddError() {

    }

    handleAddRadar() {
        RadarRepository_addRadar(this.props.userId, this.state.radarNameInput, this.state.selectedRadarType, this.handleAddSuccess, this.handleAddError );
    }

    handleDropdownSelectionNotify(radarType){
        this.setState({selectedRadarType: radarType});
    }

    render(){
        return(
            <tr>
                <td><input type="text" ref="radarName" required="required" onChange={ this.handleRadarNameChange } /></td>
                <td>
                    <RadarTypeDropdown userId={this.props.userId} selectionNotification={this.handleDropdownSelectionNotify} data={this.props.radarTypes}/>
                </td>
                <td><input type="button" className="btn btn-primary" value="Add Radar" onClick={this.handleAddRadar} /></td>
            </tr>
        );
    }
};