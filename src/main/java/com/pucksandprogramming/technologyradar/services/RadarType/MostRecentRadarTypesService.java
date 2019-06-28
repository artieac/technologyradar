package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarInstanceRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarType.RadarTypeServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MostRecentRadarTypesService extends RadarTypeServiceBase
{
    @Autowired
    public MostRecentRadarTypesService(RadarTypeRepository radarTypeRepository,
                                       RadarUserRepository radarUserRepository,
                                       RadarInstanceRepository radarInstanceRepository)
    {
        super(radarTypeRepository, radarUserRepository, radarInstanceRepository);
    }

    public List<RadarType> findAllByUserId(RadarUser currentUser, Long userId)
    {
        return this.radarTypeRepository.findMostRecentRadarTypesForUser(userId);
    }

    public List<RadarType> findAllByUserAndRadarType(RadarUser currentUser, Long userId, Long radarTypeId)
    {
        return this.radarTypeRepository.findHistoryForRadarType(userId, radarTypeId);
    }

    public List<RadarType> findTypesByPublishedRadars(RadarUser owningUser)
    {
        return  this.radarTypeRepository.findMostRecentByUserAndIsPublished(owningUser.getId());
    }

    public List<RadarType> findSharedRadarTypes(RadarUser currentUser, Long userToExclude)
    {
        return this.radarTypeRepository.findAllSharedRadarTypesExcludeOwned(userToExclude);
    }
}
