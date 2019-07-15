import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import radarTypeReducer from  '../../redux/RadarTypeReducer';
import { addSelectedRadarTypeToState, addAssociatedRadarTypesToState, addRadarTypeHistoryToState, setShowEdit, setShowHistory } from  '../../redux/RadarTypeReducer';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository } from '../../../../Repositories/RadarTypeRepository';

class RadarTypeHistoryItem extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTypeRepository = new RadarTypeRepository();

        this.handleSelectItem = this.handleSelectItem.bind(this);
    }

    handleSelectItem(){
        this.props.storeSelectedRadarType(this.props.radarType);
        this.forceUpdate();
    }

    render() {
        if(typeof this.props.radarType !== 'undefined'){
            return (
                <div className="row">
                    <div className="col-md-2">
                        <input type="radio" ref="isSelected" checked={this.props.selectedRadarType.version===this.props.radarType.version} onChange = {(event) => this.handleSelectItem(event) }/>
                    </div>
                    <div className="col-md-6">{this.props.radarType.name } - v{ this.props.radarType.version}</div>
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
        selectedRadarType : state.radarTypeReducer.selectedRadarType,
        currentUser : state.radarTypeReducer.currentUser,
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarType : radarType => { dispatch(addSelectedRadarTypeToState(radarType))},
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeHistoryItem);