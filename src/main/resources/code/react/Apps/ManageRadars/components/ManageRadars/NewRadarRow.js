'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarReducer from '../../redux/RadarReducer';
import { addRadarsToState} from '../../redux/RadarReducer';
import { DropdownButton, Dropdown} from 'react-bootstrap';
import { RadarRepository } from '../../../../Repositories/RadarRepository';
import { RadarTypeDropdown } from './RadarTypeDropdown';

class NewRadarRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: '',
            selectedRadarType: {}
        };

        this.radarRepository = new RadarRepository();

        this.handleRadarNameChange = this.handleRadarNameChange.bind(this);
        this.handleAddSuccess = this.handleAddSuccess.bind(this);
        this.handleGetByUserIdSuccess = this.handleGetByUserIdSuccess.bind(this);
        this.handleAddError = this.handleAddError.bind(this);
        this.handleAddRadar = this.handleAddRadar.bind(this);
        this.handleDropdownSelectionNotify = this.handleDropdownSelectionNotify.bind(this);
    }


    handleRadarNameChange(event){
        this.setState({radarNameInput:event.target.value});
    }

    handleAddSuccess(radars) {
        this.props.storeRadars(radars);
    }

    handleGetByUserIdSuccess(radars){
        this.props.storeRadars(radars);
        this.forceUpdate();
    }

    handleAddError() {

    }

    handleAddRadar() {
        this.radarRepository.addRadar(this.props.currentUser.id, this.state.radarNameInput, this.state.selectedRadarType, this.handleAddSuccess, this.handleAddError );
    }

    handleDropdownSelectionNotify(radarType){
        this.setState({selectedRadarType: radarType});
    }

    render(){
        return(
            <tr>
                <td><input type="text" ref="radarName" required="required" onChange={ this.handleRadarNameChange } /></td>
                <td>
                    <RadarTypeDropdown selectionNotification={this.handleDropdownSelectionNotify} data={this.props.radarTypes}/>
                </td>
                <td><input type="button" className="btn btn-techradar" value="Add Radar" onClick={this.handleAddRadar} /></td>
            </tr>
        );
    }
};

function mapStateToProps(state) {
  return {
        radarTypes: state.radarReducer.radarTypes,
        currentUser: state.radarReducer.currentUser
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeRadars : (userRadars) => { dispatch(addRadarsToState(userRadars))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NewRadarRow);