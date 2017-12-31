package com.alwaysmoveforward.technologyradar.services;

import com.alwaysmoveforward.technologyradar.data.repositories.Auth0Repository;
import com.alwaysmoveforward.technologyradar.data.repositories.RadarUserRepository;
import com.alwaysmoveforward.technologyradar.domainmodel.Auth0UserProfile;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.domainmodel.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by acorrea on 12/23/2017.
 */
@Component
public class RadarUserService
{
    private RadarUserRepository radarUserRepository;
    private Auth0Repository auth0Repository;

    @Autowired
    public RadarUserService(RadarUserRepository radarUserRepository, Auth0Repository auth0Repository)
    {
        this.radarUserRepository = radarUserRepository;
        this.auth0Repository = auth0Repository;
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

    public RadarUser addUser(String authenticationId, String authority, String issuer, String email, String nickname)
    {
        RadarUser retVal = null;

        if(!authenticationId.isEmpty())
        {
            retVal = this.radarUserRepository.findByAuthenticationId(authenticationId);

            if(retVal == null)
            {
                retVal = RadarUserService.createDefaultRadarUser();
                retVal.setAuthenticationId(authenticationId);
                retVal.setAuthority(authority);
                retVal.setIssuer(issuer);
                retVal.setEmail(email);
                retVal.setNickname(nickname);
                this.radarUserRepository.save(retVal);
            }
        }

        return retVal;
    }

    public Auth0UserProfile getUserProfile(String issuer, String accessToken)
    {
        String cleanedAccessToken = Base64.getEncoder().encodeToString(accessToken.getBytes(StandardCharsets.UTF_8));
        return this.auth0Repository.getUserProfile(issuer, accessToken);
    }
}
