import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { addRadarTypeCollectionToState } from '../../../../redux/reducers/adminAppReducer';
import { RadarTypeList } from './RadarTypeList';
import { RadarTypeEditor } from './RadarTypeEditor';
import { NewRadarTypeRow } from './NewRadarTypeRow';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class ManageOwnedRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val(),
            selectedRadarType: {}
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleRadarTypeSelection = this.handleRadarTypeSelection.bind(this);
        this.handleGetByUserIdSuccess = this.handleGetByUserIdSuccess.bind(this);
    }

    componentDidMount(){
       this.getByUserId();
       this.setState({selectedRadarType : this.radarTypeRepository.createDefaultRadarType('Radar Type Name')});
    }

    getByUserId(){
       this.radarTypeRepository.getByUserId(this.state.userId, this.handleGetByUserIdSuccess);
    }

    handleGetByUserIdSuccess(radarTypeCollection){
        this.props.storeRadarTypeCollection(radarTypeCollection);
        this.forceUpdate();
    }

    handleRadarTypeSelection(radarType){
        this.setState({selectedRadarType: radarType});
        this.forceUpdate();
    }

    render() {
        if(this.props.radarTypeCollection !== undefined && this.props.radarTypeCollection.length > 0){
            return (
                <div className="bodyContent">
                    <div className="contentPageTitle">
                        <label>Manage Your Radar Types</label>
                    </div>
                    <p>Add a new type to have rate different types of things</p>
                    <div className="row">
                        <div className="col-md-4">
                            <div className="row">
                                <div className="col-md-6">
                                   <NewRadarTypeRow userId={this.props.userId} onSelectRadarType={this.handleRadarTypeSelection}/>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                    <RadarTypeList radarTypeCollection={this.props.radarTypeCollection} onSelectRadarType={this.handleRadarTypeSelection}/>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-8">
                            <RadarTypeEditor radarType={this.state.selectedRadarType} userId={this.state.userId} parentContainer={this}/>
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


const mapMORTPDispatchToProps = dispatch => {
  return {
        storeRadarTypeCollection : radarTypeCollection => { dispatch(addRadarTypeCollectionToState(radarTypeCollection))}
    }
};


function mapMORTStateToProps(state) {
  return {
    	radarTypeCollection: state.radarTypeCollection
    };
}

export default connect(
  mapMORTStateToProps,
    mapMORTPDispatchToProps
)(ManageOwnedRadarTypesPage);