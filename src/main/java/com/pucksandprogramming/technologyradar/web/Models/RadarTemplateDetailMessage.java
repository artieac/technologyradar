package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;

public class RadarTemplateDetailMessage
{
    private Long id;
    private String name;
    private String displayOption;

    public RadarTemplateDetailMessage()
    {
    }

    public RadarTemplateDetailMessage(RadarRing radarRing)
    {
        this.id = radarRing.getId();
        this.name = radarRing.getName();
        this.displayOption = radarRing.getDisplayOrder().toString();
    }

    public RadarTemplateDetailMessage(RadarCategory radarCategory)
    {
        this.id = radarCategory.getId();
        this.name = radarCategory.getName();
        this.displayOption = radarCategory.getColor();
    }

    public RadarRing ConvertToRadarRing()
    {
        RadarRing retVal = new RadarRing();
        retVal.setId(this.getId());
        retVal.setName(this.getName());
        retVal.setDisplayOrder(Long.parseLong(this.getDisplayOption()));

        return retVal;
    }

    public RadarCategory ConvertToRadarCategory()
    {
        RadarCategory retVal = new RadarCategory();
        retVal.setId(this.getId());
        retVal.setName(this.getName());
        retVal.setColor(this.getDisplayOption());

        return retVal;
    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getDisplayOption() { return this.displayOption;}
    public void setDisplayOption (String value) { this.displayOption = value;}
}

