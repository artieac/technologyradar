package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.security.TechRadarSecurityPrincipal;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ServiceBase {
    private RadarUserRepository radarUserRepository;
    private AuthenticatedUser authenticatedUser = null;

    public ServiceBase(RadarUserRepository radarUserRepository)
    {
        this.radarUserRepository = radarUserRepository;
    }

    public RadarUserRepository getRadarUserRepository() { return this.radarUserRepository; }

    public AuthenticatedUser getAuthenticatedUser() {
        if(this.authenticatedUser == null) {
            if(SecurityContextHolder.getContext().getAuthentication() instanceof TechRadarSecurityPrincipal) {
                TechRadarSecurityPrincipal tokenAuth = (TechRadarSecurityPrincipal) SecurityContextHolder.getContext().getAuthentication();

                if (tokenAuth != null) {
                    authenticatedUser = tokenAuth.getAuthenticatedUser();
                }
            }
        }

        return this.authenticatedUser;
    }
}
