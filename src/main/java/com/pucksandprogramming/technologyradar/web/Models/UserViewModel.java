package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;
import org.apache.catalina.User;

import javax.persistence.Column;

public class UserViewModel
{
    private Long id;
    private String email;
    private String name;
    private Role role;
    private Integer userType;
    private boolean canSeeHistory;
    private boolean canShareRadarTypes;
    private boolean canHaveVariableRadarRingCount;
    private Integer howManyRadarsCanShare;
    private Integer numberOfSharedRadars;

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
        retVal.setUserType(UserType.Free);

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

    public boolean getCanSeeHistory() { return this.canSeeHistory;}
    public void setCanSeeHistory(boolean value) { this.canSeeHistory = value;}

    public boolean getCanShareRadarTypes() { return this.canShareRadarTypes;}
    public void setCanShareRadarTypes(boolean value) { this.canShareRadarTypes = value;}

    public Integer getHowManyRadarsCanShare() { return this.howManyRadarsCanShare;}
    public void setHowManyRadarsCanShare(Integer value) { this.howManyRadarsCanShare = value;}

    public Integer getNumberOfSharedRadars() { return this.numberOfSharedRadars;}
    public void setNumberOfSharedRadar(Integer value) { this.numberOfSharedRadars = value;}

    public boolean getCanHaveVariableRadarRingCount() { return this.canHaveVariableRadarRingCount;}
    public void setCanHaveVariableRadarRingCount(boolean value) { this.canHaveVariableRadarRingCount = value;}

    public Role getRole() { return this.role;}
    public void setRole(Role value) { this.role = value;}

    public Integer getUserType() { return this.userType;}
    public void setUserType(Integer value) { this.userType = value;}
}
