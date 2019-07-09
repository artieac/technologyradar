package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MostRecentRadarTypesService extends RadarTypeServiceBase
{
    @Autowired
    public MostRecentRadarTypesService(RadarTypeRepository radarTypeRepository,
                                       RadarUserRepository radarUserRepository,
                                       RadarRepository radarRepository)
    {
        super(radarTypeRepository, radarUserRepository, radarRepository);
    }

    public List<RadarType> findAllByUserId(RadarUser currentUser, Long userId)
    {
        return this.radarTypeRepository.findMostRecentRadarTypesForUser(userId);
    }

    public List<RadarType> findAllByUserAndRadarType(RadarUser currentUser, Long userId, String radarTypeId)
    {
        return this.radarTypeRepository.findHistoryForRadarType(userId, radarTypeId);
    }

    public List<RadarType> findTypesByPublishedRadars(RadarUser owningUser)
    {
        if(owningUser==null)
        {
            return this.radarTypeRepository.findAllForPublishedRadars();
        }
        else
        {
            return this.radarTypeRepository.findMostRecentByUserAndIsPublished(owningUser.getId());
        }
    }

    public List<RadarType> findSharedRadarTypes(RadarUser currentUser, Long userToExclude)
    {
        return this.radarTypeRepository.findAllSharedRadarTypesExcludeOwned(userToExclude);
    }
}
