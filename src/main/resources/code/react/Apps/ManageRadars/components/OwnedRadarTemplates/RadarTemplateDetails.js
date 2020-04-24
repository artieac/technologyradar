import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTemplatesToState, addSelectedRadarTemplateToState } from '../../redux/RadarTemplateReducer';
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
        this.descriptionInput = React.createRef();

        this.handleRadarTemplateNameChangeEvent = this.handleRadarTemplateNameChangeEvent.bind(this);
        this.handleSaveRadarTemplate = this.handleSaveRadarTemplate.bind(this);
        this.handleEditChangeSuccess = this.handleEditChangeSuccess.bind(this);
        this.handleSharedWithOthersChange = this.handleSharedWithOthersChange.bind(this);
        this.handleRadarTemplateDescriptionChangeEvent = this.handleRadarTemplateDescriptionChangeEvent.bind(this);
    }

    handleRadarTemplateNameChangeEvent(event){
         this.props.selectedRadarTemplate.name = event.target.value;
        this.forceUpdate();
    }

    handleRadarTemplateDescriptionChangeEvent(event){
         this.props.selectedRadarTemplate.description = event.target.value;
        this.forceUpdate();
    }

    handleSharedWithOthersChange(event){
        this.props.selectedRadarTemplate.isPublished = this.refs.isPublished.checked;
        this.forceUpdate();
    }

    handleSaveRadarTemplate(){
        if(this.props.selectedRadarTemplate.radarRings===undefined || this.props.selectedRadarTemplate.radarRings.length==0){
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
        this.props.storeSelectedRadarTemplate(radarTemplate);
        this.radarTemplateRepository.getMostRecentByUserId(this.props.currentUser.id, this.props.storeRadarTemplates);
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
                            <div className={this.props.editMode===true ? "col-md-3" : "hidden"}>
                               <input type="button" className='btn btn-techradar' disabled={this.props.editMode!==true} value="Save" onClick={(event) => this.handleSaveRadarTemplate(event) }/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">Description</div>
                            <div className="col-md-6">
                                <div className="form-group">
                                      <textarea className="form-control rounded-0" rows="3" value={this.props.selectedRadarTemplate.description } ref={this.descriptionInput} onChange= {(event) => { this.handleRadarTemplateDescriptionChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}></textarea>
                                  </div>
                            </div>
                        </div>
                        <div className={ this.props.editMode===true ? "row" : "hidden"}>
                            <div className="col-md-3">Share with others?</div>
                            <div className="col-md-4">
                                <input type="checkbox" ref="isPublished" checked={ this.props.selectedRadarTemplate.isPublished } onChange = {(event) => this.handleSharedWithOthersChange(event) } readOnly={this.props.editMode ? '' : '"readonly"'}/>
                            </div>
                            <div className="col-md-5"></div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <div className="panel panel-default">
                                    <div className="panel-heading">Rings</div>
                                    <RadarRingList radarRings={this.props.selectedRadarTemplate.radarRings} editMode={this.props.editMode} canAddOrDelete={ this.props.selectedRadarTemplate.id < 0}/>
                                </div>
                            </div>
                            <div className="col-md-12">
                                <div className="panel panel-default">
                                    <div className="panel-heading">Categories</div>
                                    <div className="panel-body">
                                        <div className="row">
                                            <div className="col-md-6"><b>Name</b></div>
                                            <div className="col-md-2"><b>Icon Color</b></div>
                                        </div>
                                        <div className="row"></div>
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
        showEdit: state.radarTemplateReducer.showEdit,
        currentUser: state.userReducer.currentUser

    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarTemplate : radarTemplate => { dispatch(addSelectedRadarTemplateToState(radarTemplate))},
        storeRadarTemplates : radarTemplates => { dispatch(addRadarTemplatesToState(radarTemplates))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTemplateDetails);

