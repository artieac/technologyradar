package com.pucksandprogramming.technologyradar.domainmodel;

import java.util.HashMap;

public class UserType
{
    public static final Integer Free = 1;
    public static final Integer Subscriber_Trial = 2;
    public static final Integer Subscriber = 3;
    public static final Integer Team = 4;
    public static final Integer VIPSubscriber = 5;

    public static UserType DefaultInstance()
    {
        UserType retVal = new UserType();
        retVal.setId(UserType.Free);
        retVal.setName("Free");
        return retVal;
    }

    public static HashMap<String, Integer> GetFreeUserRights()
    {
        HashMap<String, Integer> retVal = new HashMap<String, Integer>();
        retVal.put(UserRights.CanShareNRadars, 1);
        retVal.put(UserRights.CanShareRadarTemplates, 0);
        retVal.put(UserRights.AllowNRadarTemplates, 2);
        retVal.put(UserRights.AllowNAssociatedRadarTemplates, 2);
        retVal.put(UserRights.AllowTeamMembersToManageRadars, 0);
        retVal.put(UserRights.AllowVariableRadarRingCount, 1);
        retVal.put(UserRights.CanSeeFullView, 0);
        return retVal;
    }

    public static HashMap<String, Integer> GetSubscribedUserRights()
    {
        HashMap<String, Integer> retVal = new HashMap<>();
        retVal.put(UserRights.CanShareNRadars, 10);
        retVal.put(UserRights.CanShareRadarTemplates, 1);
        retVal.put(UserRights.AllowNRadarTemplates, 10);
        retVal.put(UserRights.AllowNAssociatedRadarTemplates, 10);
        retVal.put(UserRights.AllowTeamMembersToManageRadars, 0);
        retVal.put(UserRights.AllowVariableRadarRingCount, 1);
        retVal.put(UserRights.CanSeeFullView, 1);

        return retVal;
    }

    public static HashMap<String, Integer> GetVIPSubscriberRights()
    {
        HashMap<String, Integer> retVal = new HashMap<>();
        retVal.put(UserRights.CanShareNRadars, Integer.MAX_VALUE);
        retVal.put(UserRights.CanShareRadarTemplates, 1);
        retVal.put(UserRights.AllowNRadarTemplates, Integer.MAX_VALUE);
        retVal.put(UserRights.AllowNAssociatedRadarTemplates, Integer.MAX_VALUE);
        retVal.put(UserRights.AllowTeamMembersToManageRadars, 0);
        retVal.put(UserRights.AllowVariableRadarRingCount, 1);
        retVal.put(UserRights.CanSeeFullView, 1);

        return retVal;
    }

    public static HashMap<String, Integer> GetTeamUserRights()
    {
        HashMap<String, Integer> retVal = new HashMap<String, Integer>();
        retVal.put(UserRights.CanShareNRadars, Integer.MAX_VALUE);
        retVal.put(UserRights.CanShareRadarTemplates, 1);
        retVal.put(UserRights.AllowNRadarTemplates, 10);
        retVal.put(UserRights.AllowNAssociatedRadarTemplates, 10);
        retVal.put(UserRights.AllowTeamMembersToManageRadars, 1);
        retVal.put(UserRights.AllowVariableRadarRingCount, 1);
        retVal.put(UserRights.CanSeeFullView, 1);
        return retVal;
    }

    private Integer id;
    private String name;
    public HashMap<String, Integer> grantedRights;

    public Integer getId() { return this.id;}
    public void setId(Integer value)
    {
        this.id = value;

        if (this.id == UserType.Subscriber_Trial ||
            this.id == UserType.Subscriber)
        {
            this.grantedRights = UserType.GetSubscribedUserRights();
        }
        else if (this.id == UserType.Team)
        {
            this.grantedRights = UserType.GetTeamUserRights();
        }
        else if(this.id == UserType.VIPSubscriber)
        {
            this.grantedRights = UserType.GetVIPSubscriberRights();
        }
        else
        {
            this.grantedRights = UserType.GetFreeUserRights();
        }
    }

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public UserType()
    {
        grantedRights = new HashMap<String, Integer>();
    }

    public HashMap<String, Integer> getGrantedRights() { return this.grantedRights; }

    public int getGrantValue(String grantType)
    {
        int retVal = 0;

        if(this.getGrantedRights().containsKey(grantType))
        {
            retVal = this.getGrantedRights().get(grantType);
        }

        return retVal;
    }

    public boolean isGrantEnabled(String grantType)
    {
        boolean retVal = false;

        if(this.getGrantedRights().containsKey(grantType))
        {
            if(this.getGrantedRights().get(grantType)==1)
            {
                retVal = true;
            }
        }

        return retVal;
    }
}
