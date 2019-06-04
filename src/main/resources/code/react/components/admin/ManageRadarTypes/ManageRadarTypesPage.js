import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { addRadarTypeCollectionToState } from '../../../../redux/reducers/adminAppReducer';
import { RadarTypeRow } from './RadarTypeRow';
import { NewRadarTypeRow } from './NewRadarTypeRow';

class ManageRadarTypesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };
    }

    componentDidMount(){
        this.getRadarTypeCollectionByUserId(this.state.userId);
    }

    getRadarTypeCollectionByUserId(userId){
        jQuery.ajax({
                url: '/api/User/' + userId + '/RadarTypes',
                async: true,
                dataType: 'json',
                success: function (radarTypeCollection) {
                    this.props.storeRadarTypeCollection(radarTypeCollection);
                }.bind(this)
            });
    }

    render() {
        if(this.props.radarTypeCollection !== undefined && this.props.radarTypeCollection.length > 0){
            return (
                <div className="bodyContent">
                    <div className="contentPageTitle">
                        <label>Manage Radar Types</label>
                    </div>
                    <p>Add a new type to have rate different types of things</p>
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="row">
                                <div className="col-lg-3">Name</div>
                                <div className="col-lg-3">Action</div>
                            </div>
                            {
                                this.props.radarTypeCollection.map(function (currentRow) {
                                    return <RadarTypeRow key={currentRow.id} rowData={currentRow} userId={this.state.userId} parentContainer = { this }/>
                                }.bind(this))
                            }
                            <NewRadarTypeRow userId={this.props.userId} parentContainer = { this }/>
                        </div>
                    </div>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};


const mapMRTPDispatchToProps = dispatch => {
  return {
        storeRadarTypeCollection : radarTypeCollection => { dispatch(addRadarTypeCollectionToState(radarTypeCollection))}
    }
};


function mapMRTPStateToProps(state) {
  return {
    	radarTypeCollection: state.radarTypeCollection
    };
}

export default connect(
  mapMRTPStateToProps,
    mapMRTPDispatchToProps
)(ManageRadarTypesPage);