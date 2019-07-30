package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.Auth0Repository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.data.repositories.UserTypeRepository;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 * Created by acorrea on 12/23/2017.
 */
@Component
public class RadarUserService
{
    private RadarUserRepository radarUserRepository;
    private Auth0Repository auth0Repository;
    private UserTypeRepository userTypeRepository;

    static HashMap<Integer, UserType> userTypes;

    @Autowired
    public RadarUserService(RadarUserRepository radarUserRepository, Auth0Repository auth0Repository, UserTypeRepository userTypeRepository)
    {
        this.radarUserRepository = radarUserRepository;
        this.auth0Repository = auth0Repository;
        this.userTypeRepository = userTypeRepository;
    }

    public  HashMap<Integer, UserType> getUserTypes()
    {
        if(RadarUserService.userTypes==null)
        {
            RadarUserService.userTypes = new HashMap<>();

            Iterable<UserType> foundItems = this.userTypeRepository.findAll();

            for(UserType userType : foundItems)
            {
                RadarUserService.userTypes.put(userType.getId(), userType);
            }
        }

        return RadarUserService.userTypes;
    }

    public RadarUser createDefaultRadarUser()
    {
        RadarUser retVal = new RadarUser();
        retVal.setId(new Long(0));
        retVal.setAuthenticationId("");
        retVal.setRoleId(Role.RoleType_User);
        retVal.setUserType(RadarUserService.userTypes.get(UserType.Free));
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

    public List<RadarUser> getAllUsers(RadarUser currentUser)
    {
        List<RadarUser> retVal = new ArrayList<>();

        if(currentUser != null)
        {
            Role userRole = Role.createRole(currentUser.getRoleId());

            if(userRole.getId()==Role.RoleType_Admin)
            {
                retVal = this.radarUserRepository.findAllList();
            }
        }

        return retVal;
    }

    public RadarUser addUser(String authenticationId, String authority, String issuer, String email, String nickname, String name)
    {
        RadarUser retVal = null;

        if(!authenticationId.isEmpty())
        {
            retVal = this.radarUserRepository.findByAuthenticationId(authenticationId);

            if(retVal == null)
            {
                retVal = this.createDefaultRadarUser();
                retVal.setAuthenticationId(authenticationId);
                retVal.setAuthority(authority);
                retVal.setIssuer(issuer);
                retVal.setEmail(email);
                retVal.setNickname(nickname);
                retVal.setName(name);
                retVal = this.radarUserRepository.save(retVal);
            }
        }

        return retVal;
    }

    public RadarUser updateUser(RadarUser radarUser)
    {
        RadarUser retVal = null;

        if(radarUser!=null)
        {
            retVal = this.radarUserRepository.save(radarUser);
        }

        return retVal;
    }

    public Auth0UserProfile getUserProfile(String issuer, String accessToken)
    {
        String cleanedAccessToken = Base64.getEncoder().encodeToString(accessToken.getBytes(StandardCharsets.UTF_8));
        return this.auth0Repository.getUserProfile(issuer, accessToken);
    }
}
