import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';
import { Error_NoRadarRings } from '../Errors/Error';
import RadarRingListItem from './RadarRingListItem';
import { addSelectedRadarTemplateToState } from '../../redux/RadarTemplateReducer';

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
        newRadarRing.displayOption = this.props.selectedRadarTemplate.radarRings.length;
        this.props.selectedRadarTemplate.radarRings.push(newRadarRing);
        this.props.storeSelectedRadarTemplate(this.props.selectedRadarTemplate);
        this.forceUpdate();
    }

    render(){
        if(this.props.radarRings!==undefined && this.props.radarRings.length > 0){
            return(
                <div className="panel-body">
                    <div className="row">
                        <div className="col-md-6"><b>Name</b></div>
                        <div className="col-md-2"><b>Sort Order</b></div>
                        <div className="col-md-2"></div>
                    </div>
                    <div className="row"></div>
                    {this.props.radarRings.map((currentRow) => {
                        return <RadarRingListItem key={currentRow.id} rowData={currentRow} editMode={this.props.editMode} listContainer={this} canAddOrDelete={this.props.canAddOrDelete}/>
                        })}
                     <div className={ this.props.editMode==true && this.props.canAddOrDelete ?  "row" : "hidden"}>
                        <div className="col-md-12">
                            <input type="button" className="btn btn-techradar" value="Add New Ring" disabled={this.props.radarRings.length > 7} onClick={(event) => this.handleAddNewRing(event)}/>
                        </div>
                     </div>
                </div>
            );
        }
        else{
            return (
                <div className="panel-body">
                    <div className="errorText">{ Error_NoRadarRings }</div>
                     <div className={ this.props.editMode==true ?  "row" : "hidden"}>
                        <div className="col-md-12">
                            <input type="button" className="btn btn-techradar" value="Add New Ring" disabled={this.props.radarRings.length > 7} onClick={(event) => this.handleAddNewRing(event)}/>
                        </div>
                     </div>
                </div>);
        }
    }
};

function mapStateToProps(state) {
  return {
    	selectedRadarTemplate: state.radarTemplateReducer.selectedRadarTemplate,
        showEdit: state.radarTemplateReducer.showEdit,
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarTemplate : radarTemplate => { dispatch(addSelectedRadarTemplateToState(radarTemplate))},

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingList);