import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import TableComponent from '../../../../../../components/TableComponent'
import { radarRingMap } from './radarRingMap';

class RadarRingsComponent extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            isDeleted: false
        };

        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleSortOrderChange = this.handleSortOrderChange.bind(this);
        this.handleOnDeleteClick = this.handleOnDeleteClick.bind(this);
    }

    handleNameChange(event, rowData){
        rowData.name = event.target.value;
    }

    handleSortOrderChange(event, rowData){
        rowData.displayOption = event.target.value;
    }

    handleOnDeleteClick(event, rowData){
        const { selectedRadarTemplate } = this.props;

        if(selectedRadarTemplate!==undefined){
            if(selectedRadarTemplate.radarRings!==undefined){
                for(var i = 0; i < selectedRadarTemplate.radarRings.length; i++){
                    if(selectedRadarTemplate.radarRings[i].id==rowData.id){
                        selectedRadarTemplate.radarRings.splice(i, 1);
                        storeSelectedRadarTemplate(selectedRadarTemplate);
                        this.setState({isDeleted : true});
                        break;
                    }
                }
            }
        }

        this.forceUpdate();
    }

    render() {
        const { editMode, selectedRadarTemplate } = this.props;

        return (
            <div className="row">
                <div className="col-md-12">
                    <div className="panel panel-default">
                        <div className="panel-heading">Rings</div>
                        <TableComponent data={ selectedRadarTemplate.radarRings } cols={radarRingMap(editMode, selectedRadarTemplate.id < 0, this.handleNameChange, this.handleSortOrderChange, this.handleDeleteClick)} isDark hoverable striped bordered={false} />
                    </div>
                </div>
            </div>
    );}
};


function mapStateToProps(state) {
  return {
    	selectedRadarTemplate: state.radarTemplateReducer.selectedRadarTemplate,
        currentUser: state.userReducer.currentUser
    };
};

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RadarRingsComponent);

