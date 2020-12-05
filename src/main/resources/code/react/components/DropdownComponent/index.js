import React from 'react';
import PropTypes from 'prop-types'
import "./component.css"

const DropdownComponent = ({ title, itemMap, data }) => {
    return (
        <div className="dropdown">
            <button className="btn btn-techradar dropdown-toggle" type="button" id="dropdownContainer" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                { title } <span className="caret"></span>
            </button>
            <ul className="dropdown-menu" aria-labelledby="dropdownContainer">
                 {data.map((item, index) => (
                    <li key={index}>
                        { itemMap.map((itemMapElement, key) => (
                            itemMapElement.render(item)
                        ))}
                     </li>
                 ))}
            </ul>
        </div>
    );
}

export default DropdownComponent;
