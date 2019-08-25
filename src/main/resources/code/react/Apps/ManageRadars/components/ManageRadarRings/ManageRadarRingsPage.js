import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addSetListToState, addSelectedListItemToState } from '../../redux/SetManagementReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import RadarRingSetList from './RadarRingSetList';
import RadarRingSetDetails from './RadarRingSetDetails';
import NewRadarRingSetRow from './NewRadarRingSetRow';
import { RadarRingRepository } from '../../../../Repositories/RadarRingRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';

class ManageRadarRingsPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarRingRepository = new RadarRingRepository();
        this.userRepository = new UserRepository();

        this.handleGetCurrentUserSuccess = this.handleGetCurrentUserSuccess.bind(this);
    }

    componentDidMount(){
        var clearedRadarRingSet = {};
        this.props.storeSelectedListItem(clearedRadarRingSet);

        this.userRepository.getUser(this.handleGetCurrentUserSuccess);
    }

    handleGetCurrentUserSuccess(currentUser){
       this.props.storeCurrentUser(currentUser);
       this.radarRingRepository.getByUserId(currentUser.id, this.props.storeSetList);
    }

    canAddRadarRings() {
        var retVal = false;

        if(this.props.currentUser !== undefined)
        {
            if(this.props.radarRingSets !== undefined)
            {
                if(this.props.radarRingSets.length < this.props.currentUser.canHaveNRadarTypes)
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
                    <label>Manage Your Radar Rings</label>
                </div>
                <p>Add a new Radar Ring to allow for a different rating method</p>
                <div className="row">
                    <div className="col-md-4">
                        <div className="row">
                            <div className={ this.canAddRadarRings()==true ? "col-md-6" : "col-md-6 hidden"}>
                               <NewRadarRingSetRow />
                            </div>
                           <div className={ this.canAddRadarRings()==false ? "col-md-6" : "col-md-6 hidden"}>
                               <div className="errorText">You are only allowed { this.props.currentUser.canHaveNRadarTypes } Radar Rings.  If you want a new one you need to delete one of your existing Radar Rings.</div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <RadarRingSetList radarRingSets={this.props.setList}/>
                            </div>
                        </div>
                    </div>
                    <div className={ this.props.showEdit==true ? "col-md-8" : "hidden"}>
                        <RadarRingSetDetails  parentContainer={this} />
                    </div>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	setList: state.setManagementReducer.setList,
    	currentUser: state.userReducer.currentUser,
    	showEdit: state.setManagementReducer.showEdit
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedListItem : selectedListItem => { dispatch(addSelectedListItemToState(selectedListItem))},
        storeSetList : radarCategories => { dispatch(addSetListToState(radarCategories))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageRadarRingsPage);