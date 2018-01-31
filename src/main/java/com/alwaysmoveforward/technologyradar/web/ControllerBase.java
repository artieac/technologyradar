package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.security.Auth0TokenAuthentication;
import com.alwaysmoveforward.technologyradar.services.RadarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by acorrea on 12/26/2017.
 */
public class ControllerBase
{
    @Value("${com.alwaysmoveforward.securityEnabled}")
    private boolean securityEnabled;

    @Autowired
    public RadarUserService radarUserService;

    private RadarUser currentUser = null;

    public RadarUser getCurrentUser()
    {
        if(this.currentUser == null)
        {
            if(this.securityEnabled==true)
            {
                Auth0TokenAuthentication tokenAuth = (Auth0TokenAuthentication)SecurityContextHolder.getContext().getAuthentication();

                if(tokenAuth!=null)
                {
                    this.currentUser = this.radarUserService.findByAuthenticationId(tokenAuth.getIdentifier());
                }
            }
            else
            {
                this.currentUser = new RadarUser();
                this.currentUser.setId(1L);
            }
        }

        return this.currentUser;
    }
}
