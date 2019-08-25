import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedListItemToState, setShowEdit } from  '../../redux/SetManagementReducer';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';
import { addSelectedRadarRingSetToState, addSelectedRadarCategorySetToState } from './redux/RadarTemplateReducer';

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
        this.props.storeSelectedListItem(this.props.radarTemplate);
        this.props.storeSelectedRadarRingSet(this.props.radarTemplate.radarRingSet);
        this.props.storeSelectedRadarCategorySet(this.props.radarTemplate.radarCategorySet);
        this.forceUpdate();
    }

    handleOnDeleteClick() {
        if(confirm("This will permanently remove all radars of this type.  Are you sure you want to proceed?"))
        {
            this.radarTemplateRepository.delete(this.props.currentUser.id, this.props.selectedListItem.id, this.handleDeleteResponse);
        }
    }

    handleDeleteResponse() {
        this.radarTemplateRepository.getByUserId(this.props.currentUser.id, this.props.storeSetList);
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
        currentUser : state.userReducer.currentUser,
        showEdit: state.setManagementReducer.showEdit,
    	setList: state.setManagementReducer.setList,
        selectedListItem : state.setManagementReducer.selectedListItem
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedListItem : selectedListItem => { dispatch(addSelectedListItemToState(selectedListItem))},
        storeSetList : setList => { dispatch(addSetListToState(setList))},
        setShowEdit: showEdit => { dispatch(setShowEdit(showEdit))},
        storeSelectedRadarCategorySet: radarCategorySet => { dispatch(addSelectedRadarCategorySetToState(radarCategorySet))},
        storeSelectedRadarRingSet: radarRingSet => { dispatch(addSelectedRadarRingSetToState(radarRingSet))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTemplateListItem);