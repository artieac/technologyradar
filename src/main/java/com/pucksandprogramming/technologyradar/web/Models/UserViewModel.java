package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserRights;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;
import org.apache.catalina.User;

import javax.persistence.Column;

public class UserViewModel
{
    private Long id;
    private String email;
    private String name;
    private Role role;
    private Integer numberOfSharedRadars;
    private UserType userType;

    public static UserViewModel DefaultInstance()
    {
        UserViewModel retVal = new UserViewModel();
        retVal.setId(-1L);
        retVal.setEmail("");
        retVal.setName("");
        retVal.setCanSeeHistory(false);
        retVal.setCanSeeHistory(false);
        retVal.setHowManyRadarsCanShare(1);
        retVal.setNumberOfSharedRadar(0);
        retVal.setCanHaveVariableRadarRingCount(true);
        retVal.setRole(Role.createUserRole());
        retVal.setUserType(UserType.DefaultInstance());

        return retVal;
    }

    public UserViewModel()
    {

    }

    public UserViewModel(RadarUser source)
    {
        if(source!=null)
        {
            this.setId(source.getId());
            this.setEmail(source.getEmail());
            this.setName(source.getName());
            this.setRole(Role.createRole(source.getRoleId()));
            this.setUserType(source.getUserType());
            this.setCanSeeHistory(source.canSeeHistory());
            this.setCanShareRadarTypes(source.canShareRadarTypes());
            this.setHowManyRadarsCanShare(source.howManyRadarsCanShare());
            this.setCanHaveVariableRadarRingCount(source.canHaveVariableRadarRingCounts());
        }
    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

    public String getEmail() { return this.email;}
    public void setEmail(String value) { this.email = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public boolean getCanSeeHistory()
    {
        return this.userType.isGrantEnabled(UserRights.CanViewHistory);
    }
    public void setCanSeeHistory(boolean value) { }

    public boolean getCanShareRadarTypes()
    {
        return this.userType.isGrantEnabled(UserRights.CanShareRadarTypes);
    }
    public void setCanShareRadarTypes(boolean value) { }

    public Integer getHowManyRadarsCanShare()
    {
        return this.userType.getGrantValue(UserRights.CanShareNRadars);
    }
    public void setHowManyRadarsCanShare(Integer value) { }

    public Integer getNumberOfSharedRadars() { return this.numberOfSharedRadars;}
    public void setNumberOfSharedRadar(Integer value) { this.numberOfSharedRadars = value;}

    public boolean getCanHaveVariableRadarRingCount()
    {
        return this.userType.isGrantEnabled(UserRights.AllowVarableRadarRingCount);
    }
    public void setCanHaveVariableRadarRingCount(boolean value) { }

    public boolean getCanSeeFullView()
    {
        return this.userType.isGrantEnabled(UserRights.CanSeeFullView);
    }
    public void setCanSeeFullView(boolean value) { }

    public boolean getAllowTeamMembersToManageRadars() { return this.userType.isGrantEnabled(UserRights.AllowTeamMembersToManageRadars); }
    public void setAllowTeamMembersToManageRadars(boolean value) { }

    public Role getRole() { return this.role;}
    public void setRole(Role value) { this.role = value;}

    public UserType getUserType() { return this.userType;}
    public void setUserType(UserType value) { this.userType = value;}
}
