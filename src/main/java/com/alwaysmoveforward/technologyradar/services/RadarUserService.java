package com.alwaysmoveforward.technologyradar.services;

import com.alwaysmoveforward.technologyradar.data.repositories.RadarUserRepository;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.domainmodel.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by acorrea on 12/23/2017.
 */
@Component
public class RadarUserService {
    private RadarUserRepository radarUserRepository;

    @Autowired
    public RadarUserService(RadarUserRepository radarUserRepository)
    {
        this.radarUserRepository = radarUserRepository;
    }

    public static RadarUser createDefaultRadarUser()
    {
        RadarUser retVal = new RadarUser();
        retVal.setId(new Long(0));
        retVal.setAuthenticationId("");
        retVal.setRoleId(RoleType.User);
        return retVal;
    }

    public RadarUser findOne(Long radarUserId)
    {
        return this.radarUserRepository.findOne(radarUserId);
    }

    public RadarUser findByAuthenticationId(String authenticationId)
    {
        return this.radarUserRepository.findByAuthenticationId(authenticationId);
    }

    public RadarUser addUser(String authenticationId)
    {
        RadarUser retVal = null;

        if(!authenticationId.isEmpty())
        {
            retVal = this.radarUserRepository.findByAuthenticationId(authenticationId);

            if(retVal == null)
            {
                retVal = RadarUserService.createDefaultRadarUser();
                retVal.setAuthenticationId(authenticationId);
                this.radarUserRepository.save(retVal);
            }
        }

        return retVal;
    }
}
