'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarReducer from '../../redux/RadarReducer';
import { addRadarsToState, setSourceRadarInstanceToState, setCurrentRadarInstanceToState } from '../../redux/RadarReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import { UserRepository } from '../../../../Repositories/UserRepository';
import { RadarRepository } from '../../../../Repositories/RadarRepository';
import TableComponent from '../../../../components/TableComponent'
import DropdownComponent from '../../../../components/DropdownComponent'
import { radarDropdownMap } from './radarDropdownMap';
import RadarCopyControl from './RadarCopyControl';

class AddFromPreviousRadarPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            radarId: this.props.match.params.radarId,
            filteredRadarCollection: [],
            radarItemsToAdd: [],
            radarItemsToRemove: []
        };

        this.userRepository = new UserRepository();
        this.radarRepository = new RadarRepository();

        this.handleAddItemsToRadarClick = this.handleAddItemsToRadarClick.bind(this);
        this.handleRemoveItemsFromRadarClick = this.handleRemoveItemsFromRadarClick.bind(this);
        this.handleCurrentRadarInstanceSuccess = this.handleCurrentRadarInstanceSuccess.bind(this);
        this.getRadarCollectionByUserIdAndRadarTemplateId = this.getRadarCollectionByUserIdAndRadarTemplateId.bind(this);
        this.handleGetUserSuccess = this.handleGetUserSuccess.bind(this);

        this.handleSourceRadarSelection = this.handleSourceRadarSelection.bind(this);
        this.handleSourceRadarSuccess = this.handleSourceRadarSuccess.bind(this);

        this.createRadarItemForExistingTechnology = this.createRadarItemForExistingTechnology.bind(this);
        this.onHandleAddRadarItem = this.onHandleAddRadarItem.bind(this);
        this.onHandleRemoveRadarItem = this.onHandleRemoveRadarItem.bind(this);
    }

    componentDidMount(){
        this.userRepository.getUser(this.handleGetUserSuccess);
        this.getCurrentRadarInstance(this.props.currentUser.id, this.state.radarId);
    }

    handleGetUserSuccess(currentUser){
        this.props.storeCurrentUser(currentUser);
    }

    getCurrentRadarInstance(userId, radarId){
        this.radarRepository.getByUserIdAndRadarId(userId, radarId, this.handleCurrentRadarInstanceSuccess);
    }

    handleCurrentRadarInstanceSuccess(targetRadar){
        this.props.setCurrentRadarInstance({ currentRadar: targetRadar});
        this.getRadarCollectionByUserIdAndRadarTemplateId(this.props.currentUser.id, targetRadar.radarTemplate.id);
    }

    handleSourceRadarSelection(targetRadar){
        const { currentUser } = this.props;

        this.radarRepository.getByUserIdAndRadarId(currentUser.id, targetRadar.id, this.handleSourceRadarSuccess);
    }

    handleSourceRadarSuccess(targetRadar){
        this.props.setSourceRadarInstance(targetRadar);
        this.forceUpdate();
    }

    getRadarCollectionByUserIdAndRadarTemplateId(userId, radarTemplateId){
        fetch( '/api/User/' + userId + '/Radars?radarTemplateId=' + radarTemplateId,)
            .then(response => response.json())
            .then(json => this.setState({ filteredRadarCollection: json}));
    }

    handleAddItemsToRadarClick(){
        var itemsToAdd = {};
        itemsToAdd.radarItems = this.state.radarItemsToAdd;

        $.post({
          headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
          },
          type: "POST",
          url: '/api/User/' + this.props.currentUser.id + '/Radar/' + this.props.match.params.radarId + '/Items',
          data: JSON.stringify(itemsToAdd),
          success: function() {
            this.getCurrentRadarInstance(this.props.currentUser.id, this.props.match.params.radarId);
            this.setState({radarItemsToAdd: []});
           }.bind(this)
        });
    }

    handleRemoveItemsFromRadarClick(userId, radarId){
        var itemsToRemove = {};
        itemsToRemove.radarItems = this.state.radarItemsToRemove;

        $.post({
          headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
          },
          type: "POST",
          url: '/api/User/' + this.props.currentUser.id + '/Radar/' + this.props.match.params.radarId + '/Items/Delete',
          data: JSON.stringify(itemsToRemove),
          success: function() {
            this.getCurrentRadarInstance(this.props.currentUser.id, this.props.match.params.radarId);
            this.setState({radarItemsToRemove: []});
            }.bind(this)
        });
    }

    getDestinationRadarName(){
        var retVal = "";

        if(this.props.currentRadar !== undefined && this.props.currentRadar.currentRadar !== undefined){
            return this.getRadarNameAndDate(this.props.currentRadar.currentRadar);
        }

        return retVal;
    }

    getRadarNameAndDate(radar){
        if(radar!=undefined && radar.radarName !== undefined){
            var parsedDate = new Date(radar.assessmentDate);
            return radar.radarName + " - " + (parsedDate.getMonth() + 1) + "-" + parsedDate.getUTCFullYear();
        }

        return "Select a source";
    }

    createRadarItemForExistingTechnology(assessmentItem){
        var radarItem = {};

        radarItem.radarCategory = assessmentItem.radarCategory.id;
        radarItem.radarRing = assessmentItem.radarRing.id;
        radarItem.confidenceLevel = assessmentItem.confidenceFactor;
        radarItem.assessmentDetails = assessmentItem.details;
        radarItem.technologyId = assessmentItem.technology.id;

        return radarItem;
    }

    onHandleAddRadarItem(event, assessmentItem){
        var radarItemsToAdd = this.state.radarItemsToAdd.filter(() => true);

        if(event.target.checked==true){
            radarItemsToAdd = radarItemsToAdd.concat(this.createRadarItemForExistingTechnology(assessmentItem));
            this.setState({ radarItemsToAdd: radarItemsToAdd});
        } else {
            radarItemsToAdd =
                radarItemsToAdd.filter(function( radarItem ) {
                    return radarItem.technologyId !== assessmentItem.technology.id;
                });
        }

        this.setState({ radarItemsToAdd: radarItemsToAdd});
    }

    onHandleRemoveRadarItem(event, assessmentItem){
        var radarItemsToRemove = this.state.radarItemsToRemove.filter(() => true);

        if(event.target.checked==true){
            radarItemsToRemove = radarItemsToRemove.concat(assessmentItem.id);
        } else {
            radarItemsToRemove =
                radarItemsToRemove.filter(function( radarItem ) {
                    return radarItem !== assessmentItem.id;
                });
        }

        this.setState({ radarItemsToRemove: radarItemsToRemove});
    }

    clearCopyItems(){

    }

    render() {
        const { sourceRadar, currentRadar } = this.props;
        const { filteredRadarCollection } = this.state;

        return (
            <div>
                <div className="row">
                    <div className="col-lg-6">
                        <label>Source Radar Instance</label>
                        <DropdownComponent title = { this.getRadarNameAndDate(sourceRadar) } data={ filteredRadarCollection } itemMap = { radarDropdownMap(this.handleSourceRadarSelection) } />
                        <button type="button" className="btn btn-techradar" onClick={ (event) => { this.handleAddItemsToRadarClick(event) }}>Add</button>
                    </div>
                    <div className="col-lg-6">
                        <div className="contentPageTitle">
                            <label>Add Past Radar Items to { this.getDestinationRadarName() } </label>
                            <button type="button" className="btn btn-techradar" onClick={ this.handleRemoveItemsFromRadarClick }>Remove</button>
                        </div>
                    </div>
                </div>
                <div className="row">
                    <RadarCopyControl
                        sourceRadar = { sourceRadar }
                        destinationRadar = { currentRadar.currentRadar }
                        handleAddRadarItem={this.onHandleAddRadarItem}
                        handleRemoveRadarItem={this.onHandleRemoveRadarItem}/>
                 </div>
            </div>

        );
    }
}

function mapStateToProps(state) {
  return {
    	sourceRadar: state.radarReducer.sourceRadar,
    	radarCollection: state.radarReducer.radars,
    	currentRadar: state.radarReducer.currentRadar,
    	currentUser: state.userReducer.currentUser
    };
}

const mapDispatchToProps = dispatch => {
  return {
        setSourceRadarInstance : sourceRadar => { dispatch(setSourceRadarInstanceToState(sourceRadar))},
        addRadarCollection : radarCollection => { dispatch(addRadarsToState(radarCollection))},
        setCurrentRadarInstance : currentRadar => { dispatch(setCurrentRadarInstanceToState(currentRadar))},
        storeCurrentUser: (currentUser) => { dispatch(addCurrentUserToState(currentUser))}

    };
};

export default connect(mapStateToProps, mapDispatchToProps)(AddFromPreviousRadarPage);
