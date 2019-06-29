import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTypesToState } from '../../../../redux/reducers/admin/RadarTypeReducer';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';
import { Dropdown, DropdownButton } from 'react-bootstrap';

class RadarRingDetails extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            isDeleted: false
        };

        this.handleTypeDetailsNameChange = this.handleTypeDetailsNameChange.bind(this);
        this.handleDisplayOptionChange = this.handleDisplayOptionChange.bind(this);
        this.handleOnDeleteClick = this.handleOnDeleteClick.bind(this);
    }

    handleTypeDetailsNameChange(event){
        this.props.rowData.name = event.target.value;
    }

    handleDisplayOptionChange(event){
        this.props.rowData.displayOption = event.target.value;
    }

    handleOnDeleteClick(event){
        if(this.props.selectedRadarType!==undefined){
            if(this.props.selectedRadarType.radarRings!==undefined){
                for(var i = 0; i < this.props.selectedRadarType.radarRings.length; i++){
                    if(this.props.selectedRadarType.radarRings[i].id==this.props.rowData.id){
                        this.props.selectedRadarType.radarRings.splice(i, 1);
                        this.setState({isDeleted : true});
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
                        <input type="text" className={this.props.editMode ? '' : 'readonly="readonly"'} ref="typeDetailsName" defaultValue={this.props.rowData.name} onChange= {(event) => { this.handleTypeDetailsNameChange(event) }}/>
                    </div>
                    <div className="col-md-2">
                        <input type="text" className={this.props.editMode ?  '' : 'readonly="readonly"'} ref="typeDetailsDisplayOption" defaultValue={this.props.rowData.displayOption} onChange= {(event) => { this.handleDisplayOptionChange(event) }} maxLength="2" size="2"/>
                    </div>
                    <div className={this.props.editMode ?  "col-md-2" : "hidden"}>
                        <input type="button" value="Delete" className="btn btn-primary" onClick = {(event) => { this.handleOnDeleteClick(event) }}/>
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
    	selectedRadarType: state.radarTypeReducer.selectedRadarType,
        showHistory: state.radarTypeReducer.showHistory,
        showEdit: state.radarTypeReducer.showEdit
    };
};

const mapDispatchToProps = dispatch => {
  return {

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingDetails);