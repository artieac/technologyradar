import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addRadarTemplatesToState, addSelectedRadarTemplateToState, addTargetUserToState } from '../../redux/RadarTemplateReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import RadarTemplateList from './RadarTemplateList';
import RadarTemplateDetails from './RadarTemplateDetails';
import NewRadarTemplateRow from './NewRadarTemplateRow';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';

class OwnedRadarTemplatesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTemplateRepository = new RadarTemplateRepository();
        this.userRepository = new UserRepository();

        this.handleGetCurrentUserSuccess = this.handleGetCurrentUserSuccess.bind(this);
    }

    componentDidMount(){
        var clearedRadarTemplate = {};
        this.props.storeSelectedRadarTemplate(clearedRadarTemplate);

        this.userRepository.getUser(this.handleGetCurrentUserSuccess);
    }

    handleGetCurrentUserSuccess(currentUser){
       this.props.storeCurrentUser(currentUser);
       this.radarTemplateRepository.getMostRecentByUserId(currentUser.id, this.props.storeRadarTemplates);
    }

    canAddRadarTemplates() {
        var retVal = false;

        if(this.props.currentUser !== undefined)
        {
            if(this.props.radarTemplates !== undefined)
            {
                if(this.props.radarTemplates.length < this.props.currentUser.canHaveNRadarTemplates)
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
                <p>Add a new Template to rate different types of things</p>
                <div className="row">
                    <div className="col-md-4">
                        <div className="row">
                            <div className={ this.canAddRadarTemplates()==true ? "col-md-6" : "col-md-6 hidden"}>
                               <NewRadarTemplateRow />
                            </div>
                           <div className={ this.canAddRadarTemplates()==false ? "col-md-6" : "col-md-6 hidden"}>
                               <div className="errorText">You are only allowed { this.props.currentUser.canHaveNRadarTemplates } Radar Templates.  If you want a new one you need to delete one of your existing Radar Tempalates.</div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <RadarTemplateList radarTemplates={this.props.radarTemplates}/>
                            </div>
                        </div>
                    </div>
                    <div className={ this.props.showEdit==true ? "col-md-8" : "hidden"}>
                        <RadarTemplateDetails  parentContainer={this} editMode={ true }/>
                    </div>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	radarTemplates: state.radarTemplateReducer.radarTemplates,
    	currentUser: state.userReducer.currentUser,
    	showEdit: state.radarTemplateReducer.showEdit
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeSelectedRadarTemplate : radarTemplate => { dispatch(addSelectedRadarTemplateToState(radarTemplate))},
        storeRadarTemplates : radarTemplates => { dispatch(addRadarTemplatesToState(radarTemplates))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))},
        storeTargetUser : targetUser => { dispatch(addTargetUserToState(targetUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(OwnedRadarTemplatesPage);