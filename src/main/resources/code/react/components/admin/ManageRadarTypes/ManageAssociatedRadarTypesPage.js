import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { addAssociatedRadarTypeCollectionToState } from '../../../../redux/reducers/adminAppReducer';
import { RadarTypeList } from './RadarTypeList';
import { RadarTypeEditor } from './RadarTypeEditor';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class ManageAssociatedRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val(),
            selectedRadarType: {}
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleGetAssociatedByUserIdSuccess = this.handleGetAssociatedByUserIdSuccess.bind(this);
    }

    componentDidMount(){
        this.radarTypeRepository.getAssociatedByUserId(this.state.userId, this.handleGetAssociatedByUserIdSuccess);
    }

    handleGetAssociatedByUserIdSuccess(associatedRadarTypeCollection){
        this.props.storeAssociatedRadarTypeCollection(associatedRadarTypeCollection);
    }

    render() {
        if(this.props.associatedRadarTypeCollection !== undefined){
            return (
                <div className="bodyContent">
                    <div className="contentPageTitle">
                        <label>Associate Radar Types From Others</label>
                    </div>
                    <p>Discover radar types that others have created</p>
                    <div className="row">
                        <div className="col-md-6">
                            <div className="row">
                                <div className="col-md-6">
                                    <RadarTypeList radarTypeCollection={this.props.radarTypeCollection} onSelectRadarType={this.handleRadarTypeSelection}/>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <RadarTypeEditor radarType={this.state.selectedRadarType} userId={this.state.userId} readonly={true}/>
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


const mapMARTDispatchToProps = dispatch => {
  return {
        storeAssociatedRadarTypeCollection : associatedRadarTypeCollection => { dispatch(addAssociatedRadarTypeCollectionToState(associatedRadarTypeCollection))}
    }
};


function mapMARTPStateToProps(state) {
  return {
    	associatedRadarTypeCollection: state.associatedRadarTypeCollection
    };
}

export default connect(
  mapMARTPStateToProps,
    mapMARTDispatchToProps
)(ManageAssociatedRadarTypesPage);