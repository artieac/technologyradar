'use strict'
import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarReducer from '../../../../redux/reducers/admin/RadarReducer';
import { addRadarsToState, addRadarTypesToState} from '../../../../redux/reducers/admin/RadarReducer';
import { RadarRepository} from '../../../Repositories/RadarRepository';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository'
import { RadarRow } from './RadarRow';
import { NewRadarRow } from './NewRadarRow';

class ManageRadarsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            userId: jQuery("#userId").val()
        };

        this.radarRepository = new RadarRepository();
        this.radarTypeRepository = new RadarTypeRepository();

        this.getUserRadarsResponse = this.getUserRadarsResponse.bind(this);
        this.getRadarTypeCollectionResponse = this.getRadarTypeCollectionResponse.bind(this);
    }

    componentDidMount(){
        this.radarRepository.getByUserId(this.state.userId, this.getUserRadarsResponse);
        this.radarTypeRepository.getOwnedAndAssociatedByUserId(this.state.userId, this.getRadarTypeCollectionResponse) ;
    }

    getUserRadarsResponse(radars){
        this.props.storeRadars(radars);
        this.forceUpdate();
    }

    getRadarTypeCollectionResponse(radarTypes){
        this.props.storeRadarTypes(radarTypes);
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Technology Assessments</label>
                </div>
                <p>Add an instance of your technology radar to track any changes since the last time you did this</p>
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Type</th>
                            <th width="10%">Published?</th>
                            <th width="10%">Locked?</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <RadarTableBody tableBodyData={this.props.radars} userId={this.state.userId} parentContainer = { this}  radarTypes={this.props.radarTypes}/>
                </table>
            </div>
        );
    }
};

class RadarTableBody extends React.Component{
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

const mapDispatchToProps = dispatch => {
  return {
        storeRadars : radars => { dispatch(addRadarsToState(radars))},
        storeRadarTypes : radarTypes => { dispatch(addRadarTypesToState(radarTypes))}
    }
};


function mapStateToProps(state) {
  return {
    	radars: state.radarReducer.radars,
    	radarTypes: state.radarReducer.radarTypes
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageRadarsPage);