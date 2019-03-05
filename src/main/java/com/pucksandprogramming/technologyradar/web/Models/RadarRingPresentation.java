package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;

/**
 * Created by acorrea on 10/20/2016.
 */
public class RadarRingPresentation
{
    // TBD make this dynamic
    private static final int arcWidthSegments = 10;

    private RadarRing radarRing;
    private Integer arcStart;
    private Integer arcWidth;

    public RadarRingPresentation(RadarRing radarRing, Integer arcStart, Integer arcWidth)
    {
        this.radarRing = radarRing;
        this.arcStart = arcStart;
        this.arcWidth = arcWidth;
    }

    public RadarRing getRadarRing() { return this.radarRing;}
    public Integer getArcStart() { return this.arcStart;}
    public Integer getArcWidth() { return this.arcWidth;}

    // This is arcWidthsegnments - the shift factor because we scale things from 1 - 10 with 10 being the highest, but if we do that the radial calculation goes further out
    // when what we really want is for the calculation to go closer in.  I supposed we could have stored it inverted, but Id rather have the data be a representation of what we want
    // and the presentation to determine how to present it
    public Integer calculateArcPlacement(Integer shiftFactor) { return this.arcStart + ((this.arcWidth / RadarRingPresentation.arcWidthSegments) * (RadarRingPresentation.arcWidthSegments - shiftFactor));}
}
