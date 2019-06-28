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

    public Technology findById(Long technologyId)
    {
        return this.technologyRepository.findOne(technologyId);
    }

    public List<Technology> searchTechnology(String technologyName, Long radarTypeId, Long radarRingId, Long radarCategoryId)
    {
        return this.technologyRepository.findByFilters(technologyName, radarTypeId, radarRingId, radarCategoryId);
    }
}
