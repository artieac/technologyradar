package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.Auth0TokenAuthentication;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by acorrea on 12/26/2017.
 */
public class ControllerBase
{
    @Value("${com.pucksandprogramming.securityEnabled}")
    private boolean securityEnabled;

    @Autowired
    private RadarUserService radarUserService;

    private RadarUser currentUser = null;

    public RadarUser getCurrentUser()
    {
        if(this.currentUser == null)
        {
            if(this.securityEnabled==true)
            {
                Auth0TokenAuthentication tokenAuth = (Auth0TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();

                if(tokenAuth!=null)
                {
                    this.currentUser = this.radarUserService.findByAuthenticationId(tokenAuth.getIdentifier());
                }
            }
            else
            {
                this.currentUser = new RadarUser();
                this.currentUser = this.radarUserService.findOne(1L);
            }
        }

        return this.currentUser;
    }

}
