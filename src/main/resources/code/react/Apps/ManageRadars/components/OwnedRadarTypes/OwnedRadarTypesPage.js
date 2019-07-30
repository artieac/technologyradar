import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTypesToState, addSelectedRadarTypeToState, addTargetUserToState } from '../../redux/RadarTypeReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import RadarTypeList from './RadarTypeList';
import RadarTypeDetails from './RadarTypeDetails';
import RadarTypeHistory from './RadarTypeHistory';
import NewRadarTypeRow from './NewRadarTypeRow';
import { RadarTypeRepository } from '../../../../Repositories/RadarTypeRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';

class OwnedRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTypeRepository = new RadarTypeRepository();
        this.userRepository = new UserRepository();

        this.handleGetCurrentUserSuccess = this.handleGetCurrentUserSuccess.bind(this);
    }

    componentDidMount(){
        var clearedRadarType = {};
        this.props.storeSelectedRadarType(clearedRadarType);

        this.userRepository.getUser(this.handleGetCurrentUserSuccess);
    }

    handleGetCurrentUserSuccess(currentUser){
       this.props.storeCurrentUser(currentUser);
       this.radarTypeRepository.getByUserId(currentUser.id, false, this.props.storeRadarTypes);
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
                               <NewRadarTypeRow />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
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
    	currentUser: state.userReducer.currentUser,
    	showHistory: state.radarTypeReducer.showHistory,
    	showEdit: state.radarTypeReducer.showEdit
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},
        storeRadarTypes : radarTypes => { dispatch(addRadarTypesToState(radarTypes))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))},
        storeTargetUser : targetUser => { dispatch(addTargetUserToState(targetUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(OwnedRadarTypesPage);