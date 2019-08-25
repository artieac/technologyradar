'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';

export class GenericDropdownItem extends React.Component{
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
                <li>
                    <a className="dropdown-item" onClick={this.handleOnClick} title={ this.props.dropDownItem.description }>{ this.props.dropDownItem.name }</a>
                </li>
            );
        }
        else{
            return <li></li>
        }
    }
};
