import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";

import RadarRingSetListItem from './RadarRingSetListItem';

class RadarRingSetList extends React.Component{
    constructor(props){
        super(props);
         this.state = {

        };
    }

    render() {
        if(this.props.radarRingSets !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className="row">
                            <div className="col-md-7">Name</div>
                            <div className="col-md-4">Action</div>
                        </div>
                        {
                            this.props.radarRingSets.map((currentRow, index) => {
                                return <RadarRingSetListItem key={index} rowNum={index} radarRingSet={currentRow} readonly={true} />
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
    };
}

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingSetList);