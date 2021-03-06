package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
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
// Public
        @org.hibernate.annotations.NamedNativeQuery(name = "public_FindByUserAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.IsPublished = :isPublished", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "public_FindByUserTypeAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.RadarTemplateId = :radarTemplateId AND ta.IsPublished = :isPublished", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "public_FindByUserAndType", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.RadarTemplateId = :radarTemplateId AND ta.IsPublished = :isPublished", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "public_FindByTechnologyIdRadarUserAndIsPublished", query = "SELECT ta.Id as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.IsPublished = true AND ta.RadarUserId = :radarUserId AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId) GROUP BY Id", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "public_FindByTechnologyIdAndIsPublished", query = "SELECT ta.Id as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.IsPublished = true AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId) GROUP BY Id", resultClass = RadarEntity.class), @org.hibernate.annotations.NamedNativeQuery(name = "findAllNotOwnedByTechnologyIdAndIsPublished", query = "SELECT ta.Id as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId <> :radarUserId AND ta.IsPublished = true AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId) GROUP BY Id", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "public_FindNotOwnedByTechnologyIdAndIsPublished", query = "SELECT * FROM TechnologyAssessments ta WHERE ta.IsPublished = true AND ta.RadarUserId <> :radarUserId  AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "public_FindOwnedByTechnologyIdAndIsPublished", query = "SELECT * FROM TechnologyAssessments ta WHERE ta.IsPublished = true AND ta.RadarUserId = :radarUserId  AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "public_PublicFindCurrentRadarItemsByRadarTemplate", query = "select tai.id, tai.TechnologyAssessmentId, tai.TechnologyId, tai.Details, tai.RadarRingId, tai.ConfidenceFactor, tai.RadarCategoryId, tai.State from TechnologyAssessments ta,TechnologyAssessmentItems tai INNER JOIN (SELECT MAX(Id) as Id FROM TechnologyAssessmentItems GROUP BY TechnologyId) tai2 ON tai.Id = tai2.Id WHERE ta.RadarUserId = :radarUserId AND ta.IsPublished=true AND ta.RadarTemplateId = :radarTemplateId AND tai.TechnologyAssessmentId = ta.Id", resultClass = RadarItemEntity.class),


// History
        @org.hibernate.annotations.NamedNativeQuery(name = "history_FindAllByUserType", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.RadarTemplateId = :radarTemplateId", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "history_FindByTechnologyId", query = "SELECT ta.Id as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId) GROUP BY Id", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "history_FindAllByUserAndType", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.RadarTemplateId = :radarTemplateId", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "history_FindNotOwnedByTechnologyId", query = "SELECT * FROM TechnologyAssessments ta WHERE ta.RadarUserId <> :radarUserId  AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "history_FindOwnedByTechnologyId", query = "SELECT * FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId  AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "history_PublicFindCurrentRadarItemsByRadarTemplate", query = "select tai.id, tai.TechnologyAssessmentId, tai.TechnologyId, tai.Details, tai.RadarRingId, tai.ConfidenceFactor, tai.RadarCategoryId, tai.State from TechnologyAssessments ta,TechnologyAssessmentItems tai INNER JOIN (SELECT MAX(Id) as Id FROM TechnologyAssessmentItems GROUP BY TechnologyId) tai2 ON tai.Id = tai2.Id WHERE ta.RadarUserId = :radarUserId AND ta.RadarTemplateId = :radarTemplateId AND tai.TechnologyAssessmentId = ta.Id", resultClass = RadarItemEntity.class),



        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUser", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.IsPublished = :isPublished)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllMostRecentByUserAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE (ta.id, ta.RadarTemplateId) IN (SELECT MAX(ta2.Id), ta2.RadarTemplateId FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.IsPublished = :isPublished GROUP BY ta2.RadarTemplateId)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserRadarId", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.Id = :radarId)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserRadarIdAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.Id = :radarId AND ta2.IsPublished = :isPublished)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserType", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.RadarTemplateId = :radarTemplateId)", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllByUserTypeAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.RadarUserId = :radarUserId AND ta.RadarTemplateId = :radarTemplateId AND ta.IsPublished = :isPublished", resultClass = RadarEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserTypeAndIsPublished", query = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,  ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished ,ta.IsLocked as IsLocked FROM TechnologyAssessments ta WHERE ta.id = (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId AND ta2.RadarTemplateId = :radarTemplateId AND ta2.IsPublished = :isPublished)", resultClass = RadarEntity.class),


        @org.hibernate.annotations.NamedNativeQuery(name = "findByTechnologyIdAndIsPublished", query = "SELECT * FROM TechnologyAssessments ta WHERE ta.IsPublished = :isPublished AND ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = :technologyId)", resultClass = RadarEntity.class),
    }
)
public class RadarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name="RadarTemplateId", nullable = false)
    private RadarTemplateEntity radarTemplate;

    public RadarEntity() {

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

    public RadarTemplateEntity getRadarTemplate() { return this.radarTemplate;}
    public void setRadarTemplate(RadarTemplateEntity value) { this.radarTemplate = value;}
}
