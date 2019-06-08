import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTypesToState } from '../../../../redux/reducers/admin/RadarTypeReducer';
import { RadarRingDetails } from './RadarRingDetails';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';

class RadarTypeEditor extends React.Component{
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
        this.handleSharedWithOthersChange = this.handleSharedWithOthersChange.bind(this);
    }

    handleRadarTypeNameChangeEvent(event){
         this.props.selectedRadarType.name = event.target.value;
        this.forceUpdate();
    }

    handleSharedWithOthersChange(event){
        this.props.selectedRadarType.isPublished = this.refs.isPublished.checked;
        this.forceUpdate();
    }

    handleSaveRadarType(){
        if(this.props.selectedRadarType.id > 0){
            this.radarTypeRepository.updateRadarType(this.props.userId, this.props.selectedRadarType, this.handleEditChangeSuccess);
        }
        else{
            this.radarTypeRepository.addRadarType(this.props.userId, this.props.selectedRadarType, this.handleEditChangeSuccess);
        }
    }

    handleEditChangeSuccess(radarType){
        this.props.parentContainer.getByUserId(this.props.userId, this.handleGetByUserIdSuccess);
    }

    handleDeleteRadarType(){
        this.radarTypeRepository.deleteRadarType(this.props.userId, this.props.selectedRadarType, this.handleEditChangeSuccess);
    }

    render() {
        if(this.props.selectedRadarType !== undefined && this.props.selectedRadarType.name !== undefined){
            return (
                <div>
                    <div className='row'>
                        <div className="col-md-3">Name</div>
                        <div className="col-md-6">
                            <input type="text" value={this.props.selectedRadarType.name } ref={this.nameInput} onChange= {(event) => { this.handleRadarTypeNameChangeEvent(event) }} readOnly={this.props.readonly}/>
                        </div>
                        <div className="col-md-3">
                           <input type="button" className='btn btn-primary' disabled={this.props.readonly} value="Save" onClick={ this.handleSaveRadarType }/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">Share with others?</div>
                        <div className="col-md-6">
                            <input type="checkbox" ref="isPublished" checked={ this.props.selectedRadarType.isPublished } onChange = {(event) => this.handleSharedWithOthersChange(event) } readOnly={this.props.readonly}/>
                        </div>
                        <div className="col-md-3">
                           <input type="button" className='btn btn-primary' disabled={this.props.readonly} value="Delete" onClick={ this.handleDeleteRadarType }/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12">
                            <div className="panel panel-default">
                                <div className="panel-heading">Rings</div>
                                <div className="panel-body">
                                    {this.props.selectedRadarType.radarRings.map((currentRow) => {
                                        return <RadarRingDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} readonly={this.props.readonly}/>
                                        })}
                                </div>
                            </div>
                        </div>
                        <div className="col-md-12">
                            <div className="panel panel-default">
                                <div className="panel-heading">Categories</div>
                                <div className="panel-body">
                                    {this.props.selectedRadarType.radarCategories.map((currentRow) =>{
                                        return <RadarCategoryDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer } colorMap={this.state.radarCategoriesColorMap} colorNameMap={this.state.radarCategoriesColorNameMap}/>
                                        })}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
        else{
            return(<div></div>);
        }
    }
};


function mapStateToProps(state) {
  return {
    	selectedRadarType: state.radarTypeReducer.selectedRadarType
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeRadarTypes : radarTypes => { dispatch(addRadarTypesToState(radarTypes))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeEditor);