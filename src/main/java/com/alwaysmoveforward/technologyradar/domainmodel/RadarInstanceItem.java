package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;

/**
 * Created by acorrea on 10/19/2016.
 */
public class RadarInstanceItem implements Serializable
{
    private Long id;

    private String details;

    private RadarRing radarRing;

    private Technology technology;

    private Integer confidenceFactor;

    public RadarInstanceItem()
    {

    }

    public Long getId(){ return id;}

    public void setId(Long id){ this.id = id;}

    public String getDetails() { return details;}

    public void setDetails(String details) { this.details = details;}

    public RadarRing getRadarRing() { return this.radarRing;}

    public void setRadarRing(RadarRing value) { this.radarRing = value;}

    public Technology getTechnology() { return this.technology;}

    public void setTechnology(Technology value) { this.technology = value;}

    public Integer getConfidenceFactor() { return this.confidenceFactor;}

    public void setConfidenceFactor(Integer value) { this.confidenceFactor = value;}
}
