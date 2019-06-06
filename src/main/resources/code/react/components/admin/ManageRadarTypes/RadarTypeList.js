import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import { RadarTypeListItem } from './RadarTypeListItem';
import { RadarTypeRepository_getAssociatedByUserId } from '../../../Repositories/RadarTypeRepository';

export class RadarTypeList extends React.Component{
    constructor(props){
        super(props);
         this.state = {

        };
    }

    render() {
        if(this.props.radarTypeCollection !== undefined){
            return (
                <div className="row">
                    <div className="col-lg-12">
                        <div className="row">
                            <div className="col-lg-3">Name</div>
                            <div className="col-lg-3">Action</div>
                        </div>
                        {
                            this.props.radarTypeCollection.map((currentRow) => {
                                return <RadarTypeListItem key={currentRow.id} radarType={currentRow} userId={this.state.userId} parentContainer = { this } readonly={true} onSelectRadarType={this.props.onSelectRadarType}/>
                            })
                        }
                    </div>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};


const mapRTLDispatchToProps = dispatch => {
  return {
    }
};


function mapRTLStateToProps(state) {
  return {
    	associatedRadarTypeCollection: state.associatedRadarTypeCollection
    };
}

export default connect(
  mapRTLStateToProps,
    mapRTLDispatchToProps
)(RadarTypeList);