import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addAssociatedRadarTypesToState, addSharedRadarTypesToState, addSelectedRadarTypeToState } from '../../redux/RadarTypeReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import RadarTypeList from './RadarTypeList';
import RadarTypeDetails from './RadarTypeDetails';
import { RadarTypeRepository } from '../../../../Repositories/RadarTypeRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';

class ManageAssociatedRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTypeRepository = new RadarTypeRepository();
        this.userRepository = new UserRepository();

        this.handleGetOtherUsersSharedRadarTypesSuccess = this.handleGetOtherUsersSharedRadarTypesSuccess.bind(this);
        this.handleGetAssociatedRadarTypesSuccess = this.handleGetAssociatedRadarTypesSuccess.bind(this);
        this.handleGetCurrentUserSuccess = this.handleGetCurrentUserSuccess.bind(this);
    }

    componentDidMount(){
        var clearedRadarType = {};
        this.props.storeSelectedRadarType(clearedRadarType);

        this.userRepository.getUser(this.handleGetCurrentUserSuccess);
    }

    handleGetCurrentUserSuccess(currentUser){
       this.props.storeCurrentUser(currentUser);
       this.radarTypeRepository.getAssociatedRadarTypes(currentUser.id, this.handleGetAssociatedRadarTypesSuccess);
    }

    handleGetAssociatedRadarTypesSuccess(associatedRadarTypes){
        this.props.storeAssociatedRadarTypes(associatedRadarTypes);
        this.radarTypeRepository.getOtherUsersSharedRadarTypes(this.props.currentUser.id, this.handleGetOtherUsersSharedRadarTypesSuccess);
    }

    handleGetOtherUsersSharedRadarTypesSuccess(sharedRadarTypes){
        for(var i = 0; i < this.props.associatedRadarTypes.length; i++)
        {
            var associatedRadarType = this.props.associatedRadarTypes[i];
            var foundMatch = false;

            for(var j = 0; j < sharedRadarTypes.length; j++)
            {
                if(associatedRadarType.id == sharedRadarTypes[j].id)
               {
                    foundMatch = true;
               }
            }

            if(foundMatch == false)
            {
                sharedRadarTypes.push(associatedRadarType);
            }
        }

        this.props.storeSharedRadarTypes(sharedRadarTypes);

        if(sharedRadarTypes.length > 0){
            this.props.storeSelectedRadarType(sharedRadarTypes[0]);
        }

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
                                <RadarTypeList radarTypes={this.props.sharedRadarTypes} />
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <RadarTypeDetails parentContainer={this} editMode={false}/>
                    </div>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	sharedRadarTypes: state.radarTypeReducer.sharedRadarTypes,
    	associatedRadarTypes: state.radarTypeReducer.associatedRadarTypes,
    	currentUser: state.userReducer.currentUser
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeSharedRadarTypes : sharedRadarTypes => { dispatch(addSharedRadarTypesToState(sharedRadarTypes))},
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},
        storeAssociatedRadarTypes : associatedRadarTypes => { dispatch(addAssociatedRadarTypesToState(associatedRadarTypes))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageAssociatedRadarTypesPage);