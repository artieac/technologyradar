import React from 'react';
import PropTypes from 'prop-types'
import "./component.css"
import { Link } from 'react-router-dom';

const LinkActionPanelComponent = ({ title, description, linkTarget, buttonText}) => {
    return (
        <div className="panel panel-techradar adminMenuPanel">
            <div className="panel-heading-techradar">{ title }</div>
            <div className="panel-body">
                <p>{ description }</p>
                <p></p>
                <br/>
                <Link to={ linkTarget }>
                    <button className="btn btn-techradar">{ buttonText }</button>
                </Link>
            </div>
        </div>
    );
}

export default LinkActionPanelComponent;