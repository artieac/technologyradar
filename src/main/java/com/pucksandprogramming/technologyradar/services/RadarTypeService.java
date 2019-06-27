package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.Auth0Repository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarInstanceRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
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
    private RadarInstanceRepository radarInstanceRepository;

    @Autowired
    public RadarTypeService(RadarTypeRepository radarTypeRepository, RadarUserRepository radarUserRepository, RadarInstanceRepository radarInstanceRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
        this.radarUserRepository = radarUserRepository;
        this.radarInstanceRepository = radarInstanceRepository;
    }

    public RadarType findOne(Long radarTypeId, Long version)
    {
        return this.radarTypeRepository.findOne(radarTypeId, version);
    }

    public List<RadarType> findAll(boolean publishedOnly)
    {
        return this.radarTypeRepository.findAllByIsPublished(publishedOnly);
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


}
