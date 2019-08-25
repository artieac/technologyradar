import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedListItemToState } from  '../../redux/SetManagementReducer';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';

class NewRadarTemplateRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            radarNameInput: ''
        };

        this.radarTemplateRepository = new RadarTemplateRepository();

        this.handleAddRadarTemplate = this.handleAddRadarTemplate.bind(this);
    }

    handleAddRadarTemplate() {
        this.props.storeSelectedListItem(this.radarTemplateRepository.createDefault(""));
    }

    render(){
        return(
            <div className="row">
                <div className="col-lg-1`">
                    <input type="button" className="btn btn-techradar" value="Add Radar Category Set" onClick= {(event) => { this.handleAddRadarTemplate(event) } } />
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
)(NewRadarTemplateRow);