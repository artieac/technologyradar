'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { DropdownButton, Dropdown} from 'react-bootstrap';

export class RadarCollectionDropDownItem extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };
    }

    handleOnClick(){
        this.props.setSelectedItem(this.props.dropDownItem);
    }

    render(){
        if(this.props.dropDownItem!==undefined){
            return (
                <Dropdown.Item eventKey={this.props.dropDownItem.id} onClick={this.handleOnClick.bind(this)}>{ this.props.dropDownItem.name }</Dropdown.Item>
            );
        }
        else{
            return <Dropdown.Item></Dropdown.Item>
        }
    }
};
