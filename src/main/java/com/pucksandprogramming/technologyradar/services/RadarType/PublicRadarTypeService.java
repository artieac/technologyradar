package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicRadarTypeService
{
    private RadarTypeRepository radarTypeRepository;

    @Autowired
    public PublicRadarTypeService(RadarTypeRepository radarTypeRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
    }

    public List<RadarType> findSharedRadarTypes()
    {
        return this.radarTypeRepository.findAllForPublishedRadars();
    }

    public List<RadarType> findSharedRadarTypesByUser(Long radarTypeOwner)
    {
        return this.radarTypeRepository.findAllForPublishedRadars(radarTypeOwner);
    }

    public List<RadarType> findSharedRadarTypesExcludeUser(Long userToExclude)
    {
        return this.radarTypeRepository.findAllSharedRadarTypesExcludeOwned(userToExclude);
    }
}
