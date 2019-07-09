package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.hibernate.id.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class RadarTypeServiceBase
{
    protected RadarTypeRepository radarTypeRepository;
    protected RadarUserRepository radarUserRepository;
    protected RadarRepository radarRepository;

    public RadarTypeServiceBase(RadarTypeRepository radarTypeRepository, RadarUserRepository radarUserRepository, RadarRepository radarRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
        this.radarUserRepository = radarUserRepository;
        this.radarRepository = radarRepository;
    }

    public RadarType findOne(String radarTypeId, Long version)
    {
        return this.radarTypeRepository.findOne(radarTypeId, version);
    }

    public RadarType update(RadarType radarTypeUpdates, RadarUser currentUser, Long ownerId)
    {
        RadarType retVal = null;

        // first maek sure it can even save at ll
        if(radarTypeUpdates != null && radarTypeUpdates.getRadarRings() != null && radarTypeUpdates.getRadarRings().size() > 0)
        {
            boolean canSave = false;

            RadarUser targetUser = radarUserRepository.findOne(ownerId);

            retVal = this.radarTypeRepository.findOne(radarTypeUpdates.getId(), radarTypeUpdates.getVersion());

            if(retVal==null)
            {
                canSave = true;
            }
            else
            {
                if (targetUser != null && targetUser.getId() == currentUser.getId() && retVal.canUserUpdate(currentUser))
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

    public abstract List<RadarType> findAllByUserId(RadarUser currentUser, Long userId);
    public abstract List<RadarType> findAllByUserAndRadarType(RadarUser currentUser, Long userId, String radarTypeId);
    public abstract List<RadarType> findTypesByPublishedRadars(RadarUser owningUser);
    public abstract List<RadarType> findSharedRadarTypes(RadarUser currentUser, Long userToExclude);

    public List<RadarType> findAssociatedRadarTypes(RadarUser currentUser)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(currentUser!=null)
        {
            retVal = this.radarTypeRepository.findAllAssociatedRadarTypes(currentUser.getId());
        }

        return retVal;
    }

    public boolean associateRadarType(RadarUser currentUser, Long radarUserId, String radarTypeId, Long radarTypeVersion, boolean shouldAssociate)
    {
        boolean retVal = false;

        RadarUser targetUser = this.radarUserRepository.findOne(radarUserId);
        RadarType targetRadarType = this.radarTypeRepository.findOne(radarTypeId, radarTypeVersion);

        if(targetUser != null &&  targetRadarType != null)
        {
            if (shouldAssociate == true)
            {
                if (currentUser.getId() == radarUserId && currentUser.getId() != targetRadarType.getRadarUser().getId())
                {
                    retVal = this.radarTypeRepository.saveAssociatedRadarType(targetUser, targetRadarType);
                }
            }
            else
            {
                retVal = this.radarTypeRepository.deleteAssociatedRadarType(targetUser, targetRadarType);
            }
        }

        return retVal;
    }
}
