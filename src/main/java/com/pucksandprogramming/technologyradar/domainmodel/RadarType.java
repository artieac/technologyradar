package com.pucksandprogramming.technologyradar.domainmodel;

import com.pucksandprogramming.technologyradar.data.Entities.RadarCategoryEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class RadarType {

    private String id;
    private Long version;
    private String name;
    private List<RadarRing> radarRings;
    private List<RadarCategory> radarCategories;
    private RadarUser radarUser;
    private boolean isPublished;
    private Calendar createDate;

    public RadarType()
    {
        this.isPublished = false;
    }

    public String getId(){ return this.id;}
    public void setId(String value){ this.id = value;}

    public Long getVersion() { return this.version;}
    public void setVersion(Long value) { this.version = value;}

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

    public RadarUser getRadarUser() { return this.radarUser;}
    public void setRadarUser(RadarUser value) { this.radarUser = value;}

    public boolean getIsPublished() { return this.isPublished;}
    public void setIsPublished(boolean value){ this.isPublished = value;}

    public Calendar getCreateDate() { return this.createDate;}
    public void setCreateDate(Calendar value){ this.createDate = value;}

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

        if(radarRing != null && this.radarRings != null)
        {
            for(RadarRing testRing : this.radarRings)
            {
                if(testRing.getId() == radarRing.getId())
                {
                    retVal = true;
                    break;
                }
            }
        }

        return retVal;
    }

    public boolean hasRadarCategory(RadarCategory radarCategory)
    {
        boolean retVal = false;

        if(radarCategory != null && this.radarCategories != null)
        {
            for(RadarCategory testCategory : this.radarCategories)
            {
                if(testCategory.getId() == radarCategory.getId())
                {
                    retVal = true;
                    break;
                }
            }
        }

        return retVal;
    }

    public void removeRadarRing(Long ringId)
    {
        for(int i = 0; i < this.radarRings.size(); i++)
        {
            if(this.radarRings.get(i).getId()==ringId)
            {
                this.radarRings.remove(i);
                break;
            }
        }
    }

    public boolean canUserUpdate(RadarUser radarUser)
    {
        boolean retVal = false;

        if (this.getRadarUser().getId() == radarUser.getId())
        {
            retVal = true;
        }

        return retVal;
    }
}
