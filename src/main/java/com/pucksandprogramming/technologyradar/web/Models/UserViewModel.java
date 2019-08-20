package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserRights;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;

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
        retVal.setNumberOfSharedRadar(0);
        retVal.setCanHaveVariableRadarRingCount(true);
        retVal.setRole(Role.createUserRole());
        retVal.setCanVersionRadarTypes(false);
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

    public Integer getCanHaveNRadarTypes()
    {
        return this.userType.getGrantValue(UserRights.AllowNRadarTypes);
    }
    public void setCanHaveNRadarTypes(Integer value) { }

    public Integer getCanHaveNAssociatedRadarTypes() { return this.userType.getGrantValue(UserRights.AllowNAssociatedRadarTypes); }
    public void setCanHaveNAssociatedRadarTypes(Integer value) { }

    public boolean getCanVersionRadarTypes() { return this.userType.isGrantEnabled(UserRights.CanVersionRadarTypes);}
    public void setCanVersionRadarTypes(boolean value) { }

    public boolean getCanShareRadarTypes(){ return this.userType.isGrantEnabled(UserRights.CanShareRadarTypes); }
    public void setCanShareRadarTypes(boolean value) { }

    public Integer getHowManyRadarsCanShare()
    {
        return this.userType.getGrantValue(UserRights.CanShareNRadars);
    }
    public void setHowManyRadarsCanShare(Integer value) { }

    public Integer getNumberOfSharedRadars() { return this.numberOfSharedRadars;}
    public void setNumberOfSharedRadar(Integer value) { this.numberOfSharedRadars = value;}

    public boolean getCanHaveVariableRadarRingCount() { return this.userType.isGrantEnabled(UserRights.AllowVariableRadarRingCount); }
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
