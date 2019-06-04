import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';

export class NewRadarTypeRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: ''
        };

        this.handleAddRadarType = this.handleAddRadarType.bind(this);
    }

    handleAddRadarType() {
        this.props.parentContainer.props.radarTypeCollection.push(RadarTypeRepository_createDefaultRadarType(""));
        this.props.parentContainer.forceUpdate();
    }

    render(){
        return(
            <div className="row">
                <div className="col-lg-1`"><input type="button" className="btn btn-primary" value="Add Radar Type" onClick={this.handleAddRadarType} /></div>
            </div>
        );
    }
};