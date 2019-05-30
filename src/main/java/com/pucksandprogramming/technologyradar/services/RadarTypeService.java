package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.Auth0Repository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RadarTypeService {
    private RadarTypeRepository radarTypeRepository;
    private RadarUserRepository radarUserRepository;

    @Autowired
    public RadarTypeService(RadarTypeRepository radarTypeRepository, RadarUserRepository radarUserRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
        this.radarUserRepository = radarUserRepository;
    }

    public List<RadarType> findAllByUserId(Long userId){
        return this.radarTypeRepository.findAllByUserId(userId);
    }

    public RadarType update(RadarType radarType, RadarUser currentUser, Long ownerId) {

        if(radarType!=null) {

            RadarUser targetUser = radarUserRepository.findOne(ownerId);
            if (targetUser != null && targetUser.getId() == currentUser.getId()) {
                radarType.setRadarUser(targetUser);
                radarType = this.radarTypeRepository.save(radarType);
            }
        }

        return radarType;
    }

    public boolean delete(Long radarTypeId, RadarUser currentUser, Long ownerId) {
        boolean retVal = true;

        RadarUser targetUser = radarUserRepository.findOne(ownerId);
        if (targetUser != null && targetUser.getId() == currentUser.getId()) {
            this.radarTypeRepository.delete(radarTypeId);
        }
        else{
            retVal = false;
        }

        return retVal;
    }
}
