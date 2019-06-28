package com.pucksandprogramming.technologyradar.data.Entities;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RadarTypes")
@org.hibernate.annotations.NamedNativeQueries
(
    {
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllMostRecentByUser", query = "SELECT * from RadarTypes rt1 WHERE rt1.RadarUserId = :radarUserId AND rt1.Version =  (SELECT max(rt2.Version) FROM RadarTypes rt2 WHERE rt2.id = rt1.id)", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserAndId", query = "SELECT * from RadarTypes rt1 WHERE rt1.RadarUserId = :radarUserId AND rt1.Id = :radarTypeId AND rt1.Version =  (SELECT max(rt2.Version) FROM RadarTypes rt2 WHERE rt2.id = rt1.id)", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findHistoryByRadarUserIdAndId", query = "SELECT * from RadarTypes rt WHERE rt.RadarUserId = :radarUserId AND rt.Id = :radarTypeId ORDER BY rt.Id, rt.Version", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentTypesByPublishedRadars", query = "SELECT * from RadarTypes rt WHERE rt.Version = (SELECT MAX(ta.RadarTypeVersion) FROM TechnologyAssessments ta WHERE ta.RadarTypeId = rt.Id AND ta.RadarUserId = :radarUserId AND ta.IsPublished = true)", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllTypesByPublishedRadars", query = "SELECT * from RadarTypes rt WHERE (rt.Id, rt.Version) IN (SELECT ta.RadarTypeId, ta.RadarTypeVersion FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.IsPublished = true)", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllForPublishedRadars", query = "select * from RadarTypes rt where (rt.Id, rt.Version) IN (select distinct(ta.RadarTypeId), MAX(ta.RadarTypeVersion) FROM TechnologyAssessments ta where ta.IsPublished = true GROUP BY ta.RadarTypeId)", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllForPublishedRadarsExcludeUser", query = "select * from RadarTypes rt where rt.id IN (select distinct(RadarTypeId) FROM TechnologyAssessments ta where ta.RadarUserId = :radarUserId AND ta.IsPublished = true)", resultClass = RadarTypeEntity.class),

        @org.hibernate.annotations.NamedNativeQuery(name = "findAllAssociated", query = "select * from RadarTypes rt, AssociatedRadarTypes art where art.RadarTypeId = rt.Id AND art.RadarTypeVersion = rt.Version and art.RadarUserId = :radarUserId", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllSharedRadarTypesExcludeOwned", query = "select * from RadarTypes rt where rt.IsPublished = true AND rt.radarUserId <> :radarUserId", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findOthersRadarTypes", query = "select * from RadarTypes rt where rt.IsPublished = true AND rt.Id IN (SELECT art.RadarTypeId FROM AssociatedRadarTypes art WHERE art.RadarUserId <> :radarUserId)", resultClass = RadarTypeEntity.class),

        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentVersionByRadarType", query = "SELECT * from RadarTypes rt1 WHERE rt1.Id = :radarTypeId AND rt1.Version =  (SELECT max(rt2.Version) FROM RadarTypes rt2 WHERE rt2.id = rt1.id))", resultClass = RadarTypeEntity.class)
     }
)
public class RadarTypeEntity
{
    @EmbeddedId
    VersionedIdEntity versionedId;

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


    public RadarTypeEntity()
    {
        VersionedIdEntity newVersionedIdEntity = new VersionedIdEntity(-1L,-1L);
        this.setVersionedId(newVersionedIdEntity);
    }

    public VersionedIdEntity getVersionedId(){ return this.versionedId;}
    public void setVersionedId(VersionedIdEntity value){ this.versionedId = value;}

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

}

