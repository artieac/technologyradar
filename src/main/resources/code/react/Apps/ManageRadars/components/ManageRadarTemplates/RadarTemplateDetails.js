import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { Error_NoRadarRings } from '../Errors/Error';
import { addSetListToState, addSelectedListItemToState } from '../../redux/SetManagementReducer';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';
import { GenericDropdown } from '../Common/GenericDropdown';
import { addSelectedRadarRingSetToState, addSelectedRadarCategorySetToState } from './redux/RadarTemplateReducer';

class RadarTemplateDetails extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.radarTemplateRepository = new RadarTemplateRepository();

        this.handleSaveClickEvent = this.handleSaveClickEvent.bind(this);
        this.handleNameChangeEvent = this.handleNameChangeEvent.bind(this);
        this.handleDescriptionChangeEvent = this.handleDescriptionChangeEvent.bind(this);
        this.handleEditChangeSuccess = this.handleEditChangeSuccess.bind(this);
        this.handleRadarCategoryDropdownSelectionNotify = this.handleRadarCategoryDropdownSelectionNotify.bind(this);
        this.handleRadarRingDropdownSelectionNotify = this.handleRadarRingDropdownSelectionNotify.bind(this);
    }

    componentDidMount(){
    }

    handleNameChangeEvent(event){
        this.props.selectedListItem.name = event.target.value;
        this.forceUpdate();
    }

    handleDescriptionChangeEvent(event){
         this.props.selectedListItem.description = event.target.value;
        this.forceUpdate();
    }

    handleSaveClickEvent(){
        if(this.props.selectedListItem.id > 0){
            this.radarTemplateRepository.update(this.props.currentUser.id, this.props.selectedListItem, this.handleEditChangeSuccess);
        }
        else{
            this.radarTemplateRepository.add(this.props.currentUser.id, this.props.selectedListItem, this.handleEditChangeSuccess);
        }
    }

    handleEditChangeSuccess(listItem){
        this.props.storeSelectedListItem(listItem);
        this.radarTemplateRepository.getByUserId(this.props.currentUser.id, this.props.storeSetList);
        this.forceUpdate();
    }

    handleRadarCategoryDropdownSelectionNotify(radarCategorySet){
        this.props.storeSelectedRadarCategorySet(radarCategorySet);
    }

    handleRadarRingDropdownSelectionNotify(radarRingSet){
        this.props.storeSelectedRadarRingSet(radarRingSet);
    }

    render(){
        if(this.props.selectedListItem!==undefined){
            return(
                <div className="panel-body">
                    <div className="row">
                        <div className="col-md-6">
                            <input type="text" value={ this.props.selectedListItem.name }  onChange= {(event) => { this.handleNameChangeEvent(event) }}/>
                        </div>
                        <div className="col-md-3">
                           <input type="button" className='btn btn-techradar' value="Save" onClick={(event) => this.handleSaveClickEvent(event) }/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-9">
                            <textarea className="form-control rounded-0" rows="3" value={this.props.selectedListItem.description } onChange= {(event) => { this.handleDescriptionChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}></textarea>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-6">
                            <GenericDropdown selectionNotification={this.props.storeSelectedRadarCategorySet} data={this.props.radarCategorySets} title={this.props.selectedRadarCategorySet===undefined ? "Select" : this.props.selectedRadarCategorySet.name}/>
                        </div>
                        <div className="col-md-6">
                            <GenericDropdown selectionNotification={this.props.storeSelectedRadarRingSet} data={this.props.radarRingSets} title={this.props.selectedRadarRingSet===undefined ? "Select" : this.props.selectedRadarRingSet.name}/>
                        </div>
                    </div>
                 </div>
            );
        }
        else{
            return (
                <div className="panel-body">
                </div>);
        }
    }
};

function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser,
     	setList: state.setManagementReducer.setList,
        selectedListItem : state.setManagementReducer.selectedListItem,
        radarCategorySets: state.radarTemplateReducer.radarCategorySets,
        radarRingSets: state.radarTemplateReducer.radarRingSets,
        selectedRadarCategorySet: state.radarTemplateReducer.selectedRadarCategorySet,
        selectedRadarRingSet: state.radarTemplateReducer.selectedRadarRingSet
   };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedListItem : selectedListItem => { dispatch(addSelectedListItemToState(selectedListItem))},
        storeSetList : setList => { dispatch(addSetListToState(setList))},
        storeSelectedRadarCategorySet: radarCategorySet => { dispatch(addSelectedRadarCategorySetToState(radarCategorySet))},
        storeSelectedRadarRingSet: radarRingSet => { dispatch(addSelectedRadarRingSetToState(radarRingSet))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTemplateDetails);