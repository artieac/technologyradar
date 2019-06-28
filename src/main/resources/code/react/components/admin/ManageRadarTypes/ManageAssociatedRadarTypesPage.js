import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addAssociatedRadarTypesToState, addSharedRadarTypesToState, addSelectedRadarTypeToState } from '../../../../redux/reducers/admin/RadarTypeReducer';
import RadarTypeList from './RadarTypeList';
import RadarTypeDetails from './RadarTypeDetails';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class ManageAssociatedRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleGetOtherUsersSharedRadarTypesSuccess = this.handleGetOtherUsersSharedRadarTypesSuccess.bind(this);
        this.handleGetAssociatedRadarTypesSuccess = this.handleGetAssociatedRadarTypesSuccess.bind(this);
    }

    componentDidMount(){
        this.radarTypeRepository.getOtherUsersSharedRadarTypes(this.state.userId, this.handleGetOtherUsersSharedRadarTypesSuccess);
        this.radarTypeRepository.getAssociatedRadarTypes(this.state.userId, this.handleGetAssociatedRadarTypesSuccess);
    }

    handleGetOtherUsersSharedRadarTypesSuccess(sharedRadarTypes){
        this.props.storeSharedRadarTypes(sharedRadarTypes);

        if(sharedRadarTypes.length > 0){
            this.props.storeSelectedRadarType(sharedRadarTypes[0]);
        }
    }

    handleGetAssociatedRadarTypesSuccess(associatedRadarTypes){
        this.props.storeAssociatedRadarTypes(associatedRadarTypes);
        this.forceUpdate();
    }

    render() {
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
                                <RadarTypeList radarTypes={this.props.sharedRadarTypes}/>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <RadarTypeDetails parentContainer={this} />
                    </div>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	sharedRadarTypes: state.radarTypeReducer.sharedRadarTypes,
    	associatedRadarTypes: state.radarTypeReducer.associatedRadarTypes
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeSharedRadarTypes : sharedRadarTypes => { dispatch(addSharedRadarTypesToState(sharedRadarTypes))},
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},
        storeAssociatedRadarTypes : associatedRadarTypes => { dispatch(addAssociatedRadarTypesToState(associatedRadarTypes))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageAssociatedRadarTypesPage);