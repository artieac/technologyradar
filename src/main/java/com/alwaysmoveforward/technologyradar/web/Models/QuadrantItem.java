package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessmentItem;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by acorrea on 10/20/2016.
 */
public class QuadrantItem
{
    private PolarCoords polarCoords;
    private RadarStatePresentation radarState;
    private TechnologyAssessmentItem assessmentItem;

    public QuadrantItem(Integer quadrantVisualStart, RadarStatePresentation radarState, TechnologyAssessmentItem assessmentItem)
    {
        this.assessmentItem = assessmentItem;
        this.radarState = radarState;
        this.polarCoords = new PolarCoords(this.radarState.getArcStart()  + (this.radarState.getArcWidth() / 2), quadrantVisualStart);
    }


    @JsonProperty()
    public String getName() { return this.assessmentItem.getTechnology().getName();}

    @JsonProperty(value="pc")
    public PolarCoords getPolarCoords() { return this.polarCoords;}

    @JsonProperty
    public String getMovement() { return "c";}

    @JsonProperty
    public String getUrl()
    {
        return "/technology/" + this.assessmentItem.getTechnology().getId();
    }
}
