package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.RadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.TechnologyRepository;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PublicRadarService
{
    TechnologyRepository technologyRepository;
    RadarRepository radarRepository;

    @Autowired
    public PublicRadarService(TechnologyRepository technologyRepository, RadarRepository radarRepository)
    {
        this.technologyRepository = technologyRepository;
        this.radarRepository = radarRepository;
    }

    public List<Radar> getAllByTechnologyId(Long technologyId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null)
        {
            retVal = this.radarRepository.findMostRecentByTechnolgyId(foundItem.getId());
        }

        return retVal;
    }
}
