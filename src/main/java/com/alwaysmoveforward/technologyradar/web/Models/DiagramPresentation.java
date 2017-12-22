package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarState;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/20/2016.
 */
public class DiagramPresentation
{
    private Integer height;
    private Integer width;
    private Integer rangeWidth;
    private List<Quadrant> quadrantList;
    private List<RadarStatePresentation> radarArcs;
    private List<RadarState> radarStates;

    public DiagramPresentation(Integer height, Integer width, Integer rangeWidth)
    {
        this.height = height;
        this.width = width;
        this.rangeWidth = rangeWidth;
        this.quadrantList = new ArrayList<Quadrant>();
    }

    @JsonProperty
    public Integer getHeight() { return this.height;}

    @JsonProperty
    public Integer getWidth() { return this.width;}

    @JsonProperty
    public Integer getRangeWidth() { return this.rangeWidth;}

    @JsonProperty
    public List<RadarStatePresentation> getRadarArcs() { return this.radarArcs;}

    @JsonProperty
    public List<Quadrant> getQuadrants() { return this.quadrantList;}

    @JsonProperty List<RadarState> getRadarStates() { return this.radarStates;}

    public void sddRadarArc(RadarState radarState)
    {
        if(this.radarArcs == null)
        {
            this.radarArcs = new ArrayList<RadarStatePresentation>();
        }

        if(this.radarStates == null)
        {
            this.radarStates = new ArrayList<RadarState>();
        }

        Integer arcStart = this.radarArcs.size() * this.rangeWidth;
        if(arcStart > 0)
        {
            arcStart++;
        }

        RadarStatePresentation newItem = new RadarStatePresentation(radarState, arcStart, this.rangeWidth);
        this.radarArcs.add(newItem);
        this.radarStates.add(radarState);
    }

    public void addRadarArcs(Iterable<RadarState> radarStates)
    {
        for(RadarState radarState : radarStates)
        {
            this.sddRadarArc(radarState);
        }
    }
}
