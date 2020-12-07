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
import { UserRepository } from '../../../../Repositories/UserRepository'
import TableComponent from '../../../../components/TableComponent'
import { radarColumnMap } from './radarColumnMap'
import DropdownComponent from '../../../../components/DropdownComponent'
import { radarTemplateMap } from './radarTemplateMap'

class ManageRadarsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            radarDropdownSelection: "Select Template",
            radarNameInput: '',
            selectedRadarTemplate: {}
        };

        this.radarRepository = new RadarRepository();
        this.radarTemplateRepository = new RadarTemplateRepository();
        this.userRepository = new UserRepository();

        this.getUserDetails = this.getUserDetails.bind(this);
        this.handleGetUserSuccess = this.handleGetUserSuccess.bind(this);

        this.handleIsPublishedClick = this.handleIsPublishedClick.bind(this);
        this.handlePublishSuccess = this.handlePublishSuccess.bind(this);
        this.handlePublishError = this.handlePublishError.bind(this);

        this.handleIsLockedClick = this.handleIsLockedClick.bind(this);
        this.handleLockResponse = this.handleLockResponse.bind(this);

        this.handleDeleteClick = this.handleDeleteClick.bind(this);
        this.handleDeleteSuccess = this.handleDeleteSuccess.bind(this);
        this.handleDeleteError = this.handleDeleteError.bind(this);

        this.handleRadarNameChange = this.handleRadarNameChange.bind(this);
        this.handleAddSuccess = this.handleAddSuccess.bind(this);
        this.handleAddError = this.handleAddError.bind(this);
        this.handleAddRadar = this.handleAddRadar.bind(this);

        this.handleDropdownSelection = this.handleDropdownSelection.bind(this);
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

    handlePublishSuccess(publishResponse) {
        this.props.storeCurrentUser(publishResponse.currentUser);
        this.props.storeRadars(publishResponse.radars);
        this.props.container.forceUpdate();
    }

    handlePublishError() {
    }

    handleIsPublishedClick(event, radarId) {
        const { currentUser } = this.props;

        var shouldProcess = true;

        if(event.target.checked == true){
            if(currentUser!==undefined && (currentUser.howManyRadarsCanShare <= currentUser.numberOfSharedRadars)){
                if(!confirm('You can only have ' + currentUser.numberOfSharedRadars + '.  This will overwrite that selection.  Do you want to proceed?')){
                    shouldProcess = false;
                    this.refs.isPublished.checked = !event.target.checked;
                }
            }
        }

        if(shouldProcess==true){
            this.radarRepository.publishRadar(currentUser.id, radarId, event.target.checked,  this.handlePublishSuccess, this.handlePublishError);
        }
    }

    handleLockResponse() {
        const { currentUser, storeRadars } = this.props;
        this.radarRepository.getByUserId(currentUser.id, true, storeRadars);
      }

    handleIsLockedClick(event, radarId){
        const { currentUser } = this.props;
        this.radarRepository.lockRadar(currentUser.id, radarId, event.target.checked, this.handleLockResponse, this.handleLockResponse);
    }

    handleDeleteSuccess(radars){
        const { storeRadars } = this.props;
        storeRadars(radars);
    }

    handleDeleteError() {

    }

    handleDeleteClick(radarId) {
        const { currentUser } = this.props;
        this.radarRepository.deleteRadar(currentUser.id, radarId, this.handleDeleteSuccess, this.handleDeleteError);
    }

    handleRadarNameChange(event){
        this.setState({radarNameInput:event.target.value});
    }

    handleAddSuccess(radars) {
        this.props.storeRadars(radars);
    }

    handleAddError() {

    }

    handleAddRadar() {
        const { currentUser } = this.props;
        const { radarNameInput, selectedRadarTemplate } = this.state;

        if(this.state.radarNameInput!=""){
            this.radarRepository.addRadar(currentUser.id, radarNameInput, selectedRadarTemplate, this.handleAddSuccess, this.handleAddError );
        }
        else{
            alert("You must enter a name for the radar.");
        }
    }

    handleDropdownSelection(radarTemplate){
        this.setState({selectedRadarTemplate: radarTemplate});
        this.setState({ radarDropdownSelection: radarTemplate.name});
    }

    render() {
        const { radars, currentUser, radarTemplates } = this.props;
        const { radarDropdownSelection } = this.state;

        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Technology Assessments</label>
                </div>
                <p>Add an instance of your technology radar to track any changes since the last time you did this</p>
                <div className="row">
                    <div className="col-md-4">
                        <input type="text" ref="radarName" required="required" onChange={ this.handleRadarNameChange } />
                    </div>
                    <div className="col-md-4">
                        <DropdownComponent title= { radarDropdownSelection }  itemMap= { radarTemplateMap(this.handleDropdownSelection) } data={radarTemplates}/>
                    </div>
                    <div className="col-md-4">
                        <input type="button" className="btn btn-techradar" value="Add Radar" onClick={this.handleAddRadar} />
                    </div>
                </div>
                <TableComponent data={ radars } cols={radarColumnMap(this.handleIsPublishedClick, this.handleIsLockedClick, this.handleDeleteClick, currentUser)} isDark hoverable striped bordered={false} />
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