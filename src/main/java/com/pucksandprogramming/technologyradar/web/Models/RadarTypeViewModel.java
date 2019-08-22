package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;

import java.util.ArrayList;
import java.util.List;

public class RadarTypeViewModel
{
    private String id;
    private Long version;
    private String name;
    private boolean isPublished;
    private Long radarUserId;
    private String description;
    private List<RadarTypeDetailMessage> radarRings;
    private List<RadarTypeDetailMessage> radarCategories;

    public RadarTypeViewModel()
    {

    }

    public RadarTypeViewModel(RadarType radarType)
    {
        this.Initialize(radarType);
    }

    public void Initialize(RadarType radarType)
    {
        if(radarType != null)
        {
            this.id = radarType.getId();
            this.version = radarType.getVersion();
            this.name = radarType.getName();
            this.isPublished = radarType.getIsPublished();
            this.radarUserId = radarType.getRadarUser().getId();
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

            this.description = radarType.getDescription();
        }
    }

    public RadarType ConvertToRadarType()
    {
        RadarType retVal = new RadarType();

        retVal.setId(this.getId());
        retVal.setVersion(this.getVersion());
        retVal.setName(this.getName());
        retVal.setIsPublished(this.getIsPublished());

        for(RadarTypeDetailMessage detailMessage : this.radarRings)
        {
            retVal.addRadarRing(detailMessage.ConvertToRadarRing());
        }

        for(RadarTypeDetailMessage detailMessage : this.radarCategories)
        {
            retVal.addRadarCategory(detailMessage.ConvertToRadarCategory());
        }

        retVal.setDescription(this.description);

        return retVal;
    }

    public String getId() { return this.id;}
    public void setId(String value) { this.id = value;}

    public Long getVersion() { return this.version;}
    public void setVersion(Long value) { this.version = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public boolean getIsPublished() { return this.isPublished;}
    public void setIsPublished(boolean value) { this.isPublished = value;}

    public Long getRadarUserId() { return this.radarUserId;}
    public void setRadarUserId(Long value) { this.radarUserId = value;}

    public List<RadarTypeDetailMessage> getRadarRings() { return this.radarRings;}
    public void setRadarRings(List<RadarTypeDetailMessage> value) { this.radarRings = value;}

    public List<RadarTypeDetailMessage> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarTypeDetailMessage> value) { this.radarCategories = value;}

    public String getDescription() { return this.description;}
    public void setDescription(String value) { this.description = value;}
}
