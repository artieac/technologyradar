package com.pucksandprogramming.technologyradar.services.RadarType;

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

public abstract class RadarTypeServiceBase
{
    protected RadarTypeRepository radarTypeRepository;
    protected RadarUserRepository radarUserRepository;
    protected RadarInstanceRepository radarInstanceRepository;

    public RadarTypeServiceBase(RadarTypeRepository radarTypeRepository, RadarUserRepository radarUserRepository, RadarInstanceRepository radarInstanceRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
        this.radarUserRepository = radarUserRepository;
        this.radarInstanceRepository = radarInstanceRepository;
    }

    public RadarType findOne(String radarTypeId, Long version)
    {
        return this.radarTypeRepository.findOne(radarTypeId, version);
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
}
