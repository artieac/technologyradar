import React from 'react';
import DropdownComponent from '../../../../../../../components/DropdownComponent'
import { colorMapData } from '../../../../../components/colorMapData'
import { colorMap } from './colorMap';

export const radarCategoryMap = (editMode, handleNameChange, handleColorSelectionChange) => {
    const colorMapLookup = (colorValue) => {
        if(colorValue !== undefined){
            for(var i = 0; i < colorMapData().length; i++){
                if(colorMapData()[i].value===colorValue.displayOption){
                    return colorMapData()[i];
                }
            }
        }
        return colorMapData()[0];
    };

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
            return <DropdownComponent title= { colorMapLookup(rowData).name } itemMap= { colorMap(handleColorSelectionChange, rowData) } data={colorMapData()}/>
        },
    }
  ];
};