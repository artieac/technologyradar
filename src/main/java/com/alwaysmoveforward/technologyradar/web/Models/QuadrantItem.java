package com.alwaysmoveforward.technologyradar.web.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarInstanceItem;

/**
 * Created by acorrea on 10/20/2016.
 */
public class QuadrantItem
{
    private PolarCoords polarCoords;
    private RadarRingPresentation radarRing;
    private RadarInstanceItem assessmentItem;

    public QuadrantItem(Integer quadrantVisualStart, RadarRingPresentation radarRing, RadarInstanceItem assessmentItem)
    {
        this.assessmentItem = assessmentItem;
        this.radarRing = radarRing;
        this.polarCoords = new PolarCoords(this.radarRing.calculateArcPlacement(assessmentItem.getConfidenceFactor()), quadrantVisualStart);
    }


    @JsonProperty
    public RadarInstanceItem getAssessmentItem() { return this.assessmentItem;}

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
