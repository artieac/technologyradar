package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.RadarInstanceRepository;
import com.pucksandprogramming.technologyradar.data.repositories.TechnologyRepository;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.RadarType.PublicRadarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PublicRadarInstanceService
{
    TechnologyRepository technologyRepository;
    RadarInstanceRepository radarInstanceRepository;

    @Autowired
    public PublicRadarInstanceService(TechnologyRepository technologyRepository, RadarInstanceRepository radarInstanceRepository)
    {
        this.technologyRepository = technologyRepository;
        this.radarInstanceRepository = radarInstanceRepository;
    }

    public List<Radar> getAllByTechnologyId(Long technologyId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null)
        {
            retVal = this.radarInstanceRepository.findMostRecentByTechnolgyId(foundItem.getId());
        }

        return retVal;
    }
}
