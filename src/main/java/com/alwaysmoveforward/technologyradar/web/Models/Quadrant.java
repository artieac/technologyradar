package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarCategory;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessmentItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private List<QuadrantItem> items;
    private Hashtable<Long, List<QuadrantItem>> quadrantStates;

    public Quadrant(RadarCategory radarCategory, Integer quadrantStart, Integer diagramWidth, Integer diagramHeight)
    {
        this.radarCategory = radarCategory;
        this.quadrantStart = quadrantStart;
        this.calculateLeftAndTop(diagramWidth, diagramHeight);
        this.quadrantStates = new Hashtable<Long, List<QuadrantItem>>();
    }

    private void calculateLeftAndTop(Integer diagramWidth, Integer diagramHeight)
    {
        switch(this.quadrantStart)
        {
            case 0:
                topLocation = 18;
                leftLocation = 45;
                break;
            case 90:
                topLocation = 18;
                leftLocation = diagramWidth - 200 + 30;
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

        Set<Long> categoryIds  = this.quadrantStates.keySet();

        for(Long categoryId : categoryIds)
        {
            retVal.addAll(this.quadrantStates.get(categoryId));
        }

        return retVal;
    }

    @JsonIgnore
    public void addItem(RadarStatePresentation radarState, TechnologyAssessmentItem assessmentItem)
    {
        if(this.quadrantStates == null)
        {
            this.quadrantStates = new Hashtable<Long, List<QuadrantItem>>();
        }

        QuadrantItem newItem = new QuadrantItem(this.quadrantStart, radarState, assessmentItem);

        List<QuadrantItem> stateItems = this.quadrantStates.get(radarState.getRadarState().getId());

        if(stateItems == null)
        {
            stateItems = new ArrayList<QuadrantItem>();
        }

        stateItems.add(newItem);
        this.quadrantStates.put(radarState.getRadarState().getId(), stateItems);

        Set<Long> stateIds  = this.quadrantStates.keySet();

        for(Long stateId : stateIds)
        {
            List<QuadrantItem> quadrantItems = this.quadrantStates.get(stateId);

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

    public void defaultEmptyQuadrantStates(List<RadarStatePresentation> states, Technology defaultTechnology){
        if(this.quadrantStates == null)
        {
            this.quadrantStates = new Hashtable<Long, List<QuadrantItem>>();
        }

        for(RadarStatePresentation radarState : states)
        {
            List<QuadrantItem> stateItems = this.quadrantStates.get(radarState.getRadarState().getId());

            if(stateItems==null)
            {
                stateItems = new ArrayList<>();
            }

            if (stateItems.size() == 0)
            {
                TechnologyAssessmentItem nothingItem = new TechnologyAssessmentItem();
                nothingItem.setTechnology(defaultTechnology);
                nothingItem.setRadarState(radarState.getRadarState());
                nothingItem.setRadarCategory(this.radarCategory);
                this.addItem(radarState, nothingItem);
            }
        }
    }
}
