package com.pucksandprogramming.technologyradar.web.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarItem;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Created by acorrea on 10/20/2016.
 */
public class Quadrant {
    private RadarCategory radarCategory;
    private Integer leftLocation;
    private Integer topLocation;
    private Integer quadrantStart;
    private Integer quandrantSize;
    private Hashtable<Long, List<QuadrantItem>> quadrantRings;

    public Quadrant(Integer quadrantStart, Integer quandrantSize, RadarCategory radarCategory, Integer diagramWidth, Integer diagramHeight, Integer marginTop, Integer marginLeft, List<RadarRingPresentation> radarRings) {
        this.radarCategory = radarCategory;
        this.quadrantStart = quadrantStart;
        this.quandrantSize = quandrantSize;
        this.calculateLeftAndTop(diagramWidth, diagramHeight, marginLeft, marginTop);
        this.quadrantRings = new Hashtable<Long, List<QuadrantItem>>();

        for(int i = 0; i < radarRings.size(); i++) {
            this.quadrantRings.put(radarRings.get(i).getRadarRing().getId(), new ArrayList<QuadrantItem>());
        }
    }

    private void calculateLeftAndTop(Integer diagramWidth, Integer diagramHeight, Integer marginLeft, Integer marginTop) {
        switch(this.quadrantStart) {
            case 0:
                topLocation = marginTop;
                leftLocation = diagramWidth - marginLeft;
                break;
            case 90:
                topLocation = marginTop;
                leftLocation = 45;
                break;
            case 180:
                topLocation = diagramHeight / 2 + marginTop;
                leftLocation = 45;
                break;
            case 270:
                topLocation = diagramHeight / 2 + marginTop;
                leftLocation = diagramWidth - marginLeft;
                break;
        }
    }

    @JsonProperty(value="quadrant")
    public String getName() { return this.radarCategory.getName();}

    @JsonProperty(value="left")
    public Integer getLeftLocation() { return this.leftLocation;}

    @JsonProperty(value="top")
    public Integer getTopLocation() { return this.topLocation;}

    @JsonProperty
    public String getColor() { return this.radarCategory.getColor();}

    @JsonProperty(value="items")
    public List<QuadrantItem> getItems() {
        List<QuadrantItem> retVal = new ArrayList<QuadrantItem>();

        Set<Long> categoryIds  = this.quadrantRings.keySet();

        for(Long categoryId : categoryIds) {
            retVal.addAll(this.quadrantRings.get(categoryId));
        }

        return retVal;
    }

    @JsonIgnore
    public void addItem(RadarRingPresentation radarRing, RadarItem assessmentItem) {
        if(this.quadrantRings == null) {
            this.quadrantRings = new Hashtable<Long, List<QuadrantItem>>();
        }

        QuadrantItem newItem = new QuadrantItem(this.quadrantStart, radarRing, assessmentItem);

        List<QuadrantItem> stateItems = this.quadrantRings.get(radarRing.getRadarRing().getId());

        if(stateItems == null) {
            stateItems = new ArrayList<QuadrantItem>();
        }

        stateItems.add(newItem);
        this.quadrantRings.put(radarRing.getRadarRing().getId(), stateItems);
    }

    public void evenlyDistributeItems() {
        Set<Long> stateIds  = this.quadrantRings.keySet();

        for(Long stateId : stateIds) {
            List<QuadrantItem> quadrantItems = this.quadrantRings.get(stateId);

            int distanceBetween = quandrantSize / (quadrantItems.size() + 2);
            int currentLocation = this.quadrantStart + distanceBetween;

            // recalculate the quadrant start
            for(int i = 0; i < quadrantItems.size(); i++) {
                currentLocation += distanceBetween;
                quadrantItems.get(i).getPolarCoords().setAngular(currentLocation);
            }
        }
    }
}
