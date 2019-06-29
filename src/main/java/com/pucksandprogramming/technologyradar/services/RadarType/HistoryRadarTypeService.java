package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarInstanceRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryRadarTypeService extends RadarTypeServiceBase
{
    @Autowired
    public HistoryRadarTypeService(RadarTypeRepository radarTypeRepository,
                                   RadarUserRepository radarUserRepository,
                                   RadarInstanceRepository radarInstanceRepository)
    {
        super(radarTypeRepository, radarUserRepository, radarInstanceRepository);
    }

    public List<RadarType> findAllByUserId(RadarUser currentUser, Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(currentUser!=null && currentUser.canSeeHistory()==true)
        {
            retVal = this.radarTypeRepository.findAllRadarTypeVersionsForUser(userId);
        }

        return retVal;
    }

    public List<RadarType> findAllByUserAndRadarType(RadarUser currentUser, Long userId, Long radarTypeId)
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        if(currentUser!=null && currentUser.canSeeHistory()==true)
        {
            retVal = this.radarTypeRepository.findHistoryForRadarType(userId, radarTypeId);
        }

        return retVal;
    }

    public List<RadarType> findTypesByPublishedRadars(RadarUser owningUser)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(owningUser != null && owningUser.canShareHistory())
        {
            retVal = this.radarTypeRepository.findAllTypesByUserAandPublishedRadars(owningUser.getId());
        }

        return retVal;
    }

    public List<RadarType> findSharedRadarTypes(RadarUser currentUser, Long userToExclude)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(currentUser != null && currentUser.canSeeHistory())
        {
            retVal = this.radarTypeRepository.findAllSharedRadarTypesExcludeOwned(userToExclude);
        }

        return retVal;
    }
}
