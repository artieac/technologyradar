package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;

import java.util.ArrayList;
import java.util.List;

public class PublishRadarModel
{
    boolean publishSucceeded;
    UserViewModel currentUser;
    List<Radar> radars;

    public PublishRadarModel()
    {
        this.currentUser = UserViewModel.DefaultInstance();
        this.radars = new ArrayList<>();
        this.publishSucceeded = false;
    }

    public boolean getPublishSucceeded() { return this.publishSucceeded;}
    public void setPublishSucceeded(boolean value) { this.publishSucceeded = value;}

    public UserViewModel getCurrentUser() { return this.currentUser;}
    public void setCurrentUser(UserViewModel value) { this.currentUser = value;}

    public List<Radar> getRadars() { return this.radars;}
    public void setRadars(List<Radar> value) { this.radars = value;}
}
