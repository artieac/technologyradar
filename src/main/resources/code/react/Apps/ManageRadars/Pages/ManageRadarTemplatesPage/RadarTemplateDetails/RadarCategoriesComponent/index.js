import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import TableComponent from '../../../../../../components/TableComponent'
import { radarCategoryMap } from './radarCategoryMap';
import { colorMapData } from '../../../../components/colorMapData'

class RadarCategoriesComponent extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            isDeleted: false,
            selectedColor: {}
        };

        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleColorChange = this.handleColorChange.bind(this);
        this.handleOnDeleteClick = this.handleOnDeleteClick.bind(this);
    }

    handleNameChange(event, rowData){
        rowData.name = event.target.value;
    }

    handleColorChange(event, rowData, radarCategoryItem){
        const { selectedRadarTemplate } = this.props;

        if(selectedRadarTemplate!==undefined){
            if(selectedRadarTemplate.radarCategories!==undefined){
                for(var i = 0; i < selectedRadarTemplate.radarCategories.length; i++){
                    if(selectedRadarTemplate.radarCategories[i].id==radarCategoryItem.id){
                        selectedRadarTemplate.radarCategories[i].displayOption = rowData.value;
                        break;
                    }
                }
            }
        }

        this.forceUpdate();
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
        const { selectedColor } = this.state;

        return (
            <div className="row">
                <div className="col-md-12">
                    <div className="panel panel-default">
                        <div className="panel-heading">Categories</div>
                        <TableComponent data={ selectedRadarTemplate.radarCategories } cols={radarCategoryMap(editMode, this.handleNameChange, this.handleColorChange) } isDark hoverable striped={false} bordered={false} />
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

export default connect(mapStateToProps, mapDispatchToProps)(RadarCategoriesComponent);

