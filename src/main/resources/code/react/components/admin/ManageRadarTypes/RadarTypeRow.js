import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { RadarRingDetails } from './RadarRingDetails';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository_getByUserId, RadarTypeRepository_add, RadarTypeRepository_update, RadarTypeRepository_createDefaultRadarType, RadarTypeRepository_delete } from '../../../Repositories/RadarTypeRepository';

export class RadarTypeRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            showDetails: false,
            radarCategoriesColorMap: {  "Green": "#8FA227",
                                        "Blue": "#587486",
                                        "Maroon": "#B70062",
                                        "Orange": "#DC6F1D"},
            radarCategoriesColorNameMap: {}
        };

        var colorNames = Object.keys(this.state.radarCategoriesColorMap);

        for(var i = 0; i < colorNames.length; i++){
            this.state.radarCategoriesColorNameMap[this.state.radarCategoriesColorMap[colorNames[i]]] = colorNames[i];
        }

        this.handleRadarTypeSelection = this.handleRadarTypeSelection.bind(this);
        this.handleSaveRadarType = this.handleSaveRadarType.bind(this);
    }

    handleRadarTypeSelection(event){
        this.setState({ showDetails: !this.state.showDetails});
    }

    handleRadarTypeNameChangeEvent(event){
        this.props.rowData.name = event.target.value;
    }

    handleSaveRadarType(radarType){
        if(radarType.id > 0){
            RadarTypeRepository_update(this.props.userId, radarType);
        }
        else{
            RadarTypeRepository_add(this.props.userId, radarType);
        }
    }

    handleDeleteRadarType(radarType){
        RadarTypeRepository_delete(this.props.userId, radarType);
    }

    render() {
        if(typeof this.props.rowData !== 'undefined'){
            return (
                <div>
                    <div className={this.state.showDetails ? 'row border' : 'row'}>
                        <div className="col-lg-3">
                            <input type="text" defaultValue={this.props.rowData.name }  onChange= {(event) => { this.handleRadarTypeNameChangeEvent(event) }} />
                        </div>
                        <div className="col-lg-3">
                            <span className={this.state.showDetails ? 'hidden' : ''}>
                               <input type="button" className="btn btn-primary" value="Show Details" onClick={() => { this.handleRadarTypeSelection(event) }}/>
                            </span>
                            <span className={this.state.showDetails ? '' : 'hidden'}>
                               <input type="button" className="btn btn-primary" value="Hide Details" onClick={() => { this.handleRadarTypeSelection(event) }}/>
                               <input type="button" className="btn btn-primary" value="Save" onClick={() => { this.handleSaveRadarType(this.props.rowData) }}/>
                               <input type="button" className="btn btn-primary" value="Delete" onClick={() => { this.handleDeleteRadarType(this.props.rowData) }}/>
                           </span>
                        </div>
                    </div>
                    <div className={this.state.showDetails ? 'row border' : 'row hidden'}>
                        <div className="col-lg-6">
                            <div className="panel panel-default">
                                <div className="panel-heading">Rings</div>
                                <div className="panel-body">
                                    {this.props.rowData.radarRings.map(function (currentRow) {
                                        return <RadarRingDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer }/>
                                        }.bind(this))}
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-6">
                            <div className="panel panel-default">
                                <div className="panel-heading">Categories</div>
                                <div className="panel-body">
                                    {this.props.rowData.radarCategories.map(function (currentRow) {
                                        return <RadarCategoryDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer } colorMap={this.state.radarCategoriesColorMap} colorNameMap={this.state.radarCategoriesColorNameMap}/>
                                        }.bind(this))}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
        else{
            return( <div></div>);
        }
    }
};
