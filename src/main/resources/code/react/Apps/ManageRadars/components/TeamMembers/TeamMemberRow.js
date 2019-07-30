import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";

class TeamMemberRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.teamRepository = new TeamRepository();

        this.handleUserClick = this.handleUserClick.bind(this);
    }

    componentDidMount(){}

    handleUserClick(event){
        this.teamRepository.removeMember(this.props.currentUser.id, this.props.teamId, this.props.rowData.id);
    }

    render() {
        return (
            <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                <div className="col-md-6">{ this.props.rowData.name} </div>
                <div className="col-md-3">
                    <input type="button" className="btn btn-techRadar" value="Remove" onClick={ (event) => { this.handleRemoveClick(event) }} />
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

export default connect(mapStateToProps, mapDispatchToProps)(TeamMemberRow);