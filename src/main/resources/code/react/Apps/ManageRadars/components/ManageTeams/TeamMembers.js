import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { TeamRepository } from '../../../../Repositories/TeamRepository';

class TeamMembers extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.teamRepository = new TeamRepository();

        this.handleNameChangeEvent = this.handleNameChangeEvent.bind(this);
    }

    handleNameChangeEvent(event) {
        this.props.currentTeam.name = event.target.value;
    }

    render() {
        if(this.props.currentTeam !== undefined && this.props.currentTeam.name !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className='row'>
                            <div className="col-md-3">Name</div>
                            <div className="col-md-4">
                                <input type="text" value={this.props.currentTeam.name } onChange= {(event) => { this.handleNameChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">

                            </div>
                        </div>
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
};

const mapDispatchToProps = dispatch => {
  return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(TeamMembers);

