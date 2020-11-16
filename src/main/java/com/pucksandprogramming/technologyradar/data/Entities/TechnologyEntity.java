package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by acorrea on 10/18/2016.
 */
@Entity
@Table(name = "Technology")
@org.hibernate.annotations.NamedNativeQueries(
    {
            @org.hibernate.annotations.NamedNativeQuery(name = "findByRadarRingId", query = "select * from Technology t where t.Id IN (SELECT tai.TechnologyId FROM TechnologyAssessmentItems tai WHERE tai.RadarRingId = :radarRingId)", resultClass = TechnologyEntity.class),
            @org.hibernate.annotations.NamedNativeQuery(name = "findByRadarCategoryId", query = "select * from Technology t where t.Id IN (SELECT tai.TechnologyId FROM TechnologyAssessmentItems tai WHERE tai.RadarCategoryId = :radarCategoryId)", resultClass = TechnologyEntity.class),
            @org.hibernate.annotations.NamedNativeQuery(name = "findByNameAndRadarRingId", query = "SELECT * FROM Technology t WHERE t.Name LIKE :technologyName AND t.Id IN (SELECT tai.TechnologyId FROM TechnologyAssessmentItems tai WHERE tai.RadarRingId = :radarRingId)", resultClass = TechnologyEntity.class),
            @org.hibernate.annotations.NamedNativeQuery(name = "findByNameAndRadarCategoryId", query = "SELECT * FROM Technology t WHERE t.Name LIKE :technologyName AND t.Id IN (SELECT tai.TechnologyId FROM TechnologyAssessmentItems tai WHERE tai.RadarCategoryId = :radarCategoryId)", resultClass = TechnologyEntity.class),
            @org.hibernate.annotations.NamedNativeQuery(name = "findByRadarRingIdAndRadarCategoryId", query = "SELECT * FROM Technology t WHERE t.Id IN (SELECT tai.TechnologyId FROM TechnologyAssessmentItems tai WHERE tai.RadarCategoryId = :radarCategoryId AND tai.RadarRingId = :radarRingId)", resultClass = TechnologyEntity.class),
            @org.hibernate.annotations.NamedNativeQuery(name = "findByNameRadarRingIdAndRadarCategoryId", query = "SELECT * FROM Technology t WHERE t.Name LIKE :technologyName AND t.Id IN (SELECT tai.TechnologyId FROM TechnologyAssessmentItems tai WHERE tai.RadarCategoryId = :radarCategoryId AND tai.RadarRingId = :radarRingId)", resultClass = TechnologyEntity.class)
    }
)
public class TechnologyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @Column(name = "CreateDate", nullable = false)
    private Date createDate;

    @Column(name = "Creator", nullable = false, length = 255)
    private String creator;

    @Column(name = "Url", nullable = false, length = 255)
    private String url;

    public TechnologyEntity()
    {
        this.createDate = new Date();
    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public Date getCreateDate() { return this.createDate;}
    public void setCreateDate(Date value) { this.createDate = value;}

    public String getCreator() { return this.creator;}
    public void setCreator(String value) { this.creator = value;}

    public String getUrl() { return this.url;}
    public void setUrl(String value) { this.url = value;}
}

