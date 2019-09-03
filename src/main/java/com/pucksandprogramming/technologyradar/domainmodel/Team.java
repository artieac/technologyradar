package com.pucksandprogramming.technologyradar.domainmodel;

import com.pucksandprogramming.technologyradar.data.Entities.RadarUserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Team
{
    private Long id;
    private String name;
    private RadarUser owner;
    private List<RadarUser> members;
    private List<Radar> radars;

    public Team()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public RadarUser getOwner() { return this.owner; }
    public void setOwner(RadarUser value) { this.owner = value;}

    public List<RadarUser> getMembers()
    {
        if(this.members==null)
        {
            this.members = new ArrayList<>();
        }

        return this.members;
    }
    private void setMembers(List<RadarUser> value) { this.members = value;}

    public List<Radar> getRadars()
    {
        if(this.radars==null)
        {
            this.radars = new ArrayList<>();
        }
        return this.radars;
    }
    private void setRadars(List<Radar> value) { this.radars = value;}

    public void addMember(RadarUser newTeamMember)
    {
        if(newTeamMember.getId()!=this.getOwner().getId())
        {
            if (!this.getMembers().contains(newTeamMember))
            {
                this.getMembers().add(newTeamMember);
            }
        }
    }

    public void removeMember(RadarUser teamMemberToRemove)
    {
        if (this.getMembers().contains(teamMemberToRemove))
        {
            this.getMembers().remove(teamMemberToRemove);
        }
    }

    public void addRadar(Radar newRadar)
    {
        if(newRadar.getRadarUser().getId()==this.getOwner().getId())
        {
            if (!this.getRadars().contains(newRadar))
            {
                this.getRadars().add(newRadar);
            }
        }
    }

    public void removeRadar(Radar radarToRemove)
    {
        if (this.getRadars().contains(radarToRemove))
        {
            this.getRadars().remove(radarToRemove);
        }
    }

    @Override
    public boolean equals(Object testItem)
    {
        boolean retVal = false;

        // checking if both the object references are
        // referring to the same object.
        if(this == testItem)
        {
            retVal = true;
        }

        if(!retVal)
        {
            // it checks if the argument is of the
            // type Geek by comparing the classes
            // of the passed argument and this object.
            // if(!(obj instanceof Geek)) return false; ---> avoid.
            if (testItem != null && testItem.getClass() == this.getClass())
            {
                Team testTeam = (Team) testItem;

                if (testTeam.getId().compareTo(this.getId())==0)
                {
                    retVal = true;
                }
            }
        }

        return retVal;
    }
}
