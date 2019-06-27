package com.pucksandprogramming.technologyradar.web.Models;

import org.apache.catalina.User;

import javax.persistence.Column;

public class UserViewModel
{
    private Long id;
    private String email;
    private String name;
    private boolean canSeeHistory;

    public static UserViewModel DefaultInstance()
    {
        UserViewModel retVal = new UserViewModel();
        retVal.setId(-1L);
        retVal.setEmail("");
        retVal.setName("");
        retVal.setCanSeeHistory(false);

        return retVal;
    }

    public UserViewModel()
    {

    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

    public String getEmail() { return this.email;}
    public void setEmail(String value) { this.email = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public boolean getCanSeeHistory() { return this.canSeeHistory;}
    public void setCanSeeHistory(boolean value) { this.canSeeHistory = value;}
}
