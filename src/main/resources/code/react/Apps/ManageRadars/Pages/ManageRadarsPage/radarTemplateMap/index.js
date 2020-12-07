import React from 'react';

export const radarTemplateMap = (handleDropdownSelection) => {
  return [
    {
      key: 'dropdownItem',
      render: rowData => {
        return <a className="dropdown-item" onClick = {(event) => handleDropdownSelection(rowData) } title={ rowData.description }>{ rowData.name }</a>;
      },
    },
  ];
};