import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedListItemToState } from  '../../redux/SetManagementReducer';
import { RadarRingRepository } from '../../../../Repositories/RadarRingRepository';

class NewRadarRingSetRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: ''
        };

        this.radarRingRepository = new RadarRingRepository();

        this.handleAddRadarRingSet = this.handleAddRadarRingSet.bind(this);
    }

    handleAddRadarRingSet() {
        this.props.storeSelectedListItem(this.radarRingRepository.createDefault(""));
    }

    render(){
        return(
            <div className="row">
                <div className="col-lg-1`">
                    <input type="button" className="btn btn-techradar" value="Add Radar Ring Set" onClick= {(event) => { this.handleAddRadarRingSet(event) } } />
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	setList: state.setManagementReducer.setList,
        selectedListItem : state.setManagementReducer.selectedListItem
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedListItem : selectedListItem => { dispatch(addSelectedListItemToState(selectedListItem))},
        storeSetList : setList => { dispatch(addSetListToState(setList))},
    }
};


export default connect(
  mapStateToProps,
    mapDispatchToProps
)(NewRadarRingSetRow);