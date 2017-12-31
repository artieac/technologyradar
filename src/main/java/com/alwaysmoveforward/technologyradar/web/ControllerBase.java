package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.security.Auth0TokenAuthentication;
import com.alwaysmoveforward.technologyradar.services.RadarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by acorrea on 12/26/2017.
 */
public class ControllerBase
{
    @Autowired
    public RadarUserService radarUserService;

    private RadarUser currentUser = null;

    public RadarUser getCurrentUser()
    {
        if(this.currentUser == null)
        {
//            Auth0TokenAuthentication tokenAuth = (Auth0TokenAuthentication)SecurityContextHolder.getContext().getAuthentication();
//
//            if(tokenAuth!=null)
//            {
//                this.currentUser = this.radarUserService.findByAuthenticationId(tokenAuth.getIdentifier());
//            }
            this.currentUser = new RadarUser();
            this.currentUser.setId(2l);
        }

        return this.currentUser;
    }
}
