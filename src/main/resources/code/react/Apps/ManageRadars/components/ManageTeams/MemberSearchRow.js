import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { TeamRepository } from '../../../../Repositories/TeamRepository';
import { addCurrentTeamToState } from '../../redux/TeamReducer';

class MemberSearchRow extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.teamRepository = new TeamRepository();

        this.handleRowClick = this.handleRowClick.bind(this);
    }

    componentDidMount(){}

    handleRowClick(event){
        this.props.currentTeam.members.push(this.props.rowData);
        this.props.storeCurrentTeam(this.props.currentTeam);
        this.props.listContainer.setShowSearchResults(false);

        this.props.listContainer.forceUpdate();
    }

    render() {
        if(this.props.rowData !== undefined && this.props.rowData.name !== undefined){
            return (
                <li className="list-group-item" onClick={(event) => { this.handleRowClick(event)}}>
                    <div className="row" >
                        <div className="col-md-6">
                            { this.props.rowData.name}
                        </div>
                        <div className="col-md-6">
                            {this.props.rowData.email}
                        </div>
                    </div>
                </li>
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

export default connect(mapStateToProps, mapDispatchToProps)(MemberSearchRow);