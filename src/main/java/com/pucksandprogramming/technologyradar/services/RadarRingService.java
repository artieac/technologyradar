package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarRingRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarRingSetRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RadarRingService extends ServiceBase
{
    RadarRingSetRepository radarRingSetRepository;

    public RadarRingService(RadarUserRepository radarUserRepository, RadarRingSetRepository radarRingSetRepository)
    {
        super(radarUserRepository);

        this.radarRingSetRepository = radarRingSetRepository;
    }

    public List<RadarRingSet> findByUserId(Long userId)
    {
        return this.radarRingSetRepository.findByUserId(userId);
    }

    public RadarRingSet update(RadarRingSet radarRingSetUpdates, Long ownerId)
    {
        RadarRingSet retVal = null;

        // first maek sure it can even save at ll
        if(radarRingSetUpdates != null && radarRingSetUpdates.getRadarRings() != null && radarRingSetUpdates.getRadarRings().size() > 0)
        {
            boolean canSave = false;

            RadarUser dataOwner = this.getRadarUserRepository().findOne(ownerId);

            if(radarRingSetUpdates.getId()!=null)
            {
                retVal = this.radarRingSetRepository.findOne(radarRingSetUpdates.getId());
            }

            if(retVal==null)
            {
                // trying to add one, make sure they have room in their max amount
                List<RadarRingSet> userRadarRingSets = this.radarRingSetRepository.findByUserId(ownerId);

                if(userRadarRingSets!=null && userRadarRingSets.size() < dataOwner.getUserType().getGrantValue(UserRights.AllowNRadarTypes))
                {
                    canSave = true;
                }
            }
            else
            {
                // trying to update an existing, make sure they can version
                if (dataOwner != null )//&&
//                        dataOwner.getId() == this.getAuthenticatedUser().getUserId())
                {
                    canSave = true;
                }
            }

            if(canSave==true)
            {
                radarRingSetUpdates.setRadarUser(dataOwner);
                retVal = this.radarRingSetRepository.save(radarRingSetUpdates);
            }
        }

        return retVal;
    }

    public boolean deleteRadarType(Long userId, Long radarRingSetId)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(dataOwner!=null)
        {
            RadarRingSet foundItem = this.radarRingSetRepository.findOne(radarRingSetId);

            if(foundItem.getRadarUser().getId() == userId &&
                    (this.getAuthenticatedUser().getUserId()==foundItem.getRadarUser().getId() ||
                            this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())))
            {
                List<Radar> userRadars = null;//this.fullRadarRepository.findByUserTypeAndVersion(userId, radarTypeId, radarTypeVersion);

                if(userRadars.size()==0)
                {
                    this.radarRingSetRepository.delete(foundItem);
                    retVal = true;
                }
                else
                {
                    this.radarRingSetRepository.delete((Iterable)userRadars);

//                    foundItem.setState(RadarType.State_InActive);
                    this.radarRingSetRepository.save(foundItem);
                    retVal = true;
                }
            }
        }
        return retVal;
    }
}
