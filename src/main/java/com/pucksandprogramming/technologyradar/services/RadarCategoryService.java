package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarCategorySetRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarRingSetRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RadarCategoryService extends ServiceBase
{
    RadarCategorySetRepository radarCategorySetRepository;

    public RadarCategoryService(RadarUserRepository radarUserRepository, RadarCategorySetRepository radarCategorySetRepository)
    {
        super(radarUserRepository);

        this.radarCategorySetRepository = radarCategorySetRepository;
    }

    public List<RadarCategorySet> findByUserId(Long userId)
    {
        return this.radarCategorySetRepository.findByUserId(userId);
    }

    public RadarCategorySet update(RadarCategorySet radarCategorySetUpdates, Long ownerId)
    {
        RadarCategorySet retVal = null;

        // first maek sure it can even save at ll
        if(radarCategorySetUpdates != null && radarCategorySetUpdates.getRadarCategories() != null && radarCategorySetUpdates.getRadarCategories().size() > 0)
        {
            boolean canSave = false;

            RadarUser dataOwner = this.getRadarUserRepository().findOne(ownerId);

            if(radarCategorySetUpdates.getId()!=null)
            {
                retVal = this.radarCategorySetRepository.findOne(radarCategorySetUpdates.getId());
            }

            if(retVal==null)
            {
                // trying to add one, make sure they have room in their max amount
                List<RadarCategorySet> userRadarRingSets = this.radarCategorySetRepository.findByUserId(ownerId);

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
                radarCategorySetUpdates.setRadarUser(dataOwner);
                retVal = this.radarCategorySetRepository.save(radarCategorySetUpdates);
            }
        }

        return retVal;
    }

    public boolean deleteRadarType(Long userId, Long radarCategorySetId)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(dataOwner!=null)
        {
            RadarCategorySet foundItem = this.radarCategorySetRepository.findOne(radarCategorySetId);

            if(foundItem.getRadarUser().getId() == userId &&
                    (this.getAuthenticatedUser().getUserId()==foundItem.getRadarUser().getId() ||
                            this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())))
            {
                List<Radar> userRadars = null;//this.fullRadarRepository.findByUserTypeAndVersion(userId, radarTypeId, radarTypeVersion);

                if(userRadars.size()==0)
                {
                    this.radarCategorySetRepository.delete(foundItem);
                    retVal = true;
                }
                else
                {
                    this.radarCategorySetRepository.delete((Iterable)userRadars);

//                    foundItem.setState(RadarType.State_InActive);
                    this.radarCategorySetRepository.save(foundItem);
                    retVal = true;
                }
            }
        }
        return retVal;
    }
}
