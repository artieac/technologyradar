package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;

import java.util.ArrayList;
import java.util.List;

public class RadarTypeMessage {
    private Long id;
    private String name;
    public List<RadarTypeDetailMessage> radarRings;
    public List<RadarTypeDetailMessage> radarCategories;

    public RadarTypeMessage()
    {

    }

    public RadarTypeMessage(RadarType radarType)
    {
        this.Initialize(radarType);
    }

    public void Initialize(RadarType radarType)
    {
        if(radarType != null)
        {
            this.id = radarType.getId();
            this.name = radarType.getName();

            this.radarRings = new ArrayList<RadarTypeDetailMessage>();

            for(int i = 0; i < radarType.getRadarRings().size(); i++)
            {
                RadarTypeDetailMessage newItem = new RadarTypeDetailMessage(radarType.getRadarRings().get(i));
                this.radarRings.add(newItem);
            }

            this.radarCategories = new ArrayList<RadarTypeDetailMessage>();

            for(int i = 0; i < radarType.getRadarCategories().size(); i++)
            {
                RadarTypeDetailMessage newItem = new RadarTypeDetailMessage(radarType.getRadarCategories().get(i));
                this.radarCategories.add(newItem);
            }
        }
    }

    public RadarType ConvertToRadarType()
    {
        RadarType retVal = new RadarType();

        retVal.setId(this.getId());
        retVal.setName(this.getName());

        for(RadarTypeDetailMessage detailMessage : this.radarRings)
        {
            retVal.addRadarRing(detailMessage.ConvertToRadarRing());
        }

        for(RadarTypeDetailMessage detailMessage : this.radarCategories)
        {
            retVal.addRadarCategory(detailMessage.ConvertToRadarCategory());
        }

        return retVal;
    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public List<RadarTypeDetailMessage> getRadarRings() { return this.radarRings;}
    public void setRadarRings(List<RadarTypeDetailMessage> value) { this.radarRings = value;}

    public List<RadarTypeDetailMessage> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarTypeDetailMessage> value) { this.radarCategories = value;}
}
