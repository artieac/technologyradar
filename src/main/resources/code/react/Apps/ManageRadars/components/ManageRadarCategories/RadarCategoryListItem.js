import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedListItemToState, addSetListToState } from '../../redux/SetManagementReducer';
import { RadarRingRepository } from '../../../../Repositories/RadarRingRepository';

class RadarRingListItem extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            isDeleted: false
        };

        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleDisplayOptionChange = this.handleDisplayOptionChange.bind(this);
        this.handleOnDeleteClick = this.handleOnDeleteClick.bind(this);
    }

    handleNameChange(event){
        this.props.rowData.name = event.target.value;
    }

    handleDisplayOptionChange(event){
        this.props.rowData.displayOption = event.target.value;
    }

    handleOnDeleteClick(event){
        if(this.props.selectedListItem!==undefined){
            if(this.props.selectedListItem.radarRings!==undefined){
                for(var i = 0; i < this.props.selectedListItem.radarRings.length; i++){
                    if(this.props.selectedListItem.radarRings[i].id==this.props.rowData.id){
                        this.props.selectedListItem.radarRings.splice(i, 1);
                        this.props.selectedListItem(this.props.selectedRadarType);
                        this.setState({isDeleted : true});
                        this.props.listContainer.forceUpdate();
                        break;
                    }
                }
            }
        }

        this.forceUpdate();
    }

    render(){
        if(this.props.rowData!==undefined){
            return(
                <div className={this.state.isDeleted ? "hidden row" : "row"}>
                    <div className="col-md-6">
                        <input type="text" defaultValue={this.props.rowData.name} onChange= {(event) => { this.handleTypeDetailsNameChange(event) }}/>
                    </div>
                    <div className="col-md-2">
                        <input type="text" defaultValue={this.props.rowData.displayOption} onChange= {(event) => { this.handleDisplayOptionChange(event) }} maxLength="2" size="2"/>
                    </div>
                    <div className={this.props.canAddOrDelete ?  "col-md-2" : "hidden"}>
                        <input type="button" value="Delete" className="btn btn-techradar" onClick = {(event) => { this.handleOnDeleteClick(event) }}/>
                    </div>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};

function mapStateToProps(state) {
  return {
    	setList: state.setManagementReducer.RadarCategorySets,
        selectedListItem : state.setManagementReducer.selectedListItem
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedListItem : selectedListItem => { dispatch(addSelectedListItemToState(selectedListItem))},
        storeSetList : setList => { dispatch(addSetListToState(setList))},
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingListItem);

