package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;

/**
 * Created by acorrea on 10/19/2016.
 */
public class TechnologyAssessmentItem implements Serializable
{
    private Long id;

    private String details;

    private String assessor;

    private RadarRing radarRing;

    private Technology technology;

    private Integer confidenceFactor;

    public TechnologyAssessmentItem()
    {

    }

    public Long getId(){ return id;}

    public void setId(Long id){ this.id = id;}

    public String getDetails() { return details;}

    public void setDetails(String details) { this.details = details;}

    public String getAssessor() { return assessor;}

    public void setAssessor(String creator) { this.assessor = creator;}

    public RadarRing getRadarRing() { return this.radarRing;}

    public void setRadarRing(RadarRing value) { this.radarRing = value;}

    public Technology getTechnology() { return this.technology;}

    public void setTechnology(Technology value) { this.technology = value;}

    public Integer getConfidenceFactor() { return this.confidenceFactor;}

    public void setConfidenceFactor(Integer value) { this.confidenceFactor = value;}
}
