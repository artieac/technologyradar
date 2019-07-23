package com.pucksandprogramming.technologyradar.domainmodel;

import java.util.HashMap;
import java.util.Map;

public class UserType
{
    public static final int Free = 0;
    public static final int Subscriber_Trial = 1;
    public static final int Subscriber = 2;
    public static final int Team = 3;

    public static UserType createUser(Integer userType)
    {
        UserType retVal = UserType.GetFreeUser();

        switch(userType)
        {
            case UserType.Subscriber_Trial:
            case UserType.Subscriber:
                retVal = UserType.GetSubscribedUser();
                break;
            case UserType.Team:
                retVal = UserType.GetTeamUser();
                break;
            default:
                retVal = UserType.GetFreeUser();
        }

        return retVal;
    }

    public static UserType GetFreeUser()
    {
        UserType retVal = new UserType();
        retVal.id = UserType.Team;
        retVal.name = "Free";
        retVal.getGrantedRights().put(UserRights.CanShareNRadars, 1);
        retVal.getGrantedRights().put(UserRights.CanShareRadarTypes, 0);
        retVal.getGrantedRights().put(UserRights.CanViewHistory, 0);
        retVal.getGrantedRights().put(UserRights.AllowTeamMembersToManageRadar, 0);
        retVal.getGrantedRights().put(UserRights.AllowVarableRadarRingCount, 1);
        retVal.getGrantedRights().put(UserRights.CanSeeFullView, 0);
        return retVal;
    }

    public static UserType GetSubscribedUser()
    {
        UserType retVal = new UserType();
        retVal.id = UserType.Team;
        retVal.name = "Subscribed";
        retVal.getGrantedRights().put(UserRights.CanShareNRadars, Integer.MAX_VALUE);
        retVal.getGrantedRights().put(UserRights.CanShareRadarTypes, 1);
        retVal.getGrantedRights().put(UserRights.CanViewHistory, 1);
        retVal.getGrantedRights().put(UserRights.AllowTeamMembersToManageRadar, 0);
        retVal.getGrantedRights().put(UserRights.AllowVarableRadarRingCount, 1);
        retVal.getGrantedRights().put(UserRights.CanSeeFullView, 1);
        return retVal;
    }

    public static UserType GetTeamUser()
    {
        UserType retVal = new UserType();
        retVal.id = UserType.Team;
        retVal.name = "Team";
        retVal.getGrantedRights().put(UserRights.CanShareNRadars, Integer.MAX_VALUE);
        retVal.getGrantedRights().put(UserRights.CanShareRadarTypes, 1);
        retVal.getGrantedRights().put(UserRights.CanViewHistory, 1);
        retVal.getGrantedRights().put(UserRights.AllowTeamMembersToManageRadar, 1);
        retVal.getGrantedRights().put(UserRights.AllowVarableRadarRingCount, 1);
        retVal.getGrantedRights().put(UserRights.CanSeeFullView, 1);
        return retVal;
    }

    public Integer id;
    public String name;
    public HashMap<String, Integer> grantedRights;

    public UserType()
    {
        grantedRights = new HashMap<String, Integer>();
    }

    public HashMap<String, Integer> getGrantedRights()
    {
        return this.grantedRights;
    }

    public int getGrantValue(String grantType)
    {
        int retVal = 0;

        if(this.grantedRights.containsKey(grantType))
        {
            retVal = this.grantedRights.get(grantType);
        }

        return retVal;
    }

    public boolean isGrantEnabled(String grantType)
    {
        boolean retVal = false;

        if(this.grantedRights.containsKey(grantType))
        {
            if(this.grantedRights.get(grantType)==1)
            {
                retVal = true;
            }
        }

        return retVal;
    }
}
