'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../redux/reducers/adminActionTypes';
import { addRadarCollectionToState, addRadarTypeCollectionToState} from '../../../redux/reducers/adminAppReducer';
import { RadarRepository_publishRadar, RadarRepository_lockRadar, RadarRepository_deleteRadar, RadarRepository_addRadar} from '../../Repositories/RadarRepository';
import { RadarTypeRepository_getByUserId} from '../../Repositories/RadarTypeRepository';
import { DropdownButton, Dropdown} from 'react-bootstrap';

class ManageRadars extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };

        this.getRadarTypeCollectionResponse = this.getRadarTypeCollectionResponse.bind(this);
    }

    componentDidMount(){
        this.getRadarCollectionByUserId(this.state.userId);
        RadarTypeRepository_getByUserId(this.state.userId, this.getRadarTypeCollectionResponse) ;
    }

    getRadarCollectionByUserId(userId){
        fetch( '/api/User/' + userId + '/Radars',)
            .then(response => response.json())
            .then(json => this.props.addRadarCollection({ radarCollection: json}));
    }

    getRadarTypeCollectionResponse(radarTypes){
        this.props.storeRadarTypeCollection(radarTypes);
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Technology Assessments</label>
                </div>
                <p>Add an instance of your technology radar to track any changes since the last time you did this</p>
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Type</th>
                            <th width="10%">Published?</th>
                            <th width="10%">Locked?</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <RadarTableBody tableBodyData={this.props.radarCollection} userId={this.state.userId} parentContainer = { this}  radarTypes={this.props.radarTypeCollection}/>
                </table>
            </div>
        );
    }
};

class RadarTableBody extends React.Component{
    render() {
        if(typeof this.props.tableBodyData.radarCollection !== 'undefined'){
            return (
                <tbody>
                    {this.props.tableBodyData.radarCollection.map(function (currentRow) {
                        return <RadarRow key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer } />
                        }.bind(this))}
                    <NewRadarRow userId={this.props.userId} parentContainer = { this.props.parentContainer } radarTypes={this.props.radarTypes}/>
                </tbody>
            );
        }
        else{
            return(
                <tbody>
                    <NewRadarRow userId={this.props.userId}/>
                </tbody>
            );
        }
    }
};

class RadarRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            isPublished: this.props.rowData.isPublished,
            isLocked: this.props.rowData.isLocked
        };

        this.handlePublishSuccess = this.handlePublishSuccess.bind(this);
        this.handlePublishError = this.handlePublishError.bind(this);
        this.handleIsPublishedClick = this.handleIsPublishedClick.bind(this);
        this.handleLockSuccess = this.handleLockSuccess.bind(this);
        this.handleLockError = this.handleLockError.bind(this);
        this.handleIsLockedClick = this.handleIsLockedClick.bind(this);
        this.handleDeleteSuccess = this.handleDeleteSuccess.bind(this);
        this.handleDeleteError = this.handleDeleteError.bind(this);
        this.handleDeleteClick = this.handleDeleteClick.bind(this);
    }

    handlePublishSuccess() { }

    handlePublishError() {
        this.setState( { isPublished: !this.state.isPublished })
    }

    handleIsPublishedClick() {
        this.setState( { isPublished: this.refs.isPublished.checked })
        RadarRepository_publishRadar(this.props.userId, this.props.rowData.id, this.refs.isPublished.checked, this.handlePublishSuccess.bind(this), this.handlePublishError.bind(this));
    }

    handleLockSuccess() {}

    handleLockError() {
        this.setState( { isLocked: !this.state.isLocked })
    }

    handleIsLockedClick(){
        this.setState( { isLocked: this.refs.isLocked.checked })
        RadarRepository_lockRadar(this.props.userId, this.props.rowData.id, this.refs.isLocked.checked, this.handleLockSuccess.bind(this), this.handleLockError.bind(this));
    }

    handleDeleteSuccess() {
        this.props.parentContainer.getRadarCollectionByUserId(this.props.userId,);
    }

    handleDeleteError() {

    }

    handleDeleteClick() {
        RadarRepository_deleteRadar(this.props.userId, this.props.rowData.id, this.handleDeleteSuccess, this.handleDeleteError);
    }

    getAddFromPreviousLink(userId, radarId){
        return '/admin/user/' + userId + '/radar/' + radarId + '/addfromprevious';
    }

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td>{ this.props.rowData.radarType.name}</td>
                 <td><input type="checkbox" ref="isPublished" defaultChecked={ this.state.isPublished } onClick = { this.handleIsPublishedClick }/></td>
                 <td><input type="checkbox" ref="isLocked" defaultChecked={ this.state.isLocked } onClick = { this.handleIsLockedClick }/></td>
                 <td>
                    <Link to={ this.getAddFromPreviousLink(this.props.userId, this.props.rowData.id)}>
                        <button type="button" className="btn btn-primary" disabled={(this.state.isPublished==true) || (this.state.isLocked==true)}>Add From Previous</button>
                    </Link>
                </td>
                 <td><button type="button" className="btn btn-primary" disabled={(this.state.isPublished==true) || (this.state.isLocked==true)} onClick = { this.handleDeleteClick }>Delete</button></td>
             </tr>
        );
    }
};

class NewRadarRow extends React.Component{
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

class RadarTypeDropdown extends React.Component{
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

class RadarCollectionDropDownItem extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };
    }

    handleOnClick(){
        this.props.setSelectedItem(this.props.dropDownItem);
    }

    render(){
        if(this.props.dropDownItem!==undefined){
            return (
                <Dropdown.Item eventKey={this.props.dropDownItem.id} onClick={this.handleOnClick.bind(this)}>{ this.props.dropDownItem.name }</Dropdown.Item>
            );
        }
        else{
            return <Dropdown.Item></Dropdown.Item>
        }
    }
};

const mapMRDispatchToProps = dispatch => {
  return {
        addRadarCollection : radarCollection => { dispatch(addRadarCollectionToState(radarCollection))},
        storeRadarTypeCollection : radarTypeCollection => { dispatch(addRadarTypeCollectionToState(radarTypeCollection))}
    }
};


function mapMRStateToProps(state) {
  return {
    	radarCollection: state.radarCollection,
    	radarTypeCollection: state.radarTypeCollection
    };
}

export default connect(
  mapMRStateToProps,
    mapMRDispatchToProps
)(ManageRadars);