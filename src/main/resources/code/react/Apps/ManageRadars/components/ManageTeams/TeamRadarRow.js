import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { TeamRepository } from '../../../../Repositories/TeamRepository';
import { addCurrentTeamToState } from '../../redux/TeamReducer';

class TeamRadarRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.teamRepository = new TeamRepository();

        this.handleCheckboxClick = this.handleCheckboxClick.bind(this);
        this.doesTeamHaveAccess = this.doesTeamHaveAccess.bind(this);
        this.handleUpdateRadarResponse = this.handleUpdateRadarResponse.bind(this);
    }

    componentDidMount(){}

    handleCheckboxClick(event){
        var allowAccess = this.refs.allowAccess.checked;
        this.teamRepository.updateTeamRadar(this.props.currentUser.id, this.props.currentTeam.id, this.props.rowData.id, allowAccess, this.HandleUpdateRadarResponse);
    }

    handleUpdateRadarResponse(team){
        this.props.storeCurrentTeam(team);
    }

    doesTeamHaveAccess(){
        var retVal = false;

        if(this.props.currentTeam!==undefined && this.props.currentTeam.id !==undefined)
        {
            for(var i = 0; i < this.props.currentTeam.radars.length; i++)
            {
                if(this.props.currentTeam.radars[i].id===this.rowData.id)
                {
                    retVal = true;
                }
            }
        }
        return retVal;
    }

    render() {
        if(this.props.rowData !== undefined && this.props.rowData.name !== undefined){
            return (
                <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                    <div className="col-md-10">
                        { this.props.rowData.name} - { this.props.rowData.formattedAssessmentDate}
                    </div>
                    <div className="col-md-2">
                        <span><input type="checkbox" checked={this.doesTeamHaveAccess()}  ref="allowAccess" onChange = {(event) => this.handleCheckboxClick(event) }/></span>
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
        currentUser: state.userReducer.currentUser,
        currentTeam: state.teamReducer.currentTeam
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeCurrentTeam: currentTeam => { dispatch(addCurrentTeamToState(currentTeam))},
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(TeamRadarRow);