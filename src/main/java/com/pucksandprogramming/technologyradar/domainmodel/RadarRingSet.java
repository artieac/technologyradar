package com.pucksandprogramming.technologyradar.domainmodel;

import com.pucksandprogramming.technologyradar.data.Entities.RadarUserEntity;

import java.util.ArrayList;
import java.util.List;

public class RadarRingSet
{
    private Long id;
    private String name;
    private String description;
    private RadarUser radarUser;
    List<RadarRing> radarRings;

    public RadarRingSet()
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

    public List<RadarRing> getRadarRings() { return this.radarRings;}
    public void setRadarRings(List<RadarRing> value) { this.radarRings = value;}

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
}
