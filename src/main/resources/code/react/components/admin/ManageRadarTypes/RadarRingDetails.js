import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTypesToState } from '../../../../redux/reducers/admin/RadarTypeReducer';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';
import { Dropdown, DropdownButton } from 'react-bootstrap';

export class RadarRingDetails extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.handleTypeDetailsNameChange = this.handleTypeDetailsNameChange.bind(this);
        this.handleDisplayOptionChange = this.handleDisplayOptionChange.bind(this);
    }

    handleTypeDetailsNameChange(event){
        this.props.rowData.name = event.target.value;
    }

    handleDisplayOptionChange(event){
        this.props.rowData.displayOption = event.target.value;
    }

    render(){
        if(this.props.rowData!==undefined){
            return(
                <div className="row">
                    <div className="col-md-6">
                        <input type="text" className={this.props.readonly ? 'readonly="readonly"' : ''} ref="typeDetailsName" defaultValue={this.props.rowData.name} onChange= {(event) => { this.handleTypeDetailsNameChange(event) }}/>
                    </div>
                    <div className="col-md-2">
                        <input type="text" className={this.props.readOnly ? 'readonly="readonly"' : ''} ref="typeDetailsDisplayOption" defaultValue={this.props.rowData.displayOption} onChange= {(event) => { this.handleDisplayOptionChange(event) }} maxLength="2" size="2"/>
                    </div>
                    <DeleteRadarRingButton readOnly={this.props.readonly} userId={this.props.userId} radarTypeId={this.props.radarTypeId} radarRing={this.props.rowData}/>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};

class DeleteRadarRingButton extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleOnDeleteClick = this.handleOnDeleteClick.bind(this);
    }

    handleOnDeleteClick(event){
        this.radarTypeRepository.deleteRadarRing(this.props.userId, this.props.radarTypeId, this.props.radarRing.id);
    }

    render(){
        return(
            <div className={this.props.readOnly ? "hidden" : "col-md-2"}>
                <input type="button" value="Delete" className="btn btn-primary" disabled={!this.props.radarRing.canDelete } onClick = {(event) => { this.handleOnDeleteClick(event) }}/>
            </div>
        );
    }
}
function mapStateToProps(state) {
  return {
    	selectedRadarType: state.radarTypeReducer.selectedRadarType
    };
};

const mapDispatchToProps = dispatch => {
  return {

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingDetails);