import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedListItemToState } from  '../../redux/SetManagementReducer';
import { RadarCategoryRepository } from '../../../../Repositories/RadarCategoryRepository';

class NewRadarCategorySetRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: ''
        };

        this.radarCategoryRepository = new RadarCategoryRepository();

        this.handleAddRadarCategorySet = this.handleAddRadarCategorySet.bind(this);
    }

    handleAddRadarCategorySet() {
        this.props.storeSelectedListItem(this.radarCategoryRepository.createDefault(""));
    }

    render(){
        return(
            <div className="row">
                <div className="col-lg-1`">
                    <input type="button" className="btn btn-techradar" value="Add Radar Category Set" onClick= {(event) => { this.handleAddRadarCategorySet(event) } } />
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
)(NewRadarCategorySetRow);