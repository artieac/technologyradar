import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { Dropdown, DropdownButton } from 'react-bootstrap';

export class RadarCategoryDetails extends React.Component{
    constructor(props){
        super(props);
        this.state = {
        };

        this.handleColorOnSelect = this.handleColorOnSelect.bind(this);
        this.getColorName = this.getColorName.bind(this);
        this.handleTypeDetailsNameChange = this.handleTypeDetailsNameChange.bind(this);
    }


    handleColorOnSelect(event, colorValue){
        this.props.rowData.displayOption = colorValue;
        this.forceUpdate();
    }

    getColorName(colorValue){
        return this.props.colorNameMap[colorValue];
    }

    handleTypeDetailsNameChange(event){
        this.props.rowData.name = event.target.value;
    }

    render(){
        if(this.props.rowData!==undefined){
            return(
                <div className="row">
                    <div className="col-md-6">
                        <input type="text" className={this.props.editMode===true ? '' : 'readonly="readonly"'} ref="typeDetailsName" defaultValue={this.props.rowData.name} required="required"  onChange= {(event) => { this.handleTypeDetailsNameChange(event) }}/>
                    </div>
                    <div className={this.props.editMode===true ? 'col-md-2' : 'hidden col-lg-3'} >
                        <button class="btn btn-techradar dropdown-toggle" type="button" id="categoryColorDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            { this.getColorName(this.props.rowData.displayOption)}
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="categoryColorDropdown">
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
