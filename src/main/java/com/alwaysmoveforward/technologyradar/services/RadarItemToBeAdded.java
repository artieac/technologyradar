package com.alwaysmoveforward.technologyradar.services;

/**
 * Created by acorrea on 2/16/2018.
 */
public class RadarItemToBeAdded
{
    private Long radarRingId;
    private Long technologyId;
    private String details;
    private int confidenceFactor;

    public RadarItemToBeAdded(Long radarRingId, Long technologyId, String details, int confidenceFactor)
    {
        this.radarRingId = radarRingId;
        this.technologyId = technologyId;
        this.details = details;
        this.confidenceFactor = confidenceFactor;
    }

    public Long getRadarRingId() { return this.radarRingId;}
    public Long getTechnologyId() { return this.technologyId;}
    public String getDetails() { return this.details;}
    public int getConfidenceFactor() { return this.confidenceFactor;}
}
