import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { addAssociatedRadarTypeCollectionToState, addSharedRadarTypeCollectionToState, addSelectedRadarTypeToState } from '../../../../redux/reducers/adminAppReducer';
import RadarTypeList from './RadarTypeList';
import RadarTypeEditor from './RadarTypeEditor';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class ManageAssociatedRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val(),
            selectedRadarType: {}
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleGetOtherUsersSharedRadarTypesSuccess = this.handleGetOtherUsersSharedRadarTypesSuccess.bind(this);
    }

    componentDidMount(){
        this.radarTypeRepository.getOtherUsersSharedRadarTypes(this.state.userId, this.handleGetOtherUsersSharedRadarTypesSuccess);
    }

    handleGetOtherUsersSharedRadarTypesSuccess(sharedRadarTypeCollection){
        this.props.storeSharedRadarTypeCollection(sharedRadarTypeCollection);

        if(sharedRadarTypeCollection.length > 0){
            this.props.storeSelectedRadarType(sharedRadarTypeCollection[0]);
        }
    }

    render() {
        if(this.props.sharedRadarTypeCollection !== undefined){
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
                                    <RadarTypeList userId={this.state.userId} radarTypeCollection={this.props.sharedRadarTypeCollection}/>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <RadarTypeEditor userId={this.state.userId} readonly={true} parentContainer={this} />
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

function mapStateToProps(state) {
  return {
    	sharedRadarTypeCollection: state.sharedRadarTypeCollection
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeSharedRadarTypeCollection : sharedRadarTypeCollection => { dispatch(addSharedRadarTypeCollectionToState(sharedRadarTypeCollection))},
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))}

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageAssociatedRadarTypesPage);