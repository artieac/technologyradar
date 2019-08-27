import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";

import RadarTemplateListItem from './RadarTemplateListItem';

class RadarTemplateList extends React.Component{
    constructor(props){
        super(props);
         this.state = {

        };
    }

    render() {
        if(this.props.radarTemplates !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className="row">
                            <div className="col-md-7">Name</div>
                            <div className="col-md-4">Action</div>
                        </div>
                        {
                            this.props.radarTemplates.map((currentRow, index) => {
                                return <RadarTemplateListItem key={index} rowNum={index} radarTemplate={currentRow} readonly={true} />
                            })
                        }
                    </div>
                </div>
            );
        }
        else{
            return (<div></div>);
        }
    }
};

function mapStateToProps(state) {
  return {
    	currentUser : state.userReducer.currentUser
    };
}

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarTemplateList);