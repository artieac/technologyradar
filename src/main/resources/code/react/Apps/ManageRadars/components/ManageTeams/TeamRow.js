import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';

class TeamRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };
    }

    componentDidMount(){}

    getTeamMembersLink(userId, teamId){
        return '/manageradars/user/' + userId + '/team/' + teamId + '/members';
    }

    getTeamRadarsLink(userId, teamId){
        return '/manageradars/user/' + userId + '/team/' + teamId + '/radars';
    }

    render() {
        return (
            <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                <div className="col-md-6">{ this.props.rowData.name} </div>
                <div className="col-md-3">
                    <Link to={ this.getTeamMembersLink(this.props.currentUser.id, this.props.rowData.id)}>
                        <button className="btn btn-techradar">Members</button>
                    </Link>
                </div>
                <div className="col-md-3">
                    <Link to={ this.getTeamRadarsLink(this.props.currentUser.id, this.props.rowData.id)}>
                        <button className="btn btn-techradar">Radars</button>
                    </Link>
                </div>
            </div>
        );
    }
};

function mapStateToProps(state) {
  return {
        currentUser: state.userReducer.currentUser
    };
}

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(TeamRow);