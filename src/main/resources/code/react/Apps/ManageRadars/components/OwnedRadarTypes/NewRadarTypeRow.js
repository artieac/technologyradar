import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedRadarTypeToState, addRadarTypesToState } from  '../../redux/RadarTypeReducer';
import { RadarTypeRepository } from '../../../../Repositories/RadarTypeRepository';

class NewRadarTypeRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: ''
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleAddRadarType = this.handleAddRadarType.bind(this);
    }

    handleAddRadarType() {
        this.props.storeSelectedRadarType(this.radarTypeRepository.createDefaultRadarType(""));
    }

    render(){
        return(
            <div className="row">
                <div className="col-lg-1`">
                    <input type="button" className="btn btn-techradar" value="Add Radar Type" onClick= { this.handleAddRadarType } />
                </div>
            </div>
        );
    }
};

function mapNRTRStateToProps(state) {
  return {
        selectedRadarType : state.radarTypeReducer.selectedRadarType
    };
};

const mapNRTRLDispatchToProps = dispatch => {
  return {
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))}
    }
};

export default connect(
  mapNRTRStateToProps,
    mapNRTRLDispatchToProps
)(NewRadarTypeRow);