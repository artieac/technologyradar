'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import radarReducer from '../../../../redux/reducers/admin/RadarReducer';
import { addRadarsToState, addCurrentUserToState } from '../../../../redux/reducers/admin/RadarReducer';
import { RadarRepository} from '../../../Repositories/RadarRepository';
import { UserRepository } from '../../../Repositories/UserRepository';

class RadarRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            isPublished: this.props.rowData.isPublished,
            isLocked: this.props.rowData.isLocked
        };

        this.radarRepository = new RadarRepository();
        this.userRepository = new UserRepository();

        this.handleLockSuccess = this.handleLockSuccess.bind(this);
        this.handleLockError = this.handleLockError.bind(this);
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
    }

    handlePublishError() {
        this.setState( { isPublished: !this.state.isPublished })
    }

    handleIsPublishedClick() {
        var shouldProcess = true;

        if(this.refs.isPublished.checked == true)
        {
            if(this.props.currentUser!==undefined && (this.props.currentUser.howManyRadarsCanShare <= this.props.currentUser.numberOfSharedRadars))
            {
                if(!confirm('You can only have ' + this.props.currentUser.numberOfSharedRadars + '.  This will overwrite that selection.  Do you want to proceed?'))
                {
                    shouldProcess = false;
                    this.refs.isPublished.checked = !this.refs.isPublished.checked;
                }
            }
        }

        if(shouldProcess==true)
        {
            this.setState( { isPublished: this.refs.isPublished.checked })
            this.radarRepository.publishRadar(this.props.currentUser.id, this.props.rowData.id, this.refs.isPublished.checked,  this.handlePublishSuccess, this.handlePublishError);
        }
    }

    handleLockSuccess() {}

    handleLockError() {
        this.setState( { isLocked: !this.state.isLocked })
    }

    handleIsLockedClick(){
        this.setState( { isLocked: this.refs.isLocked.checked })
        this.radarRepository.lockRadar(this.props.currentUser.id, this.props.rowData.id, this.refs.isLocked.checked, this.handleLockSuccess, this.handleLockError);
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
        return '/admin/user/' + userId + '/radar/' + radarId + '/addfromprevious';
    }

    getAddItemsLink(radarId){
        return "/home/secureradar/" + radarId;
    }

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td>{ this.props.rowData.formattedAssessmentDate}</td>
                 <td>{ this.props.rowData.radarType.name} - v{this.props.rowData.radarType.version}</td>
                 <td><input type="checkbox" ref="isPublished" defaultChecked={ this.state.isPublished } onClick = {(event) => this.handleIsPublishedClick(event) }/></td>
                 <td><input type="checkbox" ref="isLocked" defaultChecked={ this.state.isLocked } onClick = { this.handleIsLockedClick }/></td>
                 <td><a href={ this.getAddItemsLink(this.props.rowData.id) } className="btn btn-primary" role="button" aria-disabled="true">Add Items</a></td>
                 <td>
                    <Link to={ this.getAddFromPreviousLink(this.props.userId, this.props.rowData.id)}>
                        <button type="button" className="btn btn-primary" disabled={(this.state.isPublished==true) || (this.state.isLocked==true)}>Add From Previous</button>
                    </Link>
                </td>
                 <td><button type="button" className="btn btn-primary" disabled={(this.state.isPublished==true) || (this.state.isLocked==true)} onClick = { this.handleDeleteClick }>Delete</button></td>
             </tr>
        );
    }
};


function mapStateToProps(state) {
  return {
        currentUser: state.radarReducer.currentUser,
        radars: state.radarReducer.radars
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeRadars : (userRadars) => { dispatch(addRadarsToState(userRadars))},
        storeCurrentUser: (refreshedUser) => { dispatch(addCurrentUserToState(refreshedUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRow);