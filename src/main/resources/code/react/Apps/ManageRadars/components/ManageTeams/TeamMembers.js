import React from 'react';
import ReactDOM from 'react-dom';
import createReactClass from 'create-react-class';
import { connect } from "react-redux";
import { TeamRepository } from '../../../../Repositories/TeamRepository';
import { UserRepository } from '../../../../Repositories/UserRepository';
import TeamMemberRow from './TeamMemberRow';
import MemberSearchRow from './MemberSearchRow';

class TeamMembers extends React.Component{
    constructor(props){
        super(props);
         this.state = {
            searchString: "",
            searchResults: [],
            showSearchResults: false
        };

        this.teamRepository = new TeamRepository();
        this.userRepository = new UserRepository();

        this.handleNameChangeEvent = this.handleNameChangeEvent.bind(this);
        this.handleSaveClick = this.handleSaveClick.bind(this);
        this.handleSaveResponse = this.handleSaveResponse.bind(this);
        this.handleSearchChangeEvent = this.handleSearchChangeEvent.bind(this);
        this.handleSearchEvent = this.handleSearchEvent.bind(this);
        this.handleSearchResponse = this.handleSearchResponse.bind(this);
        this.setShowSearchResults = this.setShowSearchResults.bind(this);
    }

    handleNameChangeEvent(event) {
        this.props.currentTeam.name = event.target.value;
    }

    handleSaveClick(){
        this.teamRepository.saveMembers(this.props.currentUser.id, this.props.currentTeam, this.handleSaveResponse);
    }

    handleSaveResponse(team){
        this.props.detailsContainer.loadTeams(this.props.currentUser);
    }

    handleSearchChangeEvent(event){
        this.setState({searchString: event.target.value});
    }

    handleSearchEvent(event){
        this.userRepository.searchByEmail(this.state.searchString, this.handleSearchResponse);
    }

    handleSearchResponse(searchResults){
        var searchResultsArray = [];

        if(searchResults!==undefined)
        {
            if(searchResults.length !==undefined)
            {
                for(var i = 0; i < searchResults.length; i++)
                {
                    if(searchResults[i].id!==this.props.currentUser.id)
                    {
                       searchResultsArray.push(searchResults[i]);
                    }
                }
            }
            else
            {
                searchResultsArray.push(searchResults);
            }
        }

        this.setState({searchResults: searchResultsArray});
        this.setShowSearchResults(true);
    }

    setShowSearchResults(showResults){
        this.setState({showSearchResults: showResults});
    }

    render() {
        if(this.props.currentTeam !== undefined && this.props.currentTeam.name !== undefined){
            return (
                <div className="row">
                    <div className="col-md-12">
                        <div className="row">
                            <div className="col-md-12">
                                <span>
                                    <input type="text" onChange={(event) => { this.handleSearchChangeEvent(event)}}/>
                                    <input type="button" className='btn btn-techradar' onClick={(event) => { this.handleSearchEvent(event) }} value="Search"/>
                                </span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <div className='row'>
                                    <div className="col-md-3">Name</div>
                                    <div className="col-md-4">
                                        <input type="text" value={this.props.currentTeam.name } onChange= {(event) => { this.handleNameChangeEvent(event) }} readOnly={this.props.editMode ? '' : '"readonly"'}/>
                                    </div>
                                    <div className={ this.state.showSearchResults ? "row" : "hidden"}>
                                        <ul id="searchResults" className="list-group">
                                        {
                                            this.state.searchResults.map((currentRow, index) => {
                                                return <MemberSearchRow key={index} rowNum={index} rowData={currentRow} listContainer={this}/>
                                            })
                                        }
                                        </ul>
                                    </div>
                                    <div>
                                        <input type="button" className='btn btn-techradar' onClick={(event) => this.handleSaveClick(event) } value="Save Changes"/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-12">
                                        <div className="row">
                                            <div className="col-md-5">
                                                <label>Name</label>
                                            </div>
                                            <div className="col-md-4">
                                                <label>Email</label>
                                            </div>
                                            <div className="col-md-3">
                                                <label></label>
                                            </div>
                                        </div>
                                        {
                                            this.props.currentTeam.members.map((currentRow, index) => {
                                                return <TeamMemberRow key={index} rowNum={index} rowData={currentRow} listContainer={this} />
                                            })
                                        }
                                    </div>
                                </div>
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

