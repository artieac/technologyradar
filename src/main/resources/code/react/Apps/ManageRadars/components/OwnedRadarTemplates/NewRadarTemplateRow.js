import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedRadarTemplateToState, addRadarTemplatesToState } from  '../../redux/RadarTemplateReducer';
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
        this.props.storeSelectedRadarTemplate(this.radarTemplateRepository.createDefaultRadarTemplate(""));
    }

    render(){
        return(
            <div className="row">
                <div className="col-lg-1`">
                    <input type="button" className="btn btn-techradar" value="Add Radar Template" onClick= { this.handleAddRadarTemplate } />
                </div>
            </div>
        );
    }
};

function mapNRTRStateToProps(state) {
  return {
        selectedRadarTemplate : state.radarTemplateReducer.selectedRadarTemplate
    };
};

const mapNRTRLDispatchToProps = dispatch => {
  return {
        storeSelectedRadarTemplate : radarTemplate => { dispatch(addSelectedRadarTemplateToState(radarTemplate))}
    }
};

export default connect(
  mapNRTRStateToProps,
    mapNRTRLDispatchToProps
)(NewRadarTemplateRow);