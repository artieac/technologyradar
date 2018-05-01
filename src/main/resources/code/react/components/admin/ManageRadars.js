'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import radarCollectionActions from '../../../reflux/actions/RadarCollectionActions';
import RadarCollectionStore from '../../../reflux/stores/RadarCollectionStore';
import StoreManager from '../../stores/StoreManager';

class ManageRadars extends React.Component{
    constructor(props){
        super(props);

        this.storeManager = new StoreManager();

        this.state = {
            radarCollection: [],
            userId: jQuery("#userId").val()
        };
    }

    componentDidMount () {
        this.storeManager.getRadarCollectionStore().onGetByUserIdTwo(this.userId);
        this.setState({radarCollection: storeManager.radarCollection});
    }

    handleGetByUserIdResponse (getByUserIdResponse) {
        console.log(getByUserIdResponse);
        this.setState({radarCollection: getByUserIdResponse});
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
                    <RadarTableBody tableBodyData={this.state.radarCollection} userId={this.state.userId}/>
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

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td><input type="checkbox" ref="isPublished" value={ this.props.rowData.isPublished } defaultChecked={ this.props.rowData.isPublished } onChange={this.handleIsPublishedClick}/></td>
                 <td><input type="checkbox" ref="isLocked" value={ this.props.rowData.isLocked } defaultChecked={ this.props.rowData.isLocked } onChange={this.handleIsLockedClick}/></td>
                 <td><button type="button" className="btn btn-primary" disabled={(this.props.rowData.isPublished==true) || (this.props.rowData.isLocked==true)} onClick={this.handleAddFromPreviousClick}>Add From Previous</button></td>
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

export default ManageRadars;
