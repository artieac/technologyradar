package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/20/2016.
 */
public class DiagramPresentation
{
    private Integer height;
    private Integer width;
    private Integer rangeWidth;
    private Long radarId;
    private String radarName;
    private Date assessmentDate;
    private List<Quadrant> quadrantList;
    private List<RadarRingPresentation> radarArcs;
    private List<RadarRing> radarRings;

    public DiagramPresentation(Integer height, Integer width, Integer rangeWidth)
    {
        this.height = height;
        this.width = width;
        this.rangeWidth = rangeWidth;
        this.quadrantList = new ArrayList<Quadrant>();
    }

    @JsonProperty
    public Integer getHeight() {
        return this.height;
    }

    @JsonProperty
    public Integer getWidth() {
        return this.width;
    }

    @JsonProperty
    public Integer getRangeWidth() {
        return this.rangeWidth;
    }

    public Long getRadarId() {
        return this.radarId;
    }

    public String getRadarName() {
        return this.radarName;
    }

    public Date getAssessmentDate() {
        return this.assessmentDate;
    }

    @JsonProperty
    public List<RadarRingPresentation> getRadarArcs() {
        return this.radarArcs;
    }

    @JsonProperty
    public List<Quadrant> getQuadrants() {
        return this.quadrantList;
    }

    @JsonProperty
    List<RadarRing> getRadarRings() {
        return this.radarRings;
    }

    public void setRadarInstanceDetails(Radar radarInstance)
    {
        this.radarId = radarInstance.getId();
        this.radarName = radarInstance.getName();
        this.assessmentDate = radarInstance.getAssessmentDate();
    }

    public void sddRadarArc(RadarRing radarRing)
    {
        if(this.radarArcs == null)
        {
            this.radarArcs = new ArrayList<RadarRingPresentation>();
        }

        if(this.radarRings == null)
        {
            this.radarRings = new ArrayList<RadarRing>();
        }

        Integer arcStart = this.radarArcs.size() * this.rangeWidth;
        if(arcStart > 0)
        {
            arcStart++;
        }

        RadarRingPresentation newItem = new RadarRingPresentation(radarRing, arcStart, this.rangeWidth);
        this.radarArcs.add(newItem);
        this.radarRings.add(radarRing);
    }

    public void addRadarArcs(Iterable<RadarRing> radarRings)
    {
        for(RadarRing radarRing : radarRings)
        {
            this.sddRadarArc(radarRing);
        }
    }
}
