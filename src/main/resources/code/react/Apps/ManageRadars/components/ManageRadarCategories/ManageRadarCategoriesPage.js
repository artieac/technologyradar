import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSetListToState, addSelectedListItemToState } from '../../redux/SetManagementReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import RadarCategorySetList from './RadarCategorySetList';
import RadarCategorySetDetails from './RadarCategorySetDetails';
import NewRadarCategorySetRow from './NewRadarCategorySetRow';
import { RadarCategoryRepository } from '../../../../Repositories/RadarCategoryRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';

class ManageRadarCategoriesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarCategoryRepository = new RadarCategoryRepository();
        this.userRepository = new UserRepository();

        this.handleGetCurrentUserSuccess = this.handleGetCurrentUserSuccess.bind(this);
    }

    componentDidMount(){
        var clearedRadarCategorySet = {};
        this.props.storeSelectedListItem(clearedRadarCategorySet);

        this.userRepository.getUser(this.handleGetCurrentUserSuccess);
    }

    handleGetCurrentUserSuccess(currentUser){
       this.props.storeCurrentUser(currentUser);
       this.radarCategoryRepository.getByUserId(currentUser.id, this.props.storeSetList);
    }

    canAddRadarCategorySets() {
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
                    <label>Manage Your Radar Categories</label>
                </div>
                <p>Add a new Radar Category to allow for a different rating method</p>
                <div className="row">
                    <div className="col-md-4">
                        <div className="row">
                            <div className={ this.canAddRadarCategorySets()==true ? "col-md-6" : "col-md-6 hidden"}>
                               <NewRadarCategorySetRow />
                            </div>
                           <div className={ this.canAddRadarCategorySets()==false ? "col-md-6" : "col-md-6 hidden"}>
                               <div className="errorText">You are only allowed { this.props.currentUser.canHaveNRadarTypes } Radar Categories.  If you want a new one you need to delete one of your existing Radar Categories.</div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <RadarCategorySetList radarCategories={this.props.setList}/>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-8">
                        <RadarCategorySetDetails  parentContainer={this} />
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
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageRadarCategoriesPage);