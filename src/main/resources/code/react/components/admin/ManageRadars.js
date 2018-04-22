'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import radarCollectionActions from '../../../reflux/actions/RadarCollectionActions';
import RadarCollectionStore from '../../../reflux/stores/RadarCollectionStore';

var ManageRadars = createReactClass({
    mixins: [
        Reflux.connect(RadarCollectionStore, "radarCollection")
    ],

    userId: jQuery("#userId").val(),

    getInitialState: function() {
        return {
            radarCollection: [],
            userId: jQuery("#userId").val()
        };
    },

    componentDidMount: function () {
        // Add event listeners in componentDidMount
        this.listenTo(RadarCollectionStore, this.handleGetByUserIdResponse);
        radarCollectionActions.getByUserId(this.userId);
    },

    handleGetByUserIdResponse: function (getByUserIdResponse) {
        console.log(getByUserIdResponse);
        this.setState({radarCollection: getByUserIdResponse});
    },

    render:function() {
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
                    <RadarTableBody tableBodyData={this.state.radarCollection} userId={this.state.userId}/>
                </table>
            </div>
        );
    }
});

var RadarTableBody = createReactClass({
    render:function() {
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
});

var RadarRow = createReactClass({

    handleIsPublishedClick:function(){
        radarCollectionActions.publishRadarInstance(this.props.userId, this.props.rowData.id, !this.props.rowData.isPublished);
    },

    handleIsLockedClick:function(){
        radarCollectionActions.lockRadarInstance(this.props.userId, this.props.rowData.id, !this.props.rowData.isLocked);
    },

    handleAddFromPreviousClick:function() {

    },

    handleDeleteClick:function() {
        radarCollectionActions.deleteRadarInstance(this.props.userId, this.props.rowData.id);
    },

    render:function() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td><input type="checkbox" ref="isPublished" value={ this.props.rowData.isPublished } defaultChecked={ this.props.rowData.isPublished } onChange={this.handleIsPublishedClick}/></td>
                 <td><input type="checkbox" ref="isLocked" value={ this.props.rowData.isLocked } defaultChecked={ this.props.rowData.isLocked } onChange={this.handleIsLockedClick}/></td>
                 <td><button type="button" className="btn btn-primary" disabled={(this.props.rowData.isPublished==true) || (this.props.rowData.isLocked==true)} onClick={this.handleAddFromPreviousClick}>Add From Previous</button></td>
                 <td><button type="button" className="btn btn-primary" disabled={(this.props.rowData.isPublished==true) || (this.props.rowData.isLocked==true)} onClick={this.handleDeleteClick}>Delete</button></td>
             </tr>
        );
    },
});

var NewRadarRow = createReactClass({
    getInitialState: function() {
        return {
            radarNameInput: ''
        };
    },

    handleSaveButtonClick:function(userId){
        radarCollectionActions.createRadarInstance(this.props.userId, this.state.radarNameInput);
    },

    handleRadarNameChange:function(event){
        this.setState({radarNameInput:event.target.value});
    },

    render:function(){
        return(
            <tr>
                <td><input type="text" ref="radarName" required="true" onChange={ this.handleRadarNameChange } /></td>
                <td><input type="button" className="btn btn-primary" value="Add Radar" onClick={this.handleSaveButtonClick} /></td>
            </tr>
        );
    },
});

module.exports = ManageRadars;
