'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { DropdownButton, Dropdown } from 'react-bootstrap';

export class RadarTypeDropdownItem extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.handleOnClick = this.handleOnClick.bind(this);
    }

    handleOnClick(){
        this.props.setSelectedItem(this.props.dropDownItem);
    }

    render(){
        if(this.props.dropDownItem!==undefined){
            return (
                <div>
                    <a className="dropdown-item" onClick={this.handleOnClick}>{ this.props.dropDownItem.name } - v{this.props.dropDownItem.version}</a>
                </div>
            );
        }
        else{
            return <Droodown.Item></Droodown.Item>
        }
    }
};
