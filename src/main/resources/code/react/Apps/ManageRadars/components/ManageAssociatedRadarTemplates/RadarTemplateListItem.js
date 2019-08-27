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
        this.handleAssociateRadarTemplateChange = this.handleAssociateRadarTemplateChange.bind(this);
        this.handleAssociatedRadarTemplateSuccess = this.handleAssociatedRadarTemplateSuccess.bind(this);
        this.handleGetAssociatedRadarTemplatesSuccess = this.handleGetAssociatedRadarTemplatesSuccess.bind(this);
        this.canAssociateRadarTemplates = this.canAssociateRadarTemplates.bind(this);
    }

    handleShowEditClick(event){
        this.props.storeSelectedRadarTemplate(this.props.radarTemplate);
        this.props.setShowEdit(true);
        this.forceUpdate();
    }

    handleAssociateRadarTemplateChange(event){
        var shouldAssociate = this.refs.shouldAssociate.checked;

        if(shouldAssociate==true)
        {
            if(this.canAssociateRadarTemplates(this.props.currentUser, this.props.associatedRadarTemplates)==true)
            {
                this.radarTemplateRepository.associateRadarTemplate(this.props.currentUser.id, this.props.radarTemplate.id, shouldAssociate, this.handleAssociatedRadarTemplateSuccess);
                this.forceUpdate();
            }
            else
            {
                alert("You are only allowed to use " + this.props.currentUser.canHaveNAssociatedRadarTemplates + " types from other users.  Please uncheck another before trying to add this one.");
            }
        }
        else
        {
            this.radarTemplateRepository.associateRadarTemplate(this.props.currentUser.id, this.props.radarTemplate.id, shouldAssociate, this.handleAssociatedRadarTemplateSuccess);
            this.forceUpdate();
        }
    }

    handleAssociatedRadarTemplateSuccess(){
       this.radarTemplateRepository.getAssociatedRadarTemplates(this.props.currentUser.id, this.props.storeAssociatedRadarTemplates);
    }

    handleGetAssociatedRadarTemplatesSuccess(associatedRadarTemplates){
        this.props.storeAssociatedRadarTemplates(associatedRadarTemplates);
    }

    isAssociatedToUser(){
        var retVal = false;

        if(this.props.associatedRadarTemplates.length > 0){
            for(var i = 0; i < this.props.associatedRadarTemplates.length; i++){
                if(this.props.associatedRadarTemplates[i].id==this.props.radarTemplate.id){
                    retVal = true;
                    break;
                }
            }
        }

        return retVal;
    }

    canAssociateRadarTemplates(currentUser, associatedRadarTemplates) {
        var retVal = false;

        if(currentUser !== undefined && associatedRadarTemplates !== undefined){
            if(associatedRadarTemplates.length < currentUser.canHaveNAssociatedRadarTemplates){
                retVal = true;
            }
        }

        return retVal;
    }

    render() {
        if(typeof this.props.radarTemplate !== 'undefined'){
            return (
                <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                    <div className="col-md-7">{this.props.radarTemplate.name } </div>
                    <div className={this.props.currentUser.id == this.props.radarTemplate.radarUserId ? 'hidden' : 'col-md-6'}>
                        <span>Use this?  <input type="checkbox" checked={this.isAssociatedToUser()} ref="shouldAssociate" onChange = {(event) => this.handleAssociateRadarTemplateChange(event) }/></span>
                    </div>
                    <div className="col-md-2">
                       <input type="button" className="btn btn-techradar" value="View" onClick= {(event) => this.handleShowEditClick(event) } />
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
        setShowHistory: showHistory => { dispatch(setShowHistory(showHistory))},
        setShowEdit: showEdit => { dispatch(setShowEdit(showEdit))},
        storeRadarTemplateHistory: radarTemplateHistory => { dispatch(addRadarTemplateHistoryToState(radarTemplateHistory))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTemplateListItem);