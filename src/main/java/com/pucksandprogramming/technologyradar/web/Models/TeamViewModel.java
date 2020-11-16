package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.Team;

public class TeamViewModel {
    private Long id;
    private String name;
    private UserViewModel owner;

    public TeamViewModel() {

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

    public void initialize(Team source) {
        this.setId(source.getId());
        this.setName(source.getName());
        this.setOwner(new UserViewModel(source.getOwner()));
    }
}
