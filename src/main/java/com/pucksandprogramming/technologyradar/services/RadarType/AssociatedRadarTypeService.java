package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssociatedRadarTypeService
{
    RadarTypeRepository radarTypeRepository;

    @Autowired
    public AssociatedRadarTypeService(RadarTypeRepository radarTypeRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
    }

    public List<RadarType> findAllAssociatedRadarTypes(Long userId)
    {
        return this.radarTypeRepository.findAllAssociatedRadarTypes(userId);
    }

    public boolean associatedRadarType(RadarUser currentUser, String radarTypeId, Long radarTypeVersion, boolean shouldAssociate)
    {
        boolean retVal = false;

        RadarType radarType = this.radarTypeRepository.findOne(radarTypeId, radarTypeVersion);

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
