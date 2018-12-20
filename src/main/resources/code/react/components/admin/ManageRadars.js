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

    constructor(props){
        super(props);
         this.state = {
            isPublished: this.props.rowData.isPublished,
            isLocked: this.props.rowData.isLocked
        };
        this.handleIsPublishedClick = this.handleIsPublishedClick.bind(this);
        this.handleIsLockedClick = this.handleIsLockedClick.bind(this);
    }

    handleIsPublishedClick() {
        this.setState( { isPublished: !this.refs.isPublished.checked })

        var radarToUpdate = {};
        radarToUpdate.isPublished = this.state.isPublished;

        $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + this.props.userId + '/Radar/' + this.props.rowData.id + '/Publish',
              data: JSON.stringify(radarToUpdate),
              success: function() {
               }.bind(this),
              error: function(xhr, status, err) {
                    this.setState( { isPublished: !this.state.isPublished })
              }.bind(this)
            });
    }

    handleIsLockedClick(){
        this.setState( { isLocked: !this.refs.isLocked.checked })

        var radarToUpdate = {};
        radarToUpdate.isLocked = this.state.isLocked;

        $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "PUT",
              url: '/api/User/' + this.props.userId + '/Radar/' + this.props.rowData.id + '/Lock',
              data: JSON.stringify(radarToUpdate),
              success: function() {
                   this.setState( { isLocked: !this.state.isLocked })
               }.bind(this),
             error: function(xhr, status, err) {

             }.bind(this)
            });
    }

    handleDeleteClick(userId, radarId) {
        $.ajax({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "DELETE",
              url: '/api/User/' + userId + '/Radar/' + radarId,
              success: function() {
                this.getRadarCollectionByUserId(userId,);
               }.bind(this)
            });
    }

    getAddFromPreviousLink(userId, radarId){
        return '/admin/user/' + userId + '/radar/' + radarId + '/addfromprevious';
    }

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td><input type="checkbox" ref="isPublished" value= { this.state.isPublished } defaultChecked={ this.state.isPublished } onChange = { this.handleIsPublishedClick }/></td>
                 <td><input type="checkbox" ref="isLocked" value = { this.state.isLocked } defaultChecked={ this.state.isLocked } onChange = { this.handleIsLockedClick }/></td>
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

    handleAddRadar() {
        var radarToAdd = {};
        radarToAdd.name = this.state.radarNameInput;

        $.post({
              headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
              },
              type: "POST",
              url: '/api/User/' + this.props.match.params.userId + '/Radar',
              data: JSON.stringify(radarToAdd),
              success: function() {
                this.getRadarCollectionByUserId(this.props.match.params.userId,);
               }.bind(this)
            });
    }

    handleRadarNameChange(event){
        this.setState({radarNameInput:event.target.value});
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