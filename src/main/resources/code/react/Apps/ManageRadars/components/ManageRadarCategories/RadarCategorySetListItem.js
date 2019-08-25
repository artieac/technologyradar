import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedListItemToState, setShowEdit } from  '../../redux/SetManagementReducer';
import { RadarCategoryRepository } from '../../../../Repositories/RadarCategoryRepository';

class RadarCategorySetListItem extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarCategoryRepository = new RadarCategoryRepository();

        this.handleShowEditClick = this.handleShowEditClick.bind(this);
        this.handleOnDeleteClick = this.handleOnDeleteClick.bind(this);
        this.handleDeleteResponse = this.handleDeleteResponse.bind(this);
    }

    handleShowEditClick(event){
        this.props.storeSelectedListItem(this.props.radarCategorySet);
        this.props.setShowEdit(true);
        this.forceUpdate();
    }

    handleOnDeleteClick() {
        if(confirm("This will permanently remove all radars of this type.  Are you sure you want to proceed?"))
        {
            this.radarCategoryRepository.deleteRadarCategorySet(this.props.currentUser.id, this.props.selectedListItem.id, this.handleDeleteResponse);
        }
    }

    handleDeleteResponse() {
        this.radarCategoryRepository.getByUserId(this.props.currentUser.id, this.props.storeSetList);
        this.forceUpdate();
    }

    render() {
        if(typeof this.props.radarCategorySet !== 'undefined'){
            return (
                <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                    <div className="col-md-6">{this.props.radarCategorySet.name } </div>
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
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarCategorySetListItem);