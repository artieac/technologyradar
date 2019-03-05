package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.TechnologyRepository;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 11/3/2016.
 */
@Component
public class TechnologyService
{
    private TechnologyRepository technologyRepository;

    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository)
    {
        this.technologyRepository = technologyRepository;
    }

    public static Technology createDefaultTechnology()
    {
        Technology retVal = new Technology();
        retVal.setId(new Long(0));
        retVal.setCreator("None");
        retVal.setName("Nothing");
        retVal.setUrl("http://www.foo.com");
        retVal.setCreateDate(new Date());
        return retVal;
    }

    public List<Technology> searchTechnology(String technologyName, Long radarRingId, Long radarCategoryId)
    {
        List<Technology> retVal = new ArrayList<>();

        boolean hasTechnologyName = false;
        boolean hasRadarRing = false;
        boolean hasRadarCategory = false;

        if(technologyName!=null && !technologyName.trim().isEmpty())
        {
            hasTechnologyName = true;
        }

        if(radarRingId != null && radarRingId > 0)
        {
            hasRadarRing = true;
        }

        if(radarCategoryId != null && radarCategoryId > 0)
        {
            hasRadarCategory = true;
        }

        if(hasTechnologyName && hasRadarRing && hasRadarCategory)
        {
            retVal = this.technologyRepository.findByNameRadarRingIdAndRadarCategoryId(technologyName, radarRingId, radarCategoryId);
        }
        else
        {
            if(hasTechnologyName)
            {
                if(hasRadarCategory)
                {
                    retVal = this.technologyRepository.findByNameAndRadarCategoryId(technologyName, radarCategoryId);
                }
                else
                {
                    if (hasRadarRing)
                    {
                        retVal = this.technologyRepository.findByNameAndRadarRingId(technologyName, radarRingId);
                    }
                    else
                    {
                        retVal = this.technologyRepository.searchByName(technologyName);
                    }
                }
            }
            else
            {
                if(hasRadarCategory && hasRadarRing)
                {
                    retVal = this.technologyRepository.findByRadarRingIdAndRadarCategoryId(radarRingId, radarCategoryId);
                }
                else
                {
                    if(hasRadarCategory)
                    {
                        retVal = this.technologyRepository.findByRadarCategoryId(radarCategoryId);
                    }
                    else
                    {
                        retVal = this.technologyRepository.findByRadarRingId(radarRingId);
                    }
                }
            }
        }

        return retVal;
    }
}
