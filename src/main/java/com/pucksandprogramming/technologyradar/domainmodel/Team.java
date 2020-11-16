package com.pucksandprogramming.technologyradar.domainmodel;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Long id;
    private String name;
    private RadarUser owner;
    private List<RadarUser> teamMembers;
    private List<Radar> radars;

    public Team() {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public RadarUser getOwner() { return this.owner; }
    public void setOwner(RadarUser value) { this.owner = value;}

    public List<RadarUser> getTeamMembers() {
        if(this.teamMembers==null) {
            this.teamMembers = new ArrayList<>();
        }

        return this.teamMembers;
    }
    private void setTeamMembers(List<RadarUser> value) { this.teamMembers = value;}

    public List<Radar> getRadars() {
        if(this.radars==null) {
            this.radars = new ArrayList<>();
        }
        return this.radars;
    }
    private void setRadars(List<Radar> value) { this.radars = value;}

    public void addTeamMember(RadarUser newTeamMember) {
        if(newTeamMember.getId()!=this.getOwner().getId()) {
            if (!this.getTeamMembers().contains(newTeamMember)) {
                this.getTeamMembers().add(newTeamMember);
            }
        }
    }

    public void removeTeamMember(RadarUser teamMemberToRemove) {
        if (!this.getTeamMembers().contains(teamMemberToRemove)) {
            this.getTeamMembers().remove(teamMemberToRemove);
        }
    }

    public void addRadar(Radar newRadar) {
        if(newRadar.getRadarUser().getId()==this.getOwner().getId()) {
            if (!this.getRadars().contains(newRadar)) {
                this.getRadars().add(newRadar);
            }
        }
    }

    public void removeRadar(Radar radarToRemove) {
        if (!this.getRadars().contains(radarToRemove)) {
            this.getRadars().remove(radarToRemove);
        }
    }
}
