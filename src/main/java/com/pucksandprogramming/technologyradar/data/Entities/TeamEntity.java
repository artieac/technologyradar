package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Teams")
public class TeamEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @OneToOne
    @JoinColumn(name = "OwnerId", nullable = false)
    private RadarUserEntity owner;

    @ManyToMany
    @JoinTable(
            name = "TeamMembers",
            joinColumns = { @JoinColumn(name = "TeamId") },
            inverseJoinColumns = { @JoinColumn(name = "MemberId") })
    private List<RadarUserEntity> teamMembers;

    public TeamEntity()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public RadarUserEntity getOwner() { return this.owner; }
    public void setOwner(RadarUserEntity value) { this.owner = value;}

    public List<RadarUserEntity> getTeamMembers() { return this.teamMembers;}
    public void setTeamMembers(List<RadarUserEntity> value) { this.teamMembers = value;}
}
