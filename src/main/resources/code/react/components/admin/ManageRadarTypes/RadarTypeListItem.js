import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarTypeReducer from  '../../../../redux/reducers/admin/RadarTypeReducer';
import { addSelectedRadarTypeToState, addAssociatedRadarTypesToState } from  '../../../../redux/reducers/admin/RadarTypeReducer';
import { RadarRingDetails } from './RadarRingDetails';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class RadarTypeListItem extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleShowDetailsClick = this.handleShowDetailsClick.bind(this);
        this.handleAssociateRadarTypeChange = this.handleAssociateRadarTypeChange.bind(this);
        this.handleAssociatedRadarTypeSuccess = this.handleAssociatedRadarTypeSuccess.bind(this);
        this.handleGetAssociatedRadarTypesSuccess = this.handleGetAssociatedRadarTypesSuccess.bind(this);
    }

    handleShowDetailsClick(){
        this.props.storeSelectedRadarType(this.props.radarType);
    }

    handleAssociateRadarTypeChange(event){
        var shouldAssociate = this.refs.shouldAssociate.checked;
        this.radarTypeRepository.associateRadarType(this.props.userId, this.props.radarType.id, shouldAssociate, this.handleAssociatedRadarTypeSuccess);
        this.forceUpdate();
    }

    handleAssociatedRadarTypeSuccess(){
        this.radarTypeRepository.getAssociatedRadarTypes(this.props.userId, this.handleGetAssociatedRadarTypesSuccess);
    }

    handleGetAssociatedRadarTypesSuccess(associatedRadarTypes){
        this.props.storeAssociatedRadarTypes(associatedRadarTypes);
        this.forceUpdate();
    }

    isAssociatedToUser(){
        var retVal = false;

        if(this.props.associateRadarTypes !== undefined){
            for(var i = 0; i < this.props.associateRadarTypes.length; i++){
                if(this.props.associateRadarTypes[i].id==this.props.radarType.id){
                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }

    render() {
        if(typeof this.props.radarType !== 'undefined'){
            return (
                <div className="row">
                    <div className="col-md-6">{this.props.radarType.name } </div>
                    <div className={this.props.userId == this.props.radarType.radarUserId ? 'hidden' : 'col-md-6'}>
                        <span>Use this?  <input type="checkbox" checked={this.isAssociatedToUser()} ref="shouldAssociate" onChange = {(event) => this.handleAssociateRadarTypeChange(event) }/></span>
                    </div>
                    <div className="col-md-6">
                       <input type="button" className="btn btn-primary" value="Details" onClick= { this.handleShowDetailsClick } />
                    </div>
                </div>
            );
        }
        else{
            return( <div></div>);
        }
    }
};


function mapStateToProps(state) {
  return {
        selectedRadarType : state.radarTypeReducer.selectedRadarType,
        associateRadarTypes: state.radarTypeReducer.associatedRadarTypes
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},
        storeAssociatedRadarTypes : radarType => { dispatch(addAssociatedRadarTypesToState(radarType))},
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeListItem);