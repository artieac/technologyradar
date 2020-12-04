package com.pucksandprogramming.technologyradar.services;

/**
 * Created by acorrea on 2/16/2018.
 */
public class RadarItemToBeAdded {
    private final Long radarCategoryId;
    private final Long radarRingId;
    private final Long technologyId;
    private final String details;
    private int confidenceFactor;

    public RadarItemToBeAdded(Long radarCategoryId, Long radarRingId, Long technologyId, String details, int confidenceFactor) {
        this.radarCategoryId = radarCategoryId;
        this.radarRingId = radarRingId;
        this.technologyId = technologyId;
        this.details = details;
        this.confidenceFactor = confidenceFactor;
    }

    public Long getRadarCategoryId() { return this.radarCategoryId;}
    public Long getRadarRingId() { return this.radarRingId;}
    public Long getTechnologyId() { return this.technologyId;}
    public String getDetails() { return this.details;}
    public int getConfidenceFactor() { return this.confidenceFactor;}
}
