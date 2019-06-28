package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "TechnologyAssessments")
@org.hibernate.annotations.NamedNativeQueries
(
    {
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUser", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.IsPublished = :isPublished)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserRadarId", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.Id = :radarId)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserRadarIdAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.Id = :radarId AND ta2.IsPublished = :isPublished)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserType", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.RadarTypeId = :radarTypeId)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllByUserTypeAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.RadarTypeId = :radarTypeId AND ta.IsPublished = :isPublished", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserTypeAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.RadarTypeId = :radarTypeId AND ta2.IsPublished = :isPublished)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserTypeAndVersion", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.RadarTypeId = :radarTypeId AND ta2.RadarTypeVersion = :radarTypeVersion)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllByUserTypeVersionAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.RadarTypeId = :radarTypeId AND ta.RadarTypeVersion = :radarTypeVersion AND ta.IsPublished = :isPublished", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserTypeVersionAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.RadarTypeId = :radarTypeId AND ta2.RadarTypeVersion = :radarTypeVersion AND ta2.IsPublished = :isPublished)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllNotOwnedByTechnologyIdAndIsPublished", query = "SELECT ta.Id as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.RadarTypeId as RadarTypeId, MAX(ta.RadarTypeVersion) as RadarTypeVersion, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId <> :radarUserId AND ta.IsPublished = true AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId) GROUP BY Id, RadarTypeVersion", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByTechnologyIdAndIsPublished", query = "SELECT ta.Id as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.RadarTypeId as RadarTypeId, MAX(ta.RadarTypeVersion) as RadarTypeVersion, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.IsPublished = true AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId) GROUP BY Id, RadarTypeVersion", resultClass = RadarInstanceEntity.class),


        @org.hibernate.annotations.NamedNativeQuery(name = "findByTechnologyIdAndIsPublished", query = "SELECT * FROM TechnologyAssessments ta WHERE ta.IsPublished = :isPublished AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId)", resultClass = RadarInstanceEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllOwnedByTechnologyIdAndIsPublished", query = "SELECT * FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId  AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId)", resultClass = RadarInstanceEntity.class),
    }
)
public class RadarInstanceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @Column(name = "AssessmentDate", nullable = false)
    private Date assessmentDate;

    @OneToOne
    @JoinColumn(name = "RadarUserId", nullable = false)
    private RadarUserEntity radarUser;

    @OneToMany(mappedBy = "radarInstance")
    private List<RadarItemEntity> radarItems;

    @Column(name = "IsPublished", nullable = false)
    private boolean isPublished;

    @Column(name = "IsLocked", nullable = false)
    private boolean isLocked;

    @OneToOne
    @JoinColumns
    ({
            @JoinColumn(name="RadarTypeId", referencedColumnName="Id"),
            @JoinColumn(name="RadarTypeVersion", referencedColumnName="Version")
    })
    private RadarTypeEntity radarType;

    public RadarInstanceEntity()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public Date getAssessmentDate() { return this.assessmentDate;}
    public void setAssessmentDate(Date value) { this.assessmentDate = value;}

    public RadarUserEntity getRadarUser() { return this.radarUser;}
    public void setRadarUser(RadarUserEntity value) { this.radarUser = value;}

    public List<RadarItemEntity> getRadarItems() { return this.radarItems;}
    public void setRadarItems(List<RadarItemEntity> value) { this.radarItems = value;}

    public boolean getIsPublished(){ return this.isPublished;}
    public void setIsPublished(boolean value){ this.isPublished = value;}

    public boolean getIsLocked(){ return this.isLocked;}
    public void setIsLocked(boolean value){ this.isLocked = value;}

    public RadarTypeEntity getRadarType() { return this.radarType;}
    public void setRadarType(RadarTypeEntity value) { this.radarType = value;}
}
