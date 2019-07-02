'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import { connect } from "react-redux";
import createReactClass from 'create-react-class';
import RadarRow from './RadarRow';
import NewRadarRow from './NewRadarRow';

class RadarTableBody extends React.Component{
    render() {
        if(typeof this.props.radars !== 'undefined'){
            return (
                <tbody>
                    {this.props.radars.map(function (currentRow) {
                        return <RadarRow key={currentRow.id} rowData={currentRow} container={this.props.container}/>
                        }.bind(this))}
                    <NewRadarRow container={this.props.container}/>
                </tbody>
            );
        }
        else{
            return(
                <tbody>
                    <NewRadarRow />
                </tbody>
            );
        }
    }
};

function mapStateToProps(state) {
  return {
        radars: state.radarReducer.radars,
        currentUser: state.radarReducer.currentUser
    };
};

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTableBody);