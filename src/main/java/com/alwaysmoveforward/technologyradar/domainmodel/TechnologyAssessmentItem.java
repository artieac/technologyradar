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

    private RadarState radarState;

    private RadarCategory radarCategory;

    private Technology technology;

    public TechnologyAssessmentItem()
    {

    }

    public Long getId(){ return id;}

    public void setId(Long id){ this.id = id;}

    public String getDetails() { return details;}

    public void setDetails(String details) { this.details = details;}

    public String getAssessor() { return assessor;}

    public void setAssessor(String creator) { this.assessor = creator;}

    public RadarState getRadarState() { return this.radarState;}

    public void setRadarState(RadarState value) { this.radarState = value;}

    public RadarCategory getRadarCategory() { return this.radarCategory;}

    public void setRadarCategory(RadarCategory value) { this.radarCategory = value;}

    public Technology getTechnology() { return this.technology;}

    public void setTechnology(Technology value) { this.technology = value;}
}
