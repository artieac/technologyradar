import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { Link } from 'react-router-dom';
import { connect } from "react-redux";
import * as actionTypes from '../../../../redux/reducers/adminActionTypes';
import { addRadarTypeCollectionToState } from '../../../../redux/reducers/adminAppReducer';
import { RadarRingDetails } from './RadarRingDetails';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

export class RadarTypeEditor extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            radarCategoriesColorMap: {  "Green": "#8FA227",
                                        "Blue": "#587486",
                                        "Maroon": "#B70062",
                                        "Orange": "#DC6F1D"},
            radarCategoriesColorNameMap: {}
        };

        var colorNames = Object.keys(this.state.radarCategoriesColorMap);

        for(var i = 0; i < colorNames.length; i++){
            this.state.radarCategoriesColorNameMap[this.state.radarCategoriesColorMap[colorNames[i]]] = colorNames[i];
        }

        this.radarTypeRepository = new RadarTypeRepository();
        this.nameInput = React.createRef();

        this.handleRadarTypeNameChangeEvent = this.handleRadarTypeNameChangeEvent.bind(this);
        this.handleSaveRadarType = this.handleSaveRadarType.bind(this);
        this.handleEditChangeSuccess = this.handleEditChangeSuccess.bind(this);
        this.handleDeleteRadarType = this.handleDeleteRadarType.bind(this);
    }

    handleRadarTypeNameChangeEvent(event){
         this.props.radarType.name = event.target.value;
        this.forceUpdate();
    }

    handleSaveRadarType(){
        if(this.props.radarType.id > 0){
            this.radarTypeRepository.updateRadarType(this.props.userId, this.props.radarType, this.handleEditChangeSuccess);
        }
        else{
            this.radarTypeRepository.addRadarType(this.props.userId, this.props.radarType, this.handleEditChangeSuccess);
        }
    }

    handleEditChangeSuccess(radarType){
        this.props.parentContainer.getByUserId(this.props.userId, this.handleGetByUserIdSuccess);
    }

    handleDeleteRadarType(){
        this.radarTypeRepository.deleteRadarType(this.props.userId, this.props.radarType, this.handleEditChangeSuccess);
    }

    render() {
        return (
            <div>
                <div className='row'>
                    <div className="col-md-6">
                        <label>Name</label>
                        <input type="text" value={this.props.radarType.name } ref={this.nameInput} onChange= {(event) => { this.handleRadarTypeNameChangeEvent(event) }} readOnly={this.props.readonly}/>
                    </div>
                    <div className="col-md-6">
                       <input type="button" className='btn btn-primary' disabled={this.props.readonly} value="Save" onClick={ this.handleSaveRadarType }/>
                       <input type="button" className='btn btn-primary' disabled={this.props.readonly} value="Delete" onClick={ this.handleDeleteRadarType }/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-12">
                        <div className="panel panel-default">
                            <div className="panel-heading">Rings</div>
                            <div className="panel-body">
                                {this.props.radarType.radarRings.map((currentRow) => {
                                    return <RadarRingDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} readonly={this.props.readonly}/>
                                    })}
                            </div>
                        </div>
                    </div>
                    <div className="col-md-12">
                        <div className="panel panel-default">
                            <div className="panel-heading">Categories</div>
                            <div className="panel-body">
                                {this.props.radarType.radarCategories.map((currentRow) =>{
                                    return <RadarCategoryDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer } colorMap={this.state.radarCategoriesColorMap} colorNameMap={this.state.radarCategoriesColorNameMap}/>
                                    })}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
};

const mapRTEDispatchToProps = dispatch => {
  return {
        storeRadarTypeCollection : radarTypeCollection => { dispatch(addRadarTypeCollectionToState(radarTypeCollection))}
    }
};


function mapRTEStateToProps(state) {
  return {
    	radarTypeCollection: state.radarTypeCollection
    };
}

export default connect(
  mapRTEStateToProps,
    mapRTEDispatchToProps
)(RadarTypeEditor);