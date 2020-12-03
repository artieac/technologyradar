package com.pucksandprogramming.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by acorrea on 10/19/2016.
 */
public class RadarItem implements Serializable {
    public static final int State_New = 0;
    public static final int State_Changed = 1;
    public static final int State_Stable = 2;

    private Long id;
    private String details;
    private RadarRing radarRing;
    private Technology technology;
    private Integer confidenceFactor;
    private RadarCategory radarCategory;
    private Integer state;

    public RadarItem() {

    }

    public RadarItem(Long id, Technology targetTechnology, RadarCategory radarCategory, RadarRing radarRing, Integer confidenceLevel, String assessmentDetails) {
        this.setId(id);
        this.setDetails(assessmentDetails);
        this.setRadarCategory(radarCategory);
        this.setRadarRing(radarRing);
        this.setTechnology(targetTechnology);
        this.setConfidenceFactor(confidenceLevel);
        this.setState(State_New);
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

    public RadarCategory getRadarCategory() { return this.radarCategory;}
    public void setRadarCategory(RadarCategory value) { this.radarCategory = value;}

    public Integer getState() { return this.state;}
    public void setState(Integer value) { this.state = value;}

    public void determineState(Optional<RadarItem> previousRadarItem) {
        this.setState(State_Stable);

        if(!previousRadarItem.isPresent()) {
            this.setState(State_New);
        }
        else {
            if((this.radarCategory.getId()!=previousRadarItem.get().getRadarCategory().getId()) ||
                    this.radarRing.getId()!=previousRadarItem.get().getRadarRing().getId()) {
                this.setState(State_Changed);
            }
        }
    }
}

