import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarTemplateReducer from  '../../redux/RadarTemplateReducer';
import { addSelectedRadarTemplateToState, addAssociatedRadarTemplatesToState, setShowEdit } from  '../../redux/RadarTemplateReducer';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';

class RadarTemplateListItem extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTemplateRepository = new RadarTemplateRepository();

        this.handleShowEditClick = this.handleShowEditClick.bind(this);
        this.handleOnDeleteClick = this.handleOnDeleteClick.bind(this);
        this.handleDeleteResponse = this.handleDeleteResponse.bind(this);
    }

    handleShowEditClick(event){
        this.props.storeSelectedRadarTemplate(this.props.radarTemplate);
        this.props.setShowEdit(true);
        this.forceUpdate();
    }

    handleOnDeleteClick() {
        if(confirm("This will permanently remove all radars of this type.  Are you sure you want to proceed?"))
        {
            this.radarTemplateRepository.deleteRadarTemplate(this.props.currentUser.id, this.props.radarTemplate.id, this.handleDeleteResponse);
        }
    }

    handleDeleteResponse() {
        this.radarTemplateRepository.getByUserId(this.props.currentUser.id, false, this.props.storeRadarTemplates);
        this.forceUpdate();
    }

    render() {
        if(typeof this.props.radarTemplate !== 'undefined'){
            return (
                <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                    <div className="col-md-6">{this.props.radarTemplate.name } </div>
                    <div className="col-md-2">
                       <input type="button" className="btn btn-techradar" value="View" onClick= {(event) => this.handleShowEditClick(event) } />
                    </div>
                    <div className="col-md-2">
                        <input type="button" className="btn btn-techradar" value="Delete" onClick = {(event) => this.handleOnDeleteClick(event) }/>
                    </div>
                </div>
            );
        }
        else{
            return( <div></div>);
        }
    }
};


function mapStateToProps(state) {
  return {
        associatedRadarTemplates: state.radarTemplateReducer.associatedRadarTemplates,
        selectedRadarTemplate : state.radarTemplateReducer.selectedRadarTemplate,
        currentUser : state.userReducer.currentUser,
        showEdit: state.radarTemplateReducer.showEdit

    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarTemplate : radarTemplate => { dispatch(addSelectedRadarTemplateToState(radarTemplate))},
        storeAssociatedRadarTemplates : radarTemplate => { dispatch(addAssociatedRadarTemplatesToState(radarTemplate))},
        setShowEdit: showEdit => { dispatch(setShowEdit(showEdit))},
        storeRadarTemplates : radarTemplates => { dispatch(addRadarTemplatesToState(radarTemplates))},

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTemplateListItem);