package com.pucksandprogramming.technologyradar.data.Entities;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Teams")
@org.hibernate.annotations.NamedNativeQueries
(
    {
        @org.hibernate.annotations.NamedNativeQuery(name = "findByMemberId", query = "SELECT * From Teams t INNER JOIN TeamMembers tm on t.Id = tm.TeamId WHERE tm.MemberId = :memberId", resultClass = TeamEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findByRadarIdAndMemberId", query = "SELECT * From Teams t INNER JOIN TeamMembers tm on t.Id = tm.TeamId INNER JOIN TeamRadars tr on t.Id = tr.Id WHERE tm.MemberId = :memberId and tr.RadarId = :radarId", resultClass = TeamEntity.class)
    }
)
public class TeamEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private List<RadarUserEntity> members;

    @ManyToMany
    @JoinTable(
            name = "TeamRadars",
            joinColumns = { @JoinColumn(name = "TeamId") },
            inverseJoinColumns = { @JoinColumn(name = "RadarId") })
    private List<RadarEntity> radars;

    public TeamEntity()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public RadarUserEntity getOwner() { return this.owner; }
    public void setOwner(RadarUserEntity value) { this.owner = value;}

    public List<RadarUserEntity> getMembers() { return this.members;}
    public void setMembers(List<RadarUserEntity> value) { this.members = value;}

    public List<RadarEntity> getRadars() { return this.radars;}
    public void setRadars(List<RadarEntity> value) { this.radars = value;}
}
