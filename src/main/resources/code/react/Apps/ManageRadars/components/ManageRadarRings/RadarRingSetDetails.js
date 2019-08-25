import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { Error_NoRadarRings } from '../Errors/Error';
import RadarRingListItem from './RadarRingListItem';
import { addSetListToState, addSelectedListItemToState } from '../../redux/SetManagementReducer';
import { RadarRingRepository } from '../../../../Repositories/RadarRingRepository';

class RadarRingSetDetails extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.radarRingRepository = new RadarRingRepository();

        this.handleAddNewRing = this.handleAddNewRing.bind(this);
        this.handleNameChangeEvent = this.handleNameChangeEvent.bind(this);
        this.handleDescriptionChangeEvent = this.handleDescriptionChangeEvent.bind(this);
        this.handleEditChangeSuccess = this.handleEditChangeSuccess.bind(this);
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

    handleAddNewRing(){
        var newRadarRing = {};
        newRadarRing.id = -1;
        newRadarRing.name = "New Radar Ring";
        newRadarRing.displayOption = this.props.selectedListItem.radarRings.length;
        this.props.selectedListItem.radarRings.push(newRadarRing);
        this.props.storeSelectedListItem(this.props.selectedListItem);
        this.forceUpdate();
    }

    handleSaveClickEvent(){
        if(this.props.selectedListItem.radarRings===undefined || this.props.selectedListItem.radarRings.length==0){
            this.forceUpdate();
        }
        else{
            if(this.props.selectedListItem.id > 0){
                this.radarRingRepository.updateRadarRingSet(this.props.currentUser.id, this.props.selectedListItem, this.handleEditChangeSuccess);
            }
            else{
                this.radarRingRepository.addRadarRingSet(this.props.currentUser.id, this.props.selectedListItem, this.handleEditChangeSuccess);
            }
        }
    }

    handleEditChangeSuccess(radarRingSet){
        this.props.storeSelectedListItem(radarRingSet);
        this.radarRingRepository.getByUserId(this.props.currentUser.id, this.props.storeSetList);
        this.forceUpdate();
    }

    render(){
        if(this.props.selectedListItem!==undefined && this.props.selectedListItem.radarRings!==undefined && this.props.selectedListItem.radarRings.length > 0){
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
                        <textarea className="form-control rounded-0" rows="3" value={this.props.selectedListItem.description } onChange= {(event) => { this.handleDescriptionChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}></textarea>
                    </div>
                    {this.props.selectedListItem.radarRings.map((currentRow) => {
                        return <RadarRingListItem key={currentRow.id} rowData={currentRow} editMode={this.props.editMode} listContainer={this} canAddOrDelete={this.props.canAddOrDelete}/>
                        })}
                     <div className={ this.props.canAddOrDelete ?  "row" : "hidden"}>
                        <div className="col-md-12">
                            <input type="button" className="btn btn-techradar" value="Add New Ring" disabled={this.props.selectedListItem.radarRings.length > 7} onClick={(event) => this.handleAddNewRing(event)}/>
                        </div>
                     </div>
                </div>
            );
        }
        else{
            return (
                <div className="panel-body">
                    <div className="errorText">{ Error_NoRadarRings }</div>
                     <div className="row">
                        <div className="col-md-12">
                            <input type="button" className="btn btn-techradar" value="Add New Ring" onClick={(event) => this.handleAddNewRing(event)}/>
                        </div>
                     </div>
                </div>);
        }
    }
};

function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser,
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

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingSetDetails);