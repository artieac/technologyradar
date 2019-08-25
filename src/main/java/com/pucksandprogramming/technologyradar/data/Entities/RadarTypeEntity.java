package com.pucksandprogramming.technologyradar.data.Entities;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RadarTypes")
@org.hibernate.annotations.NamedNativeQueries
(
    {
        @org.hibernate.annotations.NamedNativeQuery(name = "public_findSharedRadarTypes", query = "select * from RadarTypes rt where rt.State = 1 AND rt.IsPublished = true", resultClass = RadarTypeEntity.class),

// Owned Full History
        @org.hibernate.annotations.NamedNativeQuery(name = "owned_FindHistoryByRadarUserIdAndId", query = "SELECT * from RadarTypes rt WHERE rt.RadarUserId = :radarUserId AND rt.Id = :radarTypeId AND rt.State = 1 ORDER BY rt.Id, rt.CreateDate", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "owned_FindHistorySharedRadarTypesExcludeOwned", query = "select * from RadarTypes rt where rt.IsPublished = true AND rt.radarUserId <> :radarUserId AND rt.State = 1", resultClass = RadarTypeEntity.class),

// Common
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllAssociated", query = "select * from RadarTypes rt, AssociatedRadarTypes art where art.RadarTypeId = rt.Id and art.RadarUserId = :radarUserId", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllForPublishedRadars", query = "select * from RadarTypes rt where rt.Id IN(select distinct ta.RadarTypeId FROM TechnologyAssessments ta where ta.IsPublished = true)", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllForPublishedRadarsExcludeUser", query = "select * from RadarTypes rt where rt.Id IN(select distinct ta.RadarTypeId FROM TechnologyAssessments ta where ta.RadarUserId <> :radarUserId AND ta.IsPublished = true)", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllOwnedWithRadars", query = "select * from RadarTypes rt where rt.State = 1 AND rt.Id IN(select distinct ta.RadarTypeId FROM TechnologyAssessments ta where ta.RadarUserId = :radarUserId)", resultClass = RadarTypeEntity.class),

        @org.hibernate.annotations.NamedNativeQuery(name = "findByMaxId", query = "SELECT * FROM RadarTypes where Id = (select max(Id) from RadarTypes WHERE State =1);", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserAndId", query = "SELECT * from RadarTypes rt1 WHERE rt1.RadarUserId = :radarUserId AND rt1.Id = :radarTypeId and State=1", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findHistoryByRadarUserIdAndId", query = "SELECT * from RadarTypes rt WHERE rt.RadarUserId = :radarUserId AND rt.Id = :radarTypeId AND rt.State=1 ƒORDER BY rt.Id, rt.CreateDate", resultClass = RadarTypeEntity.class),
     }
)
public class RadarTypeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @OneToMany(mappedBy = "radarType", cascade = CascadeType.ALL)
    private List<RadarRingEntity> radarRings;

    @OneToMany(mappedBy = "radarType", cascade = CascadeType.ALL)
    private List<RadarCategoryEntity> radarCategories;

    @ManyToOne
    @JoinColumn(name = "RadarUserId", nullable=false)
    private RadarUserEntity radarUser;

    @Column(name = "IsPublished", nullable=false)
    private boolean isPublished;

    @Column(name= "CreateDate", nullable=false)
    private Calendar createDate;

    @Column(name="Description", nullable=true)
    private String description;

    @Column(name="State", nullable=true)
    private Integer state;

    public RadarTypeEntity()
    {
        this.radarRings = new ArrayList<>();
        this.radarCategories = new ArrayList<>();
    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public RadarUserEntity getRadarUser(){ return this.radarUser;}
    public void setRadarUser(RadarUserEntity value){ this.radarUser = value;}

    public List<RadarRingEntity> getRadarRings() { return this.radarRings;}
    public void setRadarRings(List<RadarRingEntity> value) { this.radarRings = value;}

    public List<RadarCategoryEntity> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarCategoryEntity> value) { this.radarCategories = value;}

    public boolean getIsPublished() { return this.isPublished;}
    public void setIsPublished(boolean value){ this.isPublished = value;}

    public Calendar getCreateDate() { return this.createDate;}
    public void setCreateDate(Calendar value){ this.createDate = value;}

    public String getDescription() { return this.description;}
    public void setDescription(String value) { this.description = value;}

    public Integer getState() { return this.state;}
    public void setState(Integer value) { this.state = value;}
}

