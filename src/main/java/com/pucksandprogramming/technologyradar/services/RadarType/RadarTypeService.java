package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserRights;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class RadarTypeService extends ServiceBase
{
    protected RadarTypeRepository radarTypeRepository;
    protected RadarRepository radarRepository;

    public RadarTypeService(RadarTypeRepository radarTypeRepository, RadarUserRepository radarUserRepository, RadarRepository radarRepository)
    {
        super(radarUserRepository);

        this.radarTypeRepository = radarTypeRepository;
        this.radarRepository = radarRepository;
    }

    public  RadarType findOne(String radarTypeId, Long version)
    {
        return this.radarTypeRepository.findOne(radarTypeId, version);
    }

    public RadarType update(RadarType radarTypeUpdates, Long ownerId)
    {
        RadarType retVal = null;

        // first maek sure it can even save at ll
        if(radarTypeUpdates != null && radarTypeUpdates.getRadarRings() != null && radarTypeUpdates.getRadarRings().size() > 0)
        {
            boolean canSave = false;

            RadarUser targetUser = this.getRadarUserRepository().findOne(ownerId);

            retVal = this.radarTypeRepository.findOne(radarTypeUpdates.getId(), radarTypeUpdates.getVersion());

            if(retVal==null)
            {
                canSave = true;
            }
            else
            {
                if (targetUser != null &&
                    targetUser.getId() == this.getAuthenticatedUser().getUserId())
                {
                    canSave = true;
                }
            }

            if(canSave==true)
            {
                radarTypeUpdates.setRadarUser(targetUser);
                retVal = this.radarTypeRepository.save(radarTypeUpdates);
            }
        }

        return retVal;
    }

    public List<RadarType> findAllByUserId(Long userId)
    {
        return this.radarTypeRepository.findMostRecentRadarTypesForUser(userId);
    }

    public List<RadarType> findAllByUserAndRadarType(Long userId, String radarTypeId)
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

    public List<RadarType> findSharedRadarTypes(Long userToExclude)
    {
        List<RadarType> retVal = new ArrayList<>();

        // need to change this to just most recent shared and exclude the owner
        retVal = this.radarTypeRepository.findAllSharedRadarTypesExcludeOwned(userToExclude);

        return retVal;
    }
}
