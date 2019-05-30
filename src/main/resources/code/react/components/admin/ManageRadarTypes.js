import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../redux/reducers/adminActionTypes';
import { addRadarTypeCollectionToState } from '../../../redux/reducers/adminAppReducer';
import { RadarRepository_publishRadar, RadarRepository_lockRadar, RadarRepository_deleteRadar, RadarRepository_addRadar} from '../../Repositories/RadarRepository';
import { RadarTypeRepository_getByUserId, RadarTypeRepository_add, RadarTypeRepository_update, RadarTypeRepository_createDefaultRadarType } from '../../Repositories/RadarTypeRepository';

class ManageRadarTypes extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };
    }

    componentDidMount(){
        this.getRadarTypeCollectionByUserId(this.state.userId);
    }

    getRadarTypeCollectionByUserId(userId){
        jQuery.ajax({
                url: '/api/User/' + userId + '/RadarTypes',
                async: true,
                dataType: 'json',
                success: function (radarTypeCollection) {
                    this.props.storeRadarTypeCollection(radarTypeCollection);
                }.bind(this)
            });
    }

    render() {
        if(this.props.radarTypeCollection !== undefined && this.props.radarTypeCollection.length > 0){
            return (
                <div className="bodyContent">
                    <div className="contentPageTitle">
                        <label>Manage Radar Types</label>
                    </div>
                    <p>Add a new type to have rate different types of things</p>
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="row">
                                <div className="col-lg-2"></div>
                                <div className="col-lg-2">Name</div>
                                <div className="col-lg-2">Action</div>
                            </div>
                            {
                                this.props.radarTypeCollection.map(function (currentRow) {
                                    return <RadarTypeRow key={currentRow.id} rowData={currentRow} userId={this.state.userId} parentContainer = { this }/>
                                }.bind(this))
                            }
                            <NewRadarTypeRow userId={this.props.userId} parentContainer = { this }/>
                        </div>
                    </div>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};

class RadarTypeRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            showDetails: false
        };

        this.handleRadarTypeSelection = this.handleRadarTypeSelection.bind(this);
        this.handleSaveRadarType = this.handleSaveRadarType.bind(this);

    }

    handleRadarTypeSelection(event){
        this.setState({ showDetails: !this.state.showDetails});
    }

    handleRadarTypeNameChangeEvent(event){
        this.props.rowData.name = event.target.value;
    }

    handleSaveRadarType(radarType){
        if(radarType.id > 0){
            RadarTypeRepository_update(this.props.userId, radarType);
        }
        else{
            RadarTypeRepository_add(this.props.userId, radarType);
        }
    }

    render() {
        if(typeof this.props.rowData !== 'undefined'){
            return (
                <div onClick={() => { this.handleRadarTypeSelection(event) }}>
                    <div className={this.state.showDetails ? 'row border' : 'row'}>
                        <div className="col-lg-2">
                            <input type="text" defaultValue={this.props.rowData.name }  onChange= {(event) => { this.handleRadarTypeNameChangeEvent(event) }} />
                        </div>
                        <div className="col-lg-2">
                           <input type="button" className="btn btn-primary" value="Save" onClick={() => { this.handleSaveRadarType(this.props.rowData) }}/>
                        </div>
                    </div>
                    <div className={this.state.showDetails ? 'row border' : 'row hidden'}>
                        <div className="col-lg-6">
                            <div className="panel panel-default">
                                <div className="panel-heading">Rings</div>
                                <div className="panel-body">
                                    {this.props.rowData.radarRings.map(function (currentRow) {
                                        return <RadarTypeDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer }/>
                                        }.bind(this))}
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-6">
                            <div className="panel panel-default">
                                <div className="panel-heading">Categories</div>
                                <div className="panel-body">
                                    {this.props.rowData.radarCategories.map(function (currentRow) {
                                        return <RadarTypeDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer }/>
                                        }.bind(this))}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
        else{
            return( <div></div>);
        }
    }
};


class RadarTypeDetails extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.handleTypeDetailsNameChange = this.handleTypeDetailsNameChange.bind(this);
        this.handleDisplayOptionChange = this.handleDisplayOptionChange.bind(this);
    }

    handleTypeDetailsNameChange(event){
        this.props.rowData.name = event.target.value;
    }

    handleDisplayOptionChange(event){
        this.props.rowData.displayOption = event.target.value;
    }

    render(){
        if(this.props.rowData!==undefined){
            return(
                <div className="row">
                    <div className="col-lg-3">
                        <input type="text" ref="typeDetailsName" defaultValue={this.props.rowData.name} required="required"  onChange= {(event) => { this.handleTypeDetailsNameChange(event) }} />
                    </div>
                    <div className="col-lg-3">
                        <input type="text" ref="typeDetailsDisplayOption" defaultValue={this.props.rowData.displayOption} onChange= {(event) => { this.handleDisplayOptionChange(event) }} />
                    </div>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};

class NewRadarTypeRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: ''
        };

        this.handleAddRadarType = this.handleAddRadarType.bind(this);
    }

    handleAddRadarType() {
        this.props.parentContainer.props.radarTypeCollection.push(RadarTypeRepository_createDefaultRadarType(""));
        this.props.parentContainer.forceUpdate();
    }

    render(){
        return(
            <div className="row">
                <div className="col-lg-1`"><input type="button" className="btn btn-primary" value="Add Radar Type" onClick={this.handleAddRadarType} /></div>
            </div>
        );
    }
};


const mapMRTDispatchToProps = dispatch => {
  return {
        storeRadarTypeCollection : radarTypeCollection => { dispatch(addRadarTypeCollectionToState(radarTypeCollection))}
    }
};


function mapMRTStateToProps(state) {
  return {
    	radarTypeCollection: state.radarTypeCollection
    };
}

export default connect(
  mapMRTStateToProps,
    mapMRTDispatchToProps
)(ManageRadarTypes);