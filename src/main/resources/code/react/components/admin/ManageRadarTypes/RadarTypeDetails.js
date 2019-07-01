import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTypesToState } from '../../../../redux/reducers/admin/RadarTypeReducer';
import { addErrorsToState, addWarningsToState } from '../../../../redux/reducers/admin/ErrorReducer';
import RadarRingDetails from './RadarRingDetails';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository } from '../../../Repositories/RadarTypeRepository';
import Warning from '../Errors/Warning';
import { Error, Error_NoRadarRings } from '../Errors/Error';
import ErrorManager from '../Errors/ErrorManager';

class RadarTypeDetails extends React.Component{
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
        this.handleAddNewRing = this.handleAddNewRing.bind(this);
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
        if(this.props.selectedRadarType.radarRings===undefined || this.props.selectedRadarType.radarRings.length==0){
            var errorManager = new ErrorManager();
            errorManager.setErrors(this.props.errors);
            errorManager.addError(Error_NoRadarRings);
            this.props.setErrors(errorManager.errors);
            this.forceUpdate();
        }
        else{
            if(this.props.selectedRadarType.id > 0){
                this.radarTypeRepository.updateRadarType(this.props.currentUser.id, this.props.selectedRadarType, this.handleEditChangeSuccess);
            }
            else{
                this.radarTypeRepository.addRadarType(this.props.currentUser.id, this.props.selectedRadarType, this.handleEditChangeSuccess);
            }
        }
    }

    handleEditChangeSuccess(radarType){
        this.radarTypeRepository.getByUserId(this.props.currentUser.Id, false, this.handleGetByUserIdSuccess);
    }

    handleDeleteRadarType(){
        this.radarTypeRepository.deleteRadarType(this.props.currentUser.id, this.props.selectedRadarType, this.handleEditChangeSuccess);
    }

    handleAddNewRing(){
        var newRadarRing = {};
        newRadarRing.id = -1;
        newRadarRing.name = "New Radar Ring";
        newRadarRing.displayOption = this.props.selectedRadarType.radarRings.length;

        this.props.selectedRadarType.radarRings.push(newRadarRing);

        var warningManager = new WarmingManager();
        warningManager.setWarnings(this.props.warnings);
        warningManager.removeWarning(Warning_NoRadarRings);
        this.props.setWarnings(warningManager.warnings);

        alert(JSON.stringify(this.props.warnings));

        this.forceUpdate();
    }

    render() {
        if(this.props.selectedRadarType !== undefined && this.props.selectedRadarType.name !== undefined){
            return (
                <div>
                    <div className='row'>
                        <div className="col-md-3">Name</div>
                        <div className="col-md-4">
                            <input type="text" value={this.props.selectedRadarType.name } ref={this.nameInput} onChange= {(event) => { this.handleRadarTypeNameChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}/>
                        </div>
                        <div className="col-md-2">Version: { this.props.selectedRadarType.version }</div>
                        <div className={ this.props.editMode===true ? "col-md-3" : "hidden"}>
                           <input type="button" className='btn btn-primary' disabled={this.props.editMode!==true} value="Save" onClick={(event) => this.handleSaveRadarType(event) }/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">Share with others?</div>
                        <div className="col-md-4">
                            <input type="checkbox" ref="isPublished" checked={ this.props.selectedRadarType.isPublished } onChange = {(event) => this.handleSharedWithOthersChange(event) } readOnly={this.props.editMode ? '' : '"readonly"'}/>
                        </div>
                        <div className="col-md-2"></div>
                        <div className={ this.props.editMode===true ?  "col-md-3" : "hidden"}>
                           <input type="button" className='btn btn-primary' disabled={!this.props.editMode} value="Delete" onClick={(event) => this.handleDeleteRadarType(event) }/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12">
                            <div className="panel panel-default">
                                <div className="panel-heading">Rings</div>
                                <div className="panel-body">
                                    {this.props.selectedRadarType.radarRings.map((currentRow) => {
                                        return <RadarRingDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} radarTypeId={this.props.selectedRadarType.id} editMode={this.props.editMode}/>
                                        })}
                                     <div className={ this.props.editMode==true ?  "row" : "hidden"}>
                                        <div className="col-md-12">
                                            <input type="button" className="btn btn-primary" value="Add New Ring" disabled={this.props.selectedRadarType.radarRings.length > 7} onClick={(event) => this.handleAddNewRing(event)}/>
                                        </div>
                                     </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-12">
                            <div className="panel panel-default">
                                <div className="panel-heading">Categories</div>
                                <div className="panel-body">
                                    {this.props.selectedRadarType.radarCategories.map((currentRow) =>{
                                        return <RadarCategoryDetails key={currentRow.id} rowData={currentRow} userId={this.props.userId} parentContainer = { this.props.parentContainer } colorMap={this.state.radarCategoriesColorMap} colorNameMap={this.state.radarCategoriesColorNameMap} editMode={this.props.editMode}/>
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
    	selectedRadarType: state.radarTypeReducer.selectedRadarType,
        showHistory: state.radarTypeReducer.showHistory,
        showEdit: state.radarTypeReducer.showEdit,
        currentUser: state.radarTypeReducer.currentUser,
        errors: state.errorReducer.errors,
        warnings: state.errorReducer.warnings

    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeRadarTypes : radarTypes => { dispatch(addRadarTypesToState(radarTypes))},
        setErrors: errors => {dispatch(addErrorsToState(errors))},
        setWarnings: warnings => {dispatch(addWarningsToState(warnings))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeDetails);