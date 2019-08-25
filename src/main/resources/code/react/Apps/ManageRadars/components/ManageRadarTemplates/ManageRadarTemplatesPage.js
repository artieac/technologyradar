import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSetListToState, addSelectedListItemToState } from '../../redux/SetManagementReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import RadarTemplateList from './RadarTemplateList';
import RadarTemplateDetails from './RadarTemplateDetails';
import NewRadarTemplateRow from './NewRadarTemplateRow';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';
import { RadarRingRepository } from '../../../../Repositories/RadarRingRepository';
import { RadarCategoryRepository } from '../../../../Repositories/RadarCategoryRepository';
import { addRadarRingSetsToState, addRadarCategorySetsToState } from './redux/RadarTemplateReducer';


class ManageRadarTemplatesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTemplateRepository = new RadarTemplateRepository();
        this.userRepository = new UserRepository();
        this.radarRingRepository = new RadarRingRepository();
        this.radarCategoryRepository = new RadarCategoryRepository();

        this.handleGetCurrentUserSuccess = this.handleGetCurrentUserSuccess.bind(this);
    }

    componentDidMount(){
        var clearedRadarTemplate = {};
        this.props.storeSelectedListItem(clearedRadarTemplate);

        this.userRepository.getUser(this.handleGetCurrentUserSuccess);
    }

    handleGetCurrentUserSuccess(currentUser){
       this.props.storeCurrentUser(currentUser);
       this.radarTemplateRepository.getByUserId(currentUser.id, this.props.storeSetList);
       this.radarRingRepository.getByUserId(currentUser.id, this.props.storeRadarRingSets);
       this.radarCategoryRepository.getByUserId(currentUser.id, this.props.storeRadarCategorySets);
    }

    canAddRadarTemplates() {
        var retVal = false;

        if(this.props.currentUser !== undefined)
        {
            if(this.props.setList !== undefined)
            {
                if(this.props.setList.length < this.props.currentUser.canHaveNRadarTypes)
                {
                    retVal = true;
                }
            }
        }
        return retVal;
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Manage Your Radar Templates</label>
                </div>
                <p>Add a new Radar Template for your radars.</p>
                <div className="row">
                    <div className="col-md-4">
                        <div className="row">
                            <div className={ this.canAddRadarTemplates()==true ? "col-md-6" : "col-md-6 hidden"}>
                               <NewRadarTemplateRow />
                            </div>
                           <div className={ this.canAddRadarTemplates()==false ? "col-md-6" : "col-md-6 hidden"}>
                               <div className="errorText">You are only allowed { this.props.currentUser.canHaveNRadarTypes } Radar Templates.  If you want a new one you need to delete one of your existing Radar Templates.</div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <RadarTemplateList radarTemplates={this.props.setList}/>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-8">
                        <RadarTemplateDetails  parentContainer={this} />
                    </div>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	setList: state.setManagementReducer.setList,
    	currentUser: state.userReducer.currentUser
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedListItem : selectedListItem => { dispatch(addSelectedListItemToState(selectedListItem))},
        storeSetList : radarCategories => { dispatch(addSetListToState(radarCategories))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))},
        storeRadarCategorySets: radarCategorySets => { dispatch(addRadarCategorySetsToState(radarCategorySets))},
        storeRadarRingSets: radarRingSets => { dispatch(addRadarRingSetsToState(radarRingSets))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageRadarTemplatesPage);