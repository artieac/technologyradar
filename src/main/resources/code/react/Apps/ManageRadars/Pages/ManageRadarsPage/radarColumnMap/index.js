import React from 'react';
import { Link } from 'react-router-dom';
import './component.css';

export const radarColumnMap = (handleIsPublishedClick, handleIsLockedClick, handleDeleteClick, currentUser) => {
  return [
    {
      title: 'Name',
      key: 'name',
      render: rowData => {
        return <span>{rowData.name}</span>;
      },
    },
    {
      title: 'Date',
      key: 'date',
      render: rowData => {
        return <span>{rowData.formattedAssessmentDate}</span>;
      },
    },
    {
      title: 'Type',
      key: 'type',
      render: rowData => {
        return <span>{rowData.radarTemplate.name}</span>;
      },
    },
    {
      title: 'Published?',
      key: 'published',
      render: rowData => {
        return <span><input type="checkbox" checked={ rowData.isPublished } onChange = {(event) => handleIsPublishedClick(event, rowData.id) }/></span>;
      },
    },
    {
      title: 'Locked?',
      key: 'locked',
      render: rowData => {
        return <span><input type="checkbox" checked={ rowData.isLocked } onChange = {(event) => handleIsLockedClick(event, rowData.id) }/></span>;
      },
    },
    {
        title: 'Actions',
        key: 'actions',
        render: rowData => {
            return (
                <span>
                    <a href={ "/home/secureradar/" + rowData.id}><img src="/images/action_add.PNG"/></a>
                    <img src="/images/action_delete.png" disabled={(rowData.isPublished==true) || (rowData.isLocked==true)} onClick = { handleDeleteClick }/>
                    <Link to={ "/manageradars/user/" + currentUser.id + "/radar/" + rowData.id + "/addfromprevious"}>
                        <img src="/images/arrow_right.png" disabled={(rowData.isPublished==true) || (rowData.isLocked==true)}/>
                    </Link>
                </span>
        );},
    },
  ];
};