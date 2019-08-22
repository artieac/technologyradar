import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTypesToState } from '../../redux/RadarTypeReducer';
import RadarRingList from './RadarRingList';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTypeRepository } from '../../../../Repositories/RadarTypeRepository';

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

    render() {
        if(this.props.selectedRadarType !== undefined && this.props.selectedRadarType.name !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className='row'>
                            <div className="col-md-3">Name</div>
                            <div className="col-md-4">
                                <input type="text" value={this.props.selectedRadarType.name } ref={this.nameInput} onChange= {(event) => { this.handleRadarTypeNameChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}/>
                            </div>
                            <div className="col-md-2">Version: { this.props.selectedRadarType.version }</div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <div className="panel panel-default">
                                    <div className="panel-heading">Rings</div>
                                    <RadarRingList radarRings={this.props.selectedRadarType.radarRings} editMode={this.props.editMode}/>
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
        currentUser: state.userReducer.currentUser

    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeRadarTypes : radarTypes => { dispatch(addRadarTypesToState(radarTypes))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeDetails);

