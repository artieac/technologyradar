'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { addRadarCollectionToState, addRadarTypeCollectionToState} from '../../../../redux/reducers/adminAppReducer';
import { RadarRepository_publishRadar, RadarRepository_lockRadar, RadarRepository_deleteRadar} from '../../../Repositories/RadarRepository';

export class RadarRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            isPublished: this.props.rowData.isPublished,
            isLocked: this.props.rowData.isLocked
        };

        this.handlePublishSuccess = this.handlePublishSuccess.bind(this);
        this.handlePublishError = this.handlePublishError.bind(this);
        this.handleIsPublishedClick = this.handleIsPublishedClick.bind(this);
        this.handleLockSuccess = this.handleLockSuccess.bind(this);
        this.handleLockError = this.handleLockError.bind(this);
        this.handleIsLockedClick = this.handleIsLockedClick.bind(this);
        this.handleDeleteSuccess = this.handleDeleteSuccess.bind(this);
        this.handleDeleteError = this.handleDeleteError.bind(this);
        this.handleDeleteClick = this.handleDeleteClick.bind(this);
    }

    handlePublishSuccess() { }

    handlePublishError() {
        this.setState( { isPublished: !this.state.isPublished })
    }

    handleIsPublishedClick() {
        this.setState( { isPublished: this.refs.isPublished.checked })
        RadarRepository_publishRadar(this.props.userId, this.props.rowData.id, this.refs.isPublished.checked, this.handlePublishSuccess.bind(this), this.handlePublishError.bind(this));
    }

    handleLockSuccess() {}

    handleLockError() {
        this.setState( { isLocked: !this.state.isLocked })
    }

    handleIsLockedClick(){
        this.setState( { isLocked: this.refs.isLocked.checked })
        RadarRepository_lockRadar(this.props.userId, this.props.rowData.id, this.refs.isLocked.checked, this.handleLockSuccess.bind(this), this.handleLockError.bind(this));
    }

    handleDeleteSuccess() {
        this.props.parentContainer.getRadarCollectionByUserId(this.props.userId,);
    }

    handleDeleteError() {

    }

    handleDeleteClick() {
        RadarRepository_deleteRadar(this.props.userId, this.props.rowData.id, this.handleDeleteSuccess, this.handleDeleteError);
    }

    getAddFromPreviousLink(userId, radarId){
        return '/admin/user/' + userId + '/radar/' + radarId + '/addfromprevious';
    }

    render() {
        return (
             <tr>
                 <td>{ this.props.rowData.name}</td>
                 <td>{ this.props.rowData.radarType.name}</td>
                 <td><input type="checkbox" ref="isPublished" defaultChecked={ this.state.isPublished } onClick = { this.handleIsPublishedClick }/></td>
                 <td><input type="checkbox" ref="isLocked" defaultChecked={ this.state.isLocked } onClick = { this.handleIsLockedClick }/></td>
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
