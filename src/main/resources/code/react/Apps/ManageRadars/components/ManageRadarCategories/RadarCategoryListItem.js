import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSelectedListItemToState, addSetListToState } from '../../redux/SetManagementReducer';
import { RadarRingRepository } from '../../../../Repositories/RadarRingRepository';

class RadarRingListItem extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            isDeleted: false
        };

        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleColorOnSelect = this.handleColorOnSelect.bind(this);
        this.getColorName = this.getColorName.bind(this);
    }

    handleNameChange(event){
        this.props.rowData.name = event.target.value;
    }

    handleColorOnSelect(event, colorValue){
        this.props.rowData.displayOption = colorValue;
        this.forceUpdate();
    }

    getColorName(colorValue){
        return this.props.colorNameMap[colorValue];
    }

    render(){
        if(this.props.rowData!==undefined){
            return(
                <div className={this.state.isDeleted ? "hidden row" : "row"}>
                    <div className="col-md-6">
                        <input type="text" defaultValue={this.props.rowData.name} onChange= {(event) => { this.handleNameChange(event) }}/>
                    </div>
                    <div className='col-md-2' >
                        <button className="btn btn-techradar dropdown-toggle" type="button" id="categoryColorDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            { this.getColorName(this.props.rowData.displayOption)}
                            <span className="caret"></span>
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="categoryColorDropdown">
                            <li><a onClick={(event) => this.handleColorOnSelect(event, this.props.colorMap["Green"])}>Green</a></li>
                            <li><a onClick={(event) => this.handleColorOnSelect(event, this.props.colorMap["Blue"])}>Blue</a></li>
                            <li><a onClick={(event) => this.handleColorOnSelect(event, this.props.colorMap["Maroon"])}>Maroon</a></li>
                            <li><a onClick={(event) => this.handleColorOnSelect(event, this.props.colorMap["Orange"])}>Orange</a></li>
                        </ul>
                    </div>
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
    	setList: state.setManagementReducer.RadarCategorySets,
        selectedListItem : state.setManagementReducer.selectedListItem
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedListItem : selectedListItem => { dispatch(addSelectedListItemToState(selectedListItem))},
        storeSetList : setList => { dispatch(addSetListToState(setList))},
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingListItem);

