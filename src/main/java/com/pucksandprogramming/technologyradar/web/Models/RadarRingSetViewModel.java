package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;

import java.util.ArrayList;
import java.util.List;

public class RadarRingSetViewModel
{
    private Long id;
    private String name;
    private Long radarUserId;
    private String description;
    List<RadarTemplateElementViewModel> radarRings;

    public RadarRingSetViewModel()
    {

    }

    public RadarRingSetViewModel(RadarRingSet radarRingSet)
    {
        this.Initialize(radarRingSet);
    }

    public void Initialize(RadarRingSet radarRingSet)
    {
        if(radarRingSet != null)
        {
            this.id = radarRingSet.getId();
            this.name = radarRingSet.getName();
            this.description = radarRingSet.getDescription();
            this.radarUserId = radarRingSet.getRadarUser().getId();
            this.radarRings = new ArrayList<>();

            for(int i = 0; i < radarRingSet.getRadarRings().size(); i++)
            {
                RadarTemplateElementViewModel newItem = new RadarTemplateElementViewModel(radarRingSet.getRadarRings().get(i));
                this.radarRings.add(newItem);
            }
        }
    }

    public RadarRingSet ConvertToRadarRingSet()
    {
        RadarRingSet retVal = new RadarRingSet();

        retVal.setId(this.getId());
        retVal.setName(this.getName());
        retVal.setDescription(this.description);

        for(RadarTemplateElementViewModel templateElement : this.radarRings)
        {
            retVal.addRadarRing(templateElement.ConvertToRadarRing());
        }

        return retVal;
    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public Long getRadarUserId() { return this.radarUserId;}
    public void setRadarUserId(Long value) { this.radarUserId = value;}

    public String getDescription() { return this.description;}
    public void setDescription(String value) { this.description = value;}

    public List<RadarTemplateElementViewModel> getRadarRings() { return this.radarRings;}
    public void setRadarRings(List<RadarTemplateElementViewModel> value) { this.radarRings = value;}
}
