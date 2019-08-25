package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarRingSetRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTemplateRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RadarTemplateService extends ServiceBase
{
    RadarTemplateRepository radarTemplateRepository;

    public RadarTemplateService(RadarUserRepository radarUserRepository, RadarTemplateRepository radarTemplateRepository)
    {
        super(radarUserRepository);

        this.radarTemplateRepository = radarTemplateRepository;
    }

    public List<RadarTemplate> findByUserId(Long userId)
    {
        return this.radarTemplateRepository.findByUserId(userId);
    }

    public RadarTemplate update(RadarTemplate radarTemplateUpdates, Long ownerId)
    {
        RadarTemplate retVal = null;

        // first maek sure it can even save at ll
        if(radarTemplateUpdates != null && radarTemplateUpdates.getRadarRingSet() != null && radarTemplateUpdates.getRadarCategorySet()!=null)
        {
            boolean canSave = false;

            RadarUser dataOwner = this.getRadarUserRepository().findOne(ownerId);

            if(radarTemplateUpdates.getId()!=null)
            {
                retVal = this.radarTemplateRepository.findOne(radarTemplateUpdates.getId());
            }

            if(retVal==null)
            {
                // trying to add one, make sure they have room in their max amount
                List<RadarTemplate> userTemplates = this.radarTemplateRepository.findByUserId(ownerId);

                if(userTemplates!=null && userTemplates.size() < dataOwner.getUserType().getGrantValue(UserRights.AllowNRadarTypes))
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
                radarTemplateUpdates.setRadarUser(dataOwner);
                retVal = this.radarTemplateRepository.save(radarTemplateUpdates);
            }
        }

        return retVal;
    }

    public boolean deleteRadarTemplate(Long userId, Long radarTemplateId)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(dataOwner!=null)
        {
            RadarTemplate foundItem = this.radarTemplateRepository.findOne(radarTemplateId);

            if(foundItem.getRadarUser().getId() == userId &&
                    (this.getAuthenticatedUser().getUserId()==foundItem.getRadarUser().getId() ||
                            this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())))
            {
                List<Radar> userRadars = null;//this.fullRadarRepository.findByUserTypeAndVersion(userId, radarTypeId, radarTypeVersion);

                if(userRadars.size()==0)
                {
                    this.radarTemplateRepository.delete(foundItem);
                    retVal = true;
                }
                else
                {
                    this.radarTemplateRepository.delete((Iterable)userRadars);

                    foundItem.setState(RadarTemplate.State_InActive);
                    this.radarTemplateRepository.save(foundItem);
                    retVal = true;
                }
            }
        }
        return retVal;
    }
}

