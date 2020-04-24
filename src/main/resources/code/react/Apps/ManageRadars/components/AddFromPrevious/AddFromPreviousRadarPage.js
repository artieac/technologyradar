'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarReducer from '../../redux/RadarReducer';
import { addRadarsToState, setSourceRadarInstanceToState, setCurrentRadarInstanceToState, handleAddRadarItem, handleRemoveRadarItem, clearAddRadarItems, clearRemoveRadarItems } from '../../redux/RadarReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import RadarsDropdown from './RadarsDropdown';
import RadarDetails from './RadarDetails';
import { UserRepository } from '../../../../Repositories/UserRepository';

class AddFromPreviousRadarPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            radarId: this.props.match.params.radarId,
            filteredRadarCollection: []
        };

        this.userRepository = new UserRepository();

        this.handleAddItemsToRadarClick = this.handleAddItemsToRadarClick.bind(this);
        this.handleRemoveItemsFromRadarClick = this.handleRemoveItemsFromRadarClick.bind(this);
        this.handleCurrentRadarInstanceSuccess = this.handleCurrentRadarInstanceSuccess.bind(this);
        this.getRadarCollectionByUserIdAndRadarTemplateId = this.getRadarCollectionByUserIdAndRadarTemplateId.bind(this);
        this.handleGetUserSuccess = this.handleGetUserSuccess.bind(this);
    }

    componentDidMount(){
        this.userRepository.getUser(this.handleGetUserSuccess);
        this.getCurrentRadarInstance(this.props.currentUser.id, this.state.radarId);
    }

    handleGetUserSuccess(currentUser){
        this.props.storeCurrentUser(currentUser);
    }

    getCurrentRadarInstance(userId, radarId){
        fetch( '/api/User/' + userId + '/Radar/' + radarId)
            .then(response => response.json())
            .then(json => this.handleCurrentRadarInstanceSuccess(json));
    }

    handleCurrentRadarInstanceSuccess(targetRadar){
        this.props.setCurrentRadarInstance({ currentRadar: targetRadar});
        this.getRadarCollectionByUserIdAndRadarTemplateId(this.props.currentUser.id, targetRadar.radarTemplate.id);
    }

    getRadarCollectionByUserIdAndRadarTemplateId(userId, radarTemplateId){
        fetch( '/api/User/' + userId + '/Radars?radarTemplateId=' + radarTemplateId,)
            .then(response => response.json())
            .then(json => this.setState({ filteredRadarCollection: json}));
    }

    handleAddItemsToRadarClick(){
        var itemsToAdd = {};
        itemsToAdd.radarItems = this.props.radarItemsToAdd;

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
            this.props.onClearAddRadarItems();
           }.bind(this)
        });
    }

    handleRemoveItemsFromRadarClick(userId, radarId){
        var itemsToRemove = {};
        itemsToRemove.radarItems = this.props.radarItemsToRemove;

        $.post({
          headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
          },
          type: "POST",
          url: '/api/User/' + userId + '/Radar/' + this.props.match.params.radarId + '/Items/Delete',
          data: JSON.stringify(itemsToRemove),
          success: function() {
            this.getCurrentRadarInstance(userId, this.props.match.params.radarId);
            this.props.onClearRemoveRadarItems();
            }.bind(this)
        });
    }

    getDestinationRadarName(){
        var retVal = "";

        if(this.props.currentRadar !== undefined && this.props.currentRadar.currentRadar !== undefined){
            retVal = this.props.currentRadar.currentRadar.radarName;
        }

        return retVal;
    }

    render() {
        const { setSourceRadarInstance } = this.props;

        return (
            <div>
                <div className="row">
                    <div className="col-lg-4">
                        <label>Source Radar Instance</label>
                        <RadarsDropdown data={this.state.filteredRadarCollection} itemSelection={this.props.sourceRadar.sourceRadar} userId={this.props.currentUser.id} setSourceRadarInstance={setSourceRadarInstance}/>
                        <button type="button" className="btn btn-techradar" onClick={ (event) => { this.handleAddItemsToRadarClick(event) }}>Add</button>
                    </div>
                    <div className="col-lg-4">
                        <div className="contentPageTitle">
                            <label>Add Past Radar Items to { this.getDestinationRadarName() } </label>
                            <button type="button" className="btn btn-techradar" onClick={ this.handleRemoveItemsFromRadarClick }>Remove</button>
                        </div>
                    </div>
                </div>
                <div className="row">
                    <RadarDetails radarInstance={ this.props.sourceRadar.sourceRadar } handleOnClick = { this.props.onHandleAddRadarItem }/>
                    <RadarDetails radarInstance={ this.props.currentRadar.currentRadar } handleOnClick = { this.props.onHandleRemoveRadarItem }/>
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
    	radarItemsToAdd: state.radarReducer.radarItemsToAdd,
    	radarItemsToRemove: state.radarReducer.radarItemsToRemove,
    	currentUser: state.userReducer.currentUser
    };
}

const mapDispatchToProps = dispatch => {
  return {
        setSourceRadarInstance : sourceRadar => { dispatch(setSourceRadarInstanceToState(sourceRadar))},
        addRadarCollection : radarCollection => { dispatch(addRadarsToState(radarCollection))},
        setCurrentRadarInstance : currentRadar => { dispatch(setCurrentRadarInstanceToState(currentRadar))},
        onHandleAddRadarItem : targetItem  => { dispatch(handleAddRadarItem(targetItem))},
        onHandleRemoveRadarItem : targetItem  => { dispatch(handleRemoveRadarItem(targetItem))},
        onClearAddRadarItems : () => { dispatch(clearAddRadarItems())},
        onClearRemoveRadarItems: () => { dispatch(clearRemoveRadarItems())},
        storeCurrentUser: (currentUser) => { dispatch(addCurrentUserToState(currentUser))}

    };
};

export default connect(mapStateToProps, mapDispatchToProps)(AddFromPreviousRadarPage);
