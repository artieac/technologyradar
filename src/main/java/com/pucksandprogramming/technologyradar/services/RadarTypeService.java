package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.Auth0Repository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RadarTypeService
{
    private RadarTypeRepository radarTypeRepository;
    private RadarUserRepository radarUserRepository;

    @Autowired
    public RadarTypeService(RadarTypeRepository radarTypeRepository, RadarUserRepository radarUserRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
        this.radarUserRepository = radarUserRepository;
    }

    public RadarType findOne(Long radarTypeId)
    {
        return this.radarTypeRepository.findOne(radarTypeId);
    }

    public List<RadarType> findAll(boolean publishedOnly)
    {
        return this.radarTypeRepository.findAllByIsPublished(publishedOnly);
    }

    public List<RadarType> findAllByUserId(Long userId, boolean includeAssociated)
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        retVal = this.radarTypeRepository.findAllByUserId(userId);

        if(includeAssociated==true)
        {
            retVal.addAll(this.findAllAssociatedRadarTypes(userId));
        }

        return retVal;
    }

    public List<RadarType> findAllAssociatedRadarTypes(Long userId)
    {
        return this.radarTypeRepository.findAllAssociatedRadarTypes(userId);
    }

    public List<RadarType> findAllSharedRadarTypesExcludeOwned(Long userId)
    {
        return this.radarTypeRepository.findAllSharedRadarTypesExcludeOwned(userId);
    }

    public List<RadarType> findOthersRadarTypes(Long userId)
    {
        return this.radarTypeRepository.findOthersRadarTypes(userId);
    }

    public List<RadarType> findAllForPublishedRadars(Long userId)
    {
        return this.radarTypeRepository.findAllForPublishedRadars(userId);
    }

    public RadarType update(RadarType radarType, RadarUser currentUser, Long ownerId)
    {
        if(radarType!=null)
        {
            RadarUser targetUser = radarUserRepository.findOne(ownerId);
            if (targetUser != null && targetUser.getId() == currentUser.getId())
            {
                radarType.setRadarUser(targetUser);
                radarType = this.radarTypeRepository.save(radarType);
            }
        }

        return radarType;
    }

    public boolean delete(Long radarTypeId, RadarUser currentUser, Long ownerId)
    {
        boolean retVal = true;

        RadarUser targetUser = radarUserRepository.findOne(ownerId);
        if (targetUser != null && targetUser.getId() == currentUser.getId())
        {
            this.radarTypeRepository.delete(radarTypeId);
        }
        else
        {
            retVal = false;
        }

        return retVal;
    }

    public boolean publishRadarType(RadarUser currentUser, Long radarTypeId, boolean shouldPublish)
    {
        boolean retVal = false;

        RadarType radarType = this.radarTypeRepository.findByRadarUaerIdAndId(currentUser.getId(), radarTypeId);

        if(radarType!=null)
        {
            if (radarType.getRadarUser().getId() == currentUser.getId())
            {
                radarType.setIsPublished(shouldPublish);
                this.radarTypeRepository.save(radarType);
                retVal = true;
            }
        }

        return retVal;
    }

    public boolean associatedRadarType(RadarUser currentUser, Long radarTypeId, boolean shouldAssociate)
    {
        boolean retVal = false;

        RadarType radarType = this.radarTypeRepository.findOne(radarTypeId);

        if(radarType!=null)
        {
            // don't allow a user to associate their own radar
            if (radarType.getRadarUser().getId() != currentUser.getId())
            {
                if(shouldAssociate==true)
                {
                    retVal = this.radarTypeRepository.saveAssociatedRadarType(currentUser, radarType);
                }
                else
                {
                    retVal = this.radarTypeRepository.deleteAssociatedRadarType(currentUser, radarType);
                }
            }
        }

        return retVal;
    }

}
