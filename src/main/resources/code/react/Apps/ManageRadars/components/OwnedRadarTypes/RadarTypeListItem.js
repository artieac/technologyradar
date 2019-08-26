import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarTypeReducer from  '../../redux/RadarTypeReducer';
import { addSelectedRadarTypeToState, addAssociatedRadarTypesToState, setShowEdit } from  '../../redux/RadarTypeReducer';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository } from '../../../../Repositories/RadarTypeRepository';

class RadarTypeListItem extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleShowEditClick = this.handleShowEditClick.bind(this);
        this.handleOnDeleteClick = this.handleOnDeleteClick.bind(this);
        this.handleDeleteResponse = this.handleDeleteResponse.bind(this);
    }

    handleShowEditClick(event){
        this.props.storeSelectedRadarType(this.props.radarType);
        this.props.setShowEdit(true);
        this.forceUpdate();
    }

    handleOnDeleteClick() {
        if(confirm("This will permanently remove all radars of this type.  Are you sure you want to proceed?"))
        {
            this.radarTypeRepository.deleteRadarType(this.props.currentUser.id, this.props.radarType.id, this.handleDeleteResponse);
        }
    }

    handleDeleteResponse() {
        this.radarTypeRepository.getByUserId(this.props.currentUser.id, false, this.props.storeRadarTypes);
        this.forceUpdate();
    }

    render() {
        if(typeof this.props.radarType !== 'undefined'){
            return (
                <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                    <div className="col-md-6">{this.props.radarType.name } </div>
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
        associatedRadarTypes: state.radarTypeReducer.associatedRadarTypes,
        selectedRadarType : state.radarTypeReducer.selectedRadarType,
        currentUser : state.userReducer.currentUser,
        showEdit: state.radarTypeReducer.showEdit

    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},
        storeAssociatedRadarTypes : radarType => { dispatch(addAssociatedRadarTypesToState(radarType))},
        setShowEdit: showEdit => { dispatch(setShowEdit(showEdit))},
        storeRadarTypes : radarTypes => { dispatch(addRadarTypesToState(radarTypes))},

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeListItem);