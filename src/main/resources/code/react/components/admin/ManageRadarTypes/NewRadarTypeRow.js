import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

export class NewRadarTypeRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: ''
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleAddRadarType = this.handleAddRadarType.bind(this);
    }

    handleAddRadarType() {
        this.props.onSelectRadarType(this.radarTypeRepository.createDefaultRadarType(""));
    }

    render(){
        return(
            <div className="row">
                <div className="col-lg-1`">
                    <input type="button" className="btn btn-primary" value="Add Radar Type" onClick= { this.handleAddRadarType } />
                </div>
            </div>
        );
    }
};