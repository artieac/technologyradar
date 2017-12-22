package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarState;

/**
 * Created by acorrea on 10/20/2016.
 */
public class RadarStatePresentation
{
    private RadarState radarState;
    private Integer arcStart;
    private Integer arcWidth;

    public RadarStatePresentation(RadarState radarState, Integer arcStart, Integer arcWidth)
    {
        this.radarState = radarState;
        this.arcStart = arcStart;
        this.arcWidth = arcWidth;
    }

    public RadarState getRadarState() { return this.radarState;}
    public Integer getArcStart() { return this.arcStart;}
    public Integer getArcWidth() { return this.arcWidth;}
    public Integer calculateMidPoint() { return this.arcStart + (this.arcWidth / 2);}
}
