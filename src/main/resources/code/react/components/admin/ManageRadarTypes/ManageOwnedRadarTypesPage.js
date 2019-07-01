import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTypesToState, addCurrentUserToState } from '../../../../redux/reducers/admin/RadarTypeReducer';
import RadarTypeList from './RadarTypeList';
import RadarTypeDetails from './RadarTypeDetails';
import RadarTypeHistory from './RadarTypeHistory';
import NewRadarTypeRow from './NewRadarTypeRow';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';
import { UserRepository } from '../../../Repositories/UserRepository';
import ErrorSection from '../Errors/ErrorSection';
import WarningManager from '../Errors/WarningManager';
import { addWarningsToState } from '../../../../redux/reducers/admin/ErrorReducer';

class ManageOwnedRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val(),
        };

        this.radarTypeRepository = new RadarTypeRepository();
        this.userRepository = new UserRepository();

        this.handleGetCurrentUserSuccess = this.handleGetCurrentUserSuccess.bind(this);
        this.handleGetByUserIdSuccess = this.handleGetByUserIdSuccess.bind(this);
    }

    componentDidMount(){
        this.userRepository.getUser(this.handleGetCurrentUserSuccess);
    }

    handleGetCurrentUserSuccess(currentUser){
       this.props.storeCurrentUser(currentUser);
       this.radarTypeRepository.getByUserId(currentUser.id, false, this.handleGetByUserIdSuccess);
    }

    handleGetByUserIdSuccess(radarTypes){
        this.props.storeRadarTypes(radarTypes);
        this.forceUpdate();
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Your Radar Types</label>
                </div>
                <p>Add a new type to have rate different types of things</p>
                <ErrorSection errors={this.props.errors} warnings={this.props.warnings}/>
                <div className="row">
                    <div className="col-md-4">
                        <div className="row">
                            <div className="col-md-6">
                               <NewRadarTypeRow />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6">
                                <RadarTypeList radarTypes={this.props.radarTypes}/>
                            </div>
                        </div>
                    </div>
                    <div className={ this.props.showEdit==true ? "col-md-8" : "hidden"}>
                        <RadarTypeDetails  parentContainer={this} editMode={true}/>
                    </div>
                    <div className={ this.props.currentUser.canSeeHistory==true && this.props.showHistory==true ? "col-md-8" : "hidden"}>
                        <RadarTypeHistory parentContainer={this}/>
                    </div>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	radarTypes: state.radarTypeReducer.radarTypes,
    	currentUser: state.radarTypeReducer.currentUser,
    	showHistory: state.radarTypeReducer.showHistory,
    	showEdit: state.radarTypeReducer.showEdit,
    	warnings: state.errorReducer.warnings,
    	errors: state.errorReducer.errors
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeRadarTypes : radarTypes => { dispatch(addRadarTypesToState(radarTypes))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageOwnedRadarTypesPage);