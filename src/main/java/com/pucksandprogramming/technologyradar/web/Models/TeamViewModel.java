package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.data.Entities.RadarUserEntity;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamViewModel
{
    private Long id;
    private String name;
    private UserViewModel owner;
    List<RadarViewModel> radars;
    List<UserViewModel> members;

    public TeamViewModel()
    {

    }

    public TeamViewModel(Team source)
    {
        this.initialize(source);
    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public UserViewModel getOwner() { return this.owner; }
    public void setOwner(UserViewModel value) { this.owner = value;}

    public List<RadarViewModel> getRadars() { return this.radars;}
    public void setRadars(List<RadarViewModel> value) { this.radars = value;}

    public List<UserViewModel> getMembers() { return this.members;}
    public void setMembers(List<UserViewModel> value) { this.members = value;}

    public void initialize(Team source)
    {
        this.setId(source.getId());
        this.setName(source.getName());
        this.setOwner(new UserViewModel(source.getOwner()));
        this.setRadars(new ArrayList<RadarViewModel>());

        if(source.getRadars()!= null)
        {
            for(Radar currentRadar : source.getRadars())
            {
                this.getRadars().add(new RadarViewModel(currentRadar));
            }
        }

        this.setMembers(new ArrayList<UserViewModel>());

        if(source.getTeamMembers()!=null)
        {
            for(RadarUser currentUser : source.getTeamMembers())
            {
                this.getMembers().add(new UserViewModel(currentUser));
            }
        }
    }
}
