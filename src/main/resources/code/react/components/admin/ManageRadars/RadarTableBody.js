'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import RadarRow from './RadarRow';
import NewRadarRow from './NewRadarRow';

export class RadarTableBody extends React.Component{
    render() {
        if(typeof this.props.tableBodyData !== 'undefined'){
            return (
                <tbody>
                    {this.props.tableBodyData.map(function (currentRow) {
                        return <RadarRow key={currentRow.id} rowData={currentRow} userId={this.props.userId} />
                        }.bind(this))}
                    <NewRadarRow userId={this.props.userId} radarTypes={this.props.radarTypes}/>
                </tbody>
            );
        }
        else{
            return(
                <tbody>
                    <NewRadarRow userId={this.props.userId}/>
                </tbody>
            );
        }
    }
};
