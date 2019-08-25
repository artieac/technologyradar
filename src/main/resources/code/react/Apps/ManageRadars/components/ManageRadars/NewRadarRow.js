'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import { addRadarsToState, addRadarRingSetsToState, addRadarCategorySetsToState} from '../../redux/RadarReducer';
import { UserRepository } from '../../../../Repositories/UserRepository';
import { RadarRepository } from '../../../../Repositories/RadarRepository';
import { RadarRingRepository } from '../../../../Repositories/RadarRingRepository';
import { RadarCategoryRepository } from '../../../../Repositories/RadarCategoryRepository';
import { GenericDropdown } from './GenericDropdown';

class NewRadarRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: '',
            selectedRadarRingSet: {},
            selectedRadarCategorySet: {}
        };

        this.userRepository = new UserRepository();
        this.radarRepository = new RadarRepository();
        this.radarRingRepository = new RadarRingRepository();
        this.radarCategoryRepository = new RadarCategoryRepository();

        this.handleRadarNameChange = this.handleRadarNameChange.bind(this);
        this.handleAddSuccess = this.handleAddSuccess.bind(this);
        this.handleGetByUserIdSuccess = this.handleGetByUserIdSuccess.bind(this);
        this.handleAddError = this.handleAddError.bind(this);
        this.handleAddRadar = this.handleAddRadar.bind(this);
        this.handleRadarRingDropdownSelectionNotify = this.handleRadarRingDropdownSelectionNotify.bind(this);
        this.handleRadarCategoryDropdownSelectionNotify = this.handleRadarCategoryDropdownSelectionNotify.bind(this);
        this.handleGetUserSuccess = this.handleGetUserSuccess.bind(this);
    }

    componentDidMount(){
        this.userRepository.getUser(this.handleGetUserSuccess);
    }

    handleGetUserSuccess(currentUser){
        this.props.storeCurrentUser(currentUser);
        this.radarRingRepository.getByUserId(currentUser.id, this.props.storeRadarRingSets);
        this.radarCategoryRepository.getByUserId(currentUser.id, this.props.storeRadarCategorySets);
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
        if(this.state.radarNameInput!=""){
            this.radarRepository.addRadar(this.props.currentUser.id, this.state.radarNameInput, this.state.selectedRadarRingSet, this.state.selectedRadarCategorySet, this.handleAddSuccess, this.handleAddError );
        }
        else{
            alert("You must enter a name for the radar.");
        }
    }

    handleRadarRingDropdownSelectionNotify(radarRingSet){
        this.setState({selectedRadarRingSet: radarRingSet});
    }

    handleRadarCategoryDropdownSelectionNotify(radarCategorySet){
        this.setState({selectedRadarCategorySet: radarCategorySet});
    }

    render(){
        return(
            <tr>
                <td><input type="text" ref="radarName" required="required" onChange={ this.handleRadarNameChange } /></td>
                <td></td>
                <td>
                    <GenericDropdown selectionNotification={this.handleRadarCategoryDropdownSelectionNotify} data={this.props.userRadarCategorySets.radarCategorySets}/>
                </td>
                <td>
                    <GenericDropdown selectionNotification={this.handleRadarRingDropdownSelectionNotify} data={this.props.userRadarRingSets.radarRingSets}/>
                </td>
                <td><input type="button" className="btn btn-techradar" value="Add Radar" onClick={this.handleAddRadar} /></td>
            </tr>
        );
    }
};

function mapStateToProps(state) {
  return {
    	currentUser: state.userReducer.currentUser,
        userRadarRingSets: state.radarReducer.radarRingSets,
        userRadarCategorySets: state.radarReducer.radarCategorySets
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))},
        storeRadars : userRadars => { dispatch(addRadarsToState(userRadars))},
        storeRadarRingSets: radarRingSets => {dispatch(addRadarRingSetsToState(radarRingSets))},
        storeRadarCategorySets: radarCategorySets => {dispatch(addRadarCategorySetsToState(radarCategorySets))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NewRadarRow);