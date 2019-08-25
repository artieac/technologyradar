'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import { connect } from "react-redux";
import createReactClass from 'create-react-class';
import { GenericDropdownItem } from './GenericDropdownItem';

export class GenericDropdown extends React.Component{
    constructor(props){
        super(props);
         this.state = {
         };

         this.setSelectedItem = this.setSelectedItem.bind(this);
    }

    setSelectedItem(selectedItem){
        this.props.selectionNotification(selectedItem);
    }

    render(){
        if(this.props.data!==undefined && this.props.data.length!==undefined && this.props.data.length > 0){
            return(
                <div className="dropdown">
                    <button className="btn btn-techradar dropdown-toggle" type="button" id="genericDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        { this.props.title }
                    </button>
                    <div className="dropdown-menu" aria-labelledby="genericDropdown">
                        <ul>
                            {this.props.data.map(function (currentRow, index) {
                                return <GenericDropdownItem key={ index } dropDownItem={ currentRow } setSelectedItem={this.props.selectionNotification } />
                            }.bind(this))}
                        </ul>
                    </div>
                </div>
            );
        }
        else{
            return(
                <div className="dropdown">
                  <button className="btn btn-techradar dropdown-toggle" type="button" id="genericDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    { this.props.title }
                  </button>
                </div>
            );
        }
    }
};
