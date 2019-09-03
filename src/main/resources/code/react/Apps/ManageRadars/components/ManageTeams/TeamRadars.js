import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { TeamRepository } from '../../../../Repositories/TeamRepository';
import { RadarRepository } from '../../../../Repositories/RadarRepository';
import TeamRadarRow from './TeamRadarRow';

class TeamRadars extends React.Component{
    constructor(props){
        super(props);
         this.state = {
        };

        this.teamRepository = new TeamRepository();

        this.handleNameChangeEvent = this.handleNameChangeEvent.bind(this);
    }

    componentDidMount(){
    }

    handleNameChangeEvent(event) {
        this.props.currentTeam.name = event.target.value;
    }

    handleSaveClick(){
        this.teamRepository.saveRadars(this.props.currentUser.id, this.props.currentTeam, this.handleSaveResponse);
    }

    handleSaveResponse(team){
        this.props.detailsContainer.loadTeams(this.props.currentUser);
    }

    render() {
        if(this.props.currentTeam !== undefined && this.props.currentTeam.name !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className='row'>
                            <div className="col-md-3">Name</div>
                            <div className="col-md-4">
                                <input type="text" value={this.props.currentTeam.name } onChange= {(event) => { this.handleNameChangeEvent(event) }} />
                            </div>
                            <div>
                                <input type="button" className='btn btn-techradar' onClick={(event) => this.handleSaveClick(event) } value="Save Changes"/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <div className="row">
                                    <div className="col-md-9">
                                        <label>Name</label>
                                    </div>
                                    <div className="col-md-3">
                                        <label>Allow Access</label>
                                    </div>
                                </div>
                                {
                                    this.props.userRadars.map((currentRow, index) => {
                                        return <TeamRadarRow key={index} rowNum={index} rowData={currentRow} listContainer={this} />
                                    })
                                }
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
        currentTeam: state.teamReducer.currentTeam,
        userRadars: state.radarReducer.radars
    };
};

const mapDispatchToProps = dispatch => {
  return {
        storeUserRadars: radars => { dispatch(addRadarsToState(radars))}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(TeamRadars);