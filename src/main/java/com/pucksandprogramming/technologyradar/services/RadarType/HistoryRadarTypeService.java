package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.UserRights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryRadarTypeService extends RadarTypeService
{
    RadarTypeAccessManager radarTypeAccessManager;

    @Autowired
    public HistoryRadarTypeService(RadarTypeRepository radarTypeRepository,
                                   RadarUserRepository radarUserRepository,
                                   RadarRepository radarRepository,
                                   RadarTypeAccessManager radarTypeAccessManager)
    {
        super(radarTypeRepository, radarUserRepository, radarRepository);

        this.radarTypeAccessManager = radarTypeAccessManager;
    }

    @Override
    public List<RadarType> findAllByUserId(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(this.radarTypeAccessManager.canViewAllRadarTypes(dataOwner))
        {
            retVal = this.radarTypeRepository.findAllRadarTypeVersionsForUser(userId);
        }

        return retVal;
    }

    @Override
    public List<RadarType> findAllByUserAndRadarType(Long userId, String radarTypeId)
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(this.radarTypeAccessManager.canViewAllRadarTypes(dataOwner))
        {
            retVal = this.radarTypeRepository.findHistoryForRadarType(userId, radarTypeId);
        }

        return retVal;
    }

    @Override
    public List<RadarType> findTypesByPublishedRadars(RadarUser owningUser)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(owningUser != null && owningUser.canShareRadarTypes())
        {
            retVal = this.radarTypeRepository.findAllTypesByUserAandPublishedRadars(owningUser.getId());
        }

        return retVal;
    }

    @Override
    public List<RadarType> findSharedRadarTypes(Long userToExclude)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(this.getAuthenticatedUser()!=null && this.getAuthenticatedUser().hasPrivilege(UserRights.CanViewHistory))
        {
            retVal = this.radarTypeRepository.findAllSharedRadarTypesExcludeOwned(userToExclude);
        }

        return retVal;
    }
}
