package com.pucksandprogramming.technologyradar.web.Models;

public class RadarDrawingSurface
{
    public static final Integer DisplayHeight = 1000;
    public static final Integer DisplayWidth = 1100;
    public static final Integer RingDiameter = 360;
    public static final Integer MarginTop = 18;
    public static final Integer MarginLeft = 30;
    public static final Integer LeftBorder = 45;
    public static final Integer TopBorder = 200;

    private Integer height;
    private Integer width;
    private Integer top;
    private Integer left;
    private Integer marginTop;
    private Integer marginLeft;
    private Integer numberOfRings;
    private Integer numberOfCategories;
    private Integer radarRingDiameter;

    public RadarDrawingSurface(Integer numberOfArcs, Integer numberOfCategories)
    {
        this(numberOfArcs, numberOfCategories, RingDiameter, DisplayHeight, DisplayWidth, TopBorder, LeftBorder, MarginTop, MarginLeft);
    }

    public RadarDrawingSurface(Integer numberOfRings, Integer numberOfCategories, Integer radarRingDiameter, Integer height, Integer width, Integer top, Integer left, Integer marginTop, Integer marginLeft)
    {
        this.numberOfRings = numberOfRings;
        this.numberOfCategories = numberOfCategories;
        this.radarRingDiameter = radarRingDiameter;
        this.height = height;
        this.width = width;
        this.top = top;
        this.left = left;
        this.marginTop = marginTop;
        this.marginLeft = marginLeft;
    }

    public Integer getRadarArcWidth() { return this.radarRingDiameter / this.numberOfRings;}
    public Integer getRadarArcDegrees() { return this.radarRingDiameter / this.numberOfCategories;}

    public Integer getRingDiameter() { return this.radarRingDiameter;}
    public Integer getHeight() { return this.height;}
    public Integer getWidth() { return this.width;}
    public Integer getTop() { return this.top;}
    public Integer getLeft() { return this.left;}
    public Integer getMarginTop() { return this.marginTop;}
    public Integer getMarginLeft() { return this.marginLeft;}
}
