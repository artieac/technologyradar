import React from 'react';
import ReactDOM from 'react-dom';
import Reflux from 'reflux';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addAssociatedRadarTemplatesToState, addSharedRadarTemplatesToState, addSelectedRadarTemplateToState } from '../../redux/RadarTemplateReducer';
import { addCurrentUserToState} from '../../../redux/CommonUserReducer';
import RadarTemplateList from './RadarTemplateList';
import RadarTemplateDetails from './RadarTemplateDetails';
import { RadarTemplateRepository } from '../../../../Repositories/RadarTemplateRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';

class ManageAssociatedRadarTemplatesPage extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.radarTemplateRepository = new RadarTemplateRepository();
        this.userRepository = new UserRepository();

        this.handleGetOtherUsersSharedRadarTemplatesSuccess = this.handleGetOtherUsersSharedRadarTemplatesSuccess.bind(this);
        this.handleGetAssociatedRadarTemplatesSuccess = this.handleGetAssociatedRadarTemplatesSuccess.bind(this);
        this.handleGetCurrentUserSuccess = this.handleGetCurrentUserSuccess.bind(this);
    }

    componentDidMount(){
        var clearedRadarTemplate = {};
        this.props.storeSelectedRadarTemplate(clearedRadarTemplate);

        this.userRepository.getUser(this.handleGetCurrentUserSuccess);
    }

    handleGetCurrentUserSuccess(currentUser){
       this.props.storeCurrentUser(currentUser);
       this.radarTemplateRepository.getAssociatedRadarTemplates(currentUser.id, this.handleGetAssociatedRadarTemplatesSuccess);
    }

    handleGetAssociatedRadarTemplatesSuccess(associatedRadarTemplates){
        this.props.storeAssociatedRadarTemplates(associatedRadarTemplates);
        this.radarTemplateRepository.getOtherUsersSharedRadarTemplates(this.props.currentUser.id, this.handleGetOtherUsersSharedRadarTemplatesSuccess);
    }

    handleGetOtherUsersSharedRadarTemplatesSuccess(sharedRadarTemplates){
        for(var i = 0; i < this.props.associatedRadarTemplates.length; i++)
        {
            var associatedRadarTemplate = this.props.associatedRadarTemplates[i];
            var foundMatch = false;

            for(var j = 0; j < sharedRadarTemplates.length; j++)
            {
                if(associatedRadarTemplate.id == sharedRadarTemplates[j].id)
               {
                    foundMatch = true;
               }
            }

            if(foundMatch == false)
            {
                sharedRadarTemplates.push(associatedRadarTemplate);
            }
        }

        this.props.storeSharedRadarTemplates(sharedRadarTemplates);

        if(sharedRadarTemplates.length > 0){
            this.props.storeSelectedRadarTemplate(sharedRadarTemplates[0]);
        }

        this.forceUpdate();
    }

    render() {
        return (
            <div className="bodyContent">
                <div className="contentPageTitle">
                    <label>Associate Radar Templates From Others</label>
                </div>
                <p>Discover Radar Templates that others have created</p>
                <div className="row">
                    <div className="col-md-6">
                        <div className="row">
                            <div className="col-md-6">
                                <RadarTemplateList radarTemplates={this.props.sharedRadarTemplates} />
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <RadarTemplateDetails parentContainer={this} editMode={false}/>
                    </div>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
    	sharedRadarTemplates: state.radarTemplateReducer.sharedRadarTemplates,
    	associatedRadarTemplates: state.radarTemplateReducer.associatedRadarTemplates,
    	currentUser: state.userReducer.currentUser
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeSharedRadarTemplates : sharedRadarTemplates => { dispatch(addSharedRadarTemplatesToState(sharedRadarTemplates))},
        storeSelectedRadarTemplate : radarTemplate => { dispatch(addSelectedRadarTemplateToState(radarTemplate))},
        storeAssociatedRadarTemplates : associatedRadarTemplates => { dispatch(addAssociatedRadarTemplatesToState(associatedRadarTemplates))},
        storeCurrentUser : currentUser => { dispatch(addCurrentUserToState(currentUser))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageAssociatedRadarTemplatesPage);