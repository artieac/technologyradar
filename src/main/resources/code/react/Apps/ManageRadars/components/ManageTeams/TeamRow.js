import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { addCurrentTeamToState, setShowTeamMembers, setShowTeamRadars } from '../../redux/TeamReducer';

class TeamRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.handleShowTeamMembersClick = this.handleShowTeamMembersClick.bind(this);
        this.handleShowTeamRadarsClick = this.handleShowTeamRadarsClick.bind(this);
    }

    componentDidMount(){}

    handleShowTeamMembersClick(event){
        this.props.storeCurrentTeam(this.props.rowData);
        this.props.storeShowTeamMembers(true);
    }

    handleShowTeamRadarsClick(event){
        this.props.storeCurrentTeam(this.props.rowData);
        this.props.storeShowTeamRadars(true);
    }

    render() {
        return (
            <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                <div className="col-md-6">
                    { this.props.rowData.name}
                </div>
                <div className="col-md-3">
                    <button className="btn btn-techradar" onClick={(event) => { this.handleShowTeamMembersClick(event)}}>Members</button>
                </div>
                <div className="col-md-3">
                    <button className="btn btn-techradar"onClick={(event) => { this.handleShowTeamRadarsClick(event)}}>Radars</button>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser,
        showTeamMembers: state.teamReducer.showTeamMembers,
        showTeamRadars: state.teamReducer.showTeamRadars
    };
}

const mapDispatchToProps = dispatch => {
  return {
        storeCurrentTeam: currentTeam => { dispatch(addCurrentTeamToState(currentTeam))},
        storeShowTeamMembers: showTeamMembers => { dispatch(setShowTeamMembers(showTeamMembers))},
        storeShowTeamRadars: showTeamRadars => { dispatch(setShowTeamRadars(showTeamRadars))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(TeamRow);