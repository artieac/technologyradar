package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategorySet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;

import java.util.ArrayList;
import java.util.List;

public class RadarCategorySetViewModel
{
    private Long id;
    private String name;
    private Long radarUserId;
    private String description;
    List<RadarTemplateElementViewModel> radarCategories;

    public RadarCategorySetViewModel()
    {

    }

    public RadarCategorySetViewModel(RadarCategorySet radarCategorySet)
    {
        this.Initialize(radarCategorySet);
    }

    public void Initialize(RadarCategorySet radarCategorySet)
    {
        if(radarCategorySet != null)
        {
            this.id = radarCategorySet.getId();
            this.name = radarCategorySet.getName();
            this.description = radarCategorySet.getDescription();
            this.radarUserId = radarCategorySet.getRadarUser().getId();
            this.radarCategories = new ArrayList<>();

            for(int i = 0; i < radarCategorySet.getRadarCategories().size(); i++)
            {
                RadarTemplateElementViewModel newItem = new RadarTemplateElementViewModel(radarCategorySet.getRadarCategories().get(i));
                this.radarCategories.add(newItem);
            }
        }
    }

    public RadarCategorySet ConvertToRadarCategorySet()
    {
        RadarCategorySet retVal = new RadarCategorySet();

        retVal.setId(this.getId());
        retVal.setName(this.getName());
        retVal.setDescription(this.description);

        for(RadarTemplateElementViewModel templateElement : this.radarCategories)
        {
            retVal.addRadarCategory(templateElement.ConvertToRadarCategory());
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

    public List<RadarTemplateElementViewModel> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarTemplateElementViewModel> value) { this.radarCategories = value;}
}
