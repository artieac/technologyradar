'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { addRadarCollectionToState, addRadarTypeCollectionToState} from '../../../../redux/reducers/adminAppReducer';
import { RadarRepository} from '../../../Repositories/RadarRepository';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository'
import { RadarRow } from './RadarRow';
import { NewRadarRow } from './NewRadarRow';

class ManageRadarsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };

        this.radarRepository = new RadarRepository();
        this.radarTypeRepository = new RadarTypeRepository();

        this.getUserRadarsResponse = this.getUserRadarsResponse.bind(this);
        this.getRadarTypeCollectionResponse = this.getRadarTypeCollectionResponse.bind(this);
    }

    componentDidMount(){
        this.radarRepository.getByUserId(this.state.userId, this.getUserRadarsResponse);
        this.radarTypeRepository.getByUserId(this.state.userId, this.getRadarTypeCollectionResponse) ;
    }

    getUserRadarsResponse(radarCollection){
        this.props.storeRadarCollection(radarCollection);
        this.forceUpdate();
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

const mapDispatchToProps = dispatch => {
  return {
        storeRadarCollection : radarCollection => { dispatch(addRadarCollectionToState(radarCollection))},
        storeRadarTypeCollection : radarTypeCollection => { dispatch(addRadarTypeCollectionToState(radarTypeCollection))}
    }
};


function mapStateToProps(state) {
  return {
    	radarCollection: state.radarCollection,
    	radarTypeCollection: state.radarTypeCollection
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageRadarsPage);