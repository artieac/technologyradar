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


    handleColorOnSelect(event){
        this.props.rowData.displayOption = event;
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
                        <DropdownButton id="categoryColorSelection" title={this.getColorName(this.props.rowData.displayOption)} onSelect={(event) => this.handleColorOnSelect(event)}>
                          <Dropdown.Item as="button" eventKey={this.props.colorMap["Green"]}>Green</Dropdown.Item>
                          <Dropdown.Item as="button" eventKey={this.props.colorMap["Blue"]}>Blue</Dropdown.Item>
                          <Dropdown.Item as="button" eventKey={this.props.colorMap["Maroon"]}>Maroon</Dropdown.Item>
                          <Dropdown.Item as="button" eventKey={this.props.colorMap["Orange"]}>Orange</Dropdown.Item>
                        </DropdownButton>
                    </div>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};
