package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "TechnologyAssessmentItems")
public class RadarItemEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @JoinColumn(name = "technologyAssessmentId", referencedColumnName = "id")
    @ManyToOne(optional=false)
    private RadarInstanceEntity radarInstance;

    @ManyToOne
    @JoinColumn(name = "TechnologyId", nullable=false)
    private TechnologyEntity technology;

    @Column(name = "Details", nullable = false, length = 4096)
    private String details;

    @ManyToOne
    @JoinColumn(name = "RadarRingId", nullable=false)
    private RadarRingEntity radarRing;

    @ManyToOne
    @JoinColumn(name = "RadarCategoryId", nullable=false)
    private RadarCategoryEntity radarCategory;

    @Column(name = "ConfidenceFactor", nullable = false)
    private Integer confidenceFactor;

    public RadarItemEntity()
    {

    }

    public Long getId(){ return id;}
    public void setId(Long value){ this.id = value;}

    public RadarInstanceEntity getRadarInstance() { return this.radarInstance;}
    public void setRadarInstance(RadarInstanceEntity value) { this.radarInstance = value;}

    public TechnologyEntity getTechnology() { return this.technology;}
    public void setTechnology(TechnologyEntity value) { this.technology = value;}

    public String getDetails() { return this.details;}
    public void setDetails(String value) { this.details = value;}

    public RadarRingEntity getRadarRing() { return this.radarRing;}
    public void setRadarRing(RadarRingEntity value) { this.radarRing = value;}

    public RadarCategoryEntity getRadarCategory() { return this.radarCategory;}
    public void setRadarCategory(RadarCategoryEntity value) { this.radarCategory = value;}

    public Integer getConfidenceFactor() { return this.confidenceFactor;}
    public void setConfidenceFactor(Integer value) { this.confidenceFactor = value;}
}
