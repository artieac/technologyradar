import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTemplatesToState } from '../../../redux/RadarTemplateReducer';
import { RadarTemplateRepository } from '../../../../../Repositories/RadarTemplateRepository';
import { colorMapData } from '../../../components/colorMapData';
import TableComponent from '../../../../../components/TableComponent';
import { radarCategoryColumns } from './radarCategoryColumns';
import { radarRingColumns } from './radarRingColumns';

class ViewRadarTemplateControl extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };
    }

    render() {
        if(this.props.selectedRadarTemplate !== undefined && this.props.selectedRadarTemplate.name !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className='row'>
                            <div className="col-md-3">Name</div>
                            <div className="col-md-4">{this.props.selectedRadarTemplate.name }</div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <div className="panel panel-default">
                                    <div className="panel-heading">Rings</div>
                                    <div className="panel-body">
                                        <TableComponent data={this.props.selectedRadarTemplate.radarRings} cols={ radarRingColumns() }/>
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-12">
                                <div className="panel panel-default">
                                    <div className="panel-heading">Categories</div>
                                    <div className="panel-body">
                                        <TableComponent data = { this.props.selectedRadarTemplate.radarCategories } cols= { radarCategoryColumns() }/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
        else{
            return(<div></div>);
        }
    }
};


function mapStateToProps(state) {
  return {
    	selectedRadarTemplate: state.radarTemplateReducer.selectedRadarTemplate,
        showHistory: state.radarTemplateReducer.showHistory,
        showEdit: state.radarTemplateReducer.showEdit,
        currentUser: state.userReducer.currentUser

    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeRadarTemplates : radarTemplates => { dispatch(addRadarTemplatesToState(radarTemplates))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ViewRadarTemplateControl);

