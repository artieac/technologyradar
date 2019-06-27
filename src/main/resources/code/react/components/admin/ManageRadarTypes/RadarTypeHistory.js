import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import RadarTypeHistoryItem from './RadarTypeHistoryItem';
import RadarTypeDetails from './RadarTypeDetails';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class RadarTypeHistory extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            radarTypeHistory: []
        };

        var radarTypeRepository = new RadarTypeRepository();
    }

    componentDidMount(){}

    render() {
        if(this.props.radarTypeHistory !== undefined){
            return (
                <div className="row">
                    <div className="col-lg-12">
                        <div className="row">
                            <div className="col-lg-3">Name</div>
                            <div className="col-lg-3">Action</div>
                        </div>
                        {
                            this.props.radarTypeHistory.map((currentRow) => {
                                return <RadarTypeHistoryItem key={currentRow.version} radarType={currentRow} parentContainer = { this } />
                            })
                        }
                        <div className="row">
                            <RadarTypeDetails/>
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

function mapStateToProps(state) {
  return {
    	currentUser : state.radarTypeReducer.currentUser,
    	radarTypeHistory: state.radarTypeReducer.radarTypeHistory
    };
}

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeHistory);