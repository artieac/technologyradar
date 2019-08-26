package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;

import java.util.ArrayList;
import java.util.List;

public class RadarTemplateViewModel
{
    private Long id;
    private String name;
    private boolean isPublished;
    private Long radarUserId;
    private String description;
    private List<RadarTemplateDetailMessage> radarRings;
    private List<RadarTemplateDetailMessage> radarCategories;

    public RadarTemplateViewModel()
    {

    }

    public RadarTemplateViewModel(RadarTemplate radarTemplate)
    {
        this.Initialize(radarTemplate);
    }

    public void Initialize(RadarTemplate radarTemplate)
    {
        if(radarTemplate != null)
        {
            this.id = radarTemplate.getId();
            this.name = radarTemplate.getName();
            this.isPublished = radarTemplate.getIsPublished();
            this.radarUserId = radarTemplate.getRadarUser().getId();
            this.radarRings = new ArrayList<RadarTemplateDetailMessage>();

            for(int i = 0; i < radarTemplate.getRadarRings().size(); i++)
            {
                RadarTemplateDetailMessage newItem = new RadarTemplateDetailMessage(radarTemplate.getRadarRings().get(i));
                this.radarRings.add(newItem);
            }

            this.radarCategories = new ArrayList<RadarTemplateDetailMessage>();

            for(int i = 0; i < radarTemplate.getRadarCategories().size(); i++)
            {
                RadarTemplateDetailMessage newItem = new RadarTemplateDetailMessage(radarTemplate.getRadarCategories().get(i));
                this.radarCategories.add(newItem);
            }

            this.description = radarTemplate.getDescription();
        }
    }

    public RadarTemplate ConvertToRadarTemplate()
    {
        RadarTemplate retVal = new RadarTemplate();

        retVal.setId(this.getId());
        retVal.setName(this.getName());
        retVal.setIsPublished(this.getIsPublished());

        for(RadarTemplateDetailMessage detailMessage : this.radarRings)
        {
            retVal.addRadarRing(detailMessage.ConvertToRadarRing());
        }

        for(RadarTemplateDetailMessage detailMessage : this.radarCategories)
        {
            retVal.addRadarCategory(detailMessage.ConvertToRadarCategory());
        }

        retVal.setDescription(this.description);

        return retVal;
    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public boolean getIsPublished() { return this.isPublished;}
    public void setIsPublished(boolean value) { this.isPublished = value;}

    public Long getRadarUserId() { return this.radarUserId;}
    public void setRadarUserId(Long value) { this.radarUserId = value;}

    public List<RadarTemplateDetailMessage> getRadarRings() { return this.radarRings;}
    public void setRadarRings(List<RadarTemplateDetailMessage> value) { this.radarRings = value;}

    public List<RadarTemplateDetailMessage> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarTemplateDetailMessage> value) { this.radarCategories = value;}

    public String getDescription() { return this.description;}
    public void setDescription(String value) { this.description = value;}
}
