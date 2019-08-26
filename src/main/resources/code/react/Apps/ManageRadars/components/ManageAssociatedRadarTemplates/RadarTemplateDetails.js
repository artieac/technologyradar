import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTemplatesToState } from '../../redux/RadarTemplateReducer';
import RadarRingList from './RadarRingList';
import { RadarCategoryDetails } from './RadarCategoryDetails';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';

class RadarTemplateDetails extends React.Component{
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

        this.radarTemplateRepository = new RadarTemplateRepository();
        this.nameInput = React.createRef();

        this.handleRadarTemplateNameChangeEvent = this.handleRadarTemplateNameChangeEvent.bind(this);
        this.handleSaveRadarTemplate = this.handleSaveRadarTemplate.bind(this);
        this.handleEditChangeSuccess = this.handleEditChangeSuccess.bind(this);
        this.handleSharedWithOthersChange = this.handleSharedWithOthersChange.bind(this);
    }

    handleRadarTemplateNameChangeEvent(event){
         this.props.selectedRadarTemplate.name = event.target.value;
        this.forceUpdate();
    }

    handleSharedWithOthersChange(event){
        this.props.selectedRadarTemplate.isPublished = this.refs.isPublished.checked;
        this.forceUpdate();
    }

    handleSaveRadarTemplate(){
        if(this.props.selectedRadarTemplate.radarRings===undefined || this.props.selectedRadarTemplate.radarRings.length==0){
            var errorManager = new ErrorManager();
            errorManager.setErrors(this.props.errors);
            errorManager.addError(Error_NoRadarRings);
            this.props.setErrors(errorManager.errors);
            this.forceUpdate();
        }
        else{
            if(this.props.selectedRadarTemplate.id > 0){
                this.radarTemplateRepository.updateRadarTemplate(this.props.currentUser.id, this.props.selectedRadarTemplate, this.handleEditChangeSuccess);
            }
            else{
                this.radarTemplateRepository.addRadarTemplate(this.props.currentUser.id, this.props.selectedRadarTemplate, this.handleEditChangeSuccess);
            }
        }
    }

    handleEditChangeSuccess(radarTemplate){
        this.radarTemplateRepository.getByUserId(this.props.currentUser.Id, false, this.handleGetByUserIdSuccess);
    }

    render() {
        if(this.props.selectedRadarTemplate !== undefined && this.props.selectedRadarTemplate.name !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className='row'>
                            <div className="col-md-3">Name</div>
                            <div className="col-md-4">
                                <input type="text" value={this.props.selectedRadarTemplate.name } ref={this.nameInput} onChange= {(event) => { this.handleRadarTemplateNameChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <div className="panel panel-default">
                                    <div className="panel-heading">Rings</div>
                                    <RadarRingList radarRings={this.props.selectedRadarTemplate.radarRings} editMode={this.props.editMode}/>
                                </div>
                            </div>
                            <div className="col-md-12">
                                <div className="panel panel-default">
                                    <div className="panel-heading">Categories</div>
                                    <div className="panel-body">
                                        {this.props.selectedRadarTemplate.radarCategories.map((currentRow) =>{
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
    	selectedRadarTemplate: state.radarTemplateReducer.selectedRadarTemplate,
        showHistory: state.radarTemplateReducer.showHistory,
        showEdit: state.radarTemplateReducer.showEdit,
        currentUser: state.userReducer.currentUser

    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeRadarTemplates : radarTemplates => { dispatch(addRadarTemplatesToState(radarTemplates))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTemplateDetails);

