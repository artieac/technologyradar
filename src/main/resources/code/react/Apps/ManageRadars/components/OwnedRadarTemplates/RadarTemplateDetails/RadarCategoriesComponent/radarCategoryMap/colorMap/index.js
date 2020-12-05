import React from 'react';

export const colorMap = (handleDropdownSelection) => {
  return [
    {
      key: 'dropdownItem',
      render: rowData => {
        return <a onClick ={(event) => handleDropdownSelection(event, rowData)}>{ rowData.name }</a>;
      },
    },
  ];
};