import React from 'react';

export const colorMap = (handleDropdownSelection, radarCategoryItem) => {
  return [
    {
      key: 'dropdownItem',
      render: rowData => {
        return <a onClick ={(event) => handleDropdownSelection(event, rowData, radarCategoryItem)}>{ rowData.name }</a>;
      },
    },
  ];
};