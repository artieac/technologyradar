import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarTypeReducer from  '../../../../redux/reducers/admin/RadarTypeReducer';
import { addSelectedRadarTypeToState, addAssociatedRadarTypesToState, addRadarTypeHistoryToState, setShowEdit, setShowHistory } from  '../../../../redux/reducers/admin/RadarTypeReducer';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class RadarTypeListItem extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleShowEditClick = this.handleShowEditClick.bind(this);
        this.handleShowHistoryClick = this.handleShowHistoryClick.bind(this);
        this.handleGetHistorySuccess = this.handleGetHistorySuccess.bind(this);
        this.handleAssociateRadarTypeChange = this.handleAssociateRadarTypeChange.bind(this);
        this.handleAssociatedRadarTypeSuccess = this.handleAssociatedRadarTypeSuccess.bind(this);
        this.handleGetAssociatedRadarTypesSuccess = this.handleGetAssociatedRadarTypesSuccess.bind(this);
    }

    handleShowEditClick(event){
        this.props.storeSelectedRadarType(this.props.radarType);
        this.props.setShowEdit(true);
        this.forceUpdate();
    }

    handleShowHistoryClick(event){
        this.radarTypeRepository.getHistory(this.props.currentUser.id, this.props.radarType.id, this.handleGetHistorySuccess);
    }

    handleGetHistorySuccess(radarTypeHistory){
        this.props.storeRadarTypeHistory(radarTypeHistory);
        this.props.storeSelectedRadarType(radarTypeHistory[0]);
        this.props.setShowHistory(true);
        this.forceUpdate();
    }

    handleAssociateRadarTypeChange(event){
        var shouldAssociate = this.refs.shouldAssociate.checked;
        this.radarTypeRepository.associateRadarType(this.props.currentUser.id, this.props.radarType.id, this.props.radarType.version, shouldAssociate, this.handleAssociatedRadarTypeSuccess);
        this.forceUpdate();
    }

    handleAssociatedRadarTypeSuccess(){
       this.radarTypeRepository.getAssociatedRadarTypes(this.props.currentUser.id, this.handleGetAssociatedRadarTypesSuccess);
    }

    handleGetAssociatedRadarTypesSuccess(associatedRadarTypes){
        this.props.storeAssociatedRadarTypes(associatedRadarTypes);
    }

    isAssociatedToUser(){
        var retVal = false;

        if(this.props.associatedRadarTypes.length > 0){
            for(var i = 0; i < this.props.associatedRadarTypes.length; i++){
                if(this.props.associatedRadarTypes[i].id==this.props.radarType.id && this.props.associatedRadarTypes[i].version == this.props.radarType.version){
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
                <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                    <div className="col-md-7">{this.props.radarType.name } </div>
                    <div className={this.props.currentUser.id == this.props.radarType.radarUserId ? 'hidden' : 'col-md-6'}>
                        <span>Use this?  <input type="checkbox" checked={this.isAssociatedToUser()} ref="shouldAssociate" onChange = {(event) => this.handleAssociateRadarTypeChange(event) }/></span>
                    </div>
                    <div className="col-md-2">
                       <input type="button" className="btn btn-primary" value="View" onClick= {(event) => this.handleShowEditClick(event) } />
                    </div>
                    <div className={ this.props.currentUser.canSeeHistory==true ? "col-md-2" : "col-md-2 hidden"}>
                       <input type="button" className="btn btn-primary" value="History" onClick= {(event) => this.handleShowHistoryClick(event) } />
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
        associatedRadarTypes: state.radarTypeReducer.associatedRadarTypes,
        selectedRadarType : state.radarTypeReducer.selectedRadarType,
        currentUser : state.radarTypeReducer.currentUser,
        showHistory: state.radarTypeReducer.showHistory,
        showEdit: state.radarTypeReducer.showEdit

    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},
        storeAssociatedRadarTypes : radarType => { dispatch(addAssociatedRadarTypesToState(radarType))},
        setShowHistory: showHistory => { dispatch(setShowHistory(showHistory))},
        setShowEdit: showEdit => { dispatch(setShowEdit(showEdit))},
        storeRadarTypeHistory: radarTypeHistory => { dispatch(addRadarTypeHistoryToState(radarTypeHistory))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeListItem);