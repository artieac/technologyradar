import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { RadarTypeRepository } from '../../../../Repositories/RadarTypeRepository';
import { Error_NoRadarRings } from '../Errors/Error';
import RadarRingListItem from './RadarRingListItem';
import { addSelectedRadarTypeToState } from '../../redux/RadarTypeReducer';

class RadarRingList extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.handleAddNewRing = this.handleAddNewRing.bind(this);
    }

    handleAddNewRing(){
        var newRadarRing = {};
        newRadarRing.id = -1;
        newRadarRing.name = "New Radar Ring";
        newRadarRing.displayOption = this.props.selectedRadarType.radarRings.length;
        this.props.selectedRadarType.radarRings.push(newRadarRing);
        this.props.storeSelectedRadarType(this.props.selectedRadarType);
        this.forceUpdate();
    }

    render(){
        if(this.props.radarRings!==undefined && this.props.radarRings.length > 0){
            return(
                <div className="panel-body">
                    {this.props.radarRings.map((currentRow) => {
                        return <RadarRingListItem key={currentRow.id} rowData={currentRow} editMode={this.props.editMode} listContainer={this}/>
                        })}
                </div>
            );
        }
        else{
            return (
                <div className="panel-body">
                    <div className="errorText">{ Error_NoRadarRings }</div>
                </div>);
        }
    }
};

function mapStateToProps(state) {
  return {
    	selectedRadarType: state.radarTypeReducer.selectedRadarType,
        showEdit: state.radarTypeReducer.showEdit,
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingList);