'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarReducer from '../../../redux/reducers/admin/RadarReducer';
import { addRadarsToState, setSourceRadarInstanceToState, setCurrentRadarInstanceToState, handleAddRadarItem, handleRemoveRadarItem, clearAddRadarItems, clearRemoveRadarItems } from '../../../redux/reducers/admin/RadarReducer';
import { DropdownButton, Dropdown} from 'react-bootstrap';

class AddFromPreviousRadar extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: this.props.match.params.userId,
            radarId: this.props.match.params.radarId,
            filteredRadarCollection: []
        };

        this.handleCurrentRadarInstanceSuccess = this.handleCurrentRadarInstanceSuccess.bind(this);
        this.getRadarCollectionByUserIdAndRadarTypeId = this.getRadarCollectionByUserIdAndRadarTypeId.bind(this);
    }

    componentDidMount(){
        this.getCurrentRadarInstance(this.state.userId, this.state.radarId);
    }

    getCurrentRadarInstance(userId, radarId){
        fetch( '/api/User/' + userId + '/Radar/' + radarId)
            .then(response => response.json())
            .then(json => this.handleCurrentRadarInstanceSuccess(json));
    }

    handleCurrentRadarInstanceSuccess(targetRadar){
        this.props.setCurrentRadarInstance({ currentRadar: targetRadar});
        this.getRadarCollectionByUserIdAndRadarTypeId(this.state.userId, targetRadar.radarType.id);
    }

    getRadarCollectionByUserIdAndRadarTypeId(userId, radarTypeId){
        fetch( '/api/User/' + userId + '/Radars?radarTypeId=' + radarTypeId,)
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
          url: '/api/User/' + this.props.match.params.userId + '/Radar/' + this.props.match.params.radarId + '/Items',
          data: JSON.stringify(itemsToAdd),
          success: function() {
            this.getCurrentRadarInstance(this.props.match.params.userId, this.props.match.params.radarId);
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
          url: '/api/User/' + this.props.match.params.userId + '/Radar/' + this.props.match.params.radarId + '/Items/Delete',
          data: JSON.stringify(itemsToRemove),
          success: function() {
            this.getCurrentRadarInstance(this.props.match.params.userId, this.props.match.params.radarId);
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
                        <RadarCollectionDropDown data={this.state.filteredRadarCollection} itemSelection={this.props.sourceRadar.sourceRadar} userId={this.props.match.params.userId} setSourceRadarInstance={setSourceRadarInstance}/>
                        <button type="button" className="btn btn-primary" onClick={ this.handleAddItemsToRadarClick.bind(this) }>Add</button>
                    </div>
                    <div className="col-lg-4">
                        <div className="contentPageTitle">
                            <label>Add Past Radar Items to { this.getDestinationRadarName() } </label>
                            <button type="button" className="btn btn-primary" onClick={ this.handleRemoveItemsFromRadarClick.bind(this) }>Remove</button>
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

class RadarCollectionDropDown extends React.Component{
    constructor(props){
        super(props);
         this.state = {
         };
    }

    getTitle(){
        var retVal = "Select Radar";

        if(this.props.itemSelection !== undefined){
            retVal = this.props.itemSelection.radarName;
        }

        return retVal;
    }

    render(){
        const { setSourceRadarInstance } = this.props;

        if(this.props.data!==undefined){
            return(
                <DropdownButton title={this.getTitle()} id="radarCollection">
                    {this.props.data.map(function (currentRow) {
                        return <RadarCollectionDropDownItem key={ currentRow.id } dropDownItem={ currentRow } userId={this.props.userId} setSourceRadarInstance={setSourceRadarInstance}/>
                    }.bind(this))}
                </DropdownButton>
            );
        }
        else{
            return(
                <SplitButton title="Select" id="radarCollection">
                </SplitButton>
            );
        }
    }
}

class RadarCollectionDropDownItem extends React.Component{
    handleOnClick(){
        this.getSourceRadarInstance(this.props.userId, this.props.dropDownItem.id);
    }

    getSourceRadarInstance(userId, radarId, radarTypeId){
        fetch( '/api/User/' + userId + '/Radar/' + radarId)
            .then(response => response.json())
            .then(json => this.props.setSourceRadarInstance({ sourceRadar: json}));
    }

    render(){
        return (<Dropdown.Item eventKey={this.props.dropDownItem.id} onClick={this.handleOnClick.bind(this)}>{ this.props.dropDownItem.name }</Dropdown.Item>);
    }
}

class RadarDetails extends React.Component{
    render(){
        if(this.props.radarInstance !== undefined && this.props.radarInstance.quadrants !== undefined){
            return(
                <div className="col-lg-4">
                    {this.props.radarInstance.quadrants.map(function (currentRow) {
                        return <RadarQuadrant key={currentRow.quadrant} quadrant = { currentRow } handleOnClick = { this.props.handleOnClick }/>
                    }.bind(this))}
                </div>
            );
        }
        else{
            return(<div className="col-lg-4"></div>);
        }
    }
}

class RadarQuadrant extends React.Component{
    render(){
        return(
            <div className="row">
                <div className="col-lg-12">
                    <div className="row">
                        <div className="col-lg-12">
                            <h3>{ this.props.quadrant.quadrant }</h3>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-lg-12">
                            <table className="table table-striped">
                                <tbody>
                                    {this.props.quadrant.items.map(function (currentRow) {
                                        return <RadarQuadrantItem key={currentRow.assessmentItem.id} quadrantItem = { currentRow } handleOnClick = { this.props.handleOnClick }/>
                                    }.bind(this))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

class RadarQuadrantItem extends React.Component{
    handleCheckboxClick(event){
        this.props.handleOnClick(this.props.quadrantItem.assessmentItem);
    }

    render(){
        return(
            <tr>
                <td><input type="checkbox" name="addToRadar" onChange= {(event) => { this.handleCheckboxClick(event) }}/></td>
                <td>{ this.props.quadrantItem.assessmentItem.radarRing.name }</td>
                <td><a href="">{ this.props.quadrantItem.name}</a></td>
            </tr>
        );
    }
}

function mapStateToProps(state) {
  return {
    	sourceRadar: state.radarReducer.sourceRadar,
    	radarCollection: state.radarReducer.radars,
    	currentRadar: state.radarReducer.currentRadar,
    	radarItemsToAdd: state.radarReducer.radarItemsToAdd,
    	radarItemsToRemove: state.radarReducer.radarItemsToRemove
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
        onClearRemoveRadarItems: () => { dispatch(clearRemoveRadarItems())}

    };
};

export default connect(mapStateToProps, mapDispatchToProps)(AddFromPreviousRadar);
