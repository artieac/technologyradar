package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategorySet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/20/2016.
 */
public class DiagramPresentation
{
    public static final Integer DiagramDisplayHeight = 900;
    public static final Integer DiagramDisplayWidth = 1100;
    public static final Integer RingDiameter = 360;
    public static final Integer MarginTop = 18;
    public static final Integer MarginLeft = 170;

    private Integer height;
    private Integer width;
    private Integer marginTop;
    private Integer marginLeft;
    private Integer ringDiameter;
    private Integer radarArcWidth;
    private Long radarId;
    private String radarName;
    private Date assessmentDate;
    private List<Quadrant> quadrantList;
    private List<RadarRingPresentation> radarArcs;
    private RadarRingSet radarRingSet;
    private RadarCategorySet radarCategorySet;

    public DiagramPresentation()
    {
        this(DiagramPresentation.DiagramDisplayHeight,
             DiagramPresentation.DiagramDisplayWidth,
             DiagramPresentation.RingDiameter,
             DiagramPresentation.MarginTop,
             DiagramPresentation.MarginLeft);
    }

    public DiagramPresentation(Integer height, Integer width, Integer ringDiameter, Integer marginTop, Integer marginLeft)
    {
        this.height = height;
        this.width = width;
        this.marginTop = marginTop;
        this.marginLeft = marginLeft;
        this.ringDiameter = ringDiameter;
        this.radarArcWidth = 0;
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
    public Integer getMarginTop() { return this.marginTop;}

    @JsonProperty
    public Integer getMarginLeft() { return this.marginLeft;}

    @JsonProperty
    public Integer getRingDiameter() {
        return this.ringDiameter;
    }

    @JsonProperty
    public Integer getRangeWidth() { return this.radarArcWidth; }

    public Long getRadarId() {
        return this.radarId;
    }

    public String getRadarName() {
        return this.radarName;
    }

    public Date getAssessmentDate() {
        return this.assessmentDate;
    }

    public RadarRingSetViewModel getRadarRingSet() { return new RadarRingSetViewModel(this.radarRingSet);}

    public RadarCategorySetViewModel getRadarCategorySet() { return new RadarCategorySetViewModel(this.radarCategorySet);}

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
        return this.radarRingSet.getRadarRings();
    }

    public void setRadarInstanceDetails(Radar radarInstance)
    {
        this.radarId = radarInstance.getId();
        this.radarName = radarInstance.getName();
        this.assessmentDate = new Date(radarInstance.getAssessmentDate().getDate());
        this.radarRingSet = radarInstance.getRadarRingSet();
        this.radarCategorySet = radarInstance.getRadarCategorySet();
    }

    public void sddRadarArc(RadarRing radarRing)
    {
        if(this.radarArcs == null)
        {
            this.radarArcs = new ArrayList<RadarRingPresentation>();
        }

        if(this.radarRingSet == null)
        {
            this.radarRingSet = new RadarRingSet();
        }

        Integer arcStart = this.radarArcs.size() * this.radarArcWidth;
        if(arcStart > 0)
        {
            arcStart++;
        }

        RadarRingPresentation newItem = new RadarRingPresentation(radarRing, arcStart, this.radarArcWidth);
        this.radarArcs.add(newItem);
        this.radarRingSet.addRadarRing(radarRing);
    }

    public void addRadarArcs(List<RadarRing> radarRings)
    {
        this.radarArcWidth = this.ringDiameter / radarRings.size();

        for(RadarRing radarRing : radarRings)
        {
            this.sddRadarArc(radarRing);
        }
    }
}
