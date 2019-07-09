import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTypesToState, addSelectedRadarTypeToState } from '../../../../redux/reducers/admin/RadarTypeReducer';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class RadarRingListItem extends React.Component{
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
                        this.props.storeSelectedRadarType(this.props.selectedRadarType);
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
                <div className="row">
                    <div className="col-md-6">
                        <input type="text" className='readonly="readonly"' ref="typeDetailsName" defaultValue={this.props.rowData.name} onChange= {(event) => { this.handleTypeDetailsNameChange(event) }}/>
                    </div>
                    <div className="col-md-2">
                        <input type="text" className='readonly="readonly"' ref="typeDetailsDisplayOption" defaultValue={this.props.rowData.displayOption} onChange= {(event) => { this.handleDisplayOptionChange(event) }} maxLength="2" size="2"/>
                    </div>
                    <div className="col-md-2"></div>
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
        showEdit: state.radarTypeReducer.showEdit,
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingListItem);