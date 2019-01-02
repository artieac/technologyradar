'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../redux/reducers/adminActionTypes';
import { addRadarCollectionToState } from '../../../redux/reducers/adminAppReducer';
import { RadarRepository_publishRadar, RadarRepository_lockRadar, RadarRepository_deleteRadar, RadarRepository_addRadar} from '../../Repositories/RadarRepository';

class ManageRadars extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };
    }

    componentDidMount(){
        this.getRadarCollectionByUserId(this.state.userId);
    }

    getRadarCollectionByUserId(userId){
        fetch( '/api/User/' + userId + '/Radars',)
            .then(response => response.json())
            .then(json => this.props.addRadarCollection({ radarCollection: json}));
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
                            <th width="10%">Published?</th>
                            <th width="10%">Locked?</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <RadarTableBody tableBodyData={this.props.radarCollection} userId={this.state.userId} parentContainer = { this} />
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
                        return <RadarRow key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer }/>
                        }.bind(this))}
                    <NewRadarRow userId={this.props.userId} parentContainer = { this.props.parentContainer }/>
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
            radarNameInput: ''
        };

        this.handleRadarNameChange = this.handleRadarNameChange.bind(this);
        this.handleAddSuccess = this.handleAddSuccess.bind(this);
        this.handleAddError = this.handleAddError.bind(this);
        this.handleAddRadar = this.handleAddRadar.bind(this);
    }

    handleRadarNameChange(event){
        this.setState({radarNameInput:event.target.value});
    }

    handleAddSuccess() {
        this.props.parentContainer.getRadarCollectionByUserId(this.props.userId,);
    }

    handleAddError() {

    }

    handleAddRadar() {
        RadarRepository_addRadar(this.props.userId, this.state.radarNameInput, this.handleAddSuccess, this.handleAddError );
    }


    render(){
        return(
            <tr>
                <td><input type="text" ref="radarName" required="true" onChange={ this.handleRadarNameChange } /></td>
                <td><input type="button" className="btn btn-primary" value="Add Radar" onClick={this.handleAddRadar} /></td>
            </tr>
        );
    }
};

const mapMRDispatchToProps = dispatch => {
  return {
        addRadarCollection : radarCollection => { dispatch(addRadarCollectionToState(radarCollection))}
    }
};


function mapMRStateToProps(state) {
  return {
    	radarCollection: state.radarCollection
    };
}

export default connect(
  mapMRStateToProps,
    mapMRDispatchToProps
)(ManageRadars);