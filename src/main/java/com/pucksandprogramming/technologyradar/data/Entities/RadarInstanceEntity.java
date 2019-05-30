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
    @JoinColumn(name = "RadarTypeId", nullable = false)
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

    public RadarInstanceEntity mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        RadarInstanceEntity retVal = new RadarInstanceEntity();
        retVal.setId(rs.getLong("Id"));
        retVal.setName(rs.getString("Name"));
        retVal.setAssessmentDate(rs.getDate("AssessmentDate"));
        return retVal;
    }
}
