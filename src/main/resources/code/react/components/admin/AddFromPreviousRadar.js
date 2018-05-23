'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { setSourceRadarInstanceToState, setCurrentRadarInstanceToState, handleAddRadarItem, handleRemoveRadarItem } from '../../../redux/reducers/adminAppReducer';
import { SplitButton, MenuItem } from 'react-bootstrap';

class AddFromPreviousRadar extends React.Component{
    constructor(props){
        super(props);
         this.state = {};
    }

    componentDidMount(){
        this.getCurrentRadarInstance(this.props.match.params.userId, this.props.match.params.radarId);
    }

    getCurrentRadarInstance(userId, radarId){
        fetch( '/api/User/' + userId + '/Radar/' + radarId)
            .then(response => response.json())
            .then(json => this.props.setCurrentRadarInstance({ currentRadar: json}));
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
            }.bind(this)
        });
    }

    render() {
        const { setSourceRadarInstance } = this.props;

        return (
            <div>
                <div className="row">
                    <div className="col-lg-4">
                        <RadarCollectionDropDown data={this.props.radarCollection.radarCollection} itemSelection={this.props.sourceRadar} userId={this.props.match.params.userId} setSourceRadarInstance={setSourceRadarInstance}/>
                        <button type="button" className="btn btn-primary" onClick={ this.handleAddItemsToRadarClick.bind(this) }>Add</button>
                    </div>
                    <div className="col-lg-4">
                        <div className="contentPageTitle">
                            <label>Add Past Radar Items to { this.props.currentRadar.radarName }</label>
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
    getTitle(){
        var retVal = "";

        if(this.props.data.itemSelection !== undefined){
            retVal = this.props.data.itemSelection.name;
        }

        return retVal;
    }

    render(){
        const { setSourceRadarInstance } = this.props;

        if(this.props.data!==undefined){
            return(
                <SplitButton title={this.getTitle()} id="radarCollection">
                    {this.props.data.map(function (currentRow) {
                        return <RadarCollectionDropDownItem key={ currentRow.id } dropDownItem={ currentRow } userId={this.props.userId} setSourceRadarInstance={setSourceRadarInstance}/>
                    }.bind(this))}
                </SplitButton>
            );
        }
        else{
            return(
                <ul className="dropdown-menu" aria-labelledby="radarInstanceDropdown">
                </ul>
            );
        }
    }
}

class RadarCollectionDropDownItem extends React.Component{
    handleOnClick(){
        this.getSourceRadarInstance(this.props.userId, this.props.dropDownItem.id);
    }

    getSourceRadarInstance(userId, radarId){
        fetch( '/api/User/' + userId + '/Radar/' + radarId)
            .then(response => response.json())
            .then(json => this.props.setSourceRadarInstance({ sourceRadar: json}));
    }

    render(){
        return (<MenuItem eventKey={this.props.dropDownItem.id} onClick={this.handleOnClick.bind(this)}>{ this.props.dropDownItem.name }</MenuItem>);
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

const mapAFPRDispatchToProps = dispatch => {
  return {
        setSourceRadarInstance : sourceRadar => { dispatch(setSourceRadarInstanceToState(sourceRadar))},
        setCurrentRadarInstance : currentRadar => { dispatch(setCurrentRadarInstanceToState(currentRadar))},
        onHandleAddRadarItem : targetItem  => { dispatch(handleAddRadarItem(targetItem))},
        onHandleRemoveRadarItem : targetItem  => { dispatch(handleRemoveRadarItem(targetItem))},

    };
};

function mapStateToAFPRProps(state) {
  return {
    	radarCollection: state.radarCollection,
    	sourceRadar: state.sourceRadar,
    	currentRadar: state.currentRadar,
    	radarItemsToAdd: state.radarItemsToAdd,
    	radarItemsToRemove: state.radarItemsToRemove
    };
}

export default connect(
  mapStateToAFPRProps,
    mapAFPRDispatchToProps
)(AddFromPreviousRadar);
