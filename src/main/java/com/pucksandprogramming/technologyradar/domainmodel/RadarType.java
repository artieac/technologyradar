package com.pucksandprogramming.technologyradar.domainmodel;

import com.pucksandprogramming.technologyradar.data.Entities.RadarCategoryEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RadarType {

    private Long id;
    private String name;
    private List<RadarRing> radarRings;
    private List<RadarCategory> radarCategories;
    private RadarUser creator;
    private boolean isPublished;

    public RadarType()
    {
        this.isPublished = false;
    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public List<RadarRing> getRadarRings()
    {
        this.radarRings.sort(Comparator.comparing(RadarRing::getDisplayOrder));
        return this.radarRings;
    }

    public void setRadarRings(List<RadarRing> value) { this.radarRings = value;}

    public List<RadarCategory> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarCategory> value) { this.radarCategories = value;}

    public RadarUser getCreator() { return this.creator;}
    public void setCreator(RadarUser value) { this.creator = value;}

    public boolean getIsPublished() { return this.isPublished;}
    public void setIsPublished(boolean value){ this.isPublished = value;}

    public void addRadarRing(RadarRing radarRing)
    {
        if(radarRing != null)
        {
            if(this.radarRings == null)
            {
                this.radarRings = new ArrayList<RadarRing>();
            }

            this.radarRings.add(radarRing);
        }
    }

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

    public boolean hasRadarRing(RadarRing radarRing)
    {
        boolean retVal = false;

        if(radarRing != null)
        {
            retVal = this.radarRings.contains(radarRing);
        }

        return retVal;
    }

    public boolean hasRadarCategory(RadarCategory radarCategory)
    {
        boolean retVal = false;

        if(radarCategory != null)
        {
            retVal = this.radarCategories.contains(radarCategory);
        }

        return retVal;
    }
}
