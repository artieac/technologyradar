package com.pucksandprogramming.technologyradar.data.Entities;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RadarTemplates")
@org.hibernate.annotations.NamedNativeQueries
(
        /// NOTE ALL OF THESE WERE REQUIRED WHEN I HAD VERSIONING ON MY TEMPLATES.  NOW THAT I DON"T HAVE VERSIONING I NEED TO
        // REVISIT THESE AND MOVE WHAT I CAN TO THE DAO.
    {
        @org.hibernate.annotations.NamedNativeQuery(name = "public_findSharedRadarTemplates", query = "select * from RadarTemplates rt where rt.State = 1 AND rt.IsPublished = true", resultClass = RadarTemplateEntity.class),

// Owned Full History
        @org.hibernate.annotations.NamedNativeQuery(name = "owned_FindHistoryByRadarUserIdAndId", query = "SELECT * from RadarTemplates rt WHERE rt.RadarUserId = :radarUserId AND rt.Id = :radarTemplateId AND rt.State = 1 ORDER BY rt.Id, rt.CreateDate", resultClass = RadarTemplateEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "owned_FindHistorySharedRadarTemplatesExcludeOwned", query = "select * from RadarTemplates rt where rt.IsPublished = true AND rt.radarUserId <> :radarUserId AND rt.State = 1", resultClass = RadarTemplateEntity.class),

// Common
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllAssociated", query = "select * from RadarTemplates rt, AssociatedRadarTemplates art where art.RadarTemplateId = rt.Id and art.RadarUserId = :radarUserId", resultClass = RadarTemplateEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllForPublishedRadars", query = "select * from RadarTemplates rt where rt.Id IN(select distinct ta.RadarTemplateId FROM TechnologyAssessments ta where ta.IsPublished = true)", resultClass = RadarTemplateEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllForPublishedRadarsExcludeUser", query = "select * from RadarTemplates rt where rt.Id IN(select distinct ta.RadarTemplateId FROM TechnologyAssessments ta where ta.RadarUserId <> :radarUserId AND ta.IsPublished = true)", resultClass = RadarTemplateEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllOwnedWithRadars", query = "select * from RadarTemplates rt where rt.State = 1 AND rt.Id IN(select distinct ta.RadarTemplateId FROM TechnologyAssessments ta where ta.RadarUserId = :radarUserId)", resultClass = RadarTemplateEntity.class),

        @org.hibernate.annotations.NamedNativeQuery(name = "findByMaxId", query = "SELECT * FROM RadarTemplates where Id = (select max(Id) from RadarTemplates WHERE State =1);", resultClass = RadarTemplateEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findMostRecentByUserAndId", query = "SELECT * from RadarTemplates rt1 WHERE rt1.RadarUserId = :radarUserId AND rt1.Id = :radarTemplateId and State=1", resultClass = RadarTemplateEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findHistoryByRadarUserIdAndId", query = "SELECT * from RadarTemplates rt WHERE rt.RadarUserId = :radarUserId AND rt.Id = :radarTemplateId AND rt.State=1 ƒORDER BY rt.Id, rt.CreateDate", resultClass = RadarTemplateEntity.class),
     }
)
public class RadarTemplateEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @OneToMany(mappedBy = "radarTemplate", cascade = CascadeType.ALL)
    private List<RadarRingEntity> radarRings;

    @OneToMany(mappedBy = "radarTemplate", cascade = CascadeType.ALL)
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

    public RadarTemplateEntity()
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

