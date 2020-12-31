package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.Auth0Repository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.data.repositories.UserTypeRepository;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by acorrea on 12/23/2017.
 */
@Component
@RequestScope
public class RadarUserService {
    private final RadarUserRepository radarUserRepository;
    private final Auth0Repository auth0Repository;
    private final UserTypeRepository userTypeRepository;

    static HashMap<Integer, UserType> userTypes = null;

    @Autowired
    public RadarUserService(RadarUserRepository radarUserRepository, Auth0Repository auth0Repository, UserTypeRepository userTypeRepository) {
        this.radarUserRepository = radarUserRepository;
        this.auth0Repository = auth0Repository;
        this.userTypeRepository = userTypeRepository;
    }

    public HashMap<Integer, UserType> getUserTypes() {
        if (RadarUserService.userTypes == null) {
            RadarUserService.userTypes = new HashMap<>();

            Iterable<UserType> foundItems = this.userTypeRepository.findAll();

            for (UserType userType : foundItems) {
                RadarUserService.userTypes.put(userType.getId(), userType);
            }
        }

        return RadarUserService.userTypes;
    }

    public RadarUser createDefaultRadarUser() {
        RadarUser retVal = new RadarUser();
        retVal.setId(new Long(0));
        retVal.setAuthenticationId("");
        retVal.setName("");
        retVal.setNickname("");
        retVal.setRoleId(Role.RoleType_User);
        retVal.setUserType(this.getUserTypes().get(UserType.Free));
        return retVal;
    }

    public Optional<RadarUser> findOne(Long radarUserId) {
        return this.radarUserRepository.findById(radarUserId);
    }

    public Optional<RadarUser> findByAuthenticationId(String authenticationId) {
        return this.radarUserRepository.findByAuthenticationId(authenticationId);
    }

    public List<RadarUser> getAllUsers(Optional<RadarUser> currentUser) {
        List<RadarUser> retVal = new ArrayList<>();

        if (currentUser.isPresent()) {
            if (currentUser.get().isInRole(Role.createAdminRole())) {
                retVal = this.radarUserRepository.findAllList();
            }
        }


        return retVal;
    }

    public Optional<RadarUser> addUser(String authenticationId, String authority, String issuer, String email, String nickname, String name) {
        if(!authenticationId.isEmpty()) {
            Optional<RadarUser> targetUser = this.radarUserRepository.findByAuthenticationId(authenticationId);

            if(!targetUser.isPresent()) {
                RadarUser newUser = this.createDefaultRadarUser();
                newUser.setAuthenticationId(authenticationId);
                newUser.setAuthority(authority);
                newUser.setIssuer(issuer);
                newUser.setEmail(email);

                if(nickname==null) {
                    newUser.setNickname(name);
                }
                else {
                    newUser.setNickname(nickname);
                }

                newUser.setName(name);
                return Optional.ofNullable(this.radarUserRepository.save(newUser));
            }
        }

        return Optional.empty();
    }

    public RadarUser updateUser(RadarUser radarUser) {
        RadarUser retVal = null;

        if(radarUser!=null) {
            retVal = this.radarUserRepository.save(radarUser);
        }

        return retVal;
    }

    public Optional<Auth0UserProfile> getUserProfile(String issuer, String accessToken) {
        String cleanedAccessToken = Base64.getEncoder().encodeToString(accessToken.getBytes(StandardCharsets.UTF_8));
        return this.auth0Repository.getUserProfile(issuer, accessToken);
    }
}
