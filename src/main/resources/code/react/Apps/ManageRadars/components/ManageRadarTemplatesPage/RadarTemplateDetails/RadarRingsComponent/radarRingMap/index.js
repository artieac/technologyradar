import React from 'react';
import { Link } from 'react-router-dom';

export const radarRingMap = (editMode, canAddOrDelete, handleNameChange, handleSortOrderChange, handleDeleteClick) => {
  return [
    {
      title: 'Name',
      key: 'name',
      render: rowData => {
        return <span><input type="text" className={ editMode ? '' : 'readonly="readonly"'}  defaultValue={ rowData.name } onChange= {(event) => { handleNameChange(event, rowData) }}/></span>;
      },
    },
    {
        title: 'Sort Order',
        key: 'sortOrder',
        render: rowData => {
            return <span><input type="text" className={ editMode ?  '' : 'readonly="readonly"'} defaultValue={ rowData.displayOption } onChange= {(event) => { handleSortOrderChange(event, rowData) }} maxLength="2" size="2"/></span>;
        },
    },
    {
        title: 'Actions',
        key: 'actions',
        render: rowData => {
            return <span className={ editMode && canAddOrDelete ?  "col-md-2" : "hidden"}><img src="/images/action_delete.png" onClick = {(event) => handleDeleteClick(event, rowData) }/></span>
        },
     },
  ];
};