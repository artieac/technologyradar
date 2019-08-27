'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarReducer from '../../redux/RadarReducer';
import { addRadarsToState, addRadarTemplatesToState} from '../../redux/RadarReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import { RadarRepository} from '../../../../Repositories/RadarRepository';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository'
import RadarTableBody   from './RadarTableBody';
import { UserRepository } from '../../../../Repositories/UserRepository'

class ManageRadarsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarRepository = new RadarRepository();
        this.radarTemplateRepository = new RadarTemplateRepository();
        this.userRepository = new UserRepository();

        this.getUserDetails = this.getUserDetails.bind(this);
        this.handleGetUserSuccess = this.handleGetUserSuccess.bind(this);
    }

    componentDidMount(){
        this.getUserDetails();
    }

    getUserDetails(){
        this.userRepository.getUser(this.handleGetUserSuccess);
    }

    handleGetUserSuccess(currentUser){
        this.props.storeCurrentUser(currentUser);
        this.radarTemplateRepository.getOwnedAndAssociatedByUserId(currentUser.id, this.props.storeRadarTemplates) ;
        this.radarRepository.getByUserId(currentUser.id, true, this.props.storeRadars);
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
                    <RadarTableBody radars={this.props.radars} container={this}/>
                </table>
            </div>
        );
    }
};


const mapDispatchToProps = dispatch => {
  return {
        storeRadars : radars => { dispatch(addRadarsToState(radars))},
        storeRadarTemplates : radarTemplates => { dispatch(addRadarTemplatesToState(radarTemplates))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))}
    }
};


function mapStateToProps(state) {
  return {
    	radars: state.radarReducer.radars,
    	radarTemplates: state.radarReducer.radarTemplates,
    	currentUser: state.userReducer.currentUser
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageRadarsPage);