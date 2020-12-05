import React from 'react';
import DropdownComponent from '../../../../../../../components/DropdownComponent'
import { colorMapData } from './colorMapData';
import { colorMap } from './colorMap';

export const radarCategoryMap = (editMode, handleNameChange, handleColorChange) => {
  return [
    {
      title: 'Name',
      key: 'name',
      render: rowData => {
        return <span> <input type="text" className={ editMode===true ? '' : 'readonly="readonly"'} defaultValue={ rowData.name } required="required"  onChange= {(event) => handleNameChange(event, rowData) }/></span>;
      },
    },
    {
        title: "Icon Color",
        key: "iconColor",
        render: rowData => {
            return <DropdownComponent title= "Green"  itemMap= { colorMap(this) } data={colorMapData()}/>
        },
    }
  ];
};