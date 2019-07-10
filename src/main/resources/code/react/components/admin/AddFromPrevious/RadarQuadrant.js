'use strict'
/// <reference path="../ChartSummaryTable/ChartSummaryRow.js" />
import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import RadarQuadrantItem from './RadarQuadrantItem';

export default class RadarQuadrant extends React.Component{
    render(){
        return(
            <div className="row">
                <div className="col-lg-12">
                    <div className="row">
                        <div className="col-lg-12">
                            <h3>{ this.props.quadrant.quadrant }</h3>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-lg-12">
                            <table className="table table-striped">
                                <tbody>
                                    {this.props.quadrant.items.map(function (currentRow) {
                                        return <RadarQuadrantItem key={currentRow.assessmentItem.id} quadrantItem = { currentRow } handleOnClick = { this.props.handleOnClick }/>
                                    }.bind(this))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}