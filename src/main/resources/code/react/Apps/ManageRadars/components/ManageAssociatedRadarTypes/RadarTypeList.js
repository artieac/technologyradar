import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";

import RadarTypeListItem from './RadarTypeListItem';

class RadarTypeList extends React.Component{
    constructor(props){
        super(props);
         this.state = {

        };
    }

    render() {
        if(this.props.radarTypes !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className="row">
                            <div className="col-md-7">Name</div>
                            <div className="col-md-4">Action</div>
                        </div>
                        {
                            this.props.radarTypes.map((currentRow, index) => {
                                return <RadarTypeListItem key={index} rowNum={index} radarType={currentRow} readonly={true} />
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

export default connect(mapStateToProps, mapDispatchToProps)(RadarTypeList);