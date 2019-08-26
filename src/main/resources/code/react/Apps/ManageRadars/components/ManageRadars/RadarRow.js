'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import radarReducer from '../../redux/RadarReducer';
import { addRadarsToState } from '../../redux/RadarReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import { RadarRepository} from '../../../../Repositories/RadarRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';

class RadarRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarRepository = new RadarRepository();
        this.userRepository = new UserRepository();

        this.handleLockResponse = this.handleLockResponse.bind(this);
        this.handleIsLockedClick = this.handleIsLockedClick.bind(this);
        this.handleDeleteSuccess = this.handleDeleteSuccess.bind(this);
        this.handleDeleteError = this.handleDeleteError.bind(this);
        this.handleDeleteClick = this.handleDeleteClick.bind(this);
        this.handleIsPublishedClick = this.handleIsPublishedClick.bind(this);
        this.handlePublishSuccess = this.handlePublishSuccess.bind(this);
        this.handlePublishError = this.handlePublishError.bind(this);
    }

    componentDidUpdate(){
    }

    handlePublishSuccess(publishResponse) {
        this.props.storeCurrentUser(publishResponse.currentUser);
        this.props.storeRadars(publishResponse.radars);
        this.props.container.forceUpdate();
    }

    handlePublishError() {
    }

    handleIsPublishedClick(event) {
        var shouldProcess = true;

        if(event.target.checked == true)
        {
            if(this.props.currentUser!==undefined && (this.props.currentUser.howManyRadarsCanShare <= this.props.currentUser.numberOfSharedRadars))
            {
                if(!confirm('You can only have ' + this.props.currentUser.numberOfSharedRadars + '.  This will overwrite that selection.  Do you want to proceed?'))
                {
                    shouldProcess = false;
                    this.refs.isPublished.checked = !event.target.checked;
                }
            }
        }

        if(shouldProcess==true)
        {
            this.radarRepository.publishRadar(this.props.currentUser.id, this.props.rowData.id, event.target.checked,  this.handlePublishSuccess, this.handlePublishError);
        }
    }

    handleLockResponse() {
        this.radarRepository.getByUserId(this.props.currentUser.id, this.props.storeRadars);
    }

    handleIsLockedClick(event){
        this.radarRepository.lockRadar(this.props.currentUser.id, this.props.rowData.id, this.refs.isLocked.checked, this.handleLockResponse, this.handleLockResponse);
    }

    handleDeleteSuccess(radars)
    {
        this.props.storeRadars(radars);
    }

    handleDeleteError() {

    }

    handleDeleteClick() {
        this.radarRepository.deleteRadar(this.props.currentUser.id, this.props.rowData.id, this.handleDeleteSuccess, this.handleDeleteError);
    }

    getAddFromPreviousLink(userId, radarId){
        return '/manageradars/user/' + userId + '/radar/' + radarId + '/addfromprevious';
    }

    getAddItemsLink(radarId){
        return "/home/secureradar/" + radarId;
    }

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td>{ this.props.rowData.formattedAssessmentDate}</td>
                 <td>{ this.props.rowData.radarType.name}</td>
                 <td><input type="checkbox" ref="isPublished" checked={ this.props.rowData.isPublished } onChange = {(event) => this.handleIsPublishedClick(event) }/></td>
                 <td><input type="checkbox" ref="isLocked" checked={ this.props.rowData.isLocked } onClick = {(event) => this.handleIsLockedClick(event) }/></td>
                 <td><a href={ this.getAddItemsLink(this.props.rowData.id) } className="btn btn-techradar" role="button" aria-disabled="true">Add Items</a></td>
                 <td>
                    <Link to={ this.getAddFromPreviousLink(this.props.currentUser.id, this.props.rowData.id)}>
                        <button type="button" className="btn btn-techradar" disabled={(this.props.rowData.isPublished==true) || (this.props.rowData.isLocked==true)}>Add From Previous</button>
                    </Link>
                </td>
                 <td><button type="button" className="btn btn-techradar" disabled={(this.props.rowData.isPublished==true) || (this.props.rowData.isLocked==true)} onClick = { this.handleDeleteClick }>Delete</button></td>
             </tr>
        );
    }
};


function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeRadars : (userRadars) => { dispatch(addRadarsToState(userRadars))},
        storeCurrentUser: (refreshedUser) => { dispatch(addCurrentUserToState(refreshedUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRow);