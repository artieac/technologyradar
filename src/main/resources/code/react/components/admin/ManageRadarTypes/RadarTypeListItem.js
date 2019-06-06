import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { RadarRingDetails } from './RadarRingDetails';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository_getByUserId, RadarTypeRepository_add, RadarTypeRepository_update, RadarTypeRepository_delete } from '../../../Repositories/RadarTypeRepository';

export class RadarTypeListItem extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.handleShowDetailsClick = this.handleShowDetailsClick.bind(this);
    }

    handleShowDetailsClick(){
        this.props.onSelectRadarType(this.props.radarType);
    }

    render() {
        if(typeof this.props.radarType !== 'undefined'){
            return (
                <div className="row">
                    <div className="col-md-6">{this.props.radarType.name } </div>
                    <div className="col-md-6">
                       <input type="button" className="btn btn-primary" value="Details" onClick= { this.handleShowDetailsClick } />
                    </div>
                </div>
            );
        }
        else{
            return( <div></div>);
        }
    }
};
