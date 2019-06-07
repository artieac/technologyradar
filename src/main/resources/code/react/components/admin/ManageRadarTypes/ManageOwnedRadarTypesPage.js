import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { addRadarTypeCollectionToState } from '../../../../redux/reducers/adminAppReducer';
import RadarTypeList from './RadarTypeList';
import RadarTypeEditor from './RadarTypeEditor';
import NewRadarTypeRow from './NewRadarTypeRow';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class ManageOwnedRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleGetByUserIdSuccess = this.handleGetByUserIdSuccess.bind(this);
    }

    componentDidMount(){
       this.getByUserId();
    }

    getByUserId(){
       this.radarTypeRepository.getByUserId(this.state.userId, this.handleGetByUserIdSuccess);
    }

    handleGetByUserIdSuccess(radarTypeCollection){
        this.props.storeRadarTypeCollection(radarTypeCollection);
        this.forceUpdate();
    }

    render() {
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
                                <RadarTypeList userId={this.state.userId} radarTypeCollection={this.props.radarTypeCollection}/>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-8">
                        <RadarTypeEditor userId={this.state.userId} parentContainer={this}/>
                    </div>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	radarTypeCollection: state.radarTypeCollection
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeRadarTypeCollection : radarTypeCollection => { dispatch(addRadarTypeCollectionToState(radarTypeCollection))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageOwnedRadarTypesPage);