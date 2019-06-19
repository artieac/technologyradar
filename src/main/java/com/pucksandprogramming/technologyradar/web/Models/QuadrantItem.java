package com.pucksandprogramming.technologyradar.web.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pucksandprogramming.technologyradar.domainmodel.RadarItem;

/**
 * Created by acorrea on 10/20/2016.
 */
public class QuadrantItem
{
    private PolarCoords polarCoords;
    private RadarRingPresentation radarRing;
    private RadarItem assessmentItem;
    private String movement;

    public QuadrantItem(Integer quadrantVisualStart, RadarRingPresentation radarRing, RadarItem assessmentItem)
    {
        this.assessmentItem = assessmentItem;
        this.radarRing = radarRing;
        this.polarCoords = new PolarCoords(this.radarRing.calculateArcPlacement(assessmentItem.getConfidenceFactor()), quadrantVisualStart);

        switch(assessmentItem.getState())
        {
            case RadarItem.State_New:
                this.movement = "t";
                break;
            case RadarItem.State_Changed:
                this.movement = "t";
                break;
            default:
                this.movement = "c";
        }
    }


    @JsonProperty
    public RadarItem getAssessmentItem() { return this.assessmentItem;}

    @JsonProperty()
    public String getName() { return this.assessmentItem.getTechnology().getName();}

    @JsonProperty(value="pc")
    public PolarCoords getPolarCoords() { return this.polarCoords;}

    @JsonProperty
    public String getMovement() { return this.movement;}

    @JsonProperty
    public String getUrl()
    {
        return "/radarsubject/" + this.assessmentItem.getTechnology().getId();
    }
}
