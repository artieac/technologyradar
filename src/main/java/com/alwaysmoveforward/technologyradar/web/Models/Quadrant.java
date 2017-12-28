package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarRing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarCategory;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessmentItem;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Created by acorrea on 10/20/2016.
 */
public class Quadrant
{
    private RadarCategory radarCategory;
    private Integer leftLocation;
    private Integer topLocation;
    private Integer quadrantStart;
    private Hashtable<Long, List<QuadrantItem>> quadrantRings;

    public Quadrant(RadarCategory radarCategory, Integer diagramWidth, Integer diagramHeight, List<RadarRingPresentation> radarRings)
    {
        this.radarCategory = radarCategory;
        this.quadrantStart = radarCategory.getQuadrantStart();
        this.calculateLeftAndTop(diagramWidth, diagramHeight);
        this.quadrantRings = new Hashtable<Long, List<QuadrantItem>>();

        for(int i = 0; i < radarRings.size(); i++)
        {
            this.quadrantRings.put(radarRings.get(i).getRadarRing().getId(), new ArrayList<QuadrantItem>());
        }
    }

    private void calculateLeftAndTop(Integer diagramWidth, Integer diagramHeight)
    {
        switch(this.quadrantStart)
        {
            case 0:
                topLocation = 18;
                leftLocation = diagramWidth - 200 + 30;
                break;
            case 90:
                topLocation = 18;
                leftLocation = 45;
                break;
            case 180:
                topLocation = diagramHeight / 2 + 18;
                leftLocation = 45;
                break;
            case 270:
                topLocation = diagramHeight / 2 + 18;
                leftLocation = diagramWidth - 200 + 30;
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
    public List<QuadrantItem> getItems()
    {
        List<QuadrantItem> retVal = new ArrayList<QuadrantItem>();

        Set<Long> categoryIds  = this.quadrantRings.keySet();

        for(Long categoryId : categoryIds)
        {
            retVal.addAll(this.quadrantRings.get(categoryId));
        }

        return retVal;
    }

    @JsonIgnore
    public void addItem(RadarRingPresentation radarRing, TechnologyAssessmentItem assessmentItem)
    {
        if(this.quadrantRings == null)
        {
            this.quadrantRings = new Hashtable<Long, List<QuadrantItem>>();
        }

        QuadrantItem newItem = new QuadrantItem(this.quadrantStart, radarRing, assessmentItem);

        List<QuadrantItem> stateItems = this.quadrantRings.get(radarRing.getRadarRing().getId());

        if(stateItems == null)
        {
            stateItems = new ArrayList<QuadrantItem>();
        }

        stateItems.add(newItem);
        this.quadrantRings.put(radarRing.getRadarRing().getId(), stateItems);
    }

    public void evenlyDistributeItems()
    {
        Set<Long> stateIds  = this.quadrantRings.keySet();

        for(Long stateId : stateIds)
        {
            List<QuadrantItem> quadrantItems = this.quadrantRings.get(stateId);

            int distanceBetween = 90 / (quadrantItems.size() + 2);
            int currentLocation = this.quadrantStart + distanceBetween;

            // recalculate the quadrant start
            for(int i = 0; i < quadrantItems.size(); i++)
            {
                currentLocation += distanceBetween;
                quadrantItems.get(i).getPolarCoords().setAngular(currentLocation);
            }
        }
    }
}
