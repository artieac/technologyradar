'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../redux/reducers/adminActionTypes';
import addRadarCollectionToState from '../../../redux/reducers/adminAppReducer';

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
                    <RadarTableBody tableBodyData={this.props.radarCollection} userId={this.state.userId}/>
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
                        return <RadarRow key={currentRow.id} rowData={currentRow} userId={this.props.userId}/>
                        }.bind(this))}
                    <NewRadarRow userId={this.props.userId}/>
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

    handleIsPublishedClick(){
        radarCollectionActions.publishRadarInstance(this.props.userId, this.props.rowData.id, !this.props.rowData.isPublished);
    }

    handleIsLockedClick(){
        radarCollectionActions.lockRadarInstance(this.props.userId, this.props.rowData.id, !this.props.rowData.isLocked);
    }

    handleAddFromPreviousClick() {

    }

    handleDeleteClick() {
        radarCollectionActions.deleteRadarInstance(this.props.userId, this.props.rowData.id);
    }

    getAddFromPreviousLink(userId, radarId){
        return '/admin/user/' + userId + '/radar/' + radarId + '/addfromprevious';
    }

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td><input type="checkbox" ref="isPublished" value={ this.props.rowData.isPublished } defaultChecked={ this.props.rowData.isPublished } onChange={this.handleIsPublishedClick}/></td>
                 <td><input type="checkbox" ref="isLocked" value={ this.props.rowData.isLocked } defaultChecked={ this.props.rowData.isLocked } onChange={this.handleIsLockedClick}/></td>
                 <td>
                    <Link to={ this.getAddFromPreviousLink(this.props.userId, this.props.rowData.id)}>
                        <button type="button" className="btn btn-primary" disabled={(this.props.rowData.isPublished==true) || (this.props.rowData.isLocked==true)}>Add From Previous</button>
                    </Link>
                </td>
                 <td><button type="button" className="btn btn-primary" disabled={(this.props.rowData.isPublished==true) || (this.props.rowData.isLocked==true)} onClick={this.handleDeleteClick}>Delete</button></td>
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
    }

    handleSaveButtonClick(userId){
        radarCollectionActions.createRadarInstance(this.props.userId, this.state.radarNameInput);
    }

    handleRadarNameChange(event){
        this.setState({radarNameInput:event.target.value});
    }

    render(){
        return(
            <tr>
                <td><input type="text" ref="radarName" required="true" onChange={ this.handleRadarNameChange } /></td>
                <td><input type="button" className="btn btn-primary" value="Add Radar" onClick={this.handleSaveButtonClick} /></td>
            </tr>
        );
    }
};

const mapDispatchToProps = dispatch => {
  return {
    addRadarCollection : radarCollection => {
        dispatch({
            type : actionTypes.SETRADARCOLLECTION,
            payload: radarCollection
        })
    }};
};

function mapStateToProps(state) {
  return {
    	radarCollection: state.radarCollection
    };
}

export default connect(
  mapStateToProps,
    mapDispatchToProps
)(ManageRadars);