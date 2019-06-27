'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarReducer from '../../../../redux/reducers/admin/RadarReducer';
import { addRadarsToState, addRadarTypesToState, addCurrentUserToState} from '../../../../redux/reducers/admin/RadarReducer';
import { RadarRepository} from '../../../Repositories/RadarRepository';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository'
import { RadarTableBody }  from './RadarTableBody';
import { UserRepository } from '../../../Repositories/UserRepository'

class ManageRadarsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };

        this.radarRepository = new RadarRepository();
        this.radarTypeRepository = new RadarTypeRepository();
        this.userRepository = new UserRepository();

        this.handleGetUserSuccess = this.handleGetUserSuccess.bind(this);
        this.getUserRadarsResponse = this.getUserRadarsResponse.bind(this);
        this.getRadarTypeCollectionResponse = this.getRadarTypeCollectionResponse.bind(this);
    }

    componentDidMount(){
        this.userRepository.getUser(this.handleGetUserSuccess);
    }

    handleGetUserSuccess(currentUser){
        this.props.storeCurrentUser(currentUser);
        this.radarRepository.getByUserId(currentUser.id, currentUser.canSeeHistory, this.getUserRadarsResponse);
        this.radarTypeRepository.getOwnedAndAssociatedByUserId(currentUser.id, currentUser.canSeeHistory, this.getRadarTypeCollectionResponse) ;
    }

    getUserRadarsResponse(radars){
        this.props.storeRadars(radars);
        this.forceUpdate();
    }

    getRadarTypeCollectionResponse(radarTypes){
        this.props.storeRadarTypes(radarTypes);
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Technology Assessments</label>
                </div>
                <p>Add an instance of your technology radar to track any changes since the last time you did this</p>
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Date</th>
                            <th width="20%">Type</th>
                            <th width="10%">Published?</th>
                            <th width="10%">Locked?</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <RadarTableBody tableBodyData={this.props.radars} userId={this.state.userId} parentContainer = { this}  radarTypes={this.props.radarTypes}/>
                </table>
            </div>
        );
    }
};


const mapDispatchToProps = dispatch => {
  return {
        storeRadars : radars => { dispatch(addRadarsToState(radars))},
        storeRadarTypes : radarTypes => { dispatch(addRadarTypesToState(radarTypes))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))}
    }
};


function mapStateToProps(state) {
  return {
    	radars: state.radarReducer.radars,
    	radarTypes: state.radarReducer.radarTypes,
    	currentUser: state.radarReducer.currentUser
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageRadarsPage);