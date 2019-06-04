import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { Dropdown, DropdownButton } from 'react-bootstrap';

export class RadarRingDetails extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.handleTypeDetailsNameChange = this.handleTypeDetailsNameChange.bind(this);
        this.handleDisplayOptionChange = this.handleDisplayOptionChange.bind(this);
    }

    handleTypeDetailsNameChange(event){
        this.props.rowData.name = event.target.value;
    }

    handleDisplayOptionChange(event){
        this.props.rowData.displayOption = event.target.value;
    }

    render(){
        if(this.props.rowData!==undefined){
            return(
                <div className="row">
                    <div className="col-lg-3">
                        <input type="text" ref="typeDetailsName" defaultValue={this.props.rowData.name} required="required"  onChange= {(event) => { this.handleTypeDetailsNameChange(event) }} />
                    </div>
                    <div className="col-lg-3">
                        <input type="text" ref="typeDetailsDisplayOption" defaultValue={this.props.rowData.displayOption} onChange= {(event) => { this.handleDisplayOptionChange(event) }} />
                    </div>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};