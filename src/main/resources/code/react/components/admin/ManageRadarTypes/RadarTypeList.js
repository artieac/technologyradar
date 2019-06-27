import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";

import RadarTypeListItem from './RadarTypeListItem';

class RadarTypeList extends React.Component{
    constructor(props){
        super(props);
         this.state = {

        };
    }

    render() {
        if(this.props.radarTypes !== undefined){
            return (
                <div className="row">
                    <div className="col-lg-12">
                        <div className="row">
                            <div className="col-lg-3">Name</div>
                            <div className="col-lg-3">Action</div>
                        </div>
                        {
                            this.props.radarTypes.map((currentRow, index) => {
                                return <RadarTypeListItem key={index} radarType={currentRow} parentContainer = { this } readonly={true} />
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

function mapStateToProps(state) {
  return {
    	associatedRadarTypes: state.radarTypeReducer.associatedRadarTypes,
    	currentUser : state.radarTypeReducer.currentUser
    };
}

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeList);