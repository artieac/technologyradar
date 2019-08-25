import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { Error_NoRadarRings } from '../Errors/Error';
import RadarCategoryListItem from './RadarCategoryListItem';
import { addSetListToState, addSelectedListItemToState } from '../../redux/SetManagementReducer';
import { RadarCategoryRepository } from '../../../../Repositories/RadarCategoryRepository';

class RadarCategorySetDetails extends React.Component{
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

        this.radarCategoryRepository = new RadarCategoryRepository();

        this.handleSaveClickEvent = this.handleSaveClickEvent.bind(this);
        this.handleNameChangeEvent = this.handleNameChangeEvent.bind(this);
        this.handleDescriptionChangeEvent = this.handleDescriptionChangeEvent.bind(this);
        this.handleEditChangeSuccess = this.handleEditChangeSuccess.bind(this);
    }

    componentDidMount(){
    }

    handleNameChangeEvent(event){
        this.props.selectedListItem.name = event.target.value;
        this.forceUpdate();
    }

    handleDescriptionChangeEvent(event){
         this.props.selectedListItem.description = event.target.value;
        this.forceUpdate();
    }

    handleSaveClickEvent(){
        if(this.props.selectedListItem.radarCategories===undefined || this.props.selectedListItem.radarCategories.length==0){
            this.forceUpdate();
        }
        else{
            if(this.props.selectedListItem.id > 0){
                this.radarCategoryRepository.update(this.props.currentUser.id, this.props.selectedListItem, this.handleEditChangeSuccess);
            }
            else{
                this.radarCategoryRepository.add(this.props.currentUser.id, this.props.selectedListItem, this.handleEditChangeSuccess);
            }
        }
    }

    handleEditChangeSuccess(listItem){
        this.props.storeSelectedListItem(listItem);
        this.radarCategoryRepository.getByUserId(this.props.currentUser.id, this.props.storeSetList);
        this.forceUpdate();
    }

    render(){
        if(this.props.selectedListItem!==undefined && this.props.selectedListItem.radarCategories!==undefined && this.props.selectedListItem.radarCategories.length > 0){
            return(
                <div className="panel-body">
                    <div className="row">
                        <div className="col-md-6">
                            <input type="text" value={ this.props.selectedListItem.name }  onChange= {(event) => { this.handleNameChangeEvent(event) }}/>
                        </div>
                        <div className="col-md-3">
                           <input type="button" className='btn btn-techradar' value="Save" onClick={(event) => this.handleSaveClickEvent(event) }/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-9">
                            <textarea className="form-control rounded-0" rows="3" value={this.props.selectedListItem.description } onChange= {(event) => { this.handleDescriptionChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}></textarea>
                        </div>
                    </div>
                    {this.props.selectedListItem.radarCategories.map((currentRow) => {
                        return <RadarCategoryListItem key={currentRow.id} rowData={currentRow} editMode={this.props.editMode} listContainer={this} colorMap={this.state.radarCategoriesColorMap} colorNameMap={this.state.radarCategoriesColorNameMap}/>
                        })}
                </div>
            );
        }
        else{
            return (
                <div className="panel-body">
                </div>);
        }
    }
};

function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser,
     	setList: state.setManagementReducer.setList,
         selectedListItem : state.setManagementReducer.selectedListItem
   };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedListItem : selectedListItem => { dispatch(addSelectedListItemToState(selectedListItem))},
        storeSetList : setList => { dispatch(addSetListToState(setList))},
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarCategorySetDetails);