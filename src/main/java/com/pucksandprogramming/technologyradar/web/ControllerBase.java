package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.Auth0TokenAuthentication;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by acorrea on 12/26/2017.
 */
public class ControllerBase {
    @Value("${com.pucksandprogramming.securityEnabled}")
    private boolean securityEnabled;

    @Autowired
    private RadarUserService radarUserService;

    private RadarUser currentUser = null;
    private AuthenticatedUser authenticatedUser = null;

    public AuthenticatedUser getAuthenticatedUser() {
        if(this.authenticatedUser == null) {
            if(SecurityContextHolder.getContext().getAuthentication() instanceof Auth0TokenAuthentication) {
                Auth0TokenAuthentication tokenAuth = (Auth0TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();

                if (tokenAuth != null) {
                    authenticatedUser = tokenAuth.getAuthenticatedUser();
                }
                else {
                    if(this.securityEnabled==false) {
                        RadarUser radarUser = this.radarUserService.findOne(1L);
                        this.authenticatedUser= new AuthenticatedUser(radarUser);
                        Auth0TokenAuthentication oauthWrapper = new Auth0TokenAuthentication(this.authenticatedUser);
                        SecurityContextHolder.getContext().setAuthentication(oauthWrapper);
                    }
                }
            }
            else {
                if(this.securityEnabled==false) {
                    RadarUser radarUser = this.radarUserService.findOne(1L);
                    this.authenticatedUser= new AuthenticatedUser(radarUser);
                    Auth0TokenAuthentication oauthWrapper = new Auth0TokenAuthentication(this.authenticatedUser);
                    SecurityContextHolder.getContext().setAuthentication(oauthWrapper);
                }
            }
        }

        return this.authenticatedUser;
    }

    public RadarUser getCurrentUser() {
        if(this.currentUser == null) {
             if(this.getAuthenticatedUser()!=null) {
                 this.currentUser = this.radarUserService.findOne(this.getAuthenticatedUser().getUserId());
             }
        }

        return this.currentUser;
    }


    @ModelAttribute("currentUserId")
    public Long getCurrentUserId() {
        Long retVal = -1L;

        if(this.getCurrentUser()!=null) {
            retVal = this.getCurrentUser().getId();
        }

        return retVal;
    }

}
