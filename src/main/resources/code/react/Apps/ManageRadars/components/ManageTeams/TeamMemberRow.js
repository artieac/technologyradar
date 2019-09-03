import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { TeamRepository } from '../../../../Repositories/TeamRepository';
import { addCurrentTeamToState } from '../../redux/TeamReducer';

class TeamMemberRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.teamRepository = new TeamRepository();

        this.handleRemoveClick = this.handleRemoveClick.bind(this);
        this.handleUpdateResponse = this.handleUpdateResponse.bind(this);
    }

    componentDidMount(){}

    handleRemoveClick(event){
        for(var i = this.props.currentTeam.members.length - 1; i > -1; i--)
        {
            if(this.props.currentTeam.members[i].id===this.props.rowData.id){
                this.props.currentTeam.members.splice(i, 1);
                this.props.storeCurrentTeam(this.props.currentTeam);
                this.props.listContainer.forceUpdate();
                break;
            }
        }
    }

    handleUpdateResponse(team){
        this.props.storeCurrentTeam(team);
    }

    render() {
        if(this.props.rowData !== undefined && this.props.rowData.name !== undefined){
            return (
                <div className={ this.props.rowNum % 2 > 0 ? "row alternatingRow" : "row"}>
                    <div className="col-md-5">
                        { this.props.rowData.name}
                    </div>
                    <div className="col-md-4">
                        {this.props.rowData.email}
                    </div>
                    <div className="col-md-3">
                        <input type="button" className='btn btn-techradar' onClick={(event) => this.handleRemoveClick(event) } value="Remove"/>
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

export default connect(mapStateToProps, mapDispatchToProps)(TeamMemberRow);