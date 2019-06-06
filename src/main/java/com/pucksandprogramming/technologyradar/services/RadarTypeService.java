package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.Auth0Repository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<RadarType> findAll(boolean publishedOnly)
    {
        return this.findAll(publishedOnly);
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

    public List<RadarType> findOthersRadarTypes(Long userId)
    {
        return this.radarTypeRepository.findOthersRadarTypes(userId);
    }

    public RadarType update(RadarType radarType, RadarUser currentUser, Long ownerId)
    {
        if(radarType!=null)
        {
            RadarUser targetUser = radarUserRepository.findOne(ownerId);
            if (targetUser != null && targetUser.getId() == currentUser.getId())
            {
                radarType.setCreator(targetUser);
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
}
