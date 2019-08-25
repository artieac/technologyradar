package com.pucksandprogramming.technologyradar.domainmodel;

import java.util.ArrayList;
import java.util.List;

public class RadarCategorySet
{
    private Long id;
    private String name;
    private String description;
    private RadarUser radarUser;
    List<RadarCategory> radarCategories;

    public RadarCategorySet()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getDescription() { return this.description;}
    public void setDescription(String value) { this.description = value;}

    public RadarUser getRadarUser(){ return this.radarUser;}
    public void setRadarUser(RadarUser value){ this.radarUser = value;}

    public List<RadarCategory> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarCategory> value) { this.radarCategories = value;}

    public void addRadarCategory(RadarCategory radarCategory)
    {
        if(radarCategory != null)
        {
            if(this.radarCategories == null)
            {
                this.radarCategories = new ArrayList<RadarCategory>();
            }

            this.radarCategories.add(radarCategory);
        }
    }

    public boolean hasRadarCategory(RadarCategory radarCategory)
    {
        boolean retVal = false;

        if(radarCategory != null && this.radarCategories != null)
        {
            for(RadarCategory listItem : this.radarCategories)
            {
                if(listItem.getId() == radarCategory.getId())
                {
                    retVal = true;
                    break;
                }
            }
        }

        return retVal;
    }

    public void removeRadarCategory(Long radarCategoryId)
    {
        for(int i = 0; i < this.radarCategories.size(); i++)
        {
            if(this.radarCategories.get(i).getId()==radarCategoryId)
            {
                this.radarCategories.remove(i);
                break;
            }
        }
    }
}
