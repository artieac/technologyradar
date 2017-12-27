package com.alwaysmoveforward.technologyradar.data.Entities;

import javax.persistence.*;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "TechnologyAssessmentItems")
public class TechnologyAssessmentItemEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @JoinColumn(name = "technologyAssessmentId", referencedColumnName = "id")
    @ManyToOne(optional=false)
    private TechnologyAssessmentEntity technologyAssessment;

    @ManyToOne
    @JoinColumn(name = "TechnologyId", nullable=false)
    private TechnologyEntity technology;

    @Column(name = "Details", nullable = false, length = 4096)
    private String details;

    @ManyToOne
    @JoinColumn(name = "RadarRingId", nullable=false)
    private RadarRingEntity radarRing;

    @Column(name = "ConfidenceFactor", nullable = false)
    private Integer confidenceFactor;

    public TechnologyAssessmentItemEntity()
    {

    }

    public Long getId(){ return id;}

    public void setId(Long value){ this.id = value;}

    public TechnologyAssessmentEntity getTechnologyAssessment() { return this.technologyAssessment;}

    public void setTechnologyAssessment(TechnologyAssessmentEntity value) { this.technologyAssessment = value;}

    public TechnologyEntity getTechnology() { return this.technology;}

    public void setTechnology(TechnologyEntity value) { this.technology = value;}

    public String getDetails() { return this.details;}

    public void setDetails(String value) { this.details = value;}

    public RadarRingEntity getRadarRing() { return this.radarRing;}

    public void setRadarRing(RadarRingEntity value) { this.radarRing = value;}

    public Integer getConfidenceFactor() { return this.confidenceFactor;}

    public void setConfidenceFactor(Integer value) { this.confidenceFactor = value;}
}
